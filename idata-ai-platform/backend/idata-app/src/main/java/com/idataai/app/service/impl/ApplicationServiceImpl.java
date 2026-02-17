package com.idataai.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.idataai.common.core.domain.PageResult;
import com.idataai.common.core.exception.BusinessException;
import com.idataai.common.core.exception.ErrorCode;
import com.idataai.app.domain.dto.ApplicationQueryDTO;
import com.idataai.app.domain.entity.Application;
import com.idataai.app.domain.entity.Category;
import com.idataai.app.domain.vo.ApplicationDetailVO;
import com.idataai.app.domain.vo.ApplicationVO;
import com.idataai.app.mapper.ApplicationMapper;
import com.idataai.app.mapper.CategoryMapper;
import com.idataai.app.service.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 应用服务实现类
 *
 * @author iDataAI
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements IApplicationService {

    private final ApplicationMapper applicationMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public PageResult<ApplicationVO> getApplicationList(ApplicationQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(queryDTO.getAppName()), Application::getAppName, queryDTO.getAppName())
                .eq(queryDTO.getCategoryId() != null, Application::getCategoryId, queryDTO.getCategoryId())
                .eq(StringUtils.hasText(queryDTO.getAppType()), Application::getAppType, queryDTO.getAppType())
                .eq(queryDTO.getIsHot() != null, Application::getIsHot, queryDTO.getIsHot())
                .eq(queryDTO.getIsRecommended() != null, Application::getIsRecommended, queryDTO.getIsRecommended())
                .eq(queryDTO.getStatus() != null, Application::getStatus, queryDTO.getStatus())
                .eq(Application::getStatus, 1) // 只查询已发布的应用
                .orderByDesc(Application::getSortOrder)
                .orderByDesc(Application::getUseCount);

        // 分页查询
        Page<Application> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        IPage<Application> appPage = applicationMapper.selectPage(page, wrapper);

        // 转换为VO
        List<ApplicationVO> voList = appPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return PageResult.build(voList, appPage.getTotal(), appPage.getCurrent(), appPage.getSize());
    }

    @Override
    public ApplicationDetailVO getApplicationDetail(Long id) {
        Application application = applicationMapper.selectById(id);
        if (application == null) {
            throw new BusinessException(ErrorCode.APP_NOT_FOUND, "应用不存在");
        }

        ApplicationDetailVO detailVO = new ApplicationDetailVO();
        BeanUtils.copyProperties(application, detailVO);

        // 获取分类名称
        if (application.getCategoryId() != null) {
            Category category = categoryMapper.selectById(application.getCategoryId());
            if (category != null) {
                detailVO.setCategoryName(category.getCategoryName());
            }
        }

        return detailVO;
    }

    @Override
    public List<ApplicationVO> getHotApplications() {
        // 查询热门应用
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getIsHot, true)
                .eq(Application::getStatus, 1)
                .orderByDesc(Application::getUseCount)
                .last("LIMIT 10");

        List<Application> applications = applicationMapper.selectList(wrapper);
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationVO> getRecommendedApplications() {
        // 查询推荐应用
        LambdaQueryWrapper<Application> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Application::getIsRecommended, true)
                .eq(Application::getStatus, 1)
                .orderByDesc(Application::getSortOrder)
                .last("LIMIT 10");

        List<Application> applications = applicationMapper.selectList(wrapper);
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public void increaseUseCount(Long id) {
        applicationMapper.increaseUseCount(id);
    }

    /**
     * 转换为VO对象
     */
    private ApplicationVO convertToVO(Application application) {
        ApplicationVO vo = new ApplicationVO();
        BeanUtils.copyProperties(application, vo);

        // 获取分类名称
        if (application.getCategoryId() != null) {
            Category category = categoryMapper.selectById(application.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getCategoryName());
            }
        }

        return vo;
    }
}
