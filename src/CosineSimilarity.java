import java.util.HashMap;
import java.util.Map;

public class CosineSimilarity {
/**
     * Method to calculate the cosine similarity between two documents
     * @param doc1
     * @param doc2
     * @return Cosine Similarity
     */
    public static double calculateCosineSimilarity(String doc1, String doc2) {
        // Compute the term frequency vectors for each document
        Map<String, Integer> tfDoc1 = getTermFrequencyVector(doc1);
        Map<String, Integer> tfDoc2 = getTermFrequencyVector(doc2);
        // Compute the dot product
        double dotProduct = 0;
        for (String term : tfDoc1.keySet()) {
            if (tfDoc2.containsKey(term)) {
                dotProduct += tfDoc1.get(term) * tfDoc2.get(term);
            }
        }

        // Compute the magnitude of the two vectors
        double magnitudeDoc1 = getVectorMagnitude(tfDoc1);
        double magnitudeDoc2 = getVectorMagnitude(tfDoc2);

        // Compute the cosine similarity
        double cosineSimilarity = dotProduct /(magnitudeDoc1 * magnitudeDoc2);

        return cosineSimilarity;
    }
    
    /**
     * get term Frequency Vector
     * @param doc
     * @return term frquency vector
     */
    private static Map<String, Integer> getTermFrequencyVector(String doc) {
        Map<String, Integer> tfVector = new HashMap<>();
        // Initialize an empty map to hold the term frequencies
        String[] terms = doc.split("\\s+");
        // Count the frequency of each term in the document
        for (String term : terms) {
            tfVector.put(term, tfVector.getOrDefault(term, 0) + 1);
        }
        return tfVector;
    }

    /**
     * Method to compute the magnitude of a term frequency vector
     * @param vector
     * @return magnitude
     */
    private static double getVectorMagnitude(Map<String, Integer> vector) {
        // Compute the sum of the squared term frequencies
        double magnitudeSquared = 0;
        for (int freq : vector.values()) {
            magnitudeSquared += freq * freq;
        }
        // Compute the square root of the sum of squared term frequencies to get the magnitude
        return Math.sqrt(magnitudeSquared);
    }
}