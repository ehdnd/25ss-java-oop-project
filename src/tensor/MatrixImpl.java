package tensor;

/*
# 행렬의 자료구조
- 행렬은 논리적으로 스칼라 객체를 2차원 배열 구조로 관리
  - Collection 선택
 */


import java.io.*;
import java.util.*;

class MatrixImpl implements Matrix {

  // 자료구조 - 행렬은 논리적으로 스칼라 객체를 2차원 배열 구조로 관리
  private List<List<Scalar>> matrixValue;

  // 06. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬 생성
  MatrixImpl(String bigDecimalString, int rowSize, int colSize) {
    matrixValue = new ArrayList<>();
    for (int i = 0; i < rowSize; i++) {
      List<Scalar> row = new ArrayList<>();
      for (int j = 0; j < colSize; j++) {
        row.add(new ScalarImpl(bigDecimalString));
      }
      matrixValue.add(row);
    }
  }

  // 07. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬 생성
  MatrixImpl(String i, String j, int rowSize, int colSize) {
    matrixValue = new ArrayList<>();
    for (int r = 0; r < rowSize; r++) {
      List<Scalar> row = new ArrayList<>();
      for (int c = 0; c < colSize; c++) {
        // 각 원소마다 새로운 난수 생성
        row.add(new ScalarImpl(i, j));
      }
      matrixValue.add(row);
    }
  }

