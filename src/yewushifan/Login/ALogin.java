package yewushifan.Login;

public class ALogin extends Thread {
    public void run(){
        LoginServlet LoginServlet=new LoginServlet();
        LoginServlet.doPost("a","aa");
    }
}
