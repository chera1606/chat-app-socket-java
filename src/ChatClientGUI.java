import javax.swing.*;

public class ChatClientGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat App");
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(chatArea);
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

