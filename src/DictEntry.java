/**
 * DictEntry: entity class 
 * 
 * doc_freq: number of documents that contain the term
 * term_freq: number of times the term is mentioned in the collection
 * pList: posting list
 */
public class DictEntry {
    int doc_freq = 0;
    int term_freq = 0;
    Posting pList = null;
}

