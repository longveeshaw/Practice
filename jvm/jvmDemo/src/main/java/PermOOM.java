import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/*
*
* Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3210)

	*/
public class PermOOM {

    public static void main(String args[]){
        /*List<String> stringList = new ArrayList<String>();
        for(int i=0;i<100000;i++){
            stringList.add(UUID.randomUUID().toString());
        }*/
        HashMap<Long,byte[]> map=new HashMap<Long,byte[]>();
        long startTime = System.currentTimeMillis();
        try{
            while(true){
                if(map.size()*512/1024/1024>=450){
                    System.out.println("=====准备清理=====:"+map.size());
                    map.clear();
                }

                for(int i=0;i<1024;i++){
                    map.put(System.nanoTime(), new byte[512]);
                }
                long currentDiffTime = (System.currentTimeMillis() - startTime);
                System.out.println("time: "+ currentDiffTime);
                Thread.sleep(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
