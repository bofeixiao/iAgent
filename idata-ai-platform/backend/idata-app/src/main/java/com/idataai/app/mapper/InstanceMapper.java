package com.idataai.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idataai.app.domain.entity.Instance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实例Mapper
 *
 * @author iDataAI
 */
@Mapper
public interface InstanceMapper extends BaseMapper<Instance> {
}
