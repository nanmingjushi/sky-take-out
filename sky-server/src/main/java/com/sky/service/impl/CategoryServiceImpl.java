package com.sky.service.impl;

/* 
    @author nanchao 
    @date 2025/5/13
*/

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    //新增分类
    @Override
    public void save(CategoryDTO categoryDTO) {
        //存入数据库用Entity
        Category category=new Category();
        //对象属性拷贝
        BeanUtils.copyProperties(categoryDTO,category);
        //状态 默认为禁用0
        category.setStatus(StatusConstant.DISABLE);
        //创建时间
        category.setCreateTime(LocalDateTime.now());
        //创建人
        category.setCreateUser(BaseContext.getCurrentId());
        //修改时间
        category.setUpdateTime(LocalDateTime.now());
        //修改人
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.insert(category);

    }

    //分类分页查询
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //pagehelper分页框架
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.pageQuery(categoryPageQueryDTO);
        long total =page.getTotal();
        List<Category> records=page.getResult();
        return new PageResult(total,records);
    }

    //根据id删除分类
    @Override
    public void deleteById(Long id) {
        //此分类已关联的菜品dish的菜品数量
        Integer countDish= dishMapper.countByCategoryId(id);
        //此分类已关联的套餐setmeal的菜品数量
        Integer countSetmeal= setmealMapper.countByCategoryId(id);
        if(countDish>0){  //若countDish>0，则此分类下有菜品，无法删除此分类。
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        if(countSetmeal>0){  //若countSetmeal>0，则此分类下有套餐，无法删除此分类
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        categoryMapper.delectById(id);
    }

    //修改分类信息
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category =new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        //更新人，更新时间
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    // 启用/禁用分类
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category=new Category();
        category.setId(id);
        category.setStatus(status);
        categoryMapper.update(category);
    }

    //根据类型（菜品、套餐）查询分类
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }


}
