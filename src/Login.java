import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

    public static void main(String[] args) {
        Login login=new Login();
        login.setVisible(true);
    }

    private PWKeyBoard kb;
    private JPanel P1=new JPanel(new GridLayout(2,2,3,3));
    private JPanel P2=new JPanel(new GridLayout(1,3,3,3));
    private JLabel LAC=new JLabel("AC:");
    private JLabel LPW=new JLabel("PW:");
    private JTextField AC=new JTextField();
    private JPasswordField PW=new JPasswordField();
    private JButton KeyBoard =new JButton("KeyBoard");
    private JButton Login =new JButton("Login");
    private JButton Eixt =new JButton("Eixt");

    public Login(){
        init();
    }

    private  void init(){
        this.setTitle("Login");
        //this.setBounds(400,100,300,120);
        this.setSize(300,120);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container c1 = this.getContentPane();
        c1.setLayout(new BorderLayout(3,3));
        c1.add(P1,BorderLayout.NORTH);
        c1.add(P2,BorderLayout.CENTER);
        P1.add(LAC);P1.add(AC);P1.add(LPW);P1.add(PW);
        P2.add(KeyBoard);P2.add(Login);P2.add(Eixt);
        KeyBoard.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(kb==(null))
                {
                    kb=new PWKeyBoard(Login.this,getX()-250,getY());
                    kb.setVisible(true);
                }
                else {
                    kb.setLocation(getX()-250,getY());
                    kb.setVisible(true);
                }
            }
        });
        Login.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(AC.getText().equals("105022002")&&new String(PW.getPassword()).equals("105022002"))
                {
                    MainFrame main=new MainFrame();
                    main.setVisible(true);
                    Login.this.dispose();
                    if(kb!=null)
                        kb.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(Login.this, "error");
                    PW.setText("");
                }

            }
        });
        Eixt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void setPW(String pw){
        PW.setText(pw);
    }

}
