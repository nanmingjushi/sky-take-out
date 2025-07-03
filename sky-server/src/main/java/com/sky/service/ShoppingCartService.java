package com.sky.service;

/* 
    @author nanchao 
    @date 2025/7/2
*/

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> showShoppingCart();

    void cleanShoppingCart();
}
