package com.sky.controller.admin;

/* 
    @author nanchao 
    @date 2025/7/5
*/


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


//数据统计
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    //营业额数据统计
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end
    ){
        log.info("营业额数据统计:{},{}",begin,end);
        TurnoverReportVO turnoverReportVO=reportService.getTurnover(begin,end);
        return Result.success(turnoverReportVO);
    }


    //用户数据统计
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end
    ){
        log.info("用户数据统计:{},{}",begin,end);
        UserReportVO userReportVO=reportService.getUserStatistic(begin,end);
        return Result.success(userReportVO);
    }



    //订单数据统计
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistic(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end
    ){
        log.info("订单数据统计:{},{}",begin,end);
        OrderReportVO orderReportVO=reportService.getOrderStatistics(begin,end);
        return Result.success(orderReportVO);
    }


}
