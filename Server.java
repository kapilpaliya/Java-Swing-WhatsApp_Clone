
// Import And Load Classes.
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server extends JFrame implements ActionListener {
    // Variable Declaration.
    ImageIcon LOGO = new ImageIcon(ClassLoader.getSystemResource("image/whatsapp.png")),
            IMAGE = new ImageIcon(ClassLoader.getSystemResource("image/teacher.png")),
            VIDEO = new ImageIcon(ClassLoader.getSystemResource("image/video-call.png")),
            CALL = new ImageIcon(ClassLoader.getSystemResource("image/call.png")),
            MANU = new ImageIcon(ClassLoader.getSystemResource("image/list.png"));
    Image b_IMAGE = IMAGE.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT),
            b_VIDEO = VIDEO.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT),
            b_CALL = CALL.getImage().getScaledInstance(40,40,Image.SCALE_DEFAULT),
            b_MANU = MANU.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
    JPanel Top, Center; JTextField input; JButton send;
    JLabel img, name, active, video, call, manu;
    Box vartical = Box.createVerticalBox();
    ServerSocket server; DataInputStream input1;
    Socket client; DataOutputStream out;
    Server(){
            // Frame Creation
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.white);
            setBounds(300,100,400,600);
            setIconImage(LOGO.getImage());
            setTitle("WhatsApp");
            setLayout(null);



            // Create Panel To Store Top Component.
            Top = new JPanel();
            Top.setLayout(null);
            Top.setBounds(0,0,400,70);
            Top.setBackground(new Color(27,84,94));



            // Add User Image In Top Component.
            img = new JLabel(new ImageIcon(b_IMAGE));
            img.setBounds(5,10,50,50);
            Top.add(img);



            // Add User_Name Image In Top Component.
            name = new JLabel("Mr.Black");
            name.setFont(new Font("Arial",Font.BOLD,16));
            name.setForeground(Color.white);
            name.setBounds(60,8,70,30);
            Top.add(name);



            // Add User_Status In Top Component.
            active = new JLabel("Online");
            active.setFont(new Font("Arial",Font.BOLD,12));
            active.setForeground(Color.white);
            active.setBounds(60,30,100,30);
            Top.add(active);



            // Add Video Image In Top Component.
            video = new JLabel(new ImageIcon(b_VIDEO));
            video.setBounds(225,10,50,50);
            Top.add(video);



            // Add Call Image In Top Component.
            call = new JLabel(new ImageIcon(b_CALL));
            call.setBounds(275,10,50,50);
            Top.add(call);



            // Add 3 Dot Menu Icon Image In Top Component.
            manu = new JLabel(new ImageIcon(b_MANU));
            manu.setBounds(330,10,50,50);
            Top.add(manu);



            // Add Center Container To Store Name.
            Center = new JPanel();
            Center.setBounds(5,75,375,440);
            Center.setLayout(new BorderLayout());



            // Add Input TextField In Bottom Of Frame.
            input = new JTextField();
            input.setFont(new Font("Arial", Font.BOLD, 16));
            input.setBorder(BorderFactory.createLineBorder(Color.black));
            input.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    active.setText("typing...");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    active.setText("Online");
                }
            });
            input.setBounds(10,520,280,40);
            add(input);



            // Add Send Button In Bottom Of Frame.
            send = new JButton("Send");
            send.setFont(new Font("Arial", Font.BOLD, 16));
            send.addActionListener(this);
            send.setBounds(295,520,80,40);
            send.setBackground(new Color(27,84,94));
            send.setForeground(Color.white);
            send.setBorder(null);
            add(send);



            add(Center);
            add(Top);
            setVisible(true);
            setConnection();
        }

    // Override The ActionListener Methods.
    public void actionPerformed(ActionEvent e){
            // Declaration Of Send Button.
            if(e.getSource()==send){
                try {
                    String str = input.getText();

                    JPanel right = new JPanel();
                    right.setLayout(new BorderLayout());
                    right.add(setStyle(str), BorderLayout.LINE_END);

                    vartical.add(right);
                    vartical.add(Box.createVerticalStrut(15));

                    Center.add(vartical, BorderLayout.PAGE_START);


                    input.setText(null);
                    repaint();
                    invalidate();
                    revalidate();
                    out.writeUTF(str);
                }
                catch(Exception z){
                    z.printStackTrace();
                }
            }
        }

    // Create User Define Function To Design Te Message.
    public static JPanel setStyle(String str){
            JPanel x = new JPanel();
            x.setLayout(new BoxLayout(x,BoxLayout.Y_AXIS));


            JLabel l = new JLabel("<html><p style='width:150px'>"+str+"</p></html>");
            l.setFont(new Font("Arial",Font.BOLD,16));
            l.setBackground(new Color(37,211,102));
            l.setBorder(new EmptyBorder(5,5,5,20));
            l.setOpaque(true);
            x.add(l);


            Calendar cal = Calendar.getInstance();
            SimpleDateFormat d = new SimpleDateFormat("HH:mm");
            JLabel time = new JLabel(d.format(cal.getTime()));
            x.add(time);


            return x;
        }

    // Create User Define Function To Describe The Server Or Client Connection.
    public void setConnection(){
            try{
                server = new ServerSocket(2345);
                while(true){
                    client = server.accept();
                    System.out.println("Connected");
                    input1 = new DataInputStream(client.getInputStream());
                    out = new DataOutputStream(client.getOutputStream());
                    while(true){
                        String takeInput = input1.readUTF();

                        JPanel left = new JPanel();
                        left.setLayout(new BorderLayout());
                        left.add(setStyle(takeInput),BorderLayout.LINE_START);

                        vartical.add(left);
                        invalidate();
                        revalidate();
                        validate();
                    }
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        Server n  = new Server();
        n.setConnection();
    }
}
