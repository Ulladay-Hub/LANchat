package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LANchatClient {
    private static final String SERVER_ADDRESS = "localhost"; // Change to your server's IP address if needed
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        try {
            // Connect to the LANchat server
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to LANchat server");

            // Setup input and output streams for communication with the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Listen for messages from the server
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message from server: " + message);
            }

            // Send messages typed by the user to the server
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);
            }

            // Close the socket and streams when done
            socket.close();
            out.close();
            in.close();
            consoleInput.close();
        } catch (IOException e) {
            System.err.println("Error connecting to LANchat server: " + e.getMessage());
        }
    }
}
