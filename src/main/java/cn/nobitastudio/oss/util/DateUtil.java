package cn.nobitastudio.oss.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    public static String convertDate(String dateStr, String sourceFormat, String targetFormat) {
        if (StringUtils.isBlank(targetFormat)) {
            return null;
        }
        if (StringUtils.isBlank(sourceFormat)) {
            sourceFormat = STANDARD_DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
        try {
            Date date = sdf.parse(dateStr);
            if (date == null) {
                return null;
            }
            SimpleDateFormat targetSdf = new SimpleDateFormat(targetFormat);

            return targetSdf.format(date);
        } catch (ParseException ex) {
            logger.error("parse error", ex);
            return null;
        }

    }

    public static Date formatDate(String dateStr, String format) {
        if (StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {

            SimpleDateFormat targetSdf = new SimpleDateFormat(format);
            return targetSdf.parse(dateStr);
        } catch (ParseException ex) {
            logger.error("parse error", ex);
            return null;
        }
    }

    public static String getMonthLastDate(String loadDate) {
        LocalDate localDate = LocalDate.parse(loadDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        return localDate.withDayOfMonth(1).plusMonths(1).plusDays(-1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * @param month 6位的月份
     * @return
     */
    public static String getMonthLastDate(int month) {
        LocalDate localDate = LocalDate.of(month / 100, month % 100, 1);
        return localDate.plusMonths(1).plusDays(-1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 计算两个月份时间之间的月份列表,不包含起始月份，包含结束月份
     *
     * @param monthFrom 起始月份
     * @param monthTo   结束月份
     * @return
     */
    public static List<String> diffMonth(String monthFrom, String monthTo) {
        YearMonth from = YearMonth.parse(monthFrom, DateTimeFormatter.ofPattern("yyyyMM"));
        YearMonth to = YearMonth.parse(monthTo, DateTimeFormatter.ofPattern("yyyyMM"));

        List<String> result = new ArrayList<>();
        if (monthTo.compareTo(monthFrom) > 0) {
            for (YearMonth i = from.plusMonths(1); i.compareTo(to) <= 0; i=i.plusMonths(1)) {
                result.add(i.format(DateTimeFormatter.ofPattern("yyyyMM")));
            }
        }
        return result;
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyy-MM-dd HH:mm:ss"，未传入时，返回调用时间
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToStardardString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.STANDARD_DATE_FORMAT));
    }

    /**
     * 格式化 LocalDateTime 为String类型 "yyyyMMdd"，未传入时，返回调用时间
     * @param localDateTime
     * @return
     */
    public static String formatLocalDateTimeToSimpleString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DateUtil.SIMPLE_DATE_FORMAT));
    }
    
    /**
     * 格式化 LocalDateTime 为String类型 "yyyy-MM-dd HH:mm:ss"，未传入时，返回调用时间
     * @return
     */
    public static String getCurrentDateTime() {
        return formatLocalDateTimeToStardardString(LocalDateTime.now());
    }

    /**
     * 格式化LocalDateTime 为 Date 类型
     * @param localDateTime
     * @return
     */
    public static Date formatLocalDateTimeToDate(LocalDateTime localDateTime) {
        return new Date(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }


    public static void main(String[] args) {
        String str = convertDate("2018-07-21", "yyyy-MM-dd", "yyyyMMdd");
        System.out.println(str);
        System.out.println(getMonthLastDate("20180703"));
        System.out.println(diffMonth("201811", "201903"));

        System.out.println("201701".compareTo("201702"));
        System.out.println("20170101".compareTo("201701"));
        System.out.println("201702".compareTo("20170101"));
    }


}
