package fi.utu.tech.assignment6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    // Creating new thread who handles clients
    public ClientHandler(Socket client) {
        this.clientSocket = client;

        // Create new reader and writer
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        System.out.println("Running client on thread");
        try {
            // Read message from client
            String receivedMessage;
            while (true) {
                receivedMessage = reader.readLine();
                System.out.println("Message recieved: "+ receivedMessage);

                // Handle the message
                if (receivedMessage != null && receivedMessage.startsWith("LIGHT;")) {
                    String[] parts = receivedMessage.split(";");
                    if (parts.length == 3) {
                        String command = parts[1];
                        String id = parts[2];

                        // Handle the command
                        switch (command) {
                            case "ON":
                                System.out.println("Switch ON light " + id);
                                break;
                            case "OFF":
                                System.out.println("Switch OFF light " + id);
                                break;
                            case "QUERY":
                                System.out.println("Query command received");
                                break;
                            default:
                                System.out.println("Unknown command: " + command);
                                break;
                                
                        }
                    } else {
                        System.out.println("Invalid message type");
                    }
                } else if (receivedMessage.equals("quit")) {
                    clientSocket.close();
                    System.out.println("Client " + clientSocket.getInetAddress() + " Connection closed");
                    break;
                } else {
                    System.out.println("Error in reading message");
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}