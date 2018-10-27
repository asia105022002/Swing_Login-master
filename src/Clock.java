import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class Clock extends JFrame{

    private JPanel mainPanel;
    private JButton startBtn;
    private JButton stopButton;
    private JButton exitButton;
    private JPanel clockPanel;
    private JLabel jLabel0;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel[] jLabels;
    private ImageIcon[] numbersImage=new ImageIcon[10];
    private Timer timer;
    private LocalDateTime time = LocalDateTime.now().with(LocalDateTime.MIN);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
    private MainFrame Main;

    public static void main(String[] args) {
        Clock clock=new Clock();
        clock.setVisible(true);
    }

    public Clock() {
        init();
    }

    public Clock(MainFrame Main) {
        this.Main=Main;
        init();
    }

    private void init() {
        setTitle("Clock");
        setSize(420, 180);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setResizable(false);
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
        jLabels = new JLabel[]{jLabel0,jLabel1,jLabel3,jLabel4,jLabel6,jLabel7};
        for(int c = 0; c< numbersImage.length; c++) {
            numbersImage[c]=new ImageIcon("img/"+c+".png");
        }
        for(JLabel jLabel:jLabels)
            jLabel.setIcon(numbersImage[0]);
        jLabel2.setIcon(new ImageIcon("img/colon.png"));
        jLabel5.setIcon(new ImageIcon("img/colon.png"));
        initListener();
    }

    private void initListener() {
        startBtn.addActionListener(e -> {
            timer=new Timer();
            TimerTask showTime= new TimerTask(){
                @Override
                public void run() {
                    time=time.plus(1, ChronoUnit.SECONDS);
                    setClock(time.format(dateTimeFormatter));
                }
            };
            timer.schedule(showTime, 0, 1000);
        });
        stopButton.addActionListener(e -> timer.cancel());
        exitButton.addActionListener(e -> {
                    if (Main == null)
                        System.exit(0);
                    else {
                        Main.setVisible(true);
                        dispose();
                    }
                }
        );
    }

    private void setClock(String time) {
        for(int c=0;c<time.length();c++) {
            jLabels[c].setIcon(numbersImage[Character.getNumericValue(time.charAt(c))]);
        }
    }
}
