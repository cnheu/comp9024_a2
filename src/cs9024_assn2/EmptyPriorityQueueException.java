package cs9024_assn2;
/**
 * Thrown when a priority queue cannot fulfill the requested operation
 * because it is empty.
 * @author Roberto Tamassia
 */
public class EmptyPriorityQueueException  extends RuntimeException {
  public EmptyPriorityQueueException (String message) {
    super (message);
  }
}
