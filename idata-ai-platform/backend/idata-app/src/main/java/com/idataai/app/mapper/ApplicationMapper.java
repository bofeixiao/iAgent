package com.idataai.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idataai.app.domain.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 应用Mapper
 *
 * @author iDataAI
 */
@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {

    /**
     * 增加应用使用次数
     *
     * @param id 应用ID
     */
    void increaseUseCount(@Param("id") Long id);
}
