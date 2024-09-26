package model;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ServerConnection {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3000;

    private Gson gson;
    private boolean alertShown = false;

    public ServerConnection() {
        gson = new Gson();
    }

    public OpMove getOptimalMove(PureGame game) {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to server at " + SERVER_HOST + ":" + SERVER_PORT);
            alertShown = false;

            String jsonGameState = gson.toJson(game);
            System.out.println("Preparing to send game state to server: " + jsonGameState);

            out.println(jsonGameState);
            System.out.println("Sent game state to server: " + jsonGameState);

            String response = in.readLine();

            if (response == null) {
                System.err.println("Server returned null or empty response.");
                return new OpMove(0, 0); // Return default move
            }

            System.out.println("Received response from server: " + response);

            OpMove move = gson.fromJson(response, OpMove.class);
            System.out.println("Converted response to OpMove: opX=" + move.opX() + ", opRotate=" + move.opRotate());

            return move;

        } catch (IOException e) {
            System.err.println("Error during communication with server: " + e.getMessage());

            if (!alertShown) {
                int option = JOptionPane.showOptionDialog(null,
                        "Cannot connect to the server. Would you like to start the server?",
                        "Server Connection Error",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new String[]{"Start Server", "Cancel"},
                        "Start Server");

                if (option == JOptionPane.YES_OPTION) {
                    new Thread(() -> {
                        try {
                            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "ExternalJAR/TetrisServer.jar");

                            Process process = processBuilder.start();
                            System.out.println("Server starting...");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,
                                    "Failed to start the server: " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }).start();
                }
                alertShown = true;
            }

            return new OpMove(0, 0);

        } finally {
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
    }
}
