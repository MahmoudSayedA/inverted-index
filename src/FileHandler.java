import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static void writeToFile(String fileName, String data) {
        try {
            // Create a FileWriter object with the file path and name
            FileWriter writer = new FileWriter("reultfolder\\..\\resultfolder\\" + fileName);
            // Write the string to the file using the write method
            writer.write(data);
            // Close the FileWriter object to flush and release resources
            writer.close();
            System.out.println("Data written successfully to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    // for testing
    // public static void main(String[] args) {
    //     writeToFile("fake.txt", "afasdfasdf ??? ahdufhb? dfashdf");
    // }
}
