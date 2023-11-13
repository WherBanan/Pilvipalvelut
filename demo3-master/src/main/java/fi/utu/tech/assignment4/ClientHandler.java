package fi.utu.tech.assignment4;

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
                if (receivedMessage.equals("Van tuu trii")) {
                    writer.println("Copy");
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