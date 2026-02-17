package com.idataai.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.idataai.app.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类Mapper
 *
 * @author iDataAI
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
