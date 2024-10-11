

@Slf4j
public class DateUtils {

    /**
     * <p> Field DATE_YYYYMMDD:DATE_YYYYMMDD </p>
     */
    public static final String DATE_YYYYMMDD = "yyyy-MM-dd";
    /**
     * <p>Field DATETIME_YYYYMMDD_HHMMSS: DATETIME_YYYYMMDD_HHMMSS</p>
     */
    public static final String DATETIME_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    /**
     * <p>Field TIME_HHMMSS: TIME_HHMMSS</p>
     */
    public static final String TIME_HHMMSS = "HH:mm:ss";
    /**
     * <p>Field DATE_8: DATE_8</p>
     */
    public static final String DATE_8 = "yyyyMMdd";
    /**
     * <p>Field DATE_6: DATE_6</p>
     */
    public static final String DATE_6 = "yyMMdd";
    /**
     * <p>Field TIME_6: TIME_6</p>
     */
    public static final String TIME_6 = "HHmmss";
    /**
     * <p>Field DATE8_TIME6: DATE8_TIME6</p>
     */
    public static final String DATE8_TIME6 = "yyyyMMddHHmmss";
    /**
     * <p>Field DATE8_TIME9: DATE8_TIME9</p>
     */
    public static final String DATE8_TIME9 = "yyyyMMddHHmmssSSS";
    /**
     * <p>Field DATE_4: DATE_4</p>
     */
    public static final String DATE_4 = "yyyy";
    /**
     * <p>Field TIME_HHMM: TIME_HHMM</p>
     */
    public static final String TIME_HHMM = "HH:mm";
    /**
     * <p> Field DATE_YYYYMM: yyyy-MM </p>
     */
    public static final String DATE_YYYYMM = "yyyy-MM";

