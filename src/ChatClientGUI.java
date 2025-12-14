import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {
    static String username;

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Login");
        JTextField usernameField = new JTextField();
        JButton loginButton = new JButton("Login");

        loginFrame.setLayout(null);
        usernameField.setBounds(50, 50, 200, 30);
        loginButton.setBounds(50, 100, 100, 30);

        loginFrame.add(usernameField);
        loginFrame.add(loginButton);
        loginFrame.setSize(300, 200);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            username = usernameField.getText();
            loginFrame.dispose();
            try {
                Socket socket = new Socket("localhost", 5000);
                System.out.println("Connected to server as " + username);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
