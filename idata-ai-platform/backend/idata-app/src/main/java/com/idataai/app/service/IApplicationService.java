package com.idataai.app.service;

import com.idataai.common.core.domain.PageResult;
import com.idataai.app.domain.dto.ApplicationQueryDTO;
import com.idataai.app.domain.vo.ApplicationDetailVO;
import com.idataai.app.domain.vo.ApplicationVO;

import java.util.List;

/**
 * 应用服务接口
 *
 * @author iDataAI
 */
public interface IApplicationService {

    /**
     * 分页查询应用列表
     *
     * @param queryDTO 查询条件
     * @return 应用列表
     */
    PageResult<ApplicationVO> getApplicationList(ApplicationQueryDTO queryDTO);

    /**
     * 获取应用详情
     *
     * @param id 应用ID
     * @return 应用详情
     */
    ApplicationDetailVO getApplicationDetail(Long id);

    /**
     * 获取热门应用
     *
     * @return 热门应用列表
     */
    List<ApplicationVO> getHotApplications();

    /**
     * 获取推荐应用
     *
     * @return 推荐应用列表
     */
    List<ApplicationVO> getRecommendedApplications();

    /**
     * 增加应用使用次数
     *
     * @param id 应用ID
     */
    void increaseUseCount(Long id);
}