    /**
     * <p>Field fmtLong: SimpleDateFormat</p>
     */
    private static final SimpleDateFormat FMT_LONG = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);

    /**
     * <p>Description: 构造方法</p>
     */
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * <p> Description: Formatting datetime </p>
     * @param date date
     * @param format format
     * @return data
     */
    public static String format(Date date, String format) {
        if (null != date && null != format) {
            return new SimpleDateFormat(format).format(date);
        }
        return null;
    }

    /**
     * <p> Description: parse date from string </p>
     * @param sDate date
     * @param format format
     * @return data
     */
    public static Date parseDate(String sDate, String format) {
        if (null != sDate && null != format) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                return formatter.parse(sDate);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * <p>Description: parse date using format yyMMdd</p>
     * @param date date with format yyMMdd
     * @return data
     */
    public static Date parseDate6(String date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_6);
            try {
                return formatter.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * <p>Description: parse date using format yyMMdd</p>
     * @param date String with format yyMMdd
     * @return String
     */
    public static String parseDate6(Date date) {
        if (null != date) {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_6);
            return formatter.format(date);
        }
        return null;
    }

    /**
     * <p>Description: 解析yyyy-MM-dd HH:mm:ss</p>
     * @param date yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date parseLongDate(String date) {
        if (null != date) {
            try {
                return FMT_LONG.parse(date);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * <p>Description: parse edi date using format yyMMdd</p>
     * @param date date
     * @return data
     */
    public static Date parseEdiDate(String date) {
        if (null != date) {
            if ("000000".equals(date)) {
                return null;
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_6);
                try {
                    return formatter.parse(date);
                } catch (ParseException e) {
                    log.error("parseEdiDate", e);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * <p> Description: Formatting edi datetime </p>
     * @param date date
     * @return data yyyyMMdd
     */
    public static String fmtEdiDate(Date date) {
        if (null != date) {
            return new SimpleDateFormat(DATE_8).format(date);
        }
        return null;
    }

    /**
     * <p> Description: get current day start time </p>
     * @return datetime
     */
    public static Date getCurrentDateStart() {
        Date date = new Date();
        String day = fmtDate(date) + " 00:00:00";
        return parseDateTime(day);
    }

    /**
     * <p> Description: add days </p>
     * @param date date
     * @param n days
     * @return date
     */
    public static Date addDay(Date date, int n) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, n);
    }

    /**
     * <p> Description: add months </p>
     * @param date date
     * @param n months
     * @return date
     */
    public static Date addMonth(Date date, int n) {
        return org.apache.commons.lang3.time.DateUtils.addMonths(date, n);
    }

    /**
     * <p>Description: add years</p>
     * @param date date
     * @param n years
     * @return date
     */
    public static Date addYear(Date date, int n) {
        if (null == date) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, n);
        return calendar.getTime();
    }

    /**
     * <p> Description: add hours </p>
     * @param date date
     * @param n hours
     * @return date
     */
    public static Date addHour(Date date, int n) {
        return org.apache.commons.lang3.time.DateUtils.addHours(date, n);
    }

    /**
     * <p> Description: add minutes </p>
     * @param date date
     * @param n minutes
     * @return date
     */
    public static Date addMinute(Date date, int n) {
        return org.apache.commons.lang3.time.DateUtils.addMinutes(date, n);
    }

    /**
     * <p>Description: add second</p>
     * @param date date
     * @param n seconds
     * @return date
     */
    public static Date addSecond(Date date, int n) {
        if (null == date) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, n);
        return calendar.getTime();
    }

    /**
     * <p> Description: parse date using format yyyyMMddHHmmss </p>
     * @param sDate date
     * @return date
     */
    public static Date getDateFor14(String sDate) {
        if (null == sDate) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE8_TIME6);
        try {
            return formatter.parse(sDate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>Description: parse date using format yyyy-MM-dd</p>
     * @param date date
     * @return date
     */
    public static Date parseDate(String date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_YYYYMMDD);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>Description: parse date using format yyyy-MM-dd HH:mm:ss</p>
     * @param date date
     * @return date
     */
    public static Date parseDateTime(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);
        try {
            return fmt.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>Description: parse date using format yyyy-MM-dd HH:mm:ss</p>
     * @param date date
     * @return date
     */
    public static Date parseDateTimeStart(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);
        try {
            return fmt.parse(date + " 00:00:00");
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>Description: parse date using format yyyy-MM-dd HH:mm:ss</p>
     * @param date date
     * @return date
     */
    public static Date parseDateTimeEnd(String date) {
        if (null == date || "".equals(date)) {
            return null;
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);
        try {
            return fmt.parse(date + " 23:59:59");
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * <p>Description: format date</p>
     * @param date date
     * @return date yyyy-MM-dd
     */
    public static String fmtDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_YYYYMMDD);
        return fmt.format(date);
    }

    /**
     * <p>Description: format date</p>
     * @param date date
     * @return date yyyyMMdd
     */
    public static String fmtDate8(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_8);
        return fmt.format(date);
    }

    /**
     * <p>Description: format long date</p>
     * @param date date
     * @return date yyyy-MM-dd HH:mm:ss
     */
    public static String fmtLongDate(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);
        return fmt.format(date);
    }

    /**
     * <p>Description: format date</p>
     * @param date date
     * @return date
     */
    public static String fmtTime14Date(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE8_TIME6);
        return fmt.format(date);
    }

    /**
     * <p>Description: format date</p>
     * @param date date
     * @return date
     */
    public static String fmtTime17Date(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE8_TIME9);
        return fmt.format(date);
    }

    /**
     * <p>Description: get year of date</p>
     * @param date date
     * @return year
     */
    public static String fmtDate4(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_4);
        return fmt.format(date);
    }

    /**
     * <p> Description: compare date is between startDate and endDate </p>
     * @param startDate start date
     * @param endDate end date
     * @param temp date
     * @return result
     */
    public static boolean isBetween(Date startDate, Date endDate, Date temp) {
        if (0 <= temp.compareTo(startDate) && 0 >= temp.compareTo(endDate)) {
            return true;
        }
        return false;
    }

    /**
     * <p> Description: get current year </p>
     * @return year
     */
    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) + "";
    }

    /**
     * <p> Description: get current month </p>
     * @param date date
     * @return month
     */
    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1 + "";
    }

    /**
     * <p> Description: get current short date </p>
     * @return date
     */
    public static String getCurrentShortDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat(DATE_YYYYMMDD);
        return formatter.format(cal.getTime());
    }

    /**
     * <p> Description: get current datetime using format yyyyMMddHHmmss</p>
     * @return datetime
     */
    public static String getCurrentLongDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE8_TIME6);
        return formatter.format(new Date());
    }

    /**
     * <p>Description: get day of week</p>
     * @param date date
     * @return day of week
     */
    public static Integer getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * <p>Description: get year week</p>
     * @param date date
     * @return week of the year
     */
    public static String getYearWeek(Date date) {
        if (null == date) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Integer year = c.get(Calendar.YEAR);
        Integer week = c.get(Calendar.WEEK_OF_YEAR);
        return year + "" + week;
    }

    /**
     * <p>Description: verify date format</p>
     * @param date date
     * @return result
     */
    public static boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_YYYYMMDD_HHMMSS);
        try {
            sdf.setLenient(false);
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * <p>Description: 日期后添加 00:00:00</p>
     * @param date 日期
     * @return 日期
     */
    public static String addStartTime(String date) {
        if (null != date) {
            return date.trim() + " 00:00:00";
        }
        return date;
    }

    /**
     * <p>Description: 日期后添加 23:59:59</p>
     * @param date 日期
     * @return 日期
     */
    public static String addEndTime(String date) {
        if (null != date) {
            return date.trim() + " 23:59:59";
        }
        return date;
    }

    /**
     * <p>Description: 取得日期区间的月数</p>
     * @param startDate 开始时间
     * @param endDate 结果时间
     * @return 列表
     */
    public static List<Date> getDateListStepByMonth(Date startDate, Date endDate) {
        List<Date> list = new ArrayList<>();
        list.add(startDate);
        while (startDate.before(endDate)) {
            startDate = addMonth(startDate, 1);
            list.add(startDate);
        }
        return list;
    }

    /**
     * <p>Description: 相差天数</p>
     * @param b B
     * @param a A
     * @return 天数
     */
    public static int differentDaysByDate(Date b, Date a) {
        return (int) ((b.getTime() - a.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * <p>Description: 相差天数，去除双休日</p>
     * @param b B
     * @param a A
     * @return 天数
     */
    @SuppressWarnings("deprecation")
    public static int getDutyDays(Date b, Date a) {
        Long result = 0L;
        Date temp = new Date(a.getTime());
        while (temp.compareTo(b) < 0 && ((int) ((b.getTime() - temp.getTime()) / (1000 * 3600 * 24))) >= 1) {
            if (temp.getDay() != 6 && temp.getDay() != 0) {
                result += 1000 * 3600 * 24;
            }
            temp.setDate(temp.getDate() + 1);
        }
        return (int) (result / (1000 * 3600 * 24));
    }

    /**
     * 获取两个日期相差的月数
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }

    /**
     * 获取两个日期相差的小时
     */
    public static int getHourDiff(Date startDate, Date endDate) {
        // milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long hoursInMilli = 1000 * 60 * 60;
        long l = different / hoursInMilli; //
        return (int) l;
    }

    public static Date trim(Date date) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static boolean isSameDay(Date a, Date b) {
        final Calendar x = Calendar.getInstance();
        x.setTime(a);
        final Calendar y = Calendar.getInstance();
        y.setTime(b);
        return x.get(Calendar.YEAR) == y.get(Calendar.YEAR) && x.get(Calendar.DAY_OF_YEAR) == y.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean isSameWeek(Date a, Date b) {
        final Calendar x = Calendar.getInstance();
        x.setTime(a);
        final Calendar y = Calendar.getInstance();
        y.setTime(b);
        return x.get(Calendar.YEAR) == y.get(Calendar.YEAR) && x.get(Calendar.WEEK_OF_YEAR) == y.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean isSameMonth(Date a, Date b) {
        final Calendar x = Calendar.getInstance();
        x.setTime(a);
        final Calendar y = Calendar.getInstance();
        y.setTime(b);
        return x.get(Calendar.YEAR) == y.get(Calendar.YEAR) && x.get(Calendar.MONTH) == y.get(Calendar.MONTH);
    }

    public static boolean isSameYear(Date a, Date b) {
        final Calendar x = Calendar.getInstance();
        x.setTime(a);
        final Calendar y = Calendar.getInstance();
        y.setTime(b);
        return x.get(Calendar.YEAR) == y.get(Calendar.YEAR);
    }

    public static Date getNextMonday(Date date) {
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        ld = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getSlEndDate(Date importTime, int days) {
        Date monday = getNextMonday(importTime);
        Date end = addDay(monday, days);
        return end;
    }

    /**
     * 获取当前周的第一天
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        // 一周第一天为周日，所以此处日+1
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR), 2);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前周的最后一天
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        // 一周第一天为周日，所以此处为下一周第一天
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR) + 1, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获取上月的第一天
     */
    public static Date getFirstDayOfLastMonth() {
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        // 设置为1号
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上月的最后一天
     */
    public static Date getLastDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        // 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取指定年月的第一天
     * @param year
     * @param month
     * @return Date
     */
    public static Date getFirstDayOfMonth(Integer year, Integer month) {
        if (Objects.isNull(year) || Objects.isNull(month)) {
            return null;
        }
        LocalDate localDate = YearMonth.of(year, month).atDay(1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定年月的第最后一天
     * @param year
     * @param month
     * @return Date
     */
    public static Date getLastDayOfMonth(Integer year, Integer month) {
        if (Objects.isNull(year) || Objects.isNull(month)) {
            return null;
        }
        LocalDate localDate = YearMonth.of(year, month).atEndOfMonth();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的是星期几
     * @param date
     * @return Integer
     */
    public static Integer getWeekOfDay(Date date) {
        if (Objects.isNull(date)) {
            return 0;
        }
        String[] split = fmtDate(date).split("-");
        LocalDate localDate = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getValue();
    }
    
    /**
     * 给日期设置开始时间
     * @param date
     * @return
     */
    public static Date setStartTime(Date date) {
        Date result = null;
        if (null != date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            result = calendar.getTime();
        }
        return result;
    }
    
    /**
     * 给日期设置结束时间
     * @param date
     * @return
     */
    public static Date setEndTime(Date date) {
        Date result = null;
        if (null != date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            result = calendar.getTime();
        }
        return result;
    }

}
