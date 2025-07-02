package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/15
*/


import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


//菜品和套餐关联的中间表
@Mapper
public interface SetmealDishMapper {
    //根据菜品Id查询关联的套餐的Id
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    //保存套餐和菜品的关联关系
    void insertBatch(List<SetmealDish> setmealDishes);

    //根据套餐id删除套餐和菜品的关联关系
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);

    //根据套餐id查询套餐和菜品的关联关系
    @Select("select * from setmeal_dish where setmeal_id=#{setmealId};")
    List<SetmealDish> getBySetmealId(Long setmealId);

 }
