package cn.nobitastudio.oss.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil {
    static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static String convertDate(String dateStr, String sourceFormat, String targetFormat) {
        if (StringUtils.isBlank(targetFormat)) {
            return null;
        }
        if (StringUtils.isBlank(sourceFormat)) {
            sourceFormat = "yyyy-MM-dd hh:mm:ss";
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
            format = "yyyy-MM-dd hh:mm:ss";
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
