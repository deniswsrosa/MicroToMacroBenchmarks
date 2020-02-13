package com.denix.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class JMHSample_27_Params {

        @Param({"1", "31", "65", "101", "103"})
        public int arg;

        @Param({"0", "1", "2", "4", "8", "16", "32"})
        public int certainty;

        @Benchmark
        public boolean bench() {
            return BigInteger.valueOf(arg).isProbablePrime(certainty);
        }

        public static void main(String[] args) throws RunnerException {
            Options opt = new OptionsBuilder()
                    .include(JMHSample_27_Params.class.getSimpleName())
//                .param("arg", "41", "42") // Use this to selectively constrain/override parameters
                    .build();

            new Runner(opt).run();
        }


    /**
     * Benchmark                  (arg)  (certainty)  Mode  Cnt      Score       Error  Units
     * JMHSample_27_Params.bench      1            0  avgt    5      3.561 ±     0.178  ns/op
     * JMHSample_27_Params.bench      1            1  avgt    5      6.161 ±     0.817  ns/op
     * JMHSample_27_Params.bench      1            2  avgt    5      5.999 ±     0.646  ns/op
     * JMHSample_27_Params.bench      1            4  avgt    5      5.955 ±     0.240  ns/op
     * JMHSample_27_Params.bench      1            8  avgt    5      5.869 ±     0.470  ns/op
     * JMHSample_27_Params.bench      1           16  avgt    5      5.835 ±     0.115  ns/op
     * JMHSample_27_Params.bench      1           32  avgt    5      6.173 ±     0.691  ns/op
     * JMHSample_27_Params.bench     31            0  avgt    5      5.892 ±     0.452  ns/op
     * JMHSample_27_Params.bench     31            1  avgt    5    408.929 ±    32.578  ns/op
     * JMHSample_27_Params.bench     31            2  avgt    5    436.747 ±    48.203  ns/op
     * JMHSample_27_Params.bench     31            4  avgt    5    848.926 ±    42.374  ns/op
     * JMHSample_27_Params.bench     31            8  avgt    5   1595.138 ±    76.080  ns/op
     * JMHSample_27_Params.bench     31           16  avgt    5  18778.018 ± 44949.199  ns/op
     * JMHSample_27_Params.bench     31           32  avgt    5   6535.608 ±  1191.817  ns/op
     * JMHSample_27_Params.bench     65            0  avgt    5      6.851 ±     2.366  ns/op
     * JMHSample_27_Params.bench     65            1  avgt    5   1184.936 ±   306.184  ns/op
     * JMHSample_27_Params.bench     65            2  avgt    5   1251.644 ±   350.085  ns/op
     * JMHSample_27_Params.bench     65            4  avgt    5   1269.826 ±   174.049  ns/op
     * JMHSample_27_Params.bench     65            8  avgt    5   1219.309 ±    27.435  ns/op
     * JMHSample_27_Params.bench     65           16  avgt    5   1207.327 ±   458.522  ns/op
     * JMHSample_27_Params.bench     65           32  avgt    5   1249.577 ±   185.606  ns/op
     * JMHSample_27_Params.bench    101            0  avgt    5      5.903 ±     0.336  ns/op
     * JMHSample_27_Params.bench    101            1  avgt    5    547.673 ±    32.107  ns/op
     * JMHSample_27_Params.bench    101            2  avgt    5    547.805 ±    26.284  ns/op
     * JMHSample_27_Params.bench    101            4  avgt    5   1025.439 ±    92.895  ns/op
     * JMHSample_27_Params.bench    101            8  avgt    5   2067.132 ±   174.293  ns/op
     * JMHSample_27_Params.bench    101           16  avgt    5   3926.308 ±   111.452  ns/op
     * JMHSample_27_Params.bench    101           32  avgt    5   7773.710 ±    80.423  ns/op
     * JMHSample_27_Params.bench    103            0  avgt    5      5.782 ±     0.115  ns/op
     * JMHSample_27_Params.bench    103            1  avgt    5    459.280 ±    12.480  ns/op
     * JMHSample_27_Params.bench    103            2  avgt    5    462.550 ±     9.005  ns/op
     * JMHSample_27_Params.bench    103            4  avgt    5    876.518 ±     4.985  ns/op
     * JMHSample_27_Params.bench    103            8  avgt    5   1705.511 ±    46.307  ns/op
     * JMHSample_27_Params.bench    103           16  avgt    5   3349.446 ±    83.634  ns/op
     * JMHSample_27_Params.bench    103           32  avgt    5   6644.118 ±   192.817  ns/op
     */
}
