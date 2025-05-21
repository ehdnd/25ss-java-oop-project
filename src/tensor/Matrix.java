package tensor;

import java.util.List;

interface Matrix {

  Scalar get(int index);

  void set(int index, Scalar value);

  int getRowSize();

  int getColSize();

  // 22. 행렬은 다른 행렬과 덧셈이 가능하다. (크기가 같을 때)
  void add(Matrix otherMatrix);

  // 23. 행렬은 다른 행렬과 곱셈이 가능하다. ((m x n) x (n x l) 일 떄)
  // - 다른 행렬이 왼쪽 행렬로서 곱해지는 경우와 오른쪽 행렬로서 곱해지는 경우 모두 지원
  void mul(Matrix otherMatrix);

  // +. MatrixValue getter
  List<List<Scalar>> getMatrixValue();

  // 45. 특정 두 행의 위치를 맞교환
  void swapRows(int row1, int row2);

  // 46. 특정 두 열의 위치를 맞교환
  void swapColumns(int col1, int col2);

  // 47. 특정 행에 상수배(스칼라) 곱하기
  void scaleRow(int row, Scalar factor);

  // 48. 특정 열에 상수배(스칼라) 곱하기
  void scaleColumn(int column, Scalar factor);

  // 49. 특정 행에 다른 행의 상수배를 더하기
  void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor);

  // 50. 특정 열에 다른 열의 상수배를 더하기
  void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor);

  // 51. 이 행렬의 RREF 행렬을 계산해 새 객체로 반환
  Matrix toReducedRowEchelonForm();

  // 52. 이 행렬이 RREF 형태인지 여부 판별
  boolean isReducedRowEchelonForm();
}