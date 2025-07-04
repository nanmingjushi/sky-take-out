package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/7/3
*/

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    //插入订单数据
    void insert(Orders order);

    //分页条件查询并按下单时间排序
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    //根据Id查询订单
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    //修改订单信息
    void update(Orders orders);


    //根据状态统计订单数量
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer toBeConfirmed);
}
