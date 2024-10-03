package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public void startClient() {
        try (Socket socket = new Socket("localhost", 1234)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter your name: ");
            String username = console.readLine();
            System.out.println("Enter your password: ");
            String password = console.readLine();

            // Gửi username và password đến server
            out.println(username);
            out.println(password);

            // Nhận kết quả từ server
            String response = in.readLine();
            System.out.println("Server responded: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}
