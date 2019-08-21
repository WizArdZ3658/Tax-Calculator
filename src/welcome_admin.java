import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class welcome_admin extends JPanel implements ActionListener {
    private  JButton b1, b2, b3;
    JLabel l1, l2, l3, l4;
    String s1, s2, s3, s4;
    private masterwindow_admin pointer;

    public welcome_admin(masterwindow_admin parent, String uid) {
        pointer = parent;
        pointer.setTitle("Welcome");
        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        l4 = new JLabel();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select fname, mname, lname from admins where userid=\""+uid+"\"");

            if (rs.next()) {
                s1 = rs.getString(1);
                s2 = rs.getString(2);
                s3 = rs.getString(3);
            }
            con.close();
        }
        catch(Exception e1){
            System.out.println(e1);
        }
        l4.setText("Welcome,");
        l4.setBounds(50,50,400,70);
        l4.setFont(l4.getFont().deriveFont(70f));
        add(l4);
        l1.setText(s1);
        l1.setBounds(50,120,400,70);
        l1.setFont(l1.getFont().deriveFont(70f));
        add(l1);
        int yp = 190;
        if ("".equals(s2) || s2.equals(null)){
        }
        else{
            l2.setText(s2);
            l2.setBounds(50,yp,400,70);
            l2.setFont(l2.getFont().deriveFont(70f));
            add(l2);
            yp+=70;
        }
        l3.setText(s3);
        l3.setBounds(50,yp,400,70);
        l3.setFont(l3.getFont().deriveFont(70f));
        add(l3);

        b1 = new JButton("Update information");
        b1.addActionListener(this);
        b1.setActionCommand("update");
        b1.setBounds(87,760,150,30);

        b2 = new JButton("Show statistics");
        b2.addActionListener(this);
        b2.setActionCommand("stats");
        b2.setBounds(325,760,150,30);

        b3 = new JButton("Log out");
        b3.addActionListener((e -> parent.show("login")));
        b3.setBounds(562,760,150,30);

        add(b1);
        add(b2);
        add(b3);
        setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("update")){
            pointer.setTitle("Update");
            pointer.show("update_admin");
        }
        else if (cmd.equals("stats")){
            pointer.setTitle("Statistics");
            pointer.show("stats");
        }
    }
}
