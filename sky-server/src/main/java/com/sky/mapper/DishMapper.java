package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/5/13
*/

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface DishMapper {
    //根据分类的category_id 查询菜品的数量
    @Select("select count(*) from dish where category_id=#{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //新增菜品
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    //菜品分类查询
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //根据主键查询菜品
    @Select("select * from dish where id =#{id}")
    Dish getById(Long id);

    //根据主键删除菜品数据
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    //根据Id修改菜品
    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);


    //根据分类的Id来查询菜品
    List<Dish> list(Dish dish);

    //根据套餐id查询菜品
    @Select("select dish.* from dish " +
            "join setmeal_dish on dish.id=setmeal_dish.dish_id " +
            "where setmeal_dish.setmeal_id=#{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
