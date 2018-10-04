import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    JMenuBar manubar=new JMenuBar();
    JMenu file=new JMenu("File");
    JMenu tools=new JMenu("Tools");
    JMenu game=new JMenu("Game");
    JMenu help=new JMenu("Help");
    JMenuItem open=new JMenuItem("Open");
    JMenuItem save=new JMenuItem("Save");
    JMenuItem exit=new JMenuItem("Exit");
    JMenuItem gogame=new JMenuItem("井字遊戲");


    public static void main(String[] args) {
        MainFrame mainframe=new MainFrame();
        mainframe.setVisible(true);
    }

    public MainFrame(){
        init();
    }

    private void init() {
        this.setTitle("Main");
        this.setBounds(400, 100, 300, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setJMenuBar(manubar);
        manubar.add(file);
        manubar.add(tools);
        manubar.add(game);
        manubar.add(help);
        file.add(open);
        file.add(save);
        file.add(exit);
        game.add(gogame);


        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

//        gogame.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GoGame goGame=new GoGame(MainFrame.this);
//                goGame.setVisible(true);
//                setVisible(false);
//            }
//        });

        gogame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GoGame goGame=new GoGame(MainFrame.this);
                goGame.setVisible(true);
                setVisible(false);
            }
        });



    }



}
