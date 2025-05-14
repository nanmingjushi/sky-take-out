package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/14
*/

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    //新增口味
    void insertBatch(List<DishFlavor> flavors);
}
