package com.sky.service.impl;

/* 
    @author nanchao 
    @date 2025/7/5
*/

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;


    //根据时间区间统计营业额
    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {

        List<LocalDate> dateList = new ArrayList<>(); //时间列表
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Double> turnoverList = new ArrayList<>(); //营业额列表
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map map = new HashMap();
            map.put("status", Orders.COMPLETED);
            map.put("begin", beginTime);
            map.put("end", endTime);

            Double turnover = orderMapper.sumByMap(map);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);

        }


        //数据封装
        TurnoverReportVO turnoverReportVO = new TurnoverReportVO();
        turnoverReportVO.setDateList(StringUtils.join(dateList, ",")); //前端要的数据格式是用，隔开的
        turnoverReportVO.setTurnoverList(StringUtils.join(turnoverList, ",")); //前端要的数据格式是用，隔开的
        return turnoverReportVO;

    }


    //根据时间区间统计用户数量
    @Override
    public UserReportVO getUserStatistic(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>(); //时间列表
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        List<Integer> newUserList = new ArrayList<>(); //新用户数量 列表
        List<Integer> totalUserList = new ArrayList<>(); //所有用户数量 列表

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Integer newUser=getUserCount(beginTime,endTime);
            Integer totalUser = getUserCount(null, endTime);

            newUserList.add(newUser);
            totalUserList.add(totalUser);

        }

        //数据封装
        UserReportVO userReportVO=new UserReportVO();
        userReportVO.setDateList(StringUtils.join(dateList,","));
        userReportVO.setNewUserList(StringUtils.join(newUserList,","));
        userReportVO.setTotalUserList(StringUtils.join(totalUserList,","));
        return userReportVO;
    }

    //根据时间区间统计用户数量
    private Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        Map map = new HashMap();
        map.put("begin",beginTime);
        map.put("end", endTime);
        return userMapper.countByMap(map);
    }
}
