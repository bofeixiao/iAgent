package com.idataai.app.service;

import com.idataai.common.core.domain.PageResult;
import com.idataai.app.domain.dto.InstanceCreateDTO;
import com.idataai.app.domain.dto.InstanceQueryDTO;
import com.idataai.app.domain.vo.InstanceVO;

/**
 * 实例服务接口
 *
 * @author iDataAI
 */
public interface IInstanceService {

    /**
     * 创建实例
     *
     * @param createDTO 创建DTO
     * @return 实例ID
     */
    Long createInstance(InstanceCreateDTO createDTO);

    /**
     * 查询我的实例列表
     *
     * @param queryDTO 查询条件
     * @return 实例列表
     */
    PageResult<InstanceVO> getMyInstances(InstanceQueryDTO queryDTO);

    /**
     * 获取实例详情
     *
     * @param id 实例ID
     * @return 实例详情
     */
    InstanceVO getInstanceById(Long id);

    /**
     * 删除实例
     *
     * @param id 实例ID
     */
    void deleteInstance(Long id);

    /**
     * 处理实例任务
     *
     * @param instanceId 实例ID
     */
    void processInstance(Long instanceId);
}
