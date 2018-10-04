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
    private JLabel l1=new JLabel("game",JLabel.CENTER);
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

    private void check(){
        int[][] i={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int[] anI : i) {
            if (!JB[anI[0]].getText().equals("")) {
                if (JB[anI[0]].getText().equals(JB[anI[1]].getText()) && JB[anI[0]].getText().equals(JB[anI[2]].getText())) {
                    game = false;
                    String msg;
                    msg = (JB[anI[0]].getText().equals("O") ? "O勝利" : "X勝利");
                    msg += ",重新開始遊戲?";
                    int rtn = JOptionPane.showConfirmDialog(GoGame.this, msg);
                    if (rtn == JOptionPane.YES_OPTION)
                        restart();
                }
                else if(st==9)
                {
                    String msg;
                    msg = "平局";
                    msg += ",重新開始遊戲?";
                    int rtn = JOptionPane.showConfirmDialog(GoGame.this, msg);
                    if (rtn == JOptionPane.YES_OPTION)
                        restart();
                }
            }
        }
    }

    private void restart() {
        game=true;
        st=0;
        for(JButton btn:JB)
            btn.setText("");
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
        l1.setBackground(Color.ORANGE);
        l1.setOpaque(true);
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
                            }
                            else
                                t.setText("X");
                            st++;
                            check();
                        }
                    }
                }
            });
            P1.add(JB[c]);
        }
    }
}
