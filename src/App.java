import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String[] files = { "test1.txt", "test2.txt", "test3.txt" };
        InvertedIndex index = new InvertedIndex();
        index.buildIndex(files);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("enter word to search about:");
            String word = scanner.next();
            index.search(word);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
