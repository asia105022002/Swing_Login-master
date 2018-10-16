import javax.swing.*;

public class Main extends JFrame {

    public static void main(String[] args) {
         float a=99*0.85f;
         //a=Math.round(a*10)/10f;
        System.out.println(a);

        Login login=new Login();
        login.setVisible(true);
    }
}



