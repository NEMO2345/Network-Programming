package CauHoi1;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9999);
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData()).trim();
            String[] words = sentence.split("\\s+");
            
            StringBuilder result = new StringBuilder();
            result.append("Xâu vừa nhập có ").append(words.length).append(" từ.\n");
            
            for (String word : words) {
                int count = 0;
                for (String w : words) {
                    if (word.equalsIgnoreCase(w)) {
                        count++;
                    }
                }
                result.append(word).append(" số lần xuất hiện ").append(count).append(".\n");
            }
            
            byte[] sendData = result.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
                    receivePacket.getPort());
            serverSocket.send(sendPacket);
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
