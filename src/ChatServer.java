// ChatServer.java
// A simple TCP chat server using Java sockets
// It accepts multiple clients and broadcasts messages

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started on port 5000");

            while (true) {
                Socket socket = serverSocket.accept();
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

    public static void removeClient(ClientHandler client) {
        clientHandlers.remove(client);
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
        if (out != null) {
            out.println(message);
        }
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        
            System.out.println(name + " joined the chat");
            ChatServer.broadcast(name + " has joined the chat!", this);   

            String message;
            while ((message = in.readLine()) != null) {
                ChatServer.broadcast(name + ": " + message, this);
            }

        } catch (IOException e) {
            System.out.println(name + " disconnected");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ChatServer.removeClient(this);
            ChatServer.broadcast(name + " has left the chat.", this);
        }
    }
}
