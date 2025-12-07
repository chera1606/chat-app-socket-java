import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started... Waiting for clients...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client connected!");

                ClientHandler client = new ClientHandler(socket);
                clientHandlers.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String name;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("Enter your name: ");
            name = in.readLine();
            System.out.println(name + " joined the chat!");

            String message;
            while ((message = in.readLine()) != null) {
                ChatServer.broadcast(name + ": " + message, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
