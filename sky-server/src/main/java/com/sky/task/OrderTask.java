package com.sky.task;

/* 
    @author nanchao 
    @date 2025/7/5
*/


import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


//自定义定时任务，实现订单状态定时处理
@Component
@Slf4j
public class OrderTask {


    @Autowired
    private OrderMapper orderMapper;

    //定时处理 支付超时 订单
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("处理支付超时订单：{}", new Date());

        LocalDateTime time=LocalDateTime.now().plusMinutes(-15); //下单后，超过15分钟未付款就取消订单

        List<Orders> ordersList=orderMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT,time);

        if(ordersList!=null && ordersList.size()>0){
            for (Orders order : ordersList) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("支付超时，订单取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }

    }


    //定时处理 派送中 订单
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder(){
        log.info("处理派送中订单:{}",new Date());

        LocalDateTime time=LocalDateTime.now().plusMinutes(-60); //每天1点 将前一天 派送中 订单 改为已完成

        List<Orders> ordersList=orderMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS,time);

        if(ordersList!=null && ordersList.size()>0){
            for (Orders order : ordersList) {
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }

    }

}
