package ru.levelp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientExample {
    private static final String IP = "127.0.0.1"; //localhost
    private static final int PORT = 8080;

    public void start() {
        try {
            Socket socket = new Socket(IP, PORT);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            //Сначала отправляем логин!!!
            MessageHandler handler = new MessageHandler(socket);
            handler.start();
            while(true) {
                String message = consoleReader.readLine();
                if (message == null) {
                    break;
                }
                writer.println(message);
                writer.flush();
                if (message.equals("exit")) {
                    break;
                }
            }
            handler.stopHandler();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
