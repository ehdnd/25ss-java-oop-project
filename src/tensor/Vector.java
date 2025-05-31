package tensor;

public interface Vector {

  // 11v. 특정 위치의 요소를 지정/조회할 수 있다.
  // 11v. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  Scalar get(int index);

  // 11.지정: 지정한 인덱스 위치에 Scalar 값을 설정
  void set(int index, Scalar value);

  // 13v. 크기 정보를 조회할 수 있다. (벡터는 차원)
  int size();

  // 14v. 값들을 1차원 배열 모양으로 출력할 수 있다.
  @Override
  String toString();

  // 15. 객체의 동등성을 파악할 수 있다.
  @Override
  boolean equals(Object other);

  // 17. 객체 복제를 할 수 있다.
  Vector clone();

  // 20. 벡터-벡터 덧셈 (in-place)
  Vector add(Vector other);

  // 21. 벡터-스칼라 곱셈 (in-place)
  Vector multiply(Scalar scalar);


  // 30. 자신을 열벡터 (nx1 행렬)로 변환
  Matrix toColumnMatrix();


  // 31. 자신을 행벡터 (1xn 행렬)로 변환
  Matrix toRowMatrix();
}