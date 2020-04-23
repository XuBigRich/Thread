package xitongxuexi.Bcourse.forkjoin.RecursiveAction;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

//RecursiveAction 是没有返回结果的

/**
 * 遍历指定目录（含子目录） 寻找指定类型文件
 * <p>
 * ########################关于 join 的重点说明 ####################
 * 在使用fork join 框架的时候 经测试 join 方法 是 有必要添加的   其 运行速度 比不加join 要快
 * ############测试方法：############
 * ############测试 join 方法##########
 * 39 行   List<FindDirsFiles> subTasks=new ArrayList<>();
 * 49 行   subTasks.add(new FindDirsFiles(file.getPath(), suffix));
 * 54 - 58 行
 * if(!subTasks.isEmpty()){
 * for(FindDirsFiles subTask:invokeAll(subTasks)){
 * subTask.join();
 * }
 * }
 * 为 添加join 时的   代码  （ 测试时 需启用 且 与 非join 方法互斥 ）
 * <p>
 * ############测试 非 join 方法##########
 * 测试 非 join 的执行 速度
 * 60行   FindDirsFiles findDirsFiles =new FindDirsFiles(file.getPath(), suffix) ;
 * 61行   invokeAll(findDirsFiles);
 * 为 不添加join 时的   代码  （ 测试时 需启用 且 与 join 方法互斥 ）
 */
public class FindDirsFiles extends RecursiveAction {
    public String path;
    public String suffix;

    public FindDirsFiles(String path, String suffix) {
        this.path = path;
        this.suffix = suffix;
    }

    /**
     * 判断文件是否存在
     *
     * @return 存在返回 文件夹对象
     * @throws Exception 不存在抛出异常
     */
    public File getDir() throws Exception {
        File dir = new File(path);
        if (!dir.exists()) {
            throw new Exception("您录入的文件夹路径不存在，请输入一个文件夹路径： ");
        } else {
            return dir;
        }
    }

    /**
     * 在compute方法中进行 任务拆分 和任务计算
     */
    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        try {
            // 获取文件夹下 文件列表
            File[] subFiles = getDir().listFiles();
            // 如果文件夹下有文件
            if (subFiles != null) {
                //遍历文件夹 下文件
                for (File file : subFiles) {
                    //任务满足条件则进行处理
                    if (file.isFile()) {//如果是文件
                        if (file.getAbsolutePath().endsWith(suffix)) {
                            System.out.println(file.getPath());
                        }
                        //如果任务不满足条件则进行 任务拆分
                    } else { // 如果不是文件 递归调用 继续探查
                        subTasks.add(new FindDirsFiles(file.getPath(), suffix));
//                        FindDirsFiles findDirsFiles =new FindDirsFiles(file.getPath(), suffix) ;
//                        invokeAll(findDirsFiles);
                    }
                }
                //执行完之后，会进行一次 拆分任务的检查  如果有被拆分出来的任务则  递归调用compute 方法
                if (!subTasks.isEmpty()) {
                    //invokeAll 执行子任务下的compute 方法
                    for (FindDirsFiles subTask : invokeAll(subTasks)) {
                        //此join方法要与Thread join方法区分 （此处join可以理解为执行&取值）
                        subTask.join();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles findDirsFiles = new FindDirsFiles("c:/", "avi");
        long start = System.currentTimeMillis();
//        pool.invoke(findDirsFiles); //同步调用
        pool.execute(findDirsFiles);  //异步调用
        System.out.println("完成");
        System.out.println("Task is Running.....  /n The count is " + findDirsFiles.join() + " spend time:" + (System.currentTimeMillis() - start) + "ms");
    }
}
