import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileCreator {
    public static void main(String[] args) {
        String filename1 = "test1.txt";
        String content1 = "I need full mark please";
        String filename2 = "test2.txt";
        String content2 = "can you give me full mark";
        String filename3 = "test3.txt";
        String content3 = "you will give me full mark is this right";
        // build files 
        bulidfile(filename1, content1);
        bulidfile(filename2, content2);
        bulidfile(filename3, content3);
        
    }
    static void bulidfile(String filename, String content){
        File file = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
