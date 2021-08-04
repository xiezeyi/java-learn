import cn.hutool.core.date.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-08-04 16:35
 **/
public class test {
    public static void main(String[] args) {
        LocalDate localDate1 = LocalDate.parse("2021-08-04");
        System.out.println(localDate1);
        LocalDateTime localDateTime = LocalDateTime.now();
// 设置日期
        System.out.println("localDateTime = " + localDateTime);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        LocalDateTime localDateTime1 = LocalDateTime.parse(localDate.toString()+ "T23:59:59");
        System.out.println("localDateTime1 = " + localDateTime1);

        LocalDateTime localDateTime2 = DateUtil.parseLocalDateTime(localDate1.toString() + " 23:59:59");
        System.out.println("localDateTime2 = " + localDateTime2);
    }
}
