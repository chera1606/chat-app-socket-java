import javax.swing.*;

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
        });
    }
}



