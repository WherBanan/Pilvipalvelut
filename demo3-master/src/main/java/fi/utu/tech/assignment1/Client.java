package fi.utu.tech.assignment1;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        String serverAddress = "localhost";
        try (Socket socket = new Socket(serverAddress, PORT)){
            System.out.println("Connected to server");

        
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
