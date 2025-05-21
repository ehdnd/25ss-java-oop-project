package tensor;

/*
# 행렬의 자료구조
- 행렬은 논리적으로 스칼라 객체를 2차원 배열 구조로 관리
  - Collection 선택
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class MatrixImpl implements Matrix {

  // 행렬은 논리적으로 스칼라 객체를 2차원 배열 구조로 관리
  private List<List<Scalar>> matrixValue;

  // 06. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬 생성
  MatrixImpl(String bigDecimalString) {
  }

  // 07. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬 생성
  MatrixImpl(String i, String j) {
  }

  // 08. csv 파일로부터 m x n 행렬을 생성
  //  - comma(,): 열 구분자
  //  - 라인: 행 구분자
  MatrixImpl(File cssvFile) {
    // csv 파일의 경로를 매개변수로 받음
  }

  // 09. 2차원 배열로부터 m x n 행렬 생성
  MatrixImpl(List<List<Scalar>> dimTwoList) {
  }

  // 10. 단위 행렬 생성
  MatrixImpl(MatrixImpl matrix) {
    // 내부 단위 행렬 생성
  }

  // 11m. 특정 위치의 요소를 지정/조회할 수 있다.
  @Override
  // 11. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  public Scalar get(int index) {
    return null;
  }

  @Override
  // 11. 지정: 지정한 인덱스 위치에 Scalar 값을 설정
  public void set(int index, Scalar value) {
  }

  // 13. 행렬의 행개수를 조회
  @Override
  public int getRowSize() {
    return 0;
  }

  // 13. 행렬의 열개수를 조회
  @Override
  public int getColSize() {
    return 0;
  }

  // 14m. 값들을 2차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    return super.toString();
  }

  // 15. 객체의 동등성을 판단할 수 있다.
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public List<List<Scalar>> getMatrixValue() {
    return matrixValue;
  }

  // 22. 행렬은 다른 행렬과 덧셈이 가능하다. (크기가 같을 때)
  @Override
  public void add(Matrix otherMatrix) {
    List<List<Scalar>> other = otherMatrix.getMatrixValue();

    for (int r = 0; r < matrixValue.size(); r++) {
      for (int c = 0; c < matrixValue.get(r).size(); c++) {
        // mutable Scalar에 직접 더해서 내부 값 변경
        matrixValue
            .get(r)
            .get(c)
            .add(other.get(r).get(c));
      }
    }
  }

  // 23. 행렬은 다른 행렬과 곱셈이 가능하다. ((m x n) x (n x l) 일 떄)
  // - 다른 행렬이 왼쪽 행렬로서 곱해지는 경우와 오른쪽 행렬로서 곱해지는 경우 모두 지원
  @Override
  public void mul(Matrix otherMatrix) {
    // 원본과 파라미터 행렬 값
    List<List<Scalar>> A = this.matrixValue;
    List<List<Scalar>> B = otherMatrix.getMatrixValue();

    int rowsA = A.size();
    int colsA = A.get(0).size();
    int rowsB = B.size();
    int colsB = B.get(0).size();

    boolean canAB = (colsA == rowsB);
    boolean canBA = (colsB == rowsA);

    if (!canAB && !canBA) {
      throw new IllegalArgumentException(
          String.format("Cannot multiply: A is %dx%d, B is %dx%d",
              rowsA, colsA, rowsB, colsB));
    }

    // 실제 곱셈 수행 헬퍼
    List<List<Scalar>> result;
    if (canAB) {
      // A × B
      result = multiply(A, B, rowsA, colsA, colsB);
    } else {
      // B × A
      result = multiply(B, A, rowsB, colsB, colsA);
    }

    // 연산 결과로 대체
    this.matrixValue = result;
  }

  /**
   * left (m×n) × right (n×p) → (m×p) 결과를 생성해서 반환 mutable Scalar.add(…) 으로 합계 누적, Scalar.mul(…) 으로 새
   * 객체 반환한다고 가정
   */
  private List<List<Scalar>> multiply(
      List<List<Scalar>> left,
      List<List<Scalar>> right,
      int m, int n, int p) {

    List<List<Scalar>> res = new ArrayList<>(m);
    for (int i = 0; i < m; i++) {
      List<Scalar> row = new ArrayList<>(p);
      for (int j = 0; j < p; j++) {
        // 0 으로 시작하는 Scalar (구현에 맞게 수정)
        Scalar sum = new ScalarImpl("0");
        for (int k = 0; k < n; k++) {
          // left(i,k) * right(k,j)
          Scalar prod = left.get(i).get(k).multiply(right.get(k).get(j));
          // mutable add
          sum.add(prod);
        }
        row.add(sum);
      }
      res.add(row);
    }
    return res;
  }

  @Override
  // 45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.
  public void swapRows(int row1, int row2) {
    // TODO: row1과 row2의 리스트 요소를 서로 교환
  }

  @Override
  // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
  public void swapColumns(int col1, int col2) {
    // TODO: 모든 행에 대해 col1과 col2의 값을 교환
  }

  @Override
  // 47. 지정된 행 row에 Scalar factor를 곱해 상수배한다.
  public void scaleRow(int row, Scalar factor) {
    // TODO: matrixValue.get(row)의 각 요소에 factor를 곱함
  }

  @Override
  // 48. 지정된 열 column에 Scalar factor를 곱해 상수배한다.
  public void scaleColumn(int column, Scalar factor) {
    // TODO: 모든 행의 column 인덱스 요소에 factor를 곱함
  }

  @Override
  // 49. targetRow에 sourceRow의 factor 배를 더한다. (row -> row + factor * sourceRow)
  public void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor) {
    // TODO: 각 열별로 matrix[targetRow][c] += factor * matrix[sourceRow][c]
  }

  @Override
  // 50. targetColumn에 sourceColumn의 factor 배를 더한다. (column -> column + factor * sourceColumn)
  public void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor) {
    // TODO: 모든 행에 대해 matrix[r][targetColumn] += factor * matrix[r][sourceColumn]
  }

  @Override
  // 51. 이 행렬의 RREF(row reduced echelon form) 버전을 새 Matrix로 계산하여 반환한다.
  public Matrix toReducedRowEchelonForm() {
    // TODO: Gaussian elimination 알고리즘으로 RREF 계산 후 새 Matrix 반환
    return null;
  }

  @Override
  // 52. 이 행렬이 이미 RREF 형태인지 검사하여 true/false를 반환한다.
  public boolean isReducedRowEchelonForm() {
    // TODO: 각 피벗 위치, 0-행, 피벗 위의 값 등 RREF 조건 검사
    return false;
  }
}