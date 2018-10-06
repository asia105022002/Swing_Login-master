import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class GoGame extends JFrame {

    public static void main(String[] args) {
        GoGame game=new GoGame();
        game.setVisible(true);
    }

    private MainFrame Main;
    private Container C;
    private JLabel l1=new JLabel("Player O's turn...",JLabel.CENTER);
    private JPanel P1=new JPanel(new GridLayout(3,3,3,3));
    private JButton[] JB=new JButton[9];
    private boolean game=true;
    private int st=0;

    public GoGame(){
        init();
    }

    public GoGame(MainFrame Main){
        this.Main=Main;
        init();
    }

    private void init(){
        this.setTitle("井字遊戲");
        this.setBounds(100,100,400,300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(Main!=null)
                    Main.setVisible(true);
                else
                    System.exit(0);
            }
        });

        C=this.getContentPane();
        C.setLayout(new BorderLayout(3,3));
//        l1.setBackground(Color.ORANGE);
//        l1.setOpaque(true); 設定背景顏色用
        l1.setFont(new Font(null,Font.BOLD,30));
        C.add(l1,BorderLayout.NORTH);
        C.add(P1,BorderLayout.CENTER);
        for(int c=0;c<9;++c) {
            JB[c] = new JButton();
            JB[c].setFont(new Font(null,Font.BOLD,40));
            JB[c].addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton t=(JButton)e.getSource();
                    if(game) {
                        if(t.getText().equals("")) {
                            if (st%2==0) {
                                t.setText("O");
                                l1.setText("Player X's turn...");
                            }
                            else {
                                t.setText("X");
                                l1.setText("Player O's turn...");
                            }
                            st++;
                            Judge();
                        }
                    }
                }
            });
            P1.add(JB[c]);
        }
    }

    private void Judge(){
        int[][] i={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int[] anI : i)
            if (!JB[anI[0]].getText().equals("")) {
                if (JB[anI[0]].getText().equals(JB[anI[1]].getText()) && JB[anI[0]].getText().equals(JB[anI[2]].getText()))
                    endGame(JB[anI[0]].getText().equals("O") ? "O勝利" : "X勝利");
            }
        if(st==9)
            endGame("平局");
        //下滿後判斷是否分出勝負，未分出勝負才平局
    }

    private void endGame(String msg){
        game = false;
        l1.setText("Game End");
        msg += ",是否重新開始遊戲?";
        String[] options={"是","否","關閉遊戲"};
        int rtn=JOptionPane.showOptionDialog(GoGame.this,msg,"系統提示",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,"是");
        if (rtn == JOptionPane.YES_OPTION)
            restart();
        else if(rtn == JOptionPane.CANCEL_OPTION) {
            if(Main!=null)
                Main.setVisible(true);
            dispose();
        }
    }

    private void restart() {
        l1.setText("Player O's turn...");
        game=true;
        st=0;
        for(JButton btn:JB)
            btn.setText("");
    }
}
