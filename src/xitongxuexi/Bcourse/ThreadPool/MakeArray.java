package xitongxuexi.Bcourse.ThreadPool;

import java.util.Random;

/**
 * 产生一个整形数组
 */
public class MakeArray {
    public static final int ARRAY_LENGTH=4000000;

    /**
     * 随机数
     * @return
     */
/*    public static int[] makeArray(){
        //new一个随机数发生器
        Random random=new Random();
        int[] result=new int[ARRAY_LENGTH];
        for(int i=0;i<ARRAY_LENGTH;i++){
            //用随机数填充数组
            result[i]= random.nextInt(ARRAY_LENGTH*3);
        }
        return result;
    }*/

    /**
     * 累加数值
     * @return
     */
    public static int[] makeArray(){
        int[] result=new int[ARRAY_LENGTH];
        for(int i=0;i<ARRAY_LENGTH;i++){
            //用随机数填充数组
            result[i]= i;
        }
        return result;
    }
}
