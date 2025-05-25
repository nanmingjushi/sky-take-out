package com.sky.service;

/* 
    @author nanchao 
    @date 2025/5/17
*/

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    //新增套餐
    void saveWithDish(SetmealDTO setmealDTO);
    //套餐分页查询
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
    //批量删除套餐
    void deleteBatch(List<Long> ids);

    SetmealVO getByIdWithDish(Long id);

    void update(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);
}
