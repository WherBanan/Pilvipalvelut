package fi.utu.tech.assignment1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 20540;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            Socket customer = server.accept();
            System.out.println(
                "Connection from" + customer.getInetAddress() + " port " + customer.getPort());
            customer.close();
            server.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
