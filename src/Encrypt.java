import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.SecureRandom;

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
        this.setBounds(100, 100, 800, 600);
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
        JRadioButton rdbtnencrypt = new JRadioButton("Encrypt",true);
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
                String rdbtnSelect = rdbtn.getSelection().getActionCommand();
                System.out.println("radio button:"+rdbtnSelect);

                int comboBoxSelectedIndex =comboBox.getSelectedIndex();
                String comboBoxSelect=(String)comboBox.getSelectedItem();
                System.out.println("combo box select:"+comboBoxSelect+", index:"+comboBoxSelectedIndex);

                String Pass=new String(pw.getPassword());
                System.out.println("pw:"+Pass);

                String LeftTextArea=textAreaWest.getText();
                System.out.println("Plain:\n"+LeftTextArea);

                System.out.println("寬:"+getWidth());
                System.out.println("高:"+getHeight());
                //以上測試用
                EncryptMode=rdbtn.getSelection().getActionCommand().equals("encrypt");
                int EncryptionType =comboBox.getSelectedIndex();
                switch(EncryptionType) {
                    case 0:
                        DES();
                        break;
                    case 1:
                        System.out.println("Unfinished");
                        break;
                    case 2:
                        System.out.println("Unfinished");
                        break;
                    case 3:
                        Caesar();
                        break;
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
                SecureRandom random = new SecureRandom();
                DESKeySpec desKey = new DESKeySpec(key.getBytes());
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                SecretKey securekey = keyFactory.generateSecret(desKey);
                Cipher cipher = Cipher.getInstance("DES");
                cipher.init(EncryptMode?Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE, securekey, random);
                if(EncryptMode) {
                    byte[] Temp = cipher.doFinal(textAreaWest.getText().getBytes());
                    textAreaEast.setText(byte2Hex(Temp));
                }
                else {
                    byte[] Temp=hex2Byte(textAreaWest.getText());
                    if(Temp!=null)
                        textAreaEast.setText(new String(cipher.doFinal(Temp)));
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(Encrypt.this, "解密失敗");
                //exception.printStackTrace();
            }
        }
    }

    private void Caesar(){
        try{
            int key=Integer.parseInt(new String(pw.getPassword()));
            if(key>=26||key<=-26)
                key%=26;
            if(!EncryptMode)
                key=-key;
            StringBuilder content =new StringBuilder(textAreaWest.getText());
            for(int c=0;c<content.length();c++) {
                char t=content.charAt(c);
                if(Character.isLetter(t)&&t<=126) {
                    int temp=(t-(t<='Z'?'A':'a')+key)%26;
                    temp=temp<0?temp+26:temp;
                    content.setCharAt(c,(char)(temp+(t<='Z'?'A':'a')));
                }
            }
            textAreaEast.setText(content.toString());
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(Encrypt.this, "Caesar密碼系統金鑰為0~25之整數");
        }
    }

}
