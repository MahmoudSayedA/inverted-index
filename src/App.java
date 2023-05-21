import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // prepare file names
        String[] files = { "test1.txt", "test2.txt", "test3.txt" };
        // build the index
        InvertedIndex index = new InvertedIndex();
        index.buildIndex(files);
        // read phrase
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("enter word to search about:");
            String word = scanner.nextLine();
            // get results
            Map<Integer,Double>docs = index.culculateCosineSimilarity(word);
            // print results
            printSortedMap(docs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * funciont sort and print Map
     * @param docs
     */
    private static void printSortedMap(Map<Integer, Double> docs){
        // Create a list of map entries
        List<Map.Entry<Integer, Double>> entries = new ArrayList<>(docs.entrySet());

        // Sort the list based on the value of each entry reversed
        entries.sort(Comparator.comparing(Map.Entry<Integer,Double>::getValue).reversed());

        // Create a new LinkedHashMap to store the sorted entries
        Map<Integer, Double> sortedDocs = new LinkedHashMap<>();

        // Add each entry to the new map in the sorted order
        for (Map.Entry<Integer, Double> entry : entries) {
            sortedDocs.put(entry.getKey(), entry.getValue());
        }

        System.out.println("\n\n----------------- document => similarity-----------------\n");
        // Print the sorted map
        sortedDocs.forEach((key, value) -> System.out.println("document " + key + " => " + value));
        System.out.println("\n\t----------------------------------\n\n");
    }
}
