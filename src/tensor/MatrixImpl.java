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

  // +. default 생성자
  MatrixImpl() {
    /*TODO
     * default 생성자 제작
     * 다른 생성자 default 생성자와 엮어야하나?
     */
  }

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
  public Scalar get(int row, int col) {
    return getMatrixValue().get(row).get(col);
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
  protected Matrix clone() throws CloneNotSupportedException {
    return (Matrix) super.clone();
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
    List<Scalar> tmp = matrixValue.get(row1);
    matrixValue.set(row1, matrixValue.get(row2));
    matrixValue.set(row2, tmp);
  }

  @Override
  // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
  public void swapColumns(int col1, int col2) {
    // TODO: 모든 행에 대해 col1과 col2의 값을 교환
    for (int r = 0; r < getRowSize(); ++r) {
      Scalar tmp = matrixValue.get(r).get(col1);
      matrixValue.get(r).set(col1, matrixValue.get(r).get(col2));
      matrixValue.get(r).set(col2, tmp);
    }
  }

  @Override
  // 47. 지정된 행 row에 Scalar factor를 곱해 상수배한다.
  public void scaleRow(int row, Scalar factor) {
    // TODO: matrixValue.get(row)의 각 요소에 factor를 곱함
    for (int c = 0; c < getColSize(); ++c) {
      matrixValue.get(row).get(c).multiply(factor);
    }
  }

  @Override
  // 48. 지정된 열 column에 Scalar factor를 곱해 상수배한다.
  public void scaleColumn(int column, Scalar factor) {
    // TODO: 모든 행의 column 인덱스 요소에 factor를 곱함
    for (int r = 0; r < getRowSize(); ++r) {
      matrixValue.get(r).get(column).multiply(factor);
    }
  }

  @Override
  // 49. targetRow에 sourceRow의 factor 배를 더한다. (row -> row + factor * sourceRow)
  public void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor)
          throws CloneNotSupportedException {
    // TODO: 각 열별로 matrix[targetRow][c] += factor * matrix[sourceRow][c]
    for (int c = 0; c < getColSize(); ++c) {
      Scalar sourceScalar = matrixValue.get(sourceRow).get(c).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(targetRow).get(c).add(sourceScalar);
    }
  }

  @Override
  // 50. targetColumn에 sourceColumn의 factor 배를 더한다. (column -> column + factor * sourceColumn)
  public void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor)
          throws CloneNotSupportedException {
    // TODO: 모든 행에 대해 matrix[r][targetColumn] += factor * matrix[r][sourceColumn]
    for (int r = 0; r < getRowSize(); ++r) {
      Scalar sourceScalar = matrixValue.get(r).get(sourceColumn).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(r).get(targetColumn).add(sourceScalar);
    }
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
    return equals(toReducedRowEchelonForm());
  }

  // 40. 정사각 행렬인지 반환
  @Override
  public boolean isSquare() {
    return getRowSize() == getColSize();
  }

  // 41. 상삼각 행렬인지 반환
  @Override
  public boolean isUpperTriangular() {
    if (!isSquare()) {
      return false;
    }
    int n = getRowSize();
    for (int i = 1; i < n; i++) {
      for (int j = 0; j < i; j++) {
        if (!matrixValue.get(i).get(j).equals(new ScalarImpl("0"))) {
          return false;
        }
      }
    }
    return true;
  }

  // 42. 하삼각 행렬인지 반환
  @Override
  public boolean isLowerTriangular() {
    if (!isSquare()) {
      return false;
    }
    int n = getRowSize();
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (!matrixValue.get(i).get(j).equals(new ScalarImpl("0"))) {
          return false;
        }
      }
    }
    return true;
  }

  // 43. 단위 행렬(Identity)인지 반환
  @Override
  public boolean isIdentity() {
    if (!isSquare()) {
      return false;
    }
    int n = getRowSize();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        Scalar expected = (i == j) ? new ScalarImpl("1") : new ScalarImpl("0");
        if (!matrixValue.get(i).get(j).equals(expected)) {
          return false;
        }
      }
    }
    return true;
  }

  // 44. 영행렬(Zero)인지 반환
  @Override
  public boolean isZero() {
    for (List<Scalar> row : matrixValue) {
      for (Scalar val : row) {
        if (!val.equals(new ScalarImpl("0"))) {
          return false;
        }
      }
    }
    return true;
  }

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
  static MatrixImpl concatHorizontally(MatrixImpl a, MatrixImpl b) {
    if (a.getRowSize() != b.getRowSize()) {
      throw new IllegalArgumentException("행 개수가 다릅니다.");
    }
    List<List<Scalar>> result = new ArrayList<>();
    for (int i = 0; i < a.getRowSize(); i++) {
      List<Scalar> row = new ArrayList<>(a.getMatrixValue().get(i));
      row.addAll(b.getMatrixValue().get(i));
      result.add(row);
    }
    return new MatrixImpl(result);
  }

  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
  static MatrixImpl concatVertically(MatrixImpl a, MatrixImpl b) {
    if (a.getColSize() != b.getColSize()) {
      throw new IllegalArgumentException("열 개수가 다릅니다.");
    }
    List<List<Scalar>> result = new ArrayList<>();
    result.addAll(a.getMatrixValue());
    result.addAll(b.getMatrixValue());
    return new MatrixImpl(result);
  }

  // 34. 행렬은 특정 행을 벡터 추출해 주 수 있다.
  @Override
  public VectorImpl getRowVector(int row) {
    return new VectorImpl(new ArrayList<>(matrixValue.get(row)));
  }

  // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
  @Override
  public VectorImpl getColVector(int col) {
    List<Scalar> colVec = new ArrayList<>();
    for (List<Scalar> row : matrixValue) {
      colVec.add(row.get(col));
    }
    return new VectorImpl(colVec);
  }

  // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  @Override
  public MatrixImpl subMatrix(int startRow, int endRow, int startCol, int endCol) {
    List<List<Scalar>> sub = new ArrayList<>();
    for (int i = startRow; i < endRow; i++) {
      List<Scalar> row = new ArrayList<>();
      for (int j = startCol; j < endCol; j++) {
        row.add(matrixValue.get(i).get(j));
      }
      sub.add(row);
    }
    return new MatrixImpl(sub);
  }

  // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  @Override
  public MatrixImpl minor(int rowToRemove, int colToRemove) {
    List<List<Scalar>> sub = new ArrayList<>();
    for (int i = 0; i < matrixValue.size(); i++) {
      if (i == rowToRemove) {
        continue;
      }
      List<Scalar> row = new ArrayList<>();
      for (int j = 0; j < matrixValue.get(i).size(); j++) {
        if (j == colToRemove) {
          continue;
        }
        row.add(matrixValue.get(i).get(j));
      }
      sub.add(row);
    }
    return new MatrixImpl(sub);
  }

  // 38. 행렬은 전치행렬을 구해 줄 수 있따.
  @Override
  public MatrixImpl transpose() {
    int rows = getRowSize();
    int cols = getColSize();
    List<List<Scalar>> trans = new ArrayList<>();
    for (int c = 0; c < cols; c++) {
      List<Scalar> newRow = new ArrayList<>();
      for (int r = 0; r < rows; r++) {
        newRow.add(matrixValue.get(r).get(c));
      }
      trans.add(newRow);
    }
    return new MatrixImpl(trans);
  }

  // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
  @Override
  public Scalar trace() {
    if (!isSquare()) {
      throw new IllegalStateException("정사각 행렬 아님");
    }
    Scalar sum = new ScalarImpl("0");
    for (int i = 0; i < getRowSize(); i++) {
      sum.add(matrixValue.get(i).get(i));
    }
    return sum;
  }

  //53. 행렬은 자신의 행렬식을 구해줄 수 있다.
  @Override
  public Scalar determinant() {
    if (!isSquare()) throw new IllegalStateException("정사각행렬만 지원");

    int n = getRowSize();

    if (n == 1) {
      return matrixValue.get(0).get(0); // 1x1 행렬
    }

    if (n == 2) {

      Scalar a = matrixValue.get(0).get(0);
      Scalar b = matrixValue.get(0).get(1);
      Scalar c = matrixValue.get(1).get(0);
      Scalar d = matrixValue.get(1).get(1);

      Scalar ac = a.multiply(d);
      Scalar bd = b.multiply(c).multiply(new ScalarImpl("-1"));
      ac.add(bd);
      return ac;
    }

    Scalar det = new ScalarImpl("0");
    for (int j = 0; j < n; j++) {
      Scalar sign = new ScalarImpl((j % 2 == 0) ? "1" : "-1");
      Scalar aij = matrixValue.get(0).get(j);
      MatrixImpl minor = this.minor(0, j);
      Scalar cofactor = aij.multiply(minor.determinant()).multiply(sign);
      det.add(cofactor);
    }
    return det;
  }


  // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
  @Override
  public MatrixImpl inverse() {
    if (!isSquare()) throw new IllegalStateException("정사각행렬만 역행렬 존재");

    Scalar det = this.determinant();
    if (det.getValueAsString().equals("0")) {
      throw new ArithmeticException("행렬식이 0이므로 역행렬이 존재하지 않습니다.");
    }

    int n = getRowSize();
    List<List<Scalar>> cofactorMatrix = new ArrayList<>();

    // Cofactor 행렬 생성
    for (int i = 0; i < n; i++) {
      List<Scalar> cofactorRow = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        Scalar sign = new ScalarImpl(((i + j) % 2 == 0) ? "1" : "-1");
        Scalar minorDet = this.minor(i, j).determinant();
        Scalar cofactor = sign.multiply(minorDet);
        cofactorRow.add(cofactor);
      }
      cofactorMatrix.add(cofactorRow);
    }

    MatrixImpl adjugate = new MatrixImpl(cofactorMatrix).transpose();
    Scalar detReciprocal = det.reciprocal();

    // 역행렬 = (1/det) * adjugate
    List<List<Scalar>> invMatrix = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      List<Scalar> row = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        Scalar elem = adjugate.matrixValue.get(i).get(j);
        elem = elem.multiply(detReciprocal);  // 역수를 곱함
        row.add(elem);
      }
      invMatrix.add(row);
    }
    return new MatrixImpl(invMatrix);
  }

}