import javax.swing.*;
import java.awt.*;
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
    JMenuItem encrypt =new JMenuItem("加密工具");
    JMenuItem encryptFile=new JMenuItem("檔案加密");


    public static void main(String[] args) {
        MainFrame mainframe=new MainFrame();
        mainframe.setVisible(true);
    }

    public MainFrame(){
        init();
    }

    private void init() {
        this.setTitle("Main");
        //this.setBounds(400, 100, 300, 300);
        this.setSize(300, 300);
        this.setMinimumSize(new Dimension(300,300));
        this.setLocationRelativeTo(null);
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
        tools.add(encrypt);
        tools.add(encryptFile);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gogame.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GoGame goGame=new GoGame(MainFrame.this);
                goGame.setVisible(true);
                setVisible(false);
            }
        });

        encrypt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Encrypt encrypt=new Encrypt(MainFrame.this);
                encrypt.setVisible(true);
                setVisible(false);
            }
        });

        encryptFile.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EncryptFile encryptFile=new EncryptFile(MainFrame.this);
                encryptFile.setVisible(true);
                setVisible(false);
            }
        });



    }



}
