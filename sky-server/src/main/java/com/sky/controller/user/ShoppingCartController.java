package com.sky.controller.user;

/* 
    @author nanchao 
    @date 2025/7/2
*/


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//购物车
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


    //添加购物车
    @PostMapping("/add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);

        return Result.success();

    }


    //查看购物车
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        return Result.success(shoppingCartService.showShoppingCart());
    }



    //清空购物车
    @DeleteMapping("/clean")
    public Result<String> clean(){
        shoppingCartService.cleanShoppingCart();
        return Result.success();
    }


}
