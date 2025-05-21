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
}