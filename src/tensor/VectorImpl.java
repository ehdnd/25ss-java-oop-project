package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체는 1차원 배열 구조로 관리
- Collection으로 관리

# 벡터의 생성 (default 접근 지정자 사용)
-
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class VectorImpl implements Vector {

  // 자료구조 - 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  private List<Scalar> vectorValue;
  private static final Random RANDOM = new Random();


  // 03. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터 생성
  VectorImpl(String bigDecimalString, int dimension) {
    vectorValue = new ArrayList<>(dimension);
    for (int i = 0; i < dimension; ++i) {
      vectorValue.add(new ScalarImpl(bigDecimalString));
    }
  }

  // 04. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터 생성
  VectorImpl(String i, String j, int dimension) {
    vectorValue = new ArrayList<>(dimension);
    for (int k = 0; k < dimension; ++k) {
      vectorValue.add(new ScalarImpl(i, j));
    }
  }

  // 05. 1차원 배열로부터 n-차원 벡터 생성
  VectorImpl(List<Scalar> dimOneList) {
    vectorValue = new ArrayList<>(dimOneList.size());
    for (Scalar s : dimOneList) {
      vectorValue.add(s.clone());
    }
  }

  // 11v. 특정 위치의 요소를 지정/조회할 수 있다.
  // 11v. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  @Override
  public Scalar get(int index) {
    return vectorValue.get(index);
  }

  // 11.지정: 지정한 인덱스 위치에 Scalar 값을 설정
  @Override
  public void set(int index, Scalar value) {
    vectorValue.set(index, value.clone());
  }

  // 13v. 차원의 개수를 조회할 수 있다.
  @Override
  public int size() {
    return vectorValue.size();
  }

  // 14v. 값들을 1차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < vectorValue.size(); ++i) {
      sb.append(vectorValue.get(i).toString());
      if (i < vectorValue.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  // 15. 객체의 동등성을 파악할 수 있다.
  @Override
  public boolean equals(Object other) {
    return toString().equals(other.toString());
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  public Vector clone() {
    List<Scalar> copy = new ArrayList<>(vectorValue.size());
    for (Scalar s : vectorValue) {
      copy.add(s.clone());          // 스칼라까지 재귀적으로 복제
    }
    return new VectorImpl(copy);      // Collection<Scalar> 생성자 호출
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

  // 26. 전달받은 두 벡터의 덧셈이 가능하다.(길이가 같을때) (out-place)
  static Vector add(Vector v1, Vector v2) {
    if (v1.size() != v2.size()) {
      throw new IllegalArgumentException("Vectors must be of the same length.");
    }

    Vector result = Factory.createVector("0", v1.size());
    for (int i = 0; i < v1.size(); i++) {
      Scalar sum = v1.get(i).add(v2.get(i));
      result.set(i, sum);
    }
    return result;
  }

  // 27. 전달받은 스칼라와 벡터의 곱셈이 가능하다(벡터의 모든 요소에 스칼라를 곱한다.)
  static Vector multiply(Scalar s, Vector v) {
    Vector result = Factory.createVector("0", v.size());
    for (int i = 0; i < v.size(); i++) {
      Scalar product = s.clone().multiply(v.get(i));
      result.set(i, product);
    }
    return result;
  }

  // 30. n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
  @Override
  public Matrix toColumnMatrix() {
    List<List<Scalar>> rows = vectorValue.stream()
        .map(scalar -> List.of(scalar)) // 각 스칼라를 행으로 만듦
        .toList();
    return new MatrixImpl(rows);
  }

  // 31. n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
  @Override
  public Matrix toRowMatrix() {
    List<List<Scalar>> row = List.of(vectorValue); // 한 행에 모든 스칼라 포함
    return new MatrixImpl(row);
  }
}