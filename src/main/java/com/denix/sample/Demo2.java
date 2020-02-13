package com.denix.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.PausesProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class Demo2 {


    public static void main(String[] args) throws Exception {
        //org.openjdk.jmh.Main.main(args);

        //-XX:+PrintCompilation

        Options opt = new OptionsBuilder()
                .jvmArgs("-XX:+PrintCompilation") //, ""-XX:+UnlockExperimentalVMOptions", "-XX:+UseJVMCICompiler" ) // "-XX:+UseJVMCICompiler" ) // "-XX:+UseEpsilonGC", "-Xmx100m", "-XX:+HeapDumpOnOutOfMemoryError"
               .addProfiler(GCProfiler.class) //GC profiling via standard MBeans
               // .addProfiler(StackProfiler.class) //Simple and naive Java stack profiler
                //.addProfiler(PausesProfiler.class)
                //.shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }

    //https://github.com/Valloric/jmh-playground/blob/master/src/jmh/java/org/openjdk/jmh/samples/JMHSample_05_StateFixtures.java
    @State(Scope.Thread)
    public static class MyState {
        private static int counter = 0;
        @TearDown(Level.Iteration)
        public void doTearDown() {
//            System.out.println("\n iterations counter: "+counter);
//            counter = 0;

          //  System.out.println(" MEMORY = "+(Runtime.getRuntime().freeMemory()));
        }
    }


    @Benchmark
   // @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    //@OperationsPerInvocation(10000)
//    @Warmup(iterations = 1)
//    @Measurement(iterations = 2)
    public void stringBufferBenchmark(Blackhole blackhole, MyState mystate) {
        StringBuffer bf = new StringBuffer();
        for(int i = 0; i < 1000; i++ ) {
            bf.append(i);
        }
     //   mystate.counter++;
        blackhole.consume(bf);
    }




    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
//    @Warmup(iterations = 1)
//    @Measurement(iterations = 2)
    public void stringBuilderBenchmark(Blackhole blackhole, MyState mystate) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1000; i++ ) {
            sb.append(i);
        }
       // mystate.counter++;
        blackhole.consume(sb);
    }

    //  @Warmup(iterations = 5, batchSize = 5000)
    //    @Measurement(iterations = 5, batchSize = 5000)
    //    @BenchmarkMode(Mode.SingleShotTime)


    /*
    @Fork By default JHM forks a new java process for each trial (set of iterations). This is required to defend the test from previously collected “profiles” – information about other loaded classes and their execution information. For example, if you have 2 classes implementing the same interface and test the performance of both of them, then the first implementation (in order of testing) is likely to be faster than the second one (in the same JVM), because JIT replaces direct method calls to the first implementation with interface method calls after discovering the second implementation.
     */




//==================================================================
//    @Benchmark
//    public void measureWrong() {
//        // This is wrong: result is not used and the entire computation is optimized away.
//        Math.log(x);
//    }
//
//    @Benchmark
//    public double measureRight() {
//        // This is correct: the result is being used.
//        return Math.log(x);
//    }
//==================================================================












//
//    //Result "com.denix.sample.Demo2.stringBuilderBenchmark":
//    //  11.534 us/op
//
//    //Result "com.denix.sample.Demo2.stringBufferBenchmark":
//    //  13.135 us/op
//    //
//
//    /*
//    Benchmark                                     Mode  Cnt   Score   Error  Units
//Demo2.stringBufferBenchmark                   avgt    2  12.748          us/op
//Demo2.stringBufferBenchmark:·pauses           avgt   13  32.201             ms
//Demo2.stringBufferBenchmark:·pauses.avg       avgt        2.477             ms
//Demo2.stringBufferBenchmark:·pauses.count     avgt       13.000              #
//Demo2.stringBufferBenchmark:·pauses.p0.00     avgt        1.573             ms
//Demo2.stringBufferBenchmark:·pauses.p0.50     avgt        2.191             ms
//Demo2.stringBufferBenchmark:·pauses.p0.90     avgt        4.435             ms
//Demo2.stringBufferBenchmark:·pauses.p0.95     avgt        4.858             ms
//Demo2.stringBufferBenchmark:·pauses.p0.99     avgt        4.858             ms
//Demo2.stringBufferBenchmark:·pauses.p0.999    avgt        4.858             ms
//Demo2.stringBufferBenchmark:·pauses.p0.9999   avgt        4.858             ms
//Demo2.stringBufferBenchmark:·pauses.p1.00     avgt        4.858             ms
//Demo2.stringBuilderBenchmark                  avgt    2  11.276          us/op
//Demo2.stringBuilderBenchmark:·pauses          avgt    6  23.007             ms
//Demo2.stringBuilderBenchmark:·pauses.avg      avgt        3.835             ms
//Demo2.stringBuilderBenchmark:·pauses.count    avgt        6.000              #
//Demo2.stringBuilderBenchmark:·pauses.p0.00    avgt        2.830             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.50    avgt        3.607             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.90    avgt        5.300             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.95    avgt        5.300             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.99    avgt        5.300             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.999   avgt        5.300             ms
//Demo2.stringBuilderBenchmark:·pauses.p0.9999  avgt        5.300             ms
//Demo2.stringBuilderBenchmark:·pauses.p1.00    avgt        5.300             ms
//     */
}


//4571405864
//9135351776