package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  - Collection 선택하자
 */

import java.util.ArrayList;
import java.util.List;

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

  // FIXME : default static을 Impl에 구현하라고?

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

  // 30. 자신을 열벡터 (nx1 행렬)로 변환
  Matrix toColumnMatrix();


  // 31. 자신을 행벡터 (1xn 행렬)로 변환
  Matrix toRowMatrix();
}