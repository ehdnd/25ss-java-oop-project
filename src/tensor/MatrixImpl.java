package tensor;

/*
# 행렬의 자료구조
- 행렬은 논리적으로 스칼라 객체를 2차원 배열 구조로 관리
  - Collection 선택
 */

import java.io.File;
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

}