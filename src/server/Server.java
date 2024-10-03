package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {

    private Connection connection;

    public Server() {
        connectToDatabase();
    }

    // Kết nối tới database
    private void connectToDatabase() {
        String url = "jdbc:mysql://127.0.0.1:3306/user_auth";
        String user = "hoanganh";
        String password = "phanhoanganh";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra username và password trong DB
    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy bản ghi
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lắng nghe kết nối từ client
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("server.Server started, listening on port 1234...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                // Tạo một thread mới để xử lý từng client
                new ClientHandler(socket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
