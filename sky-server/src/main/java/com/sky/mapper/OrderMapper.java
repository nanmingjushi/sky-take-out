package com.sky.mapper;

/* 
    @author nanchao 
    @date 2025/7/3
*/

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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


    //根据状态和下单时间查询订单
    @Select("select * from orders where status=#{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime orderTime);


    //根据动态条件统计营业额
    Double sumByMap(Map map);


    //根据动态条件统计订单数量
    Integer countByMap(Map map);


    //查询 菜品/套餐销量 排名
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
