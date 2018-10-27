import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class IPLonginLogs extends JFrame{

    private File path =new File(System.getProperty("user.home") + "/Desktop");
    private JTextField textField1;
    private JButton button1;
    private JButton runButton;
    private JPanel mainPanel;
    private JTextField TextField;
    private File file=null;

    public static void main(String[] args) {
        IPLonginLogs ipLonginLogs=new IPLonginLogs();
        ipLonginLogs.setVisible(true);
    }

    public IPLonginLogs() {
        init();
    }

    private void initListener() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser(path);
                //fileChooser.setCurrentDirectory(InputPath);
                if(fileChooser.showOpenDialog(IPLonginLogs.this)==JFileChooser.APPROVE_OPTION) {
                    try {
                        path = fileChooser.getSelectedFile();
                        File file = fileChooser.getSelectedFile();
                        FileInputStream fileInputStream = new FileInputStream(file);
                        String path=file.toString();
                        textField1.setText(path);
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(IPLonginLogs.this, "檔案不存在");
                        path = fileChooser.getSelectedFile().getParentFile();
                    }
                }
            }
        });
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().equals(""))
                    JOptionPane.showMessageDialog(IPLonginLogs.this, "請選擇檔案");
                else {
                    file=new File(textField1.getText());
                    try {
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);
                        StringBuilder sb=new StringBuilder();
                        StringBuilder sb2=new StringBuilder();
                        String line;
                        boolean read=false;
                        Map<String, ArrayList<String>> map = new HashMap<>();
                        while((line=br.readLine())!=null) {
                            if(line.contains("LOGIN_REQUEST"))
                                read=true;
                            if(line.contains("receiveObject end")&&read) {
                                read = false;
                                sb.append("\r\n");
                            }
                            if(read) {
                                sb.append(line);
                                sb.append("\r\n");
                                if(line.contains("LOGIN logged")) {
                                    sb2.append(line + "\r\n");
                                    String team=line.substring(line.indexOf("logged in")+10,line.indexOf("@")-1);
                                    String ip=line.substring(line.indexOf("addr")+6,line.indexOf(","));
                                    if(map.containsKey(ip)){
                                        if(map.get(ip).size()>0) {
                                            if(!map.get(ip).contains(team)) {
                                                map.get(ip).add(team);
                                            }
                                        }
                                    }
                                    else{
                                        ArrayList a=new ArrayList();
                                        a.add(team);
                                        map.put(ip,a);
                                    }
                                }
                            }
                        }
                        fr.close();
                        StringBuilder sb3=new StringBuilder();
                        Set keySet = map.keySet();
                        Iterator it = keySet.iterator();
                        while(it.hasNext()){
                            String key = (String)it.next();
                            ArrayList value = map.get(key);
                            sb3.append("IP:" + key + "\t Login team:" + value+"\r\n");
                        }
                        FileWriter fw= new FileWriter(file.getParent()+"\\all.txt");
                        fw.write(sb.toString());
                        fw.flush();
                        fw.close();
                        fw= new FileWriter(file.getParent()+"\\ip_time.txt");
                        fw.write(sb2.toString());
                        fw.flush();
                        fw.close();
                        fw= new FileWriter(file.getParent()+"\\ip.txt");
                        fw.write(sb3.toString());
                        fw.flush();
                        fw.close();
                        JOptionPane.showMessageDialog(IPLonginLogs.this, "Done!");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(new File(file.getParent()+"\\ip.txt"));
                    } catch (FileNotFoundException e1) {
                        JOptionPane.showMessageDialog(IPLonginLogs.this, "檔案不存在");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(IPLonginLogs.this, "IO例外"+e1);
                    }
                }
            }
        });
    }

    private void init() {
        setTitle("PC2登入IP查詢");
        setSize(600,134);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initListener();
    }
}
