package xitongxuexi.Bcourse.Parallel;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class ParallelDemo {
    public static void main(String[] args) {
        Instant star= Instant.now();
        LongStream.rangeClosed(0,100000000L)
                .parallel()
                .reduce(0,Long::sum);
        Instant end= Instant.now();
        System.out.println("我耗费的时间为："+ Duration.between(star,end).getSeconds());
    }
}
