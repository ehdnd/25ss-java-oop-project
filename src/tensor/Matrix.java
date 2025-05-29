package tensor;

import java.util.ArrayList;
import java.util.List;

public interface Matrix {

  // +. MatrixValue getter
  List<List<Scalar>> getMatrixValue();

  // 11m. 특정 위치의 요소를 지정/조회할 수 있다.
  // 11m. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  Scalar get(int row, int col);

  // 11m. 지정: 지정한 인덱스 위치에 Scalar 값을 설정
  void set(int row, int col, Scalar value);

  // 13. 행렬의 행개수를 조회
  int getRowSize();

  // 13. 행렬의 열개수를 조회
  int getColSize();

  // 14m. 값들을 2차원 배열 모양으로 출력할 수 있다.
  @Override
  String toString();

  // 15. 객체의 동등성을 판단할 수 있다.
  @Override
  boolean equals(Object other);

  // 17. 객체 복제를 할 수 있다.
  Matrix clone();

  // 22. 행렬은 다른 행렬과 덧셈이 가능하다. (크기가 같을 때)
  void add(Matrix otherMatrix);

  // 23. 행렬은 다른 행렬과 곱셈이 가능하다. ((m x n) x (n x l) 일 떄)
  // - 다른 행렬이 왼쪽 행렬로서 곱해지는 경우와 오른쪽 행렬로서 곱해지는 경우 모두 지원
  void mul(Matrix otherMatrix);

  // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
  Matrix concatHorizontally(Matrix other);

  // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
  Matrix concatVertically(Matrix other);

  // 34. 행렬은 특정 행을 벡터 추출해 주 수 있다.
  Vector getRowVector(int row);

  // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
  Vector getColVector(int col);

  // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  Matrix subMatrix(int startRow, int endRow, int startCol, int endCol);

  // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
  Matrix minor(int rowToRemove, int colToRemove);

  // 38. 행렬은 전치행렬을 구해 줄 수 있따.
  Matrix transpose();

  // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
  Scalar trace();

  // 40. 자신이 정사각 행렬인지 여부를 판별
  boolean isSquare();

  // 41. 자신이 상삼각 행렬인지 여부를 판별
  boolean isUpperTriangular();

  // 42. 자신이 하삼각 행렬인지 여부를 판별
  boolean isLowerTriangular();

  // 43. 자신이 단위 행렬인지 여부를 판별
  boolean isIdentity();

  // 44. 자신이 영 행렬인지 여부를 판별
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
  void addMultipleOfRow(int targetRow, int sourceRow, Scalar factor);

  // 50. 특정 열에 다른 열의 상수배를 더하기
  void addMultipleOfColumn(int targetColumn, int sourceColumn, Scalar factor);

  // 51. 이 행렬의 RREF 행렬을 계산해 새 객체로 반환
  Matrix toReducedRowEchelonForm();

  // 52. 이 행렬이 RREF 형태인지 여부 판별
  boolean isReducedRowEchelonForm();

  //53. 행렬은 자신의 행렬식을 구해줄 수 있다.
  Scalar determinant();

  // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
  Matrix inverse();
}