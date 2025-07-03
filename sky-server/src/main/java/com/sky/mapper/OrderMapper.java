package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/7/3
*/

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    //插入订单数据
    void insert(Orders order);
}
