package xitongxuexi.Bcourse.parallel;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class ParallelDemo {
    public static void main(String[] args) {
        Instant star=Instant.now();
        LongStream.rangeClosed(0,10000000000L)
                .parallel()
                .reduce(0,Long::sum);
        Instant end=Instant.now();
        System.out.println("耗费时间为："+ Duration.between(star,end).toNanos());
    }
}
