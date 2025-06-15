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

  // =============================================================================
  // 생성자 (Constructors)
  // =============================================================================

  // 06. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬 생성
  MatrixImpl(String bigDecimalString, int rowSize, int colSize) {
    validateStringValue(bigDecimalString);
    validateMatrixDimensions(rowSize, colSize);

    matrixValue = new ArrayList<>(rowSize);
    for (int i = 0; i < rowSize; i++) {
      List<Scalar> row = new ArrayList<>(colSize);
      for (int j = 0; j < colSize; j++) {
        row.add(new ScalarImpl(bigDecimalString));
      }
      matrixValue.add(row);
    }
  }

  // 07. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬 생성
  MatrixImpl(String i, String j, int rowSize, int colSize) {
    validateStringValue(i);
    validateStringValue(j);
    validateMatrixDimensions(rowSize, colSize);

    matrixValue = new ArrayList<>(rowSize);
    for (int r = 0; r < rowSize; r++) {
      List<Scalar> row = new ArrayList<>(colSize);
      for (int c = 0; c < colSize; c++) {
        // 각 원소마다 새로운 난수 생성
        row.add(new ScalarImpl(i, j));
      }
      matrixValue.add(row);
    }
  }

  // 08. csv 파일로부터 m x n 행렬을 생성
  // - comma(,): 열 구분자
  // - 라인: 행 구분자
  MatrixImpl(File csvFile) {
    validateFileArgument(csvFile);

    matrixValue = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      String line;
      int rowIndex = 0;
      int expectedColSize = -1;

      while ((line = br.readLine()) != null) {
        // 빈 줄은 건너뛰기
        if (line.trim().isEmpty()) {
          continue;
        }

        // 한 줄을 콤마(,)로 분리
        String[] tokens = line.split(",");
        List<Scalar> row = new ArrayList<>(tokens.length);

        for (String token : tokens) {
          row.add(new ScalarImpl(token.trim()));
        }

        // 첫 번째 행에서 열 크기를 설정하고, 이후 행들은 같은 크기인지 확인
        if (expectedColSize == -1) {
          expectedColSize = tokens.length;
        } else if (tokens.length != expectedColSize) {
          throw new IllegalArgumentException(
              String.format("CSV format error: Row %d has %d columns, expected %d columns",
                  rowIndex, tokens.length, expectedColSize));
        }

        matrixValue.add(row);
        rowIndex++;
      }

      if (matrixValue.isEmpty()) {
        throw new IllegalArgumentException("CSV file is empty or contains no valid data: " + csvFile.getPath());
      }

    } catch (IOException e) {
      throw new RuntimeException("CSV 파일 읽기 실패: " + csvFile.getPath(), e);
    }
  }

  // 09. 2차원 배열로부터 m x n 행렬 생성
  MatrixImpl(List<List<Scalar>> dimTwoList) {
    validateMatrixData(dimTwoList);

    // 깊은 복사 권장
    matrixValue = new ArrayList<>(dimTwoList.size());
    for (List<Scalar> row : dimTwoList) {
      List<Scalar> newRow = new ArrayList<>(row.size());
      for (Scalar s : row) {
        newRow.add(s.clone());
      }
      matrixValue.add(newRow);
    }
  }

  // 10. 단위 행렬 생성
  MatrixImpl(int size) {
    validateMatrixSize(size);

    // 내부 단위 행렬 생성
    matrixValue = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      List<Scalar> row = new ArrayList<>(size);
      for (int j = 0; j < size; j++) {
        row.add(new ScalarImpl(i == j ? "1" : "0"));
      }
      matrixValue.add(row);
    }
  }

  // =============================================================================
  // 기본 접근자 메소드 (Basic Accessor Methods)
  // =============================================================================

  // 11m. 특정 위치의 요소를 지정/조회할 수 있다.
  @Override
  // 11m. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  public Scalar get(int row, int col) {
    validateIndices(row, col);
    return getMatrixValue().get(row).get(col);
  }

  @Override
  // 11m. 지정: 지정한 인덱스 위치에 Scalar 값을 설정
  public void set(int row, int col, Scalar value) {
    validateIndices(row, col);
    validateScalarArgument(value);
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

  // 행렬의 내부 리스트에 직접 접근하는 메소드 (default)
  @Override
  public List<List<Scalar>> getMatrixValue() {
    return matrixValue;
  }

  // =============================================================================
  // 객체 기본 메소드 (Object Basic Methods)
  // =============================================================================

  // 14m. 값들을 2차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    if (matrixValue.isEmpty()) {
      return "";
    }

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
    return new MatrixImpl(copy); // Collection<Collection<Scalar>> 생성자 가정
  }

  // =============================================================================
  // 인스턴스 산술 연산 메소드 (Instance Arithmetic Operations)
  // =============================================================================

  // 22. 행렬은 다른 행렬과 덧셈이 가능하다. (크기가 같을 때)
  @Override
  public void add(Matrix otherMatrix) {
    validateMatrixArgument(otherMatrix);
    validateMatrixDimensionsMatch(this, otherMatrix, "addition");

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
    validateMatrixArgument(otherMatrix);

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
      throw new DimensionMismatchException(
          String.format("%dx%d", rowsA, colsA),
          String.format("%dx%d", rowsB, colsB));
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

  // =============================================================================
  // 헬퍼 메소드 (Helper Methods)
  // =============================================================================

  /**
   * 행렬 곱셈을 수행하는 헬퍼 메소드
   * left (m×n) × right (n×p) → (m×p) 결과를 생성해서 반환
   * mutable Scalar.add(…) 으로 합계 누적, Scalar.mul(…) 으로 새 객체 반환한다고 가정
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
        Scalar sum = ZERO_SCALAR.clone();
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

  // =============================================================================
  // 검증 메소드 (Validation Methods)
  // =============================================================================

  /**
   * 문자열 값의 유효성을 검증한다.
   * 
   * @param value 검증할 문자열 값
   * @throws NullPointerException     값이 null인 경우
   * @throws IllegalArgumentException 값이 빈 문자열이거나 유효한 숫자가 아닌 경우
   */
  private static void validateStringValue(String value) {
    if (value == null) {
      throw new NullPointerException("Matrix value cannot be null");
    }
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException("Matrix value cannot be empty");
    }
  }

  /**
   * 행렬 차원의 유효성을 검증한다.
   * 
   * @param rowSize 행 크기
   * @param colSize 열 크기
   * @throws IllegalArgumentException 차원이 음수인 경우
   */
  private static void validateMatrixDimensions(int rowSize, int colSize) {
    if (rowSize < 0) {
      throw new IllegalArgumentException("Matrix row size cannot be negative: " + rowSize);
    }
    if (colSize < 0) {
      throw new IllegalArgumentException("Matrix column size cannot be negative: " + colSize);
    }
  }

  /**
   * 정사각 행렬 크기의 유효성을 검증한다.
   * 
   * @param size 행렬 크기
   * @throws IllegalArgumentException 크기가 음수인 경우
   */
  private static void validateMatrixSize(int size) {
    if (size < 0) {
      throw new IllegalArgumentException("Matrix size cannot be negative: " + size);
    }
  }

  /**
   * 파일 인수의 유효성을 검증한다.
   * 
   * @param file 검증할 파일
   * @throws NullPointerException  파일이 null인 경우
   * @throws FileNotFoundException 파일이 존재하지 않거나 읽을 수 없는 경우
   */
  private static void validateFileArgument(File file) {
    if (file == null) {
      throw new NullPointerException("CSV file cannot be null");
    }
    if (!file.exists() || !file.canRead()) {
      throw new FileNotFoundException();
    }
  }

  /**
   * 2차원 행렬 데이터의 유효성을 검증한다.
   * 
   * @param data 검증할 2차원 리스트
   * @throws NullPointerException     데이터가 null인 경우
   * @throws IllegalArgumentException 데이터가 비어있거나 불규칙한 경우
   */
  private static void validateMatrixData(List<List<Scalar>> data) {
    if (data == null) {
      throw new NullPointerException("Matrix data cannot be null");
    }
    if (data.isEmpty()) {
      throw new IllegalArgumentException("Matrix data cannot be empty");
    }

    int expectedColSize = -1;
    for (int i = 0; i < data.size(); i++) {
      List<Scalar> row = data.get(i);
      if (row == null) {
        throw new IllegalArgumentException("Row " + i + " cannot be null");
      }

      if (expectedColSize == -1) {
        expectedColSize = row.size();
      } else if (row.size() != expectedColSize) {
        throw new IllegalArgumentException(
            String.format("Row %d has %d columns, expected %d columns", i, row.size(), expectedColSize));
      }

      for (int j = 0; j < row.size(); j++) {
        if (row.get(j) == null) {
          throw new IllegalArgumentException("Scalar at position [" + i + "," + j + "] cannot be null");
        }
      }
    }
  }

  /**
   * 행렬 인덱스의 유효성을 검증한다.
   * 
   * @param row 행 인덱스
   * @param col 열 인덱스
   * @throws IndexOutOfBoundsException 인덱스가 범위를 벗어난 경우
   */
  private void validateIndices(int row, int col) {
    if (row < 0 || row >= getRowSize()) {
      throw new IndexOutOfBoundsException(
          "Row index " + row + " is out of bounds for matrix size " + getRowSize() + "x" + getColSize());
    }
    if (col < 0 || col >= getColSize()) {
      throw new IndexOutOfBoundsException(
          "Column index " + col + " is out of bounds for matrix size " + getRowSize() + "x" + getColSize());
    }
  }

  /**
   * 스칼라 인수의 유효성을 검증한다.
   * 
   * @param scalar 검증할 스칼라 객체
   * @throws NullPointerException 스칼라가 null인 경우
   */
  private static void validateScalarArgument(Scalar scalar) {
    if (scalar == null) {
      throw new NullPointerException("Scalar argument cannot be null");
    }
  }

  /**
   * 행렬 인수의 유효성을 검증한다.
   * 
   * @param matrix 검증할 행렬 객체
   * @throws NullPointerException 행렬이 null인 경우
   */
  private static void validateMatrixArgument(Matrix matrix) {
    if (matrix == null) {
      throw new NullPointerException("Matrix argument cannot be null");
    }
  }

  /**
   * 두 행렬의 차원이 일치하는지 검증한다.
   * 
   * @param matrix1   첫 번째 행렬
   * @param matrix2   두 번째 행렬
   * @param operation 수행할 연산 이름 (오류 메시지용)
   * @throws DimensionMismatchException 차원이 일치하지 않는 경우
   */
  private static void validateMatrixDimensionsMatch(Matrix matrix1, Matrix matrix2, String operation) {
    if (matrix1.getRowSize() != matrix2.getRowSize() || matrix1.getColSize() != matrix2.getColSize()) {
      throw new DimensionMismatchException(
          String.format("%dx%d", matrix1.getRowSize(), matrix1.getColSize()),
          String.format("%dx%d", matrix2.getRowSize(), matrix2.getColSize()));
    }
  }

  // =============================================================================
  // 정적 산술 연산 메소드 (Static Arithmetic Operations)
  // =============================================================================

  // 28. 전달받은 두 행렬의 덧셈이 가능하다. (크기가 같을 때)
  static Matrix add(Matrix a, Matrix b) {
    validateMatrixArgument(a);
    validateMatrixArgument(b);
    validateMatrixDimensionsMatch(a, b, "addition");

    List<List<Scalar>> result = new ArrayList<>(a.getRowSize());
    for (int i = 0; i < a.getRowSize(); i++) {
      List<Scalar> row = new ArrayList<>(a.getColSize());
      for (int j = 0; j < a.getColSize(); j++) {
        row.add(a.get(i, j).add(b.get(i, j)));
      }
      result.add(row);
    }
    return new MatrixImpl(result);
  }

  // 29. 전달받은 두 행렬의 곱셈이 가능하다. ((m x n) x (n x l) 일 때)
  static Matrix mul(Matrix a, Matrix b) {
    validateMatrixArgument(a);
    validateMatrixArgument(b);
    if (a.getColSize() != b.getRowSize()) {
      throw new DimensionMismatchException(a.getColSize(), b.getRowSize());
    }

    List<List<Scalar>> result = new ArrayList<>(a.getRowSize());
    for (int i = 0; i < a.getRowSize(); i++) {
      List<Scalar> row = new ArrayList<>(b.getColSize());
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

  // =============================================================================
  // 연결 연산 메소드 (Concatenation Operations)
  // =============================================================================

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
  @Override
  public Matrix concatHorizontally(Matrix other) {
    validateMatrixArgument(other);
    validateRowSizeForHorizontalConcat(this, other);

    // 자기 자신과 연결하는 경우를 위해 다른 행렬을 미리 복사
    Matrix otherMatrixCopy = other.clone();

    // 현재 행렬의 각 행에 복사된 다른 행렬의 해당 행을 추가
    for (int rowIndex = 0; rowIndex < getRowSize(); rowIndex++) {
      List<Scalar> currentRow = this.matrixValue.get(rowIndex);
      for (int colIndex = 0; colIndex < otherMatrixCopy.getColSize(); colIndex++) {
        currentRow.add(otherMatrixCopy.get(rowIndex, colIndex));
      }
    }
    return this;
  }

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능) - Static 버전
  static Matrix concatHorizontally(Matrix left, Matrix right) {
    validateMatrixArgument(left);
    validateMatrixArgument(right);
    validateRowSizeForHorizontalConcat(left, right);

    List<List<Scalar>> resultMatrix = new ArrayList<>(left.getRowSize());

    for (int rowIndex = 0; rowIndex < left.getRowSize(); rowIndex++) {
      List<Scalar> newRow = new ArrayList<>(left.getColSize() + right.getColSize());

      // 왼쪽 행렬의 행 복사
      for (Scalar scalar : left.getMatrixValue().get(rowIndex)) {
        newRow.add(scalar.clone());
      }

      // 오른쪽 행렬의 행 복사
      for (Scalar scalar : right.getMatrixValue().get(rowIndex)) {
        newRow.add(scalar.clone());
      }

      resultMatrix.add(newRow);
    }

    return new MatrixImpl(resultMatrix);
  }

  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
  @Override
  public Matrix concatVertically(Matrix other) {
    validateMatrixArgument(other);
    validateColSizeForVerticalConcat(this, other);

    // 자기 자신과 연결하는 경우를 위해 다른 행렬을 미리 복사
    Matrix otherMatrixCopy = other.clone();

    // 복사된 행렬의 모든 행을 현재 행렬에 추가
    for (int rowIndex = 0; rowIndex < otherMatrixCopy.getRowSize(); rowIndex++) {
      List<Scalar> newRow = new ArrayList<>(otherMatrixCopy.getColSize());
      for (int colIndex = 0; colIndex < otherMatrixCopy.getColSize(); colIndex++) {
        newRow.add(otherMatrixCopy.get(rowIndex, colIndex));
      }
      this.matrixValue.add(newRow);
    }

    return this;
  }

  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능) - Static 버전
  static Matrix concatVertically(Matrix top, Matrix bottom) {
    validateMatrixArgument(top);
    validateMatrixArgument(bottom);
    validateColSizeForVerticalConcat(top, bottom);

    List<List<Scalar>> resultMatrix = new ArrayList<>(top.getRowSize() + bottom.getRowSize());

    // 위쪽 행렬의 모든 행 복사
    for (int rowIndex = 0; rowIndex < top.getRowSize(); rowIndex++) {
      List<Scalar> newRow = new ArrayList<>(top.getColSize());
      for (int colIndex = 0; colIndex < top.getColSize(); colIndex++) {
        newRow.add(top.get(rowIndex, colIndex).clone());
      }
      resultMatrix.add(newRow);
    }

    // 아래쪽 행렬의 모든 행 복사
    for (int rowIndex = 0; rowIndex < bottom.getRowSize(); rowIndex++) {
      List<Scalar> newRow = new ArrayList<>(bottom.getColSize());
      for (int colIndex = 0; colIndex < bottom.getColSize(); colIndex++) {
        newRow.add(bottom.get(rowIndex, colIndex).clone());
      }
      resultMatrix.add(newRow);
    }

    return new MatrixImpl(resultMatrix);
  }

  // =============================================================================
  // 추출 연산 메소드 (Extraction Operations)
  // =============================================================================

  // 34. 행렬은 특정 행을 벡터 추출해 줄 수 있다.
  @Override
  public Vector getRowVector(int row) {
    validateRowIndex(row);

    List<Scalar> rowCopy = new ArrayList<>(getColSize());
    for (Scalar scalar : matrixValue.get(row)) {
      rowCopy.add(scalar.clone());
    }
    return new VectorImpl(rowCopy);
  }

  // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
  @Override
  public Vector getColVector(int col) {
    validateColIndex(col);

    List<Scalar> colVec = new ArrayList<>(getRowSize());
    for (List<Scalar> row : matrixValue) {
      colVec.add(row.get(col).clone());
    }
    return new VectorImpl(colVec);
  }

  // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  @Override
  public Matrix subMatrix(int startRow, int endRow, int startCol, int endCol) {
    validateSubMatrixBounds(startRow, endRow, startCol, endCol);

    List<List<Scalar>> result = new ArrayList<>(endRow - startRow);

    for (int i = startRow; i < endRow; i++) {
      List<Scalar> row = new ArrayList<>(endCol - startCol);
      for (int j = startCol; j < endCol; j++) {
        row.add(matrixValue.get(i).get(j).clone());
      }
      result.add(row);
    }

    return new MatrixImpl(result);
  }

  // 37. 행렬은 특정 행과 열을 제거한 부분 행렬(minor)을 추출해 줄 수 있다.
  @Override
  public Matrix minor(int rowToRemove, int colToRemove) {
    validateRowIndex(rowToRemove);
    validateColIndex(colToRemove);

    List<List<Scalar>> result = new ArrayList<>(getRowSize() - 1);
    for (int i = 0; i < matrixValue.size(); i++) {
      if (i == rowToRemove) {
        continue;
      }
      List<Scalar> row = new ArrayList<>(getColSize() - 1);
      for (int j = 0; j < matrixValue.get(i).size(); j++) {
        if (j == colToRemove) {
          continue;
        }
        row.add(matrixValue.get(i).get(j).clone());
      }
      result.add(row);
    }

    return new MatrixImpl(result);
  }

  // =============================================================================
  // 변환 연산 메소드 (Transformation Operations)
  // =============================================================================

  // 38. 행렬은 전치행렬을 구해 줄 수 있다.
  @Override
  public Matrix transpose() {
    int rows = getRowSize();
    int cols = getColSize();
    List<List<Scalar>> result = new ArrayList<>(cols);

    for (int c = 0; c < cols; c++) {
      List<Scalar> newRow = new ArrayList<>(rows);
      for (int r = 0; r < rows; r++) {
        newRow.add(matrixValue.get(r).get(c).clone());
      }
      result.add(newRow);
    }
    return new MatrixImpl(result);
  }

  // =============================================================================
  // 특성 확인 메소드 (Property Check Methods)
  // =============================================================================

  // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
  @Override
  public Scalar trace() {
    if (!isSquare()) {
      throw new NonSquareMatrixException(getRowSize(), getColSize());
    }
    Scalar sum = ZERO_SCALAR.clone();
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

    for (int r = 1; r < getRowSize(); r++) {
      for (int c = 0; c < r; c++) {
        if (!this.get(r, c).equals(ZERO_SCALAR)) {
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

    for (int r = 0; r < getRowSize(); r++) {
      for (int c = r + 1; c < getColSize(); c++) {
        if (!this.get(r, c).equals(ZERO_SCALAR)) {
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

    for (int r = 0; r < getRowSize(); r++) {
      for (int c = 0; c < getColSize(); c++) {
        if (r == c) {
          if (!this.get(r, c).equals(ONE_SCALAR)) {
            return false;
          }
        } else {
          if (!this.get(r, c).equals(ZERO_SCALAR)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  // 44. 영행렬(Zero)인지 반환
  @Override
  public boolean isZero() {
    for (int r = 0; r < getRowSize(); r++) {
      for (int c = 0; c < getColSize(); c++) {
        if (!this.get(r, c).equals(ZERO_SCALAR)) {
          return false;
        }
      }
    }
    return true;
  }

  // =============================================================================
  // 행렬 변환 연산 메소드 (Matrix Transformation Operations)
  // =============================================================================

  // 45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.
  @Override
  public void swapRows(int row1, int row2) {
    validateRowIndex(row1);
    validateRowIndex(row2);

    List<Scalar> tmp = this.matrixValue.get(row1);
    this.matrixValue.set(row1, this.matrixValue.get(row2));
    this.matrixValue.set(row2, tmp);
  }

  // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
  @Override
  public void swapColumns(int col1, int col2) {
    validateColIndex(col1);
    validateColIndex(col2);

    for (int r = 0; r < getRowSize(); r++) {
      Scalar tmp = matrixValue.get(r).get(col1);
      matrixValue.get(r).set(col1, matrixValue.get(r).get(col2));
      matrixValue.get(r).set(col2, tmp);
    }
  }

  // 47. 지정된 행 row에 Scalar factor를 곱해 상수배한다.
  @Override
  public void scaleRow(int row, Scalar factor) {
    validateRowIndex(row);
    validateScalarArgument(factor);

    for (int c = 0; c < getColSize(); c++) {
      matrixValue.get(row).get(c).multiply(factor);
    }
  }

  // 48. 지정된 열 column에 Scalar factor를 곱해 상수배한다.
  @Override
  public void scaleColumn(int column, Scalar factor) {
    validateColIndex(column);
    validateScalarArgument(factor);

    for (int r = 0; r < getRowSize(); r++) {
      matrixValue.get(r).get(column).multiply(factor);
    }
  }

  // 49. targetRow에 sourceRow의 factor 배를 더한다. (row -> row + factor * sourceRow)
  @Override
  public void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor) {
    validateRowIndex(targetRow);
    validateRowIndex(sourceRow);
    validateScalarArgument(factor);

    for (int c = 0; c < getColSize(); c++) {
      Scalar sourceScalar = matrixValue.get(sourceRow).get(c).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(targetRow).get(c).add(sourceScalar);
    }
  }

  // 50. targetColumn에 sourceColumn의 factor 배를 더한다. (column -> column + factor *
  // sourceColumn)
  @Override
  public void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor) {
    validateColIndex(targetColumn);
    validateColIndex(sourceColumn);
    validateScalarArgument(factor);

    for (int r = 0; r < getRowSize(); r++) {
      Scalar sourceScalar = matrixValue.get(r).get(sourceColumn).clone();
      sourceScalar.multiply(factor);
      matrixValue.get(r).get(targetColumn).add(sourceScalar);
    }
  }

  // =============================================================================
  // 행렬 형식 변환 메소드 (Matrix Form Conversion Methods)
  // =============================================================================

  // 51. 이 행렬의 RREF(row-reduced echelon form) 버전을 새 Matrix로 계산하여 반환한다.
  @Override
  public Matrix toReducedRowEchelonForm() {
    Matrix m = this.clone();

    int rowCount = m.getRowSize();
    int colCount = m.getColSize();
    int lead = 0;

    for (int r = 0; r < rowCount && lead < colCount; r++) {
      // (1) 피벗이 0이면, 아래 행 중 0이 아닌 것이 나올 때까지 swap
      int pivotRow = r;
      while (pivotRow < rowCount && m.get(pivotRow, lead).isZero()) {
        pivotRow++;
      }
      if (pivotRow == rowCount) {
        lead++;
        r--;
        continue;
      }
      if (pivotRow != r) {
        m.swapRows(pivotRow, r);
      }

      // (2) 피벗을 1로 만들기
      Scalar pivot = m.get(r, lead);
      if (!pivot.equals(ONE_SCALAR)) {
        m.scaleRow(r, pivot.reciprocal());
      }

      // (3) 같은 열의 다른 행을 0으로
      for (int i = 0; i < rowCount; i++) {
        if (i == r) {
          continue;
        }
        Scalar factor = m.get(i, lead);
        if (!factor.isZero()) {
          m.addMultipleOfRow(i, r, factor.negate());
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
    int lastPivot = -1;

    for (int r = 0; r < rowCount; r++) {
      // (a) 0-행이면 아래쪽도 전부 0-행이어야 함
      int firstNonZero = findFirstNonZeroColumn(r);

      if (firstNonZero == -1) {
        // 0-행 이후에 0이 아닌 행이 있으면 RREF 아님
        if (!areRemainingRowsZero(r + 1)) {
          return false;
        }
        break;
      }

      // (b) 피벗은 1이어야 함
      if (!get(r, firstNonZero).equals(ONE_SCALAR)) {
        return false;
      }

      // (c) 피벗 열은 다른 행에서 모두 0
      if (!isPivotColumnZeroInOtherRows(r, firstNonZero)) {
        return false;
      }

      // (d) 피벗 열은 오른쪽으로 Strictly 증가
      if (firstNonZero <= lastPivot) {
        return false;
      }
      lastPivot = firstNonZero;
    }
    return true;
  }

  // =============================================================================
  // 고급 행렬 연산 메소드 (Advanced Matrix Operations)
  // =============================================================================

  // 53. 행렬은 자신의 행렬식을 구해줄 수 있다.
  @Override
  public Scalar determinant() {
    if (!isSquare()) {
      throw new NonSquareMatrixException(getRowSize(), getColSize());
    }

    int n = getRowSize();

    if (n == 1) {
      return matrixValue.get(0).get(0).clone();
    }

    if (n == 2) {
      return calculate2x2Determinant();
    }

    return calculateNxNDeterminant(n);
  }

  // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
  @Override
  public Matrix inverse() {
    if (!isSquare()) {
      throw new NonSquareMatrixException(getRowSize(), getColSize());
    }

    Scalar det = this.determinant();
    if (det.isZero()) {
      throw new SingularMatrixException();
    }

    int n = getRowSize();
    Matrix cofactorMatrix = calculateCofactorMatrix(n);
    Matrix adjugate = cofactorMatrix.transpose();
    Scalar detReciprocal = det.reciprocal();

    return multiplyMatrixByScalar(adjugate, detReciprocal);
  }

  // =============================================================================
  // 유틸리티 메소드 (Utility Methods)
  // =============================================================================

  /**
   * 해시코드를 생성한다.
   */
  @Override
  public int hashCode() {
    int result = 1;
    for (List<Scalar> row : matrixValue) {
      for (Scalar scalar : row) {
        result = 31 * result + scalar.hashCode();
      }
    }
    return result;
  }

  // =============================================================================
  // 상수 정의 (Constants)
  // =============================================================================

  private static final Scalar ZERO_SCALAR = new ScalarImpl("0");
  private static final Scalar ONE_SCALAR = new ScalarImpl("1");
  private static final Scalar NEGATIVE_ONE_SCALAR = new ScalarImpl("-1");

  // =============================================================================
  // 검증 헬퍼 메소드 (Validation Helper Methods)
  // =============================================================================

  /**
   * 행 인덱스의 유효성을 검증한다.
   */
  private void validateRowIndex(int row) {
    if (row < 0 || row >= getRowSize()) {
      throw new IndexOutOfBoundsException(
          "Row index " + row + " is out of bounds for matrix size " + getRowSize() + "x" + getColSize());
    }
  }

  /**
   * 열 인덱스의 유효성을 검증한다.
   */
  private void validateColIndex(int col) {
    if (col < 0 || col >= getColSize()) {
      throw new IndexOutOfBoundsException(
          "Column index " + col + " is out of bounds for matrix size " + getRowSize() + "x" + getColSize());
    }
  }

  /**
   * 부분 행렬 범위의 유효성을 검증한다.
   */
  private void validateSubMatrixBounds(int startRow, int endRow, int startCol, int endCol) {
    if (startRow < 0 || startRow >= getRowSize()) {
      throw new IndexOutOfBoundsException("Start row index " + startRow + " is out of bounds");
    }
    if (endRow <= startRow || endRow > getRowSize()) {
      throw new IndexOutOfBoundsException("End row index " + endRow + " is invalid");
    }
    if (startCol < 0 || startCol >= getColSize()) {
      throw new IndexOutOfBoundsException("Start column index " + startCol + " is out of bounds");
    }
    if (endCol <= startCol || endCol > getColSize()) {
      throw new IndexOutOfBoundsException("End column index " + endCol + " is invalid");
    }
  }

  /**
   * 가로 연결을 위한 행 크기 검증
   */
  private static void validateRowSizeForHorizontalConcat(Matrix left, Matrix right) {
    if (left.getRowSize() != right.getRowSize()) {
      throw new DimensionMismatchException(left.getRowSize(), right.getRowSize());
    }
  }

  /**
   * 세로 연결을 위한 열 크기 검증
   */
  private static void validateColSizeForVerticalConcat(Matrix top, Matrix bottom) {
    if (top.getColSize() != bottom.getColSize()) {
      throw new DimensionMismatchException(top.getColSize(), bottom.getColSize());
    }
  }

  // =============================================================================
  // 계산 헬퍼 메소드 (Calculation Helper Methods)
  // =============================================================================

  /**
   * 특정 행에서 첫 번째 0이 아닌 열을 찾는다.
   */
  private int findFirstNonZeroColumn(int row) {
    for (int c = 0; c < getColSize(); c++) {
      if (!get(row, c).isZero()) {
        return c;
      }
    }
    return -1;
  }

  /**
   * 지정된 행부터 나머지 모든 행이 0인지 확인한다.
   */
  private boolean areRemainingRowsZero(int startRow) {
    for (int r = startRow; r < getRowSize(); r++) {
      for (int c = 0; c < getColSize(); c++) {
        if (!get(r, c).isZero()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 피벗 열이 다른 행에서 모두 0인지 확인한다.
   */
  private boolean isPivotColumnZeroInOtherRows(int pivotRow, int pivotCol) {
    for (int i = 0; i < getRowSize(); i++) {
      if (i != pivotRow && !get(i, pivotCol).isZero()) {
        return false;
      }
    }
    return true;
  }

  /**
   * 2x2 행렬의 행렬식을 계산한다.
   */
  private Scalar calculate2x2Determinant() {
    Scalar a = matrixValue.get(0).get(0).clone();
    Scalar b = matrixValue.get(0).get(1).clone();
    Scalar c = matrixValue.get(1).get(0).clone();
    Scalar d = matrixValue.get(1).get(1).clone();

    Scalar ac = a.multiply(d);
    Scalar bd = b.multiply(c).multiply(NEGATIVE_ONE_SCALAR);
    ac.add(bd);
    return ac;
  }

  /**
   * NxN 행렬의 행렬식을 계산한다 (재귀적 방법).
   */
  private Scalar calculateNxNDeterminant(int n) {
    Scalar det = ZERO_SCALAR.clone();
    for (int j = 0; j < n; j++) {
      Scalar sign = new ScalarImpl((j % 2 == 0) ? "1" : "-1");
      Scalar aij = matrixValue.get(0).get(j).clone();
      Matrix minor = this.minor(0, j);
      Scalar cofactor = aij.multiply(minor.determinant()).multiply(sign);
      det.add(cofactor);
    }
    return det;
  }

  /**
   * 여인수 행렬을 계산한다.
   */
  private Matrix calculateCofactorMatrix(int n) {
    List<List<Scalar>> cofactorMatrix = new ArrayList<>(n);

    for (int i = 0; i < n; i++) {
      List<Scalar> cofactorRow = new ArrayList<>(n);
      for (int j = 0; j < n; j++) {
        Scalar sign = new ScalarImpl(((i + j) % 2 == 0) ? "1" : "-1");
        Scalar minorDet = this.minor(i, j).determinant();
        Scalar cofactor = sign.multiply(minorDet);
        cofactorRow.add(cofactor);
      }
      cofactorMatrix.add(cofactorRow);
    }

    return new MatrixImpl(cofactorMatrix);
  }

  /**
   * 행렬에 스칼라를 곱한다.
   */
  private Matrix multiplyMatrixByScalar(Matrix matrix, Scalar scalar) {
    List<List<Scalar>> result = new ArrayList<>(matrix.getRowSize());
    for (int i = 0; i < matrix.getRowSize(); i++) {
      List<Scalar> row = new ArrayList<>(matrix.getColSize());
      for (int j = 0; j < matrix.getColSize(); j++) {
        Scalar elem = matrix.get(i, j).clone();
        elem.multiply(scalar);
        row.add(elem);
      }
      result.add(row);
    }
    return new MatrixImpl(result);
  }
}