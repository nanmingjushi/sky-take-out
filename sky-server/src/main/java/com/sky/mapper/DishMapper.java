package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/13
*/

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    //根据分类的category_id 查询菜品的数量
    @Select("select count(*) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
