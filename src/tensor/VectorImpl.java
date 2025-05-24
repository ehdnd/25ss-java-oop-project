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
  // 11.조회: 지정한 인덱스 위치의 Scalar 값을 반환
  @Override
  public Scalar get(int index) {
    return null;
  }

  // 11.지정: 지정한 인덱스 위치에 Scalar 값을 설정
  @Override
  public void set(int index, Scalar value) {
  }
  // 13v. 차원의 개수를 조회할 수 있다.
  @Override
  public int size() {
    return 0;
  }

  // 14v. 값들을 1차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    return super.toString();
  }

  // 15. 객체의 동등성을 파악할 수 있다.
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  // 20. 길이가 같을 때 다른 벡터와 덧셈 (자신을 수정한 뒤 자신 반환)
  @Override
  public Vector add(Vector other) {
    if (other == null) {
      throw new NullPointerException("other vector is null");
    }
    if (this.size() != other.size()) {
      throw new IllegalArgumentException(
          "두 벡터의 길이가 다릅니다: " + this.size() + " vs " + other.size());
    }
    for (int i = 0; i < vectorValue.size(); i++) {
      // Scalar의 non‑static add 는 자신의 값을 바꾼 뒤 this를 리턴
      vectorValue.get(i).add(other.get(i));
    }
    return this;
  }

  // 21. 벡터를 다른 스칼라와 곱셈이 가능하다 (모든 요소에 스칼라 곱한 뒤 자신 반환)
  @Override
  public Vector multiply(Scalar scalar) {
    if (scalar == null) {
      throw new NullPointerException("scalar is null");
    }
    for (Scalar elem : vectorValue) {
      elem.multiply(scalar);   // in‑place 곱셈
    }
    return this;
  }

}