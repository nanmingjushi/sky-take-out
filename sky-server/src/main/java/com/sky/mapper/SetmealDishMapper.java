package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/15
*/


import org.apache.ibatis.annotations.Mapper;

import java.util.List;


//菜品和套餐关联的中间表
@Mapper
public interface SetmealDishMapper {
    //根据菜品Id查询关联的套餐的Id
    List<Long> getSetmealIdsDishIds(List<Long> dishIds);
}
