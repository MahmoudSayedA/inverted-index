/**
 * Posting: entity class to deal as linked list
 * 
 * next: pointer to the next
 * docId: id for the document
 * dtf: document term frequency
*/
public class Posting {
    Posting next = null;
    int docId;
    int dtf = 1;
}