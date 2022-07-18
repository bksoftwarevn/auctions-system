package com.bksoftwarevn.auction.util;

import com.bksoftwarevn.auction.constant.AucConstant;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public class DateUtils {

    public static boolean validate(Instant start, Instant end) {
        return start.isBefore(end);
    }


    public static boolean validate(String startDate, String endDate) {
        boolean rs = false;
        try {
            Instant start = org.apache.commons.lang3.time.DateUtils.parseDate(startDate, AucConstant.DATE_FORMAT).toInstant();
            Instant end = org.apache.commons.lang3.time.DateUtils.parseDate(endDate, AucConstant.DATE_FORMAT).toInstant();
            rs = start.isBefore(end);
        } catch (Exception ex) {
            log.error("[DateUtils.validate] Validate string start date and end date exception: ", ex);
        }
        return rs;
    }
}
