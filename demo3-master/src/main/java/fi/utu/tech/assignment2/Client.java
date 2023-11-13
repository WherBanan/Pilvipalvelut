package fi.utu.tech.assignment2;

import java.io.IOException;
import java.net.Socket;
import java.io.OutputStream;

public class Client {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        String serverAddress = "localhost";
        try (Socket socket = new Socket(serverAddress, PORT)){
            System.out.println("Connected to server");

            // Send message to server
            String message = "Van tuu trii";
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes());
            


        
            System.out.println("Connection closed");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}