import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {
    static String username;

    public static void main(String[] args) {
        // Login window
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

            // Chat window
            JFrame chatFrame = new JFrame("Chat App");
            JTextArea chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);
            scrollPane.setBounds(10, 10, 360, 400);

            JTextField inputField = new JTextField();
            inputField.setBounds(10, 420, 180, 30);

            JButton sendButton = new JButton("Send");
            sendButton.setBounds(200, 420, 80, 30);

            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(290, 420, 80, 30);

            chatFrame.setLayout(null);
            chatFrame.add(scrollPane);
            chatFrame.add(inputField);
            chatFrame.add(sendButton);
            chatFrame.add(exitButton);

            chatFrame.setSize(400, 500);
            chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chatFrame.setVisible(true);

            try {
                // Connect to server
                Socket socket = new Socket("localhost", 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send username to server
                out.println(username);

                // Send button action
                sendButton.addActionListener(ev -> {
                    out.println(inputField.getText());
                    inputField.setText("");
                });

                // Thread to receive messages from server
                new Thread(() -> {
                    String message;
                    try {
                        while ((message = in.readLine()) != null) {
                            chatArea.append(message + "\n");
                            chatArea.setCaretPosition(chatArea.getDocument().getLength());
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }).start();

                // Exit button action
                exitButton.addActionListener(ev -> {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    chatFrame.dispose();
                });

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
