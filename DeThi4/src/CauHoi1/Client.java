package CauHoi1;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9999;
            
            String sentence = "Học viện Công nghệ Bưu chính Viễn thông";
            byte[] sendData = sentence.getBytes();
            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            clientSocket.send(sendPacket);
            
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            
            String result = new String(receivePacket.getData()).trim();
            System.out.println("Client hiển thị:\n" + result);
            
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
