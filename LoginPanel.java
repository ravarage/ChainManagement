//*****************************************************************
// LoginPanel.java Author: Ravyar Sarbast
//
// Jpanel contain login
//*****************************************************************

package RavyarSarbastTahir;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final JButton login;
    JTextField username;
    JPasswordField password;
    public LoginPanel() {

        setLayout(new GridLayout(3, 1, 10, 10));
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

        add(username);
        add(password);

        JPanel bottomPanel = new JPanel();

        login = new JButton("Login");

        login.setBackground(new Color(76, 175, 80));

        bottomPanel.add(login);
        add(bottomPanel);
    }

    public JButton getLogin() {
        return login;
    }
}
