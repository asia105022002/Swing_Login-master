import javafx.scene.control.RadioButton;

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
    private JComboBox comboBox;
    private JTextField outputPathField;
    private JRadioButton 加密RadioButton;
    private JRadioButton 解密RadioButton;
    //private File inputPath =new File(System.getProperty("user.home") + "/Desktop");
    private File inputPath =new File("D:\\作業\\視窗");
    private File outputPath=new File(System.getProperty("user.home") + "/Desktop");
    private  byte[] bytes;
    private FileInputStream fileInputStream;
    private ButtonGroup buttonGroup;


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

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(inputPathField.getText().length()==0)
                    JOptionPane.showMessageDialog(EncryptFile.this, "請選擇檔案");
                else if(outputPathField.getText().length()==0)
                    JOptionPane.showMessageDialog(EncryptFile.this, "請選擇存檔路徑");
                else if(keyField.getText().length()==0)
                    JOptionPane.showMessageDialog(EncryptFile.this, "請輸入金鑰");
                else {
                    try {
                        String key=keyField.getText();
                        //boolean encryptMode=buttonGroup.getSelection().getActionCommand().equals("encrypt");
                        boolean encryptMode=加密RadioButton.isSelected();
                        int encryptionType =comboBox.getSelectedIndex();

                        File file = new File(inputPathField.getText());
                        long fileSize = file.length();
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] buffer = new byte[(int) fileSize];
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        bufferedInputStream.read(buffer);
                        bufferedInputStream.close();
                        fileInputStream.close();

                        switch(encryptionType) {
                            case 0:
                                buffer=EncryptMathod.DES(buffer,key,encryptMode);
                                break;
                            case 1:
                                EncryptMathod.XOR(buffer,key);
                                break;
                            case 2:
                                EncryptMathod.Caesar(buffer,Integer.parseInt(key),encryptMode);
                                break;
                        }
                        //buffer= EncryptMathod.XOR(buffer,keyField.getText());

                        FileOutputStream fileOutputStream=new FileOutputStream(outputPathField.getText());
                        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
                        bufferedOutputStream.write(buffer);
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                        JOptionPane.showMessageDialog(EncryptFile.this, "Done");

                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(EncryptFile.this, "檔案不存在");
                    }catch (Exception e1) {
                        JOptionPane.showMessageDialog(EncryptFile.this, e1);
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    private void init() {
        setTitle("檔案加密");
        setSize(682,155);
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
        buttonGroup=new ButtonGroup();
        buttonGroup.add(加密RadioButton);
        buttonGroup.add(解密RadioButton);
        initListener();
    }


}
