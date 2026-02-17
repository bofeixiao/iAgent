package com.idataai.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.exception.BusinessException;
import com.idataai.common.core.exception.ErrorCode;
import com.idataai.common.security.util.SecurityUtil;
import com.idataai.app.domain.dto.InstanceCreateDTO;
import com.idataai.app.domain.dto.InstanceQueryDTO;
import com.idataai.app.domain.entity.Application;
import com.idataai.app.domain.entity.Instance;
import com.idataai.app.domain.vo.InstanceVO;
import com.idataai.app.mapper.ApplicationMapper;
import com.idataai.app.mapper.InstanceMapper;
import com.idataai.app.mq.InstanceProducer;
import com.idataai.app.service.IInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 实例服务实现类
 *
 * @author iDataAI
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InstanceServiceImpl implements IInstanceService {

    private final InstanceMapper instanceMapper;
    private final ApplicationMapper applicationMapper;
    private final InstanceProducer instanceProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInstance(InstanceCreateDTO createDTO) {
        Long userId = SecurityUtil.getCurrentUserId();

        // 查询应用信息
        Application application = applicationMapper.selectById(createDTO.getAppId());
        if (application == null) {
            throw new BusinessException(ErrorCode.APP_NOT_FOUND, "应用不存在");
        }

        // TODO: 检查用户积分是否足够
        // TODO: 扣减用户积分

        // 创建实例
        Instance instance = new Instance();
        instance.setAppId(createDTO.getAppId());
        instance.setUserId(userId);
        instance.setInstanceName(createDTO.getInstanceName());
        instance.setInputParams(createDTO.getInputParams());
        instance.setStatus(0); // 待处理
        instance.setCreditsUsed(application.getCreditsCost());
        instance.setStartTime(LocalDateTime.now());
        instance.setTaskId(UUID.randomUUID().toString());

        instanceMapper.insert(instance);

        // 发送MQ消息
        Map<String, Object> message = new HashMap<>();
        message.put("instanceId", instance.getId());
        message.put("appId", instance.getAppId());
        message.put("appType", application.getAppType());
        message.put("inputParams", instance.getInputParams());
        message.put("taskId", instance.getTaskId());

        instanceProducer.sendAITask(message);

        log.info("创建实例成功，实例ID: {}, 任务ID: {}", instance.getId(), instance.getTaskId());

        return instance.getId();
    }

    @Override
    public PageResult<InstanceVO> getMyInstances(InstanceQueryDTO queryDTO) {
        Long userId = SecurityUtil.getCurrentUserId();

        // 构建查询条件
        LambdaQueryWrapper<Instance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Instance::getUserId, userId)
                .eq(queryDTO.getAppId() != null, Instance::getAppId, queryDTO.getAppId())
                .like(StringUtils.hasText(queryDTO.getInstanceName()), Instance::getInstanceName, queryDTO.getInstanceName())
                .eq(queryDTO.getStatus() != null, Instance::getStatus, queryDTO.getStatus())
                .ge(StringUtils.hasText(queryDTO.getStartDate()), Instance::getCreateTime, queryDTO.getStartDate())
                .le(StringUtils.hasText(queryDTO.getEndDate()), Instance::getCreateTime, queryDTO.getEndDate())
                .orderByDesc(Instance::getCreateTime);

        // 分页查询
        Page<Instance> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Instance> instancePage = instanceMapper.selectPage(page, wrapper);

        // 转换为VO
        List<InstanceVO> voList = instancePage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.build(voList, instancePage.getTotal(), instancePage.getCurrent(), instancePage.getSize());
    }

    @Override
    public InstanceVO getInstanceById(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();

        Instance instance = instanceMapper.selectById(id);
        if (instance == null) {
            throw new BusinessException(ErrorCode.INSTANCE_NOT_FOUND, "实例不存在");
        }

        // 检查权限
        if (!instance.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权查看该实例");
        }

        return convertToVO(instance);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInstance(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();

        Instance instance = instanceMapper.selectById(id);
        if (instance == null) {
            throw new BusinessException(ErrorCode.INSTANCE_NOT_FOUND, "实例不存在");
        }

        // 检查权限
        if (!instance.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权删除该实例");
        }

        // 逻辑删除
        instanceMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processInstance(Long instanceId) {
        Instance instance = instanceMapper.selectById(instanceId);
        if (instance == null) {
            log.error("实例不存在，实例ID: {}", instanceId);
            return;
        }

        try {
            // 更新状态为处理中
            instance.setStatus(1);
            instanceMapper.updateById(instance);

            // TODO: 调用AI服务处理

            // 模拟处理结果
            Map<String, Object> outputResult = new HashMap<>();
            outputResult.put("success", true);
            outputResult.put("message", "处理成功");
            outputResult.put("data", "这是AI生成的结果");

            // 更新实例状态为成功
            instance.setStatus(2);
            instance.setOutputResult(outputResult);
            instance.setCompleteTime(LocalDateTime.now());
            instanceMapper.updateById(instance);

            log.info("实例处理成功，实例ID: {}", instanceId);

        } catch (Exception e) {
            log.error("实例处理失败，实例ID: {}", instanceId, e);

            // 更新状态为失败
            instance.setStatus(3);
            instance.setFailReason(e.getMessage());
            instance.setCompleteTime(LocalDateTime.now());
            instanceMapper.updateById(instance);

            // TODO: 退还积分
        }
    }

    /**
     * 转换为VO对象
     */
    private InstanceVO convertToVO(Instance instance) {
        InstanceVO vo = new InstanceVO();
        BeanUtils.copyProperties(instance, vo);

        // 获取应用信息
        if (instance.getAppId() != null) {
            Application application = applicationMapper.selectById(instance.getAppId());
            if (application != null) {
                vo.setAppName(application.getAppName());
                vo.setAppIcon(application.getAppIcon());
            }
        }

        return vo;
    }
}
