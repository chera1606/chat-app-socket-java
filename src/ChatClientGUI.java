import javax.swing.*;

public class ChatClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat App");
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);

        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        frame.setLayout(null);
        chatArea.setBounds(10, 10, 360, 400);
        inputField.setBounds(10, 420, 260, 30);
        sendButton.setBounds(280, 420, 90, 30);

        frame.add(chatArea);
        frame.add(inputField);
        frame.add(sendButton);

        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}


