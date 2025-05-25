package com.sky.service;

/* 
    @author nanchao 
    @date 2025/5/14
*/

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    //新增菜品
    void saveWithFlavor(DishDTO dishDTO);
    //菜品分页查询
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
    //菜品批量删除
    void deleteBatch(List<Long> ids);
    //根据Id查询菜品
    DishVO getByIdWithFlavor(Long id);
    //修改菜品和对应的口味
    void updateWithFlavor(DishDTO dishDTO);

    //根据分类的Id来查询菜品
    List<Dish> list(Long categoryId);
}
