package thread_unsafe;

public class LockEliminateDemo {

    private static final int CIRCLE = 2000000;

    public static void main(String args[]) throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < CIRCLE; i++) {
            craeteStringBuffer("JVM", "Diagnosis");
        }
        long bufferCost = System.currentTimeMillis() - start;
        System.out.println("craeteStringBuffer: " + bufferCost + " ms");
    }

    public static String craeteStringBuffer(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        return sb.toString();
    }
/*
* -server -XX:+DoEscapeAnalysis -XX:-EliminateLocks
* craeteStringBuffer: 200 ms

-server -XX:+DoEscapeAnalysis -XX:+EliminateLocks
craeteStringBuffer: 156 ms

none:
craeteStringBuffer: 108 ms

 * */
}
