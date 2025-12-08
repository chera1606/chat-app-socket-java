import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {

            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = serverInput.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }

        } catch (IOException e) {
            System.out.println("Cannot connect to server. Make sure the server is running.");
            e.printStackTrace();
        }
    }
}


