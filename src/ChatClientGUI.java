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

            JFrame chatFrame = new JFrame("Chat App");
            JTextArea chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);
            scrollPane.setBounds(10, 10, 360, 400);

            chatFrame.setLayout(null);
            chatFrame.add(scrollPane);
            chatFrame.setSize(400, 500);
            chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chatFrame.setVisible(true);

            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(username);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}

