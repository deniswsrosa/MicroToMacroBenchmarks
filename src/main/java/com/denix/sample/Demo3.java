package com.denix.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.DTraceAsmProfiler;
import org.openjdk.jmh.profile.LinuxPerfNormProfiler;
import org.openjdk.jmh.profile.LinuxPerfProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.util.concurrent.TimeUnit;


@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Demo3 {

    //sudo java -jar jmh-demo-1.0-SNAPSHOT-jar-with-dependencies.jar  -lprof
    //apt install linux-tools-$(uname -r) linux-tools-generic

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .addProfiler(LinuxPerfNormProfiler.class)
                .build();
        new Runner(opt).run();
    }


    @State(Scope.Thread)
    public static class MyState {
        private int[][] a;
        private int[][] b;

        @Setup(Level.Trial)
        public void setup() {
            a = generateArray();
            b = generateArray();
            //  System.out.println(" MEMORY = "+(Runtime.getRuntime().freeMemory()));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 1)
    @Measurement(iterations = 2)
    public void multiplyPerf(MyState state) {
        multiply(state.a, state.b);
    }

// FIRST EXAMPLE: This implementation does not take any advantage of temporal/spatial locality
//    public int[][] multiply(int[][]a, int[][]b) {
//        int size = a.length;
//        int[][] r = new int[size][size];
//        for(int i = 0; i < size; i++) {
//            for(int j = 0; j < size; j++) {
//                int s = 0;
//                for(int k = 0; k < size; k++) {
//                    s += a[i][k] * b[k][j];
//                }
//                r[i][j] = s;
//            }
//        }
//        return r;
//    }


    public int[][] multiply(int[][]a, int[][]b) {
        int size = a.length;
        int[][] r = new int[size][size];
        for(int i = 0; i < size; i++) {
            for(int k = 0; k < size; k++) {
                int aik = a[i][k];
                for(int j = 0; j < size; j++) {
                    r[i][j] += aik * b[k][j];
                }
            }
        }
        return r;
    }

    private static int[][] generateArray() {
        int[][] r = new int[512][512];
        for(int i = 0; i < 512; i++) {
            for(int j = 0; j < 512; j++) {
                r[i][j] = i+j;
            }
        }
        return r;
    }


    /**
     *  Sometimes you can get the performance boost by simply using an array instead of an ArrayList or even a HashMap.
     *
     * Keep the code small.
     * Keep your data small. To keep data and code within the level 2 cache, they shouldn't exceed 64 KB (depending on your CPU). The virtual machine needs some space, too, so the limit may be a lot smaller.
     * Prefer local variables to attributes. In C programs, this frequently enables variables to be kept in the CPUs registers. As far as I know, the JVM doesn't do this optimization yet.
     *
     * Replace expensive data structures like ArrayList or HashMap by simple arrays. This may come as a surprise to most programmers who have learned that HashMap is a very fast data structure. In fact, it is. HashMap is a very efficient to store large amounts of data. If you're using very small amounts of data, different rules apply.
     *
     */


    /**
     * RUN 1
     *
     *
     *
     # Run progress: 33.33% complete, ETA 00:01:06
     # Fork: 2 of 3
     # Preparing profilers: LinuxPerfNormProfiler
     # Profilers consume stderr from target VM, use -v EXTRA to copy to console
     # Warmup Iteration   1: 305734.004 us/op
     Iteration   1: 288929.176 us/op
     Iteration   2: 290068.642 us/op
     # Processing profiler results: LinuxPerfNormProfiler

     # Run progress: 66.67% complete, ETA 00:00:32
     # Fork: 3 of 3
     # Preparing profilers: LinuxPerfNormProfiler
     # Profilers consume stderr from target VM, use -v EXTRA to copy to console
     # Warmup Iteration   1: 287833.511 us/op
     Iteration   1: 281297.600 us/op
     Iteration   2: 286683.142 us/op
     # Processing profiler results: LinuxPerfNormProfiler


     Result "com.denix.sample.Demo3.multiplyPerf":
     286919.126 ±(99.9%) 9429.120 us/op [Average]
     (min, avg, max) = (281297.600, 286919.126, 290068.642), stdev = 3362.515
     CI (99.9%): [277490.006, 296348.246] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:L1-dcache-load-misses":
     290030903.311 ±(99.9%) 3259085.483 #/op [Average]
     (min, avg, max) = (289865553.310, 290030903.311, 290220383.314), stdev = 178641.473
     CI (99.9%): [286771817.828, 293289988.794] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:L1-dcache-loads":
     537365858.913 ±(99.9%) 19815111.466 #/op [Average]
     (min, avg, max) = (536467356.521, 537365858.913, 538572873.429), stdev = 1086133.123
     CI (99.9%): [517550747.447, 557180970.379] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:L1-dcache-stores":
     536965.047 ±(99.9%) 234308.112 #/op [Average]
     (min, avg, max) = (522159.845, 536965.047, 545111.114), stdev = 12843.218
     CI (99.9%): [302656.936, 771273.159] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:L1-icache-load-misses":
     178252.300 ±(99.9%) 243908.951 #/op [Average]
     (min, avg, max) = (162909.479, 178252.300, 187403.971), stdev = 13369.473
     CI (99.9%): [≈ 0, 422161.251] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:branch-misses":
     282576.055 ±(99.9%) 42351.885 #/op [Average]
     (min, avg, max) = (279928.757, 282576.055, 284264.380), stdev = 2321.450
     CI (99.9%): [240224.170, 324927.940] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:branches":
     201623709.305 ±(99.9%) 4675467.361 #/op [Average]
     (min, avg, max) = (201327945.113, 201623709.305, 201780026.043), stdev = 256278.143
     CI (99.9%): [196948241.945, 206299176.666] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:dTLB-load-misses":
     7796.268 ±(99.9%) 7465.866 #/op [Average]
     (min, avg, max) = (7510.704, 7796.268, 8265.100), stdev = 409.229
     CI (99.9%): [330.402, 15262.134] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:dTLB-loads":
     538652304.798 ±(99.9%) 19228127.328 #/op [Average]
     (min, avg, max) = (537745932.563, 538652304.798, 539808836.944), stdev = 1053958.541
     CI (99.9%): [519424177.470, 557880432.125] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:dTLB-store-misses":
     955.225 ±(99.9%) 2690.685 #/op [Average]
     (min, avg, max) = (803.394, 955.225, 1097.943), stdev = 147.486
     CI (99.9%): [≈ 0, 3645.911] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:dTLB-stores":
     468457.815 ±(99.9%) 885089.170 #/op [Average]
     (min, avg, max) = (427838.845, 468457.815, 522177.671), stdev = 48514.724
     CI (99.9%): [≈ 0, 1353546.985] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:iTLB-load-misses":
     3699.624 ±(99.9%) 7585.046 #/op [Average]
     (min, avg, max) = (3246.155, 3699.624, 4062.859), stdev = 415.762
     CI (99.9%): [≈ 0, 11284.669] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:iTLB-loads":
     6638.827 ±(99.9%) 16550.255 #/op [Average]
     (min, avg, max) = (5737.507, 6638.827, 7551.746), stdev = 907.175
     CI (99.9%): [≈ 0, 23189.083] (assumes normal distribution)

     Secondary result "com.denix.sample.Demo3.multiplyPerf:instructions":
     1204154526.833 ±(99.9%) 14495004.338 #/op [Average]
     (min, avg, max) = (1203324724.042, 1204154526.833, 1204908297.944), stdev = 794520.099
     CI (99.9%): [1189659522.495, 1218649531.172] (assumes normal distribution)


     # Run complete. Total time: 00:01:37

     REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     experiments, perform baseline and negative tests that provide experimental control, make sure
     the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     Do not assume the numbers tell you what you want them to tell.

     Benchmark                                 Mode  Cnt           Score          Error  Units
     Demo3.multiplyPerf                        avgt    6      286919.126 ±     9429.120  us/op
     Demo3.multiplyPerf:L1-dcache-load-misses  avgt    3   290030903.311 ±  3259085.483   #/op
     Demo3.multiplyPerf:L1-dcache-loads        avgt    3   537365858.913 ± 19815111.466   #/op
     Demo3.multiplyPerf:L1-dcache-stores       avgt    3      536965.047 ±   234308.112   #/op
     Demo3.multiplyPerf:L1-icache-load-misses  avgt    3      178252.300 ±   243908.951   #/op
     Demo3.multiplyPerf:branch-misses          avgt    3      282576.055 ±    42351.885   #/op
     Demo3.multiplyPerf:branches               avgt    3   201623709.305 ±  4675467.361   #/op
     Demo3.multiplyPerf:dTLB-load-misses       avgt    3        7796.268 ±     7465.866   #/op
     Demo3.multiplyPerf:dTLB-loads             avgt    3   538652304.798 ± 19228127.328   #/op
     Demo3.multiplyPerf:dTLB-store-misses      avgt    3         955.225 ±     2690.685   #/op
     Demo3.multiplyPerf:dTLB-stores            avgt    3      468457.815 ±   885089.170   #/op
     Demo3.multiplyPerf:iTLB-load-misses       avgt    3        3699.624 ±     7585.046   #/op
     Demo3.multiplyPerf:iTLB-loads             avgt    3        6638.827 ±    16550.255   #/op
     Demo3.multiplyPerf:instructions           avgt    3  1204154526.833 ± 14495004.338   #/op
     */
}
