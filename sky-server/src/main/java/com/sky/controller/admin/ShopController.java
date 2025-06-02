package com.sky.controller.admin;

/* 
    @author nanchao 
    @date 2025/6/2
*/


import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;



    //设置店铺营业状态
    @PutMapping("/{status}")
    public Result setShopStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为：{}",status==1?"营业中":"打烊中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }


    //管理端 查询店铺营业状态
    @GetMapping("/status")
    public Result<Integer> getShopStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取到店铺的营业状态是：{}",status==1?"营业中":"打烊中");
        return Result.success(status);
    }

}
