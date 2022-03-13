package studio.banner.forumwebsite.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Ljx
 * @Date: 2022/3/5 19:16
 * @role:
 */
public class TimeUtils {

    /**
     * 根据年月日计算年龄
     * @param birthTimeString "1994-11-14"
     * @return
     */
    public static int getAgeFromBirthTime(String birthTimeString) {
        // 先截取到字符串中的年、月、日
        int selectYear = Integer.parseInt(birthTimeString.substring(0, 4));
        int selectMonth = Integer.parseInt(birthTimeString.substring(5, 7));
        int selectDay = Integer.parseInt(birthTimeString.substring(8, 10));
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age-1;
    }

    /**
     * 将时间转换为时间戳
     * @param s = "2020-08-06 00:00:00"
     * @return
     */
    public static String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     * @param timeStamp
     * @return
     */
    public static String stampToDate(Long timeStamp){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 时间戳转换成时间
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        return sd;
    }

    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getDateString(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间的字符串
     * @return
     */
    public static String getDateString01(){
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    /**
     * 计算天数
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int getDayDiffer(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long startDateTime = dateFormat.parse(startDate).getTime();
        long endDateTime = dateFormat.parse(endDate).getTime();
        return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
    }

    public static Date getDateByString(String s) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        return ft.parse(s);
    }


    public static void main(String[] args) throws ParseException {
//        2022-03-06 15:42:53
        System.out.println(getDateString());
        System.out.println(getDayDiffer("2022-03-06 15:42:53", "2022-03-07 15:42:51"));

    }
}
