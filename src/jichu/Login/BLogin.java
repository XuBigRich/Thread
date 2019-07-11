package jichu.Login;

public class BLogin extends Thread {
    public void run(){
        LoginServlet LoginServlet=new LoginServlet();
        LoginServlet.doPost("b","bb");
    }
}
