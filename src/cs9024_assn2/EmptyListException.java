package cs9024_assn2;
/**
 * Thrown when a list cannot fulfill the requested operation because
 * it is empty.
 * @author Roberto Tamassia
 */
public class EmptyListException  extends RuntimeException {
  public EmptyListException (String message) {
    super (message);
  }
}
