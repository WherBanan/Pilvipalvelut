package fi.utu.tech.assignment6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        Hub hub = new Hub();

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Wait for client connection");

            // Accept connetions
            while (true) {
                Socket client = server.accept();
                System.out.println(
                    "Connection from" + client.getInetAddress() + " port " + client.getPort());

                    ClientHandler clientHandler = new ClientHandler(client, hub);
                    clientHandler.start();
                    System.out.println("Starting client");
            }
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
