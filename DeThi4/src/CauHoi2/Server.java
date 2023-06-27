package CauHoi2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(9999)) {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String fileName = new String(receivePacket.getData()).trim();

            String fileContent = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(fileName)));

            StringBuilder result = new StringBuilder();
            String[] students = fileContent.split("\n");
            double classTotal = 0;
            int studentCount = students.length;

            for (String student : students) {
                String[] data = student.split(",");
                String name = data[0];
                double average = (Double.parseDouble(data[1]) + Double.parseDouble(data[2])) / 2;
                result.append("Sinh viên: ").append(name).append(", Điểm trung bình: ").append(average).append("\n");
                classTotal += average;
            }

            double classAverage = classTotal / studentCount;
            result.append("Điểm trung bình của lớp: ").append(classAverage).append("\n");

            result.append("Sinh viên có điểm trung bình cao hơn hoặc bằng điểm trung bình của lớp:\n");
            for (String student : students) {
                String[] data = student.split(",");
                String name = data[0];
                double average = (Double.parseDouble(data[1]) + Double.parseDouble(data[2])) / 2;
                if (average >= classAverage) {
                    result.append(name).append("\n");
                }
            }

            byte[] sendData = result.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(),
                    receivePacket.getPort());
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
