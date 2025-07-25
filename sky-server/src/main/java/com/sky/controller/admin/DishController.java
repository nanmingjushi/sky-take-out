package com.sky.controller.admin;

/* 
    @author nanchao 
    @date 2025/5/14
*/

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;


//菜品管理

@RestController
@RequestMapping("admin/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService  dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    //新增菜品
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品，菜品数据：{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);

        //清理缓存数据
        String key="dish_"+dishDTO.getCategoryId();
        cleanCache(key);

        return Result.success();
    }

    //菜品分页查询
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询:{}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    //菜品批量删除
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品批量删除:{}",ids);
        dishService.deleteBatch(ids);

        //将所有的菜品缓存数据清理掉
        cleanCache("dish_*");

        return Result.success();
    }

    //根据Id查询菜品和对应的口味数据
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据Id删除菜品：{}",id);
        DishVO dishVO=dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    //修改菜品和对应的口味
    @PutMapping
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品:{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);

        //将所有的菜品缓存数据清理掉
        cleanCache("dish_*");

        return Result.success();
    }


    //根据分类的Id来查询菜品
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list=dishService.list(categoryId);
        return Result.success(list);
    }


    //菜品起售停售
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        dishService.startOrStop(status, id);

        //将所有的菜品缓存数据清理掉所有以dish_开头的key
        cleanCache("dish_*");

        return Result.success();
    }

    //清理Redis缓存数据
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
