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
    private Hub hub;

    // Creating new thread who handles clients
    public ClientHandler(Socket client, Hub hub) {
        this.clientSocket = client;
        this.hub = hub;

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

                // Handle the message
                if (receivedMessage != null && receivedMessage.startsWith("LIGHT;")) {
                    String[] parts = receivedMessage.split(";");
                    if (parts.length == 3) {
                        String command = parts[1];
                        int id = Integer.valueOf(parts[2]);

                        // Handle the command
                        switch (command) {
                            case "ON":
                                hub.turnOnLight(id);
                                break;
                            case "OFF":
                                hub.turnOffLight(id);
                                break;
                            case "QUERY":
                                sendLightStatus();
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
    // Send the status of all light to the client
            private void sendLightStatus() {
                StringBuilder status = new StringBuilder();
                for (Light light : hub.getLights()) {
                    status.append(light.getId()).append(":").append(light.isPowerOn() ? "ON" : "OFF").append(";");
                }
                writer.println(status.toString());
            }
}