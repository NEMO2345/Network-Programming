package CauHoi2;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 9999)) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream serverOutput = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.print("Nhập đường dẫn của file: ");
            String filePath = userInput.readLine();

            serverOutput.writeBytes(filePath + "\n");

            String result;
            while ((result = serverInput.readLine()) != null) {
                System.out.println("Server hiển thị:\n" + result);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
