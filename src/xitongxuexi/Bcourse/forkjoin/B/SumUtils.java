package xitongxuexi.Bcourse.forkjoin.B;
/*负责计算数组中lo到hi之间的和*/
public class SumUtils {
    public static long sumRange(int[] arr,int lo,int hi){
        int result = 0;
        for(int i=lo;i<hi;i++){
            result+=arr[i];
        }
        return result;
    }
}
