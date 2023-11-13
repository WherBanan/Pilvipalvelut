package fi.utu.tech.assignment2;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Wait for client connection");
            Socket client = server.accept();
            System.out.println(
                "Connection from" + client.getInetAddress() + " port " + client.getPort());

            // Read message from client
            InputStream is = client.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);

            if (bytesRead > 0) {
                String message = new String(buffer, 0, bytesRead);
                System.out.println("Message got: " + message);
            }

            client.close();
            server.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
