//*****************************************************************
// SQLScreen.java Author: Ravyar Sarbast
//
// to write setting for database connection
//*****************************************************************


package RavyarSarbastTahir;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

public class SQLScreen extends JPanel {
    private final JButton login;
    JTextField username,url,dbname;
    JFormattedTextField port;
    JPasswordField password;
    public SQLScreen( JFrame frame) {

        setLayout(new GridLayout(6, 1, 10, 10));
        CompoundBorder myBorder = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createLineBorder(Color.BLACK, 0));
        setBorder(myBorder);
        username= new JTextField(40);
        username.setBorder(myBorder);
        TextPrompt placeholder = new TextPrompt("username", username);
        placeholder.setForeground(Color.BLACK);
        placeholder.setSize(username.getSize());
        Color myColor = new Color(202, 255, 227);
        username.setBackground(myColor);
        password = new JPasswordField();
        password.setBackground(myColor);

        password.setBorder(myBorder);
        TextPrompt placeholderp = new TextPrompt("password", password);
        placeholderp.setForeground(Color.BLACK);
        placeholderp.setSize(username.getSize());
        dbname= new JTextField(40);
        dbname.setBorder(myBorder);
        TextPrompt placeholderdbname = new TextPrompt("Database name", dbname);
        placeholderdbname.setForeground(Color.BLACK);
        placeholderdbname.setSize(dbname.getSize());
        dbname.setBackground(myColor);

        url= new JTextField(40);
        url.setBorder(myBorder);
        TextPrompt placeholderurl = new TextPrompt("Host", url);

        placeholderurl.setForeground(Color.BLACK);
        placeholderurl.setSize(url.getSize());
        url.setBackground(myColor);
        NumberFormat integerFieldFormatter = NumberFormat.getIntegerInstance();
        integerFieldFormatter.setGroupingUsed(false);
        port= new JFormattedTextField(integerFieldFormatter);

        port.setBorder(myBorder);
        TextPrompt placeholderport = new TextPrompt("Port", port);

        placeholderport.setForeground(Color.BLACK);
        placeholderport.setSize(port.getSize());
        port.setBackground(myColor);

        add(username);
        add(password);
        add(dbname);
        add(url);
        add(port);

        JPanel bottomPanel = new JPanel();

        login = new JButton("Save && Close");

        login.setBackground(new Color(76, 175, 80));

        bottomPanel.add(login);
        add(bottomPanel);
        XMLFile xml = new XMLFile();
        try {
            String[] values  = xml.read();
            username.setText(values[0]);
            password.setText(values[1]);
            dbname.setText(values[2]);
            url.setText(values[3]);
            port.setText(values[4]);




        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                xml.save(username.getText().strip(),(String.valueOf(password.getPassword())).strip(),dbname.getText().strip(),url.getText().strip(),port.getText().strip());
                try{
                    DBConnection dbConnection = new DBConnection();
                    JOptionPane.showMessageDialog(SQLScreen.this,"Setting Saved and  connected to the database");

                    frame.dispose();
                }
                catch (Exception e){

                    JOptionPane.showMessageDialog(SQLScreen.this,"Setting Saved but cant connect to the database");
                }

            }
        });


    }


}
