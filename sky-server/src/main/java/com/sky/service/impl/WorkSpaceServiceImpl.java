package com.sky.service.impl;

/* 
    @author nanchao 
    @date 2025/7/6
*/

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.SetmealService;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.interfaces.DSAPublicKey;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


    //根据时间段统计营业数据
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {

        /**
         * 营业额：当日已完成订单的总金额
         * 有效订单：当日已完成订单的数量
         * 订单完成率：有效订单数 / 总订单数
         * 平均客单价：营业额 / 有效订单数
         * 新增用户：当日新增用户的数量
         */

        Map map=new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = orderMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);

        //营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover=turnover==null?0.0:turnover;

        //有效订单数
        Integer validOrderCount = orderMapper.countByMap(map);

        Double unitPrice=0.0;
        Double orderCompletionRate=0.0;

        if(totalOrderCount!=0 && validOrderCount!=0){
            //订单完成率
            orderCompletionRate=validOrderCount.doubleValue()/totalOrderCount;
            //平均客单价
            unitPrice=turnover/validOrderCount;
        }

        //新增用户数
        Integer newUsers=userMapper.countByMap(map);

        //封装返回
        BusinessDataVO businessDataVO=new BusinessDataVO();
        businessDataVO.setTurnover(turnover);
        businessDataVO.setNewUsers(newUsers);
        businessDataVO.setUnitPrice(unitPrice);
        businessDataVO.setValidOrderCount(validOrderCount);
        businessDataVO.setOrderCompletionRate(orderCompletionRate);

        return businessDataVO;
    }



    //查询订单管理数据
    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map=new HashMap();
        map.put("begin",LocalDateTime.now().with(LocalTime.MIN));
        map.put("status",Orders.TO_BE_CONFIRMED);

        //待接单
        Integer waitingOrders = orderMapper.countByMap(map);

        //待派送
        map.put("status",Orders.CONFIRMED);
        Integer deliveredOrders = orderMapper.countByMap(map);

        //已完成
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = orderMapper.countByMap(map);

        //已取消
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = orderMapper.countByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }



    //查询菜品总览
    @Override
    public DishOverViewVO getDishOverView() {
        Map map=new HashMap();

        map.put("status", StatusConstant.ENABLE);
        Integer sold=dishMapper.countByMap(map);

        map.put("status",StatusConstant.DISABLE);
        Integer discontinued=dishMapper.countByMap(map);

        DishOverViewVO dishOverViewVO=new DishOverViewVO();
        dishOverViewVO.setSold(sold);
        dishOverViewVO.setDiscontinued(discontinued);

        return dishOverViewVO;
    }



    //查询套餐总览
    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map=new HashMap();

        map.put("status",StatusConstant.ENABLE);
        Integer sold=setmealMapper.countByMap(map);

        map.put("status",StatusConstant.DISABLE);
        Integer discontinued=setmealMapper.countByMap(map);

        SetmealOverViewVO setmealOverViewVO=new SetmealOverViewVO();
        setmealOverViewVO.setSold(sold);
        setmealOverViewVO.setDiscontinued(discontinued);

        return setmealOverViewVO;
    }
}
