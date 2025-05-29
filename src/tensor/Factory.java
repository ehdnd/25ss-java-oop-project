package tensor;

import java.util.List;
import java.io.File;

/*TODO
 * 행렬, 벡터, 스칼라를 생성한다
 * 모든 메서드가 public static
 * 생성 자체는 구현 클래스 내부에서 담당
 * Factory의 static 메서드는 구현 클래스에 객체 생성을 요청 -> 획득한 객체를 반환만
 */

// 객체를 만드는 단일 관문

public class Factory {

  private Factory() {
  }

  public static Scalar createScalar(String value) {
    return new ScalarImpl(value);
  }

  public static Scalar createScalar(String value1, String value2) {
    return new ScalarImpl(value1, value2);
  }

  public static Vector createVector(List<Scalar> values) {
    return new VectorImpl(values);
  }

  public static Vector createVector(String value, int dimension) {
    return new VectorImpl(value, dimension);
  }

  public static Vector createVector(String i, String j, int dimension) {
    return new VectorImpl(i, j, dimension);
  }


  public static Matrix createMatrix(List<List<Scalar>> rows) {
    return new MatrixImpl(rows);
  }

  public static Matrix createMatrix(String value, int rowSize, int colSize) {
    return new MatrixImpl(value, rowSize, colSize);
  }

  public static Matrix createMatrix(String i, String j, int rowSize, int colSize) {
    return new MatrixImpl(i, j, rowSize, colSize);
  }

  public static Matrix createMatrix(File csvFile) {
    return new MatrixImpl(csvFile);
  }

  // TODO 10번 수정 이후 수정 필요
  public static Matrix createMatrix(int size) {
    return new MatrixImpl(size);
  }
}