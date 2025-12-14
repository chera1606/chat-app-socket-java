import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClientGUI {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    private JFrame chatFrame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton exitButton;

    public ChatClientGUI() {
        showLoginWindow();
    }

    private void showLoginWindow() {
        JFrame loginFrame = new JFrame("Login");
        JTextField nameField = new JTextField();
        JButton loginButton = new JButton("Login");

        loginFrame.setLayout(new BorderLayout());
        loginFrame.add(new JLabel("Enter your username:"), BorderLayout.NORTH);
        loginFrame.add(nameField, BorderLayout.CENTER);
        loginFrame.add(loginButton, BorderLayout.SOUTH);

        loginFrame.setSize(300, 120);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = nameField.getText().trim();
            if (!username.isEmpty()) {
                name = username;
                loginFrame.dispose();
                createChatWindow();
                connectToServer();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Please enter a username!");
            }
        });

        nameField.addActionListener(e -> loginButton.doClick());
    }

    private void createChatWindow() {
        chatFrame = new JFrame("Java Chat - " + name);
        chatArea = new JTextArea();
        inputField = new JTextField();
        sendButton = new JButton("Send");
        exitButton = new JButton("Exit");

        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        chatFrame.setLayout(new BorderLayout());
        chatFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.add(sendButton);
        buttonsPanel.add(exitButton);

        bottomPanel.add(buttonsPanel, BorderLayout.EAST);
        chatFrame.add(bottomPanel, BorderLayout.SOUTH);

        chatFrame.setSize(500, 500);
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatFrame.setVisible(true);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        exitButton.addActionListener(e -> closeChat());

        chatFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeChat();
            }
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5000);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println(name);

                String msg;
                while ((msg = in.readLine()) != null) {
                    String finalMsg = msg;
                    SwingUtilities.invokeLater(() -> chatArea.append(finalMsg + "\n"));
                }

            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(chatFrame, "Cannot connect to server!"));
            }
        }).start();
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);
            inputField.setText("");
        }
    }

    private void closeChat() {
        try {
            if (out != null) {
                out.println(name + " has left the chat.");
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatFrame.dispose();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}
