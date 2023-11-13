package fi.utu.tech.assignment3;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    // Creating new thread who handles clients
    public ClientHandler(Socket client) {
        this.clientSocket = client;
    }
    
    @Override
    public void run() {
        try {
            // Read message from client
            InputStream is = clientSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);

            if (bytesRead > 0) {
                String message = new String(buffer, 0, bytesRead);
                System.out.println("Message got: " + message);
            }

            clientSocket.close();
            System.out.println("Client " + clientSocket.getInetAddress() + " Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
