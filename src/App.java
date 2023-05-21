import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    // test postianl index on the local file
    public static void main(String[] args) throws Exception {
        System.out.println("1: test cosine localy");
        System.out.println("2: test crawl in url");
        try(Scanner sle=new Scanner(System.in)){
            int slection = 0;
            slection = sle.nextInt();

            System.out.println("enter word to search about:");
            // test similarity
            if(slection == 1){
                // prepare file names
                String[] files = { "test1.txt", "test2.txt", "test3.txt" };
                // build the index
                InvertedIndex index = new InvertedIndex();
                index.buildIndex(files);
                // read phrase
                try (Scanner scanner = new Scanner(System.in)) {
                    String word = scanner.nextLine();
                    // get results
                    Map<String,Double>docs = index.culculateCosineSimilarity(word);
                    // print results
                    printSortedMap(docs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // test crawl
            else{
                    WebCrawler webCrawler =new WebCrawler(2);
                    String url = "https://example.com/";

                    try (Scanner scanner = new Scanner(System.in)) {
                        // read query
                        String query = scanner.nextLine();
                        // index and serch
                        webCrawler.searchOnline(url, query);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    /**
     * funciont sort and print Map
     * @param docs
     */
    protected static void printSortedMap(Map<String, Double> docs){
        // Create a list of map entries
        List<Map.Entry<String, Double>> entries = new ArrayList<>(docs.entrySet());

        // Sort the list based on the value of each entry reversed
        entries.sort(Comparator.comparing(Map.Entry<String,Double>::getValue).reversed());

        // Create a new LinkedHashMap to store the sorted entries
        Map<String, Double> sortedDocs = new LinkedHashMap<>();

        // Add each entry to the new map in the sorted order
        for (Map.Entry<String, Double> entry : entries) {
            sortedDocs.put(entry.getKey(), entry.getValue());
        }

        System.out.println("\n\n----------------- documentName => similarity-----------------\n");
        // Print the sorted map
        sortedDocs.forEach((key, value) -> System.out.println( key + " => " + value));
        System.out.println("\n\t----------------------------------\n\n");
    }
}