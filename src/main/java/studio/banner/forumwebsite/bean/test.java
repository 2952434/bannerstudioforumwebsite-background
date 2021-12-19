package studio.banner.forumwebsite.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Ljx
 * @Date: 2021/12/19 15:49
 * @role:
 */
public class test {
    public static void main(String[] args) throws ParseException {

        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        Date endDate = dft.parse(dft.format(date.getTime()));
    }
}
