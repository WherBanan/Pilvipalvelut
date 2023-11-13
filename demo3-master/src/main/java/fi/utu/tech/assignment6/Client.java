package fi.utu.tech.assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import java.io.PrintWriter;
import java.io.InputStreamReader;

public class Client {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        String serverAddress = "localhost";
        try (Socket socket = new Socket(serverAddress, PORT)){
            System.out.println("Connected to server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send message to server
            for (int i = 0; i < 2; i++) {
                writer.println("LIGHT;ON;"+i);
                //String response = reader.readLine();
                //System.out.println("Server response: " + response);
            }
            
            writer.println("quit");
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}