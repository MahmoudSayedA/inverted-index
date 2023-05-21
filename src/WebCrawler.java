import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebCrawler {
    // Set of URLs that have been visited during crawling
    private Set<String> visitedURLs;
    // Set of URLs that have been successfully indexed
    private Set<String> indexedURLs;
    // Maximum depth to which the crawler should crawl
    private int maxDepth;

    // Constructor to initialize the instance variables
    public WebCrawler(int maxDepth) {
        visitedURLs = new HashSet<>();
        indexedURLs = new HashSet<>();
        this.maxDepth = maxDepth;
    }

    /**
     * Method to crawl a given URL with a specified depth
     * and store the data of each page in the url in file named with page title
     * @param url
     * @param depth
     * @param fileMatches
     */
    private void crawl(String url, int depth, List<String> fileMatches) {
        // Stop crawling if the current depth exceeds the maximum depth
        if (depth > maxDepth) {
            return;
        }

        // Check if the URL has been visited before
        if (!visitedURLs.contains(url)) {
            // Add the URL to the visited URLs set
            visitedURLs.add(url);
            // Print the URL and depth of the current crawl
            // System.out.println("Crawling: " + url + ", Depth: " + depth);

            try {
                // Connect to the URL and get the HTML// document using Jsoup
                Document document = Jsoup.connect(url).get();
                // Extract all the links on the page
                String name = (document.title() + ".txt").
                    replaceAll("[^a-zA-Z0-9_.-]", "");
                String body = document.body().text();
                FileHandler.writeToFile(name, body);
                fileMatches.add("reultfolder\\..\\resultfolder\\" + name);
                Elements linksOnPage = document.select("a[href]");

                // Iterate over each link and recursively crawl it if it is a valid URL
                for (Element link : linksOnPage) {
                    String nextURL = link.absUrl("href");
                    if (isValidURL(nextURL)) {
                        // Recursively crawl the next URL with an incremented depth
                        crawl(nextURL, depth + 1,fileMatches);
                    }
                }

                // Add the current URL to the indexed URLs set
                indexedURLs.add(url);
                // Print the URL of the current crawl has been indexed
                System.out.println("Indexed: " + url);
            } catch (IOException e) {
                // Catch any connection or crawling errors and print the error message
                System.err.println("Error crawling " + url + ": " + e.getMessage());
            }
        }
    }

    // Method to check if a given URL is valid for crawling
    private boolean isValidURL(String url) {
        // Add additional checks here if needed, such as robots.txt validation, crawling frequency, etc.
        return !url.isEmpty() && !visitedURLs.contains(url) && !indexedURLs.contains(url);
    }

    /**
     * function to search on a query on spacific website
     * print cosine similarity of the result and ranks it 
     * @param url
     * @param query
     */
    public void searchOnline(String url, String query){
        // list of file names that match the query
        List<String>fileMatch = new ArrayList<>();
        // crawl and store the result
        this.crawl(url, 0, fileMatch);
        // convert list to array
        String[] arrayFileNames = fileMatch.toArray(new String[0]);
        // build the index
        InvertedIndex index = new InvertedIndex();
        index.buildIndex(arrayFileNames);
        // caculate similarity
        Map<String,Double>docs = index.culculateCosineSimilarity(query);
        // print results
        this.printSortedMap(docs);
    }

    // print map
    private void printSortedMap(Map<String, Double> docs){
        if(docs == null)
            return;
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
        sortedDocs.forEach((key, value) -> System.out.println( key.substring(28, key.length()) + " => " + value));
        System.out.println("\n\t----------------------------------\n\n");
    }
    public static void main(String[] args) {
        WebCrawler webCrawler =new WebCrawler(2);
        String url = "https://example.com/";
        String query = "illustrative examples";
        webCrawler.searchOnline(url, query);
    }
}