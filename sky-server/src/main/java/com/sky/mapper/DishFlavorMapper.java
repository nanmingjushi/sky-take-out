package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/14
*/

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    //新增口味
    void insertBatch(List<DishFlavor> flavors);

    //根据菜品dish的Id删除对应口味表中的口味数据
    @Delete("delete from dish_flavor where dish_id=#{dishId}")
    void deleteByDishId(Long dishId);

    //根据菜品Id查询对应的口味数据
    @Select("select * from dish_flavor where dish_id=#{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
