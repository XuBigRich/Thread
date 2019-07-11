package jichu.Login;

public class LoginServlet {
//    private static String usernameRef;
private  String usernameRef;
    private static String passwordRef;
    public  void doPost(String username,String password){
        try {
            usernameRef=username;
            if(username.equals("a")) {
                Thread.sleep(2223);
            }
            passwordRef=password;
            System.out.println("username="+usernameRef+" password="+password);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    /*
    * B线程先启动
    * username=a password=bb
    * username=a password=aa
    * ==================================
    * b线程执行完毕后a线程再执行
    * username=b password=bb
    * username=a password=aa
    *=================================================
    * A线程先启动
    * username=b password=bb
    * username=b password=aa
    *
    * */
}
