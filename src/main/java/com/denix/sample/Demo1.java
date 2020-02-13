package com.denix.sample;



// I learned you can start the JVM with a command line option (-XX:+PrintAssembly) s
public class Demo1 {


    /**
     * Old style benchmark
     * @param args
     */
    public static void main(String[] args) {

        long startTime = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1000; i++ ) {
            sb.append(i);
        }
        long endTime = System.nanoTime();
        System.out.println("StringBuilder = "+(endTime-startTime)/1000);

        long startTime2 = System.nanoTime();
        StringBuffer sbf = new StringBuffer();
        for(int i = 0; i < 1000; i++ ) {
            sbf.append(i);
        }
        long endTime2 = System.nanoTime();
        System.out.println("StringBuffer = "+(endTime2-startTime2)/1000);

    }

}


