import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

public class PWKeyBoard extends JFrame{

    public static void main(String[] args) {
        //PWKeyBoard kb=new PWKeyBoard(new LogIn());
        PWKeyBoard kb=new PWKeyBoard();
        kb.setVisible(true);
    }

    private Login login;
    private int x=100,y=100;
    private JPasswordField PF=new JPasswordField();
    private JPanel P0=new JPanel(new BorderLayout(3,3));
    private JPanel P1=new JPanel(new GridLayout(4,3,3,3));

    private JButton[] btnNumbers=new JButton[10];
    //private JButton btnExit=new JButton("Exit");
    private JButton btnBackspace=new JButton("←");
    private JButton btnShuffle=new JButton("Shuffle");
    private JButton btnSubmit=new JButton("Submit");

    private Font font=new Font(null,Font.BOLD,20);
    private ArrayList<Integer> A= new ArrayList<>();

    public PWKeyBoard(){
        init();
    }

    public PWKeyBoard(Login login){
        this.login=login;
        init();
    }
    public PWKeyBoard(Login login,int x,int y){
        this.login=login;
        this.x=x;
        this.y=y;
        init();
    }

    private void init() {
        this.setTitle("keyboard");
        this.setBounds(x,y,250,300);
        setResizable(false);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout(3,3));
        container.add(PF,BorderLayout.NORTH);
        container.add(P0, BorderLayout.CENTER);
        P0.add(P1, BorderLayout.CENTER);
        P0.add(btnShuffle,BorderLayout.SOUTH);
        for(int c=0;c<10;c++)
        {
            btnNumbers[c]=new JButton();
            btnNumbers[c].setFont(font);
            P1.add(btnNumbers[c]);
        }
        for(int c=0;c<10;c++)
            A.add(c);
        SetButtonName();
        P1.add(btnBackspace);
        P1.add(btnSubmit);
        btnBackspace.setFont(font);
        btnShuffle.setFont(font);

        for(JButton btn:btnNumbers)
        {
            btn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn=(JButton)e.getSource();
                    PF.setText(new String(PF.getPassword())+btn.getText());
                }
            });
        }

        btnShuffle.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetButtonName();

            }
        });

        btnSubmit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.setPW(new String(PF.getPassword()));
                PF.setText("");
                setVisible(false);
            }
        });

        btnBackspace.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pw=new String(PF.getPassword());
                PF.setText(pw.substring(0,pw.length()-1));
            }
        });
    }

    private void SetButtonName(){
        Collections.shuffle(A);
        for(int c=0;c<10;c++)
            btnNumbers[c].setText(Integer.toString(A.get(c)));
//        System.out.println(this.getBounds().getHeight());
//        System.out.println(this.getBounds().getWidth());
    }
}
