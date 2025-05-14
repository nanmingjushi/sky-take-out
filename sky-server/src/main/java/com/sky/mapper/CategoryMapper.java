package com.sky.mapper;

/*
    @author nanchao
    @date 2025/5/13
*/

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增分类
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user) " +
            "values (#{type},#{name},#{sort},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    //分类分页查询
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    //根据Id删除分类
    @Delete("delete from category where id=#{id}")
    void delectById(Long id);

    //修改分类信息
    @AutoFill(value = OperationType.UPDATE)
    void update(Category category);

    //根据类型（菜品、套餐）查询分类
    List<Category> list(Integer type);
}

