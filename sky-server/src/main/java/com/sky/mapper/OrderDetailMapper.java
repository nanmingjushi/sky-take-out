package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/7/3
*/

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    //批量插入订单明细数据
    void insertBatch(List<OrderDetail> orderDetailList);
}
