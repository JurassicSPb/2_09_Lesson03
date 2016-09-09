package ru.levelp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private ServerExample server;
    private Socket socket;
    private PrintWriter writer;

    public ClientHandler(ServerExample server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new PrintWriter(socket.getOutputStream());
            String input;
            while ((input = reader.readLine()) != null) {
                if (input.equals("exit")) {
                    break;
                }
                System.out.println(input);
                sendMessage(input);
            }
            server.remove(this);
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }
}
