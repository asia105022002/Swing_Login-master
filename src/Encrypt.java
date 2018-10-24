import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Encrypt extends JFrame {

    private MainFrame Main;
    private JComboBox comboBox;
    private JPanel container = new JPanel();
    private JTextArea textAreaWest = new JTextArea();
    private JTextArea textAreaEast = new JTextArea();
    private JPasswordField pw;
    private ButtonGroup rdbtn;
    private JButton btnRun;
    private JButton btnClose;
    private boolean EncryptMode=true;
    private JMenuItem save;
    private JMenuItem open;
    private JMenuItem exit;
    private File path=new File(System.getProperty("user.home") + "/Desktop");
    //private File path=new File("D:\\作業\\作業\\視窗");
    private byte[] buffer;

    public static void main(String[] args) {
        Encrypt encrypt=new Encrypt();
        encrypt.setVisible(true);
    }

    public Encrypt(){
        init();
    }

    public Encrypt(MainFrame Main){
        this.Main=Main;
        init();
    }

    private void init() {
        this.setTitle("加密");
        this.setSize( 800, 600);
        this.setLocationRelativeTo(null);
        JMenuBar menuBar=new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu file=new JMenu("File");
        JMenu about=new JMenu("About");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        menuBar.add(file);
        menuBar.add(about);
        file.add(open);
        file.add(save);
        file.add(exit);

        //this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
        container.setBorder(new EmptyBorder(3, 3, 3, 3));
        setContentPane(container);
        container.setLayout(new BorderLayout(3, 3));
        JScrollPane scrollPaneWest = new JScrollPane();
        scrollPaneWest.setPreferredSize(new Dimension(350, 0));
        container.add(scrollPaneWest,BorderLayout.WEST);
        scrollPaneWest.setViewportView(textAreaWest);
        textAreaWest.setLineWrap(true);
        textAreaWest.setWrapStyleWord(true);

        JPanel CenterLayout = new JPanel();
//        CenterLayout.setPreferredSize(new Dimension(600, 600));
//        CenterLayout.setMinimumSize(new Dimension(600, 600));無效
        CenterLayout.setLayout(new GridLayout(9,1,3,3));
        container.add(CenterLayout,BorderLayout.CENTER);

        JLabel LMathod=new JLabel("Mathod");
        CenterLayout.add(LMathod);
        String[] selection={"DES","AES","XOR","Caesar"};
        comboBox = new JComboBox(selection);
        CenterLayout.add(comboBox);
        JLabel LPassword=new JLabel("Password");
        CenterLayout.add(LPassword);
        pw = new JPasswordField();
        CenterLayout.add(pw);
        JRadioButton rdbtnencrypt = new JRadioButton("encrypt",true);
        rdbtnencrypt.setActionCommand("encrypt");
        CenterLayout.add(rdbtnencrypt);
        JRadioButton rdbtnDecrypt = new JRadioButton("Decrypt");
        rdbtnDecrypt.setActionCommand("decrypt");
        CenterLayout.add(rdbtnDecrypt);
        btnRun = new JButton("Run");
        CenterLayout.add(btnRun);
        btnClose = new JButton("Close");
        CenterLayout.add(btnClose);

        rdbtn = new ButtonGroup();
        rdbtn.add(rdbtnencrypt);
        rdbtn.add(rdbtnDecrypt);

        JScrollPane scrollPaneEast = new JScrollPane();
        //scrollPaneEast.setMaximumSize(new Dimension(600, 0));無效
        scrollPaneEast.setPreferredSize(new Dimension(350, 0));
        container.add(scrollPaneEast,BorderLayout.EAST);
        scrollPaneEast.setViewportView(textAreaEast);
        textAreaEast.setLineWrap(true);
        textAreaEast.setWrapStyleWord(true);
        initListener();

    }

    private void  initListener(){
        btnRun.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String rdbtnSelect = rdbtn.getSelection().getActionCommand();
//                System.out.println("radio button:"+rdbtnSelect);
//
//                int comboBoxSelectedIndex =comboBox.getSelectedIndex();
//                String comboBoxSelect=(String)comboBox.getSelectedItem();
//                System.out.println("combo box select:"+comboBoxSelect+", index:"+comboBoxSelectedIndex);
//
//                String Pass=new String(pw.getPassword());
//                System.out.println("pw:"+Pass);
//
//                String LeftTextArea=textAreaWest.getText();
//                System.out.println("Plain:\n"+LeftTextArea);
//
//                System.out.println("寬:"+getWidth());
//                System.out.println("高:"+getHeight());
                //以上測試用
                EncryptMode=rdbtn.getSelection().getActionCommand().equals("encrypt");
                int EncryptionType =comboBox.getSelectedIndex();

                if(EncryptMode&&textAreaWest.getText().equals(""))
                    JOptionPane.showMessageDialog(Encrypt.this, "明文為空");
                else if(!EncryptMode&&textAreaEast.getText().equals(""))
                    JOptionPane.showMessageDialog(Encrypt.this, "密文為空");
                else {
                    switch(EncryptionType) {
                        case 0:
                            DES();
                            break;
                        case 1:
                            System.out.println("Unfinished");
                            break;
                        case 2:
                            XOR();
                            break;
                        case 3:
                            Caesar();
                            break;
                    }
                }
            }
        });

        btnClose.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Main==null)
                    System.exit(0);
                else
                {
                    Main.setVisible(true);
                    dispose();
                }
            }
        });

        open.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser =new JFileChooser();
                fileChooser.setCurrentDirectory(path);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
                fileChooser.setFileFilter(filter);
                if(fileChooser.showOpenDialog(Encrypt.this)==JFileChooser.APPROVE_OPTION) {
                    try {
                        path = fileChooser.getSelectedFile();
                        File file=fileChooser.getSelectedFile();
                        long fileSize = file.length();
                        if (fileSize > Integer.MAX_VALUE) {
                            JOptionPane.showMessageDialog(Encrypt.this, "檔案過大");
                        }
                        else {
                            FileInputStream fileInputStream = new FileInputStream(file);
                            buffer = new byte[(int) fileSize];
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                            bufferedInputStream.read(buffer);
                            //textAreaWest.setText(new String(buffer,"GB2312"));
                            //textAreaWest.setText(new String(buffer,"UTF-16"));
                            textAreaWest.setText(new String(buffer,Encode(buffer)));
                            fileInputStream.close();
                        }
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(Encrypt.this, "檔案不存在");
                        //e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        save.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser =new JFileChooser();
                fileChooser.setCurrentDirectory(path);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
                fileChooser.setFileFilter(filter);
                if(fileChooser.showSaveDialog(Encrypt.this)==JFileChooser.APPROVE_OPTION) {
                    try {
                        if(textAreaEast.getText().length()>0) {
                            FileWriter fileWriter= new FileWriter(fileChooser.getSelectedFile());
                            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
                            bufferedWriter.write(textAreaEast.getText());
                            bufferedWriter.flush();
                            bufferedWriter.close();
//                            fileWriter.write(textAreaEast.getText());
//                            fileWriter.flush();
                            fileWriter.close();
                        }
                        else {
                            System.out.println("空的");
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


    }

    private String byte2Hex(byte[] arg_bteArray) {
        int intStringLength = arg_bteArray.length;
        StringBuilder objSb = new StringBuilder(intStringLength * 2);
        for (byte anArg_bteArray : arg_bteArray) {
            int intTemp = (int) anArg_bteArray;
            //負數需要轉成正數
            if (intTemp < 0) {
                intTemp = intTemp + 256;
            }
            // 小於0F需要補0
            if (intTemp < 16) {
                objSb.append("0");
            }
            objSb.append(Integer.toString(intTemp, 16));
        }
        return objSb.toString();
    }

    private byte[] hex2Byte(String arg_strHexString) {
        try{
            byte[] arrByteDAta = arg_strHexString.getBytes();
            int intStringLength = arrByteDAta.length;
            byte[] aryRetuenData = new byte[intStringLength / 2];
            for (int i = 0; i < intStringLength; i = i + 2)
                aryRetuenData[i / 2] =  (byte)Integer.parseInt(new String(arrByteDAta, i, 2), 16);
            return aryRetuenData;
        }
        catch (StringIndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(Encrypt.this, "密文格式不符合");
        }
        return null;
    }

    private void DES(){
        String key=new String(pw.getPassword());
        if(key.length()==0||key.length()%8!=0)
            JOptionPane.showMessageDialog(Encrypt.this, "DES金鑰長度需為8的倍數");
        else
        {
            try {
                byte[] contain=EncryptMode?textAreaWest.getText().getBytes():textAreaEast.getText().getBytes();
                SecureRandom random = new SecureRandom();
                DESKeySpec desKey = new DESKeySpec(key.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey securekey = keyFactory.generateSecret(desKey);
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(EncryptMode?Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE, securekey, random);
                if(EncryptMode) {
                    byte[] Temp = cipher.doFinal(contain);
                    textAreaEast.setText(byte2Hex(Temp));
                }
                else {
                    byte[] Temp=hex2Byte(textAreaEast.getText());
                    if(Temp!=null)
                        textAreaWest.setText(new String(cipher.doFinal(Temp)));
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(Encrypt.this, "解密失敗");
                //exception.printStackTrace();
            }
        }
    }

    private void XOR() {
        String key=new String(pw.getPassword());
        char[] chars =EncryptMode?textAreaWest.getText().toCharArray():textAreaEast.getText().toCharArray();
        for(int c=0;c<chars.length;c++)
            chars[c]=(char)(key.charAt(c%key.length())^chars[c]);
//        byte[] chars =EncryptMode?textAreaWest.getText().getBytes():hex2Byte(textAreaEast.getText());
//        byte[] keys =key.getBytes();
//        for(int c=0;c<chars.length;c++) {
//            chars[c]=(byte)(keys[c%keys.length]^chars[c]);
//        }
//        if(EncryptMode)
//            textAreaEast.setText(byte2Hex(chars));
        if(EncryptMode)
            textAreaEast.setText(new String(chars));
        else
            textAreaWest.setText(new String(chars));
    }

    private void Caesar(){
        try{
            int key=Integer.parseInt(new String(pw.getPassword()));
            if(key>=26||key<=-26)
                key%=26;
            StringBuilder content;
            if(!EncryptMode) {
                key=-key;
                content=new StringBuilder(textAreaEast.getText());
            }
            else {
                content=new StringBuilder(textAreaWest.getText());
            }
            for(int c=0;c<content.length();c++) {
                char t=content.charAt(c);
                if(Character.isLetter(t)&&t<=126) {
                    int temp=(t-(t<='Z'?'A':'a')+key)%26;
                    temp=temp<0?temp+26:temp;
                    content.setCharAt(c,(char)(temp+(t<='Z'?'A':'a')));
                }
            }
            if(EncryptMode)
                textAreaEast.setText(content.toString());
            else
                textAreaWest.setText(content.toString());
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(Encrypt.this, "Caesar密碼系統金鑰為0~25之整數");
        }
    }

    private String Encode(byte[] a){
        if(a[0]==(byte)0xEF&&a[1]==(byte)0xBB&&a[2]==(byte)0xBF)
            return "UTF-8";
        else if(a[0]==(byte)0xFE&&a[1]==(byte)0xFF)
            return "UTF-16";
        else if(a[0]==(byte)0xFF&&a[1]==(byte)0xFE)
            return "UTF-16";
        else
            return "Big5";
    }

}
