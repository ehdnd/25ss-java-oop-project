package tensor;


/** 역행렬 불가(행렬식 0) */
public class SingularMatrixException extends RuntimeException {
  public SingularMatrixException() {
    super("Matrix is singular; inverse undefined.");
  }
}