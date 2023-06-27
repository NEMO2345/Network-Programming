package CauHoi1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9999;

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nhập dãy số thực (các số cách nhau bằng dấu phẩy): ");
            String data = scanner.nextLine();
            byte[] sendData = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String result = new String(receivePacket.getData()).trim();
            System.out.println("Client hiển thị:\n" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

