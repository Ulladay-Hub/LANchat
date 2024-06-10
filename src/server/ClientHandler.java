package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private LANchatServer server;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket clientSocket, LANchatServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            // Setup input and output streams for communication with the client
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Listen for messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message received from client: " + message);
                
                // Broadcast the message to all clients (except the sender)
                server.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            System.err.println("Error handling client connection: " + e.getMessage());
        } finally {
            // Clean up resources and remove client from the server's client list
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection: " + e.getMessage());
            }
            server.removeClient(this);
        }
    }

    // Method to send a message to this client
    public void sendMessage(String message) {
        out.println(message);
    }

    // Getter method to retrieve the client socket
    public Socket getClientSocket() {
        return clientSocket;
    }
}
