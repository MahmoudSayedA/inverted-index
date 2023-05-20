import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                // create dumy pointer to track byteOffset
                int byteOffset = 0;
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
                            // If the entry already has a posting for this document, increment its term frequency
                            entry.pList.dtf++;
                        }
                        // add current byteOffset
                        entry.pList.byteOffset.add(byteOffset);
                        // update byteOffset to skip the word
                        byteOffset += word.length() + 1;
                    }
                    // update byteOffset to skip the new line
                    byteOffset ++;
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

    private void listFilesContainingQuery(String query, HashMap<String, DictEntry> index) {
        // Check if the index has been built
        if (index == null) {
            System.out.println("Index has not been built. Build it first.");
            return;
        }
    
        // Convert the query to lowercase and split it into individual words
        String[] words = query.toLowerCase().split("\\s+");
    
        // Check if the first word of the query is in the index
        String firstWord = words[0];
        if (!index.containsKey(firstWord)) {
            System.out.println("None of the query terms exist in any document.");
            return;
        }
    
        // Find the postings list for the first word
        DictEntry entry = index.get(firstWord);
        Posting posting = entry.pList;
    
        // Find the documents that contain all the words in the query
        List<Integer> candidateDocs = new ArrayList<>();
        for (int i = 0; i < entry.doc_freq; i++) {
            // Add the document to the list of candidate documents
            candidateDocs.add(posting.docId);
    
            // Check if the remaining words in the query occur in the document
            boolean foundMatch = true;
            // int prevPos = -1;
            for (int j = 1; j < words.length; j++) {
                String word = words[j];
                if (!index.containsKey(word)) {
                    foundMatch = false;
                    break;
                }
    
                // Find the position of the previous word in the document
                DictEntry prevEntry = index.get(words[j - 1]);
                Posting prevPosting = prevEntry.pList;
                while (prevPosting.docId != posting.docId) {
                    prevPosting = prevPosting.next;
                }
                int prevPos = prevPosting.byteOffset.get(prevPosting.dtf - 1);
    
                // Find the position of the current word in the document
                DictEntry currEntry = index.get(word);
                Posting currPosting = currEntry.pList;
                while (currPosting.docId != posting.docId) {
                    currPosting = currPosting.next;
                }
                if (!currPosting.byteOffset.contains(prevPos + firstWord.length() + 1)) {
                    foundMatch = false;
                    break;
                }
            }
    
            // Print the document if all the words in the query occur in it
            if (foundMatch) {
                System.out.println("Document " + posting.docId + " contains the query.");
            }
    
            // Move to the next document in the postings list
            if (i < entry.doc_freq - 1) {
                posting = posting.next;
            }
        }
    }

    public void search(String word){
        this.listFilesContainingQuery(word, index);
    }
}
