package fi.utu.tech.assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class Client {
    public static final int PORT = 20540;


    private enum Action {
        OFF, ON, QUERY
    };



    public static void main(String[] args) {

        Random rnd = new Random();
        String serverAddress = "localhost";
        try (Socket socket = new Socket(serverAddress, PORT)){
            System.out.println("Connected to server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            
            //writer.println("LIGHT;QUERY;0");
            //String response = reader.readLine();
            //System.out.println("Server response: " + response);

            // Send message to server
            for (int i = 0; i < 1001; i++) {
                int id = rnd.nextInt(5); 
                Action action = Action.values()[rnd.nextInt(2)];
                writer.println("LIGHT;"+action.toString()+";"+id);
                System.out.println("Action sent");
                if (action.toString() == "QUERY") {
                    String response = reader.readLine();
                    System.out.println("Server response: " + response);
                }
            }
            
            writer.println("quit");
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}