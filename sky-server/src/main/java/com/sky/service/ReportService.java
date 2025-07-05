package com.sky.service;

/* 
    @author nanchao 
    @date 2025/7/5
*/

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);
}
