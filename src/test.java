import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class test {
    public static void main(String[] args) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date(0));
        System.out.println(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(simpleDateFormat.format(calendar.getTime()));
        calendar.add(Calendar.SECOND,1);
        System.out.println(simpleDateFormat.format(calendar.getTime()));

        LocalDateTime localDateTime = LocalDateTime.now().with(LocalDateTime.MIN);
        System.out.println(localDateTime);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        localDateTime=localDateTime.plus(1, ChronoUnit.SECONDS);
        System.out.println(localDateTime.format(dateTimeFormatter));
    }
}