  // 08. csv 파일로부터 m x n 행렬을 생성
  //  - comma(,): 열 구분자
  //  - 라인: 행 구분자
  MatrixImpl(File csvFile) {
    matrixValue = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        // 한 줄을 콤마(,)로 분리
        String[] tokens = line.split(",");
        List<Scalar> row = new ArrayList<>();
        for (String token : tokens) {
          row.add(new ScalarImpl(token.trim()));
        }
        matrixValue.add(row);
      }
    } catch (IOException e) {
      throw new RuntimeException("CSV 파일 읽기 실패: " + csvFile.getPath(), e);
    }
  }

  // 09. 2차원 배열로부터 m x n 행렬 생성
  MatrixImpl(List<List<Scalar>> dimTwoList) {
    // 깊은 복사 권장
    matrixValue = new ArrayList<>();
    for (List<Scalar> row : dimTwoList) {
      List<Scalar> newRow = new ArrayList<>();
      for (Scalar s : row) {
        newRow.add(s.clone());
      }
      matrixValue.add(newRow);
    }
  }

  // 10. 단위 행렬 생성
  MatrixImpl(int size) {
    // 내부 단위 행렬 생성

    matrixValue = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      List<Scalar> row = new ArrayList<>();
      for (int j = 0; j < size; j++) {
        row.add(new ScalarImpl(i == j ? "1" : "0"));
      }
      matrixValue.add(row);
    }
  }

  // 11m. 특정 위치의 요소를 지정/조회할 수 있다.
  @Override
  // 11m. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  public Scalar get(int row, int col) {
    return getMatrixValue().get(row).get(col);
  }

  @Override
  // 11m. 지정: 지정한 인덱스 위치에 Scalar 값을 설정
  public void set(int row, int col, Scalar value) {
    matrixValue.get(row).set(col, value.clone());
  }

  // 13m. 행렬의 행개수를 조회
  @Override
  public int getRowSize() {
    return matrixValue.size();
  }

  // 13m. 행렬의 열개수를 조회
  @Override
  public int getColSize() {
    return matrixValue.isEmpty() ? 0 : matrixValue.get(0).size();
  }

  // 14m. 값들을 2차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < getRowSize(); i++) {
      for (int j = 0; j < getColSize(); j++) {
        sb.append(get(i, j).toString());
        if (j < getColSize() - 1) {
          sb.append(", ");
        }
      }
      if (i < getRowSize() - 1) {
        sb.append(System.lineSeparator());
      }
    }
    return sb.toString();
  }

  // 15. 객체의 동등성을 판단할 수 있다.
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Matrix)) {
      return false;
    }

    Matrix other = (Matrix) obj;

    if (getRowSize() != other.getRowSize() ||
        getColSize() != other.getColSize()) {
      return false;
    }

    for (int r = 0; r < getRowSize(); ++r) {
      for (int c = 0; c < getColSize(); ++c) {
        if (!get(r, c).equals(other.get(r, c))) {
          return false;
        }
      }
    }
    return true;
  }


  // 17. 객체 복제를 할 수 있다.
  @Override
  public Matrix clone() {
    List<List<Scalar>> copy = new ArrayList<>(matrixValue.size());
    for (List<Scalar> row : matrixValue) {
      List<Scalar> newRow = new ArrayList<>(row.size());
      for (Scalar s : row) {
        newRow.add(s.clone());
      }
      copy.add(newRow);
    }
    return new MatrixImpl(copy);      // Collection<Collection<Scalar>> 생성자 가정
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
          Scalar leftVal = left.get(i).get(k);
          Scalar rightVal = right.get(k).get(j);
          Scalar prod = Tensors.multiply(leftVal.clone(), rightVal.clone());
          sum.add(prod);
        }
        row.add(sum);
      }
      res.add(row);
    }
    return res;
  }

  // 28. 전달받은 두 행렬의 덧셈이 가능하다. (크기가 같을 때)
  static Matrix add(Matrix a, Matrix b) {
    if (a.getRowSize() != b.getRowSize() || a.getColSize() != b.getColSize()) {
      throw new IllegalArgumentException("Matrix dimensions must match for addition.");
    }

    List<List<Scalar>> result = new java.util.ArrayList<>();
    for (int i = 0; i < a.getRowSize(); i++) {
      List<Scalar> row = new java.util.ArrayList<>();
      for (int j = 0; j < a.getColSize(); j++) {
        row.add(a.get(i, j).add(b.get(i, j)));
      }
      result.add(row);
    }
    return new MatrixImpl(result);
  }

  // 29. 전달받은 두 행렬의 곱셈이 가능하다. ((m x n) x (n x l) 일 때)
  static Matrix mul(Matrix a, Matrix b) {
    if (a.getColSize() != b.getRowSize()) {
      throw new IllegalArgumentException("Matrix dimensions do not allow multiplication.");
    }

    List<List<Scalar>> result = new java.util.ArrayList<>();
    for (int i = 0; i < a.getRowSize(); i++) {
      List<Scalar> row = new java.util.ArrayList<>();
      for (int j = 0; j < b.getColSize(); j++) {
        Scalar sum = a.get(i, 0).clone();
        sum.multiply(b.get(0, j));
        for (int k = 1; k < a.getColSize(); k++) {
          Scalar tmp = a.get(i, k).clone();
          sum.add(tmp.multiply(b.get(k, j)));
        }
        row.add(sum);
      }
      result.add(row);
    }
    return new MatrixImpl(result);
  }

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
  @Override
  public Matrix concatHorizontally(Matrix other) {
    if (this.getRowSize() != other.getRowSize()) {
      throw new IllegalArgumentException("행 개수가 다릅니다.");
    }
    for (int i = 0; i < getRowSize(); i++) {
      List<Scalar> copyOfOtherRow = new ArrayList<>();
      for (Scalar s : other.getMatrixValue().get(i)) {
        copyOfOtherRow.add(s.clone());
      }
      this.getMatrixValue().get(i).addAll(copyOfOtherRow);
    }
    return this;
  }

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능) static
  static Matrix concatHorizontally(Matrix a, Matrix b) {
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

  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능) non static
  //    이 메서드는 호출된 객체(this)를 직접 수정합니다.
  @Override
  public Matrix concatVertically(Matrix other) {
    if (this.getRowSize() != other.getRowSize()) {
      throw new IllegalArgumentException("행 개수가 다릅니다.");
    }

    List<List<Scalar>> rowsToAdd = new ArrayList<>();
    for (List<Scalar> row : other.getMatrixValue()) {
      List<Scalar> newRow = new ArrayList<>(row.size());
      for (Scalar s : row) {
        newRow.add(s.clone());
      }
      rowsToAdd.add(newRow);
    }

    this.matrixValue.addAll(rowsToAdd);
    return this;
  }


  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능) default static
  static Matrix concatVertically(Matrix a, Matrix b) {
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
  public Vector getRowVector(int row) {
    return new VectorImpl(new ArrayList<>(matrixValue.get(row)));
  }

  // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
  @Override
  public Vector getColVector(int col) {
    List<Scalar> colVec = new ArrayList<>();
    for (List<Scalar> row : matrixValue) {
      colVec.add(row.get(col));
    }
    return new VectorImpl(colVec);
  }

  // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  @Override
  public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
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
  public Matrix minor(int rowToRemove, int colToRemove) {
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

  // 38. 행렬은 전치행렬을 구해 줄 수 있다.
  @Override
  public Matrix transpose() {
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
      throw new NonSquareMatrixException(getRowSize(), getColSize());
    }
    Scalar sum = new ScalarImpl("0");
    for (int i = 0; i < getRowSize(); i++) {
      sum.add(matrixValue.get(i).get(i));
    }
    return sum;
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

  @Override
  // 45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.
  public void swapRows(int row1, int row2) {
    List<Scalar> tmp = matrixValue.get(row1);
    matrixValue.set(row1, matrixValue.get(row2));
    matrixValue.set(row2, tmp);
  }

  @Override
  // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
  public void swapColumns(int col1, int col2) {
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
  public void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor) {
    // TODO: 각 열별로 matrix[targetRow][c] += factor * matrix[sourceRow][c]
    for (int c = 0; c < getColSize(); ++c) {
      Scalar sourceScalar = matrixValue.get(sourceRow).get(c).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(targetRow).get(c).add(sourceScalar);
    }
  }

  @Override
  // 50. targetColumn에 sourceColumn의 factor 배를 더한다. (column -> column + factor * sourceColumn)
  public void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor) {
    // TODO: 모든 행에 대해 matrix[r][targetColumn] += factor * matrix[r][sourceColumn]
    for (int r = 0; r < getRowSize(); ++r) {
      Scalar sourceScalar = matrixValue.get(r).get(sourceColumn).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(r).get(targetColumn).add(sourceScalar);
    }
  }

  // 51. 이 행렬의 RREF(row-reduced echelon form) 버전을 새 Matrix로 계산하여 반환한다.
  @Override
  public Matrix toReducedRowEchelonForm() {
    // 깊은 복사 – 원본 보존
    Matrix m = this.clone();

    int rowCount = m.getRowSize();
    int colCount = m.getColSize();
    int lead = 0;                       // 현재 피벗 열

    for (int r = 0; r < rowCount && lead < colCount; r++) {
      // (1)   피벗이 0이면, 아래 행 중 0 이 아닌 것이 나올 때까지 swap
      int pivotRow = r;
      while (pivotRow < rowCount && m.get(pivotRow, lead).isZero()) {
        pivotRow++;
      }
      if (pivotRow == rowCount) {     // 전체 열이 0 → 다음 열로
        lead++;
        r--;                        // 같은 행에서 다시 시도
        continue;
      }
      if (pivotRow != r) {
        m.swapRows(pivotRow, r);    // 45. swapRows 활용
      }

      // (2)   피벗을 1로 만들기
      Scalar pivot = m.get(r, lead);
      if (!pivot.equals(new ScalarImpl("1"))) {
        m.scaleRow(r, pivot.reciprocal()); // 47. scaleRow(k = 1/pivot)
      }

      // (3)   같은 열의 다른 행을 0 으로
      for (int i = 0; i < rowCount; i++) {
        if (i == r) {
          continue;
        }
        Scalar factor = m.get(i, lead);
        if (!factor.isZero()) {
          m.addMultipleOfRow(i, r, factor.negate()); // 49. R_i ← R_i − factor·R_r
        }
      }
      lead++;
    }
    return m;
  }

  // 52. 이 행렬이 이미 RREF 형태인지 검사하여 true/false를 반환한다.
  @Override
  public boolean isReducedRowEchelonForm() {
    int rowCount = getRowSize();
    int colCount = getColSize();
    int lastPivot = -1;                 // 이전 피벗 열 인덱스

    for (int r = 0; r < rowCount; r++) {
      // (a) 0-행이면 아래쪽도 전부 0-행이어야 함
      int firstNonZero = -1;
      for (int c = 0; c < colCount; c++) {
        if (!get(r, c).isZero()) {
          firstNonZero = c;
          break;
        }
      }
      if (firstNonZero == -1) {       // 전부 0
        // 0-행 이후에 0이 아닌 행이 있으면 RREF 아님
        for (int k = r + 1; k < rowCount; k++) {
          for (int c = 0; c < colCount; c++) {
            if (!get(k, c).isZero()) {
              return false;
            }
          }
        }
        break;                      // 남은 행 모두 0 — 조건 충족
      }

      // (b) 피벗은 1 이어야 함
      if (!get(r, firstNonZero).equals(new ScalarImpl("1"))) {
        return false;
      }

      // (c) 피벗 열은 다른 행에서 모두 0
      for (int i = 0; i < rowCount; i++) {
        if (i == r) {
          continue;
        }
        if (!get(i, firstNonZero).isZero()) {
          return false;
        }
      }

      // (d) 피벗 열은 오른쪽으로 Strictly 증가
      if (firstNonZero <= lastPivot) {
        return false;
      }
      lastPivot = firstNonZero;
    }
    return true;
  }

  //53. 행렬은 자신의 행렬식을 구해줄 수 있다.
  @Override
  public Scalar determinant() {
    if (!isSquare()) {
      throw new DimensionMismatchException(getRowSize() + "x" + getRowSize(),
          getRowSize() + "x" + getColSize());
    }

    int n = getRowSize();

    if (n == 1) {
      return matrixValue.get(0).get(0).clone(); // 1x1 행렬
    }

    if (n == 2) {

      Scalar a = matrixValue.get(0).get(0).clone();
      Scalar b = matrixValue.get(0).get(1).clone();
      Scalar c = matrixValue.get(1).get(0).clone();
      Scalar d = matrixValue.get(1).get(1).clone();

      Scalar ac = a.multiply(d);
      Scalar bd = b.multiply(c).multiply(new ScalarImpl("-1"));
      ac.add(bd);
      return ac;
    }

    Scalar det = new ScalarImpl("0");
    for (int j = 0; j < n; j++) {
      Scalar sign = new ScalarImpl((j % 2 == 0) ? "1" : "-1");
      Scalar aij = matrixValue.get(0).get(j).clone();
      Matrix minor = this.minor(0, j);
      Scalar cofactor = aij.multiply(minor.determinant()).multiply(sign);
      det.add(cofactor);
    }
    return det;
  }


  // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
  @Override
  public Matrix inverse() {
    if (!isSquare()) {
      throw new NonSquareMatrixException(getRowSize(), getColSize());
    }

    Scalar det = this.determinant();
    if (det.getValueAsString().equals("0")) {
      throw new SingularMatrixException();
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

    Matrix adjugate = new MatrixImpl(cofactorMatrix).transpose();
    Scalar detReciprocal = det.reciprocal();

    // 역행렬 = (1/det) * adjugate
    List<List<Scalar>> invMatrix = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      List<Scalar> row = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        Scalar elem = adjugate.get(i, j);
        elem = elem.multiply(detReciprocal);  // 역수를 곱함
        row.add(elem);
      }
      invMatrix.add(row);
    }
    return new MatrixImpl(invMatrix);
  }
}