package com.sky.controller.admin;

/* 
    @author nanchao 
    @date 2025/7/5
*/


import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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


    //销量数据统计排名
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate end
    ){
        log.info("销量数据统计排名:{},{}",begin,end);
        SalesTop10ReportVO salesTop10ReportVO=reportService.getSalesTop10(begin,end);
        return Result.success(salesTop10ReportVO);
    }


    //导出运营数据报表

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        reportService.exportBusinessData(response);
    }

}
