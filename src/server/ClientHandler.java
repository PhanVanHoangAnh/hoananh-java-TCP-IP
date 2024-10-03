package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private Server server;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Nhận username và password từ client
            String username = in.readLine();
            String password = in.readLine();

            // Xác thực và trả về kết quả
            if (server.authenticate(username, password)) {
                out.println("OK");
            } else {
                out.println("FAIL");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
