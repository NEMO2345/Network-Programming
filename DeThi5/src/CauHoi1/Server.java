package CauHoi1;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9999)) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String dataString = new String(receivePacket.getData()).trim();
            String[] dataArray = dataString.split(",");

            StringBuilder result = new StringBuilder();

            // Liệt kê các phần tử xuất hiện đúng 2 lần
            result.append("Các phần tử xuất hiện đúng 2 lần trong dãy: ");
            for (int i = 0; i < dataArray.length; i++) {
                int count = 0;
                for (int j = 0; j < dataArray.length; j++) {
                    if (i != j && dataArray[i].equals(dataArray[j])) {
                        count++;
                    }
                }
                if (count == 1) {
                    result.append(dataArray[i]).append(" ");
                }
            }
            result.append("\n");

            // Tìm và tính tổng các số nguyên tố
            double primeSum = 0;
            result.append("Các số nguyên tố trong dãy: ");
            for (String data : dataArray) {
                double number = Double.parseDouble(data);
                boolean isPrime = number > 1 && java.math.BigInteger.valueOf((long) number).isProbablePrime(50);
                if (isPrime) {
                    primeSum += number;
                    result.append((int) number).append(" ");
                }
            }
            result.append("\n");
            result.append("Tổng các số nguyên tố: ").append(Math.round(primeSum * 10) / 10.0);

            byte[] sendData = result.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
                    receivePacket.getPort());
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
