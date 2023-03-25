import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class InvertedIndex {
    private HashMap<String, DictEntry> index;

    public InvertedIndex(){
        this.index=new HashMap<>();
    }

    public HashMap<String, DictEntry> buildIndex(String[] filenames) {
        // Initialize the document ID to 1
        int docId = 1;
        // Process each file
        for (String filename : filenames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                String line;
                // Read each line of the file
                while ((line = reader.readLine()) != null) {
                    // Split the line into words seperated by spaces
                    String[] words = line.split("\\s+");
                    // Process each word
                    for (String word : words) {
                        // Convert the word to lowercase
                        word = word.toLowerCase();
                        // If the word is not already in the index, create a new entry for it
                        if (!index.containsKey(word)) {
                            index.put(word, new DictEntry());
                        }
                        // Get the entry for the word
                        DictEntry entry = index.get(word);
                        // Increment the term frequency for the entry
                        entry.term_freq++;
                        // If the entry does not already have a posting for this document, create a new
                        // posting
                        if (entry.pList == null || entry.pList.docId != docId) {
                            Posting newPosting = new Posting();
                            newPosting.docId = docId;
                            entry.doc_freq++;
                            newPosting.next = entry.pList;
                            entry.pList = newPosting;
                        } else {
                            // If the entry already has a posting for this document, increment its term
                            // frequency
                            entry.pList.dtf++;
                        }
                    }
                }
            } catch (IOException e) {
                // Handle any errors that occur while reading the file
                e.printStackTrace();
            }
            // Increment the document ID for the next file
            docId++;
        }
        // Return the completed index
        return index;
    }
    
    private void listFilesContainingWord(String word, HashMap<String, DictEntry> index) {
        // Check if the index has been built
        if(index == null){
            System.out.println("Index has not been built\nbuild it first");
            return;
        }
        // Convert the word to lowercase
        word = word.toLowerCase();
        // If the word is not in the index, print a message saying so
        if (!index.containsKey(word)) {
            System.out.println(word + " does not exist in any document.");
        } else {
            // Otherwise, print the names of the files that contain the word
            DictEntry entry = index.get(word);
            Posting posting = entry.pList;
            System.out.println(word + " appears in the following docs:");
            while (posting != null) {
                System.out.println("file" + posting.docId + ".txt");
                posting = posting.next;
            }
        }
    }

    public void search(String word){
        this.listFilesContainingWord(word, index);
    }
}
