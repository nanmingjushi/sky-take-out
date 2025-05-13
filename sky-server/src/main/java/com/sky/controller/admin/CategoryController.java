package com.sky.controller.admin;

/* 
    @author nanchao 
    @date 2025/5/13
*/

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //新增分类
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类，分类数据：{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    //分类分页查询
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询，参数为{}",categoryPageQueryDTO);
        PageResult pageResult=categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    //根据id删除分类
    @DeleteMapping
    public Result deleteById(Long id){
        log.info("删除分类：{}",id);
        categoryService.deleteById(id);
        return Result.success();
    }

    //修改分类
    @PutMapping
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类:{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    // 启用/禁用分类
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("启用/禁用员工账号，id={},status={}",id,status);
        categoryService.startOrStop(status,id);
        return Result.success();
    }

    //根据类型（菜品、套餐）查询分类
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("根据类型查询分类:{}",type);
        List<Category> list =categoryService.list(type);
        return Result.success(list);
    }
}
