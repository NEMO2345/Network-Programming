package CauHoi2;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Server đang chạy...");
            while (true) {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client đã kết nối!");

                BufferedReader clientInput = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream clientOutput = new DataOutputStream(connectionSocket.getOutputStream());

                String filePath = clientInput.readLine();
                System.out.println("Đường dẫn nhận từ client: " + filePath);

                StringBuilder result = new StringBuilder();
                File file = new File(filePath);

                if (file.exists()) {
                    BufferedReader fileReader = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = fileReader.readLine()) != null) {
                        String[] numbers = line.split("\\s+");

                        for (String number : numbers) {
                            try {
                                int num = Integer.parseInt(number);
                                result.append(isPrime(num) ? num : 0).append(" ");
                            } catch (NumberFormatException e) {
                                // Ignore non-integer values
                            }
                        }
                    }

                    fileReader.close();

                    int secondLargest = findSecondLargest(result.toString());
                    result.append("\nMảng sau khi thay thế giá trị lớn thứ 2 thành 100:\n")
                            .append(result.toString().replaceAll(String.valueOf(secondLargest), "100"));
                } else {
                    result.append("File không tồn tại!");
                }

                clientOutput.writeBytes(result.toString() + "\n");
                connectionSocket.close();
                System.out.println("Client đã ngắt kết nối!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static int findSecondLargest(String arrayString) {
        String[] numbers = arrayString.split("\\s+");
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (String number : numbers) {
            int num = Integer.parseInt(number);
            if (num > largest) {
                secondLargest = largest;
                largest = num;
            } else if (num > secondLargest && num < largest) {
                secondLargest = num;
            }
        }

        return secondLargest;
    }
}
