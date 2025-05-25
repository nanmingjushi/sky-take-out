package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/13
*/

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface SetmealMapper {
    //根据分类的category_id 查询套餐的数量
    @Select("select count(*) from setmeal where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //向套餐表插入数据
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    //套餐分页查询
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //根据id查询套餐
    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);

    //根据Id删除套餐
    @Delete("delete from setmeal where id =#{setmealId}")
    void deleteById(Long setmealId);

    //修改套餐表
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);
}
