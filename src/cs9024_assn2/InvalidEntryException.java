package cs9024_assn2;
/**
 * Thrown when an entry is discovered to be invalid.
 * @author Eric Zamore
 */
public class InvalidEntryException  extends RuntimeException {
  public InvalidEntryException (String message) {
    super (message);
  }
}
