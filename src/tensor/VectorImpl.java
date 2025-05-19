package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체는 1차원 배열 구조로 관리
- Collection으로 관리

# 벡터의 생성 (default 접근 지정자 사용)
-
 */


import java.util.List;

class VectorImpl implements Vector {

  // 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  private List<Scalar> vectorValue;

  // 03. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터 생성
  VectorImpl(String bigDecimalString) {
  }

  // 04. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터 생성
  VectorImpl(String i, String j) {
  }

  // 05. 1차원 배열로부터 n-차원 벡터 생성
  VectorImpl(List<Scalar> dimOneList) {
  }
  // 11v. 특정 위치의 요소를 지정/조회할 수 있다.
  // 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  @Override
  public Scalar get(int index) {
    return null;
  }

  // 지정: 지정한 인덱스 위치에 Scalar 값을 설정
  @Override
  public void set(int index, Scalar value) {
  }

  @Override
  public int size() {
    return 0;
  }
  @Override
  public String toString() {
    return super.toString();
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}