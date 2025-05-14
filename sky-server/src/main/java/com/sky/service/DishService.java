package com.sky.service;

/* 
    @author nanchao 
    @date 2025/5/14
*/

import com.sky.dto.DishDTO;

public interface DishService {

    //新增菜品
    void saveWithFlavor(DishDTO dishDTO);
}
