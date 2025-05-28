package tensor;

import java.util.List;

interface Matrix {

  // +. MatrixValue getter
  List<List<Scalar>> getMatrixValue();

  Scalar get(int row, int col);

  void set(int index, Scalar value);

  int getRowSize();

  int getColSize();

  // 22. 행렬은 다른 행렬과 덧셈이 가능하다. (크기가 같을 때)
  void add(Matrix otherMatrix);

  // 23. 행렬은 다른 행렬과 곱셈이 가능하다. ((m x n) x (n x l) 일 떄)
  // - 다른 행렬이 왼쪽 행렬로서 곱해지는 경우와 오른쪽 행렬로서 곱해지는 경우 모두 지원
  void mul(Matrix otherMatrix);

  // 28. 전달받은 두 행렬의 덧셈이 가능하다. (크기가 같을 때)
  static Matrix add(Matrix a, Matrix b) {
    /*TODO
     * Matrix 객체 생성
     * Matrix 객체 a, b로 수정
     * Matrix 반환
     */
    return null;
  }

  // 29. 전달받은 두 행렬의 곱셈이 가능하다. ((m x n) x (n x l) 일 때)
  static Matrix mul(Matrix a, Matrix b) {
    /*TODO
     * Matrix 객체 생성
     * Matrix 객체 a, b로 수정
     * Matrix 반환
     */
    return null;
  }

  /**
   * 40. 자신이 정사각 행렬인지 여부를 판별
   */
  boolean isSquare();

  /**
   * 41. 자신이 상삼각 행렬인지 여부를 판별
   */
  boolean isUpperTriangular();

  /**
   * 42. 자신이 하삼각 행렬인지 여부를 판별
   */
  boolean isLowerTriangular();

  /**
   * 43. 자신이 단위 행렬인지 여부를 판별
   */
  boolean isIdentity();

  /**
   * 44. 자신이 영 행렬인지 여부를 판별
   */
  boolean isZero();

  // 45. 특정 두 행의 위치를 맞교환
  void swapRows(int row1, int row2);

  // 46. 특정 두 열의 위치를 맞교환
  void swapColumns(int col1, int col2);

  // 47. 특정 행에 상수배(스칼라) 곱하기
  void scaleRow(int row, Scalar factor);

  // 48. 특정 열에 상수배(스칼라) 곱하기
  void scaleColumn(int column, Scalar factor);

  // 49. 특정 행에 다른 행의 상수배를 더하기
  void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor)
      throws CloneNotSupportedException;

  // 50. 특정 열에 다른 열의 상수배를 더하기
  void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor)
      throws CloneNotSupportedException;

  // 51. 이 행렬의 RREF 행렬을 계산해 새 객체로 반환
  Matrix toReducedRowEchelonForm() throws CloneNotSupportedException;

  // 52. 이 행렬이 RREF 형태인지 여부 판별
  boolean isReducedRowEchelonForm();

  public VectorImpl getRowVector(int row);

  // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
  VectorImpl getColVector(int col);

  // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  MatrixImpl subMatrix(int startRow, int endRow, int startCol, int endCol);

  // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  MatrixImpl minor(int rowToRemove, int colToRemove);

  // 38. 행렬은 전치행렬을 구해 줄 수 있따.
  MatrixImpl transpose();

  // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
  Scalar trace();

  Scalar determinant();

  // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
  MatrixImpl inverse();
}