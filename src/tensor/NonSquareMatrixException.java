package tensor;

public class NonSquareMatrixException extends RuntimeException {

  public NonSquareMatrixException(int rows, int cols) {
    super("Matrix must be square, but was " + rows + "×" + cols);
  }
}
