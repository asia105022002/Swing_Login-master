import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class EncryptFile extends JFrame{

    private MainFrame Main;
    private JPanel panel1;
    private JButton button1;
    private JTextField inputPathField;
    private JButton button2;
    private JProgressBar progressBar1;
    private JButton runButton;
    private JButton exitButton;
    private JTextField keyField;
    private JComboBox comboBox1;
    private JTextField outputPathField;
    private File inputPath =new File(System.getProperty("user.home") + "/Desktop");
    private File outputPath=new File(System.getProperty("user.home") + "/Desktop");
    private  byte[] bytes;
    private FileInputStream fileInputStream;


    public static void main(String[] args) {
        EncryptFile encryptFile=new EncryptFile();
        encryptFile.setVisible(true);
    }

    public EncryptFile(MainFrame Main){
        this.Main=Main;
        init();
    }

    public EncryptFile() {
        init();
    }

    private void initListener() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main!=null) {
                    Main.setVisible(true);
                    dispose();
                }
                else
                    System.exit(0);

            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser(inputPath);
                //fileChooser.setCurrentDirectory(InputPath);
                if(fileChooser.showOpenDialog(EncryptFile.this)==JFileChooser.APPROVE_OPTION) {
                    try {
                        inputPath = fileChooser.getSelectedFile();
                        File file = fileChooser.getSelectedFile();
                        fileInputStream = new FileInputStream(file);
                        String path=file.toString();
                        inputPathField.setText(path);
                        //if(outputPathField.getText().equals("")) {
                        outputPathField.setText(path.substring(0,path.lastIndexOf("."))+"_cypher"+path.substring(path.lastIndexOf(".")));
                        outputPath=new File(outputPathField.getText());
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(EncryptFile.this, "檔案不存在");
                        inputPath = fileChooser.getSelectedFile().getParentFile();
                    }
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                if(outputPathField.getText().length()==0)
                    fileChooser.setCurrentDirectory(outputPath);
                else
                    fileChooser.setCurrentDirectory(new File(outputPathField.getText()).getParentFile());
                //做不到帶不存在的檔案
               if(fileChooser.showSaveDialog(EncryptFile.this)==JFileChooser.APPROVE_OPTION) {
                   outputPath=fileChooser.getSelectedFile();
                   outputPathField.setText(outputPath.toString());
                    //有空判斷檔案是否存在(覆蓋提醒
                }
            }
        });
    }

    private void init() {
        setTitle("檔案加密");
        setSize(568,155);
        setContentPane(panel1);
        setLocationRelativeTo(null);
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
        initListener();
    }


}
