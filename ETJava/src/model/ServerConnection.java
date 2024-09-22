package model;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;

    private Gson gson;

    public ServerConnection() {
        gson = new Gson();
    }

    public OpMove getOptimalMove(PureGame game) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            // Step 1: Open a new socket connection for each request
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);

            // Step 2: Convert PureGame object to JSON and send it to the server
            String jsonGameState = gson.toJson(game);
            System.out.println("Preparing to send game state to server: " + jsonGameState);

            out.println(jsonGameState);
            System.out.println("Sent game state to server: " + jsonGameState);

            // Step 3: Wait for the server's response (OpMove) using readLine()
            String response = in.readLine();

            if (response == null) {
                System.err.println("Server returned null or empty response.");
                return null;
            }

            System.out.println("Received response from server: " + response);

            // Step 4: Convert the JSON response to an OpMove object and return
            OpMove move = gson.fromJson(response, OpMove.class);
            System.out.println("Converted response to OpMove: opX=" + move.opX() + ", opRotate=" + move.opRotate());

            return move;

        } catch (IOException e) {
            System.err.println("Error during communication with server: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 5: Ensure the connection is closed after each request
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (socket != null) socket.close();
                System.out.println("Connection closed after request.");
            } catch (IOException e) {
                System.err.println("Error closing the connection: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }
}

