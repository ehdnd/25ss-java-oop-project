package tensor;

public class DimensionMismatchException extends RuntimeException {

  public DimensionMismatchException(String expected, String actual) {
    super("Dimension mismatch - expected " + expected + ", but got " + actual);
  }

  public DimensionMismatchException(int expected, int actual) {
    super("Dimension mismatch - expected " + expected + ", but got " + actual);
  }
}
