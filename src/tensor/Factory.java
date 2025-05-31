package tensor;

import java.util.List;

public class Factory {

  private Factory() {
  }

  // 1차 제출에서는 구현체가 없으므로 null 반환 또는 더미 값 사용
  public static Scalar createScalar(String value) {
    return null;
  }

  public static Scalar createScalar(String value1, String value2) {
    return null;
  }

  public static Vector createVector(List<Scalar> values) {
    return null;
  }

  public static Vector createVector(String value, int dimension) {
    return null;
  }

  public static Vector createVector(String i, String j, int dimension) {
    return null;
  }

  public static Matrix createMatrix(List<List<Scalar>> rows) {
    return null;
  }

  public static Matrix createMatrix(String value, int rowSize, int colSize) {
    return null;
  }

  public static Matrix createMatrix(String i, String j, int rowSize, int colSize) {
    return null;
  }

  public static Matrix createMatrix(String filePath) {
    return null;
  }

  public static Matrix createMatrix(int size) {
    return null;
  }
}