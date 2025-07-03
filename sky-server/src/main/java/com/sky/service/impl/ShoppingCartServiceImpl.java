package com.sky.service.impl;

/* 
    @author nanchao 
    @date 2025/7/2
*/

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;


    //添加购物车
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());//当前用户Id

        //判断当前商品是否在购物车中
        List<ShoppingCart> shoppingCartList=shoppingCartMapper.list(shoppingCart);

        //如果已经存在，就更新数量，数量+1
        if(shoppingCartList !=null && shoppingCartList.size()>0){
            shoppingCart=shoppingCartList.get(0);
            shoppingCart.setNumber(shoppingCart.getNumber()+1);
            shoppingCartMapper.updateNumberById(shoppingCart);
        }else {//如果不存在，插入数据，数量就是1

            //判断当前添加到购物车里的是菜品还是套餐
            Long dishId=shoppingCartDTO.getDishId();
            if(dishId!=null){ //添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());

            }else { //添加的是套餐

                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());

            }

            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
        }

    }


    //查看购物车
    @Override
    public List<ShoppingCart> showShoppingCart() {
        ShoppingCart shoppingCart=new ShoppingCart();
        shoppingCart.setUserId(BaseContext.getCurrentId());
        return shoppingCartMapper.list(shoppingCart);
    }


    //清空购物车
    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }


    //删除购物车里的一个商品
    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart=new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        //设置查询条件，查询当前登录用户的购物车数据
        shoppingCart.setUserId(BaseContext.getCurrentId());

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if(list!=null && list.size()>0){
            shoppingCart=list.get(0);
            Integer number=shoppingCart.getNumber();

            if(number==1){
                shoppingCartMapper.deleteById(shoppingCart.getId());
            }else {
                shoppingCart.setNumber(shoppingCart.getNumber()-1);
                shoppingCartMapper.updateNumberById(shoppingCart);
            }


        }


    }
}



