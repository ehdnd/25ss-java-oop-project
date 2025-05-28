package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  - Collection 선택하자
 */

interface Vector {

  // 11. 요소 조회
  Scalar get(int index);

  // 11. 요소 지정
  void set(int index, Scalar value);

  // 13. 크기 정보를 조회할 수 있다.
  int size();

  /**
   * 20. 벡터-벡터 덧셈 (in-place)
   */
  Vector add(Vector other);

  /**
   * 21. 벡터-스칼라 곱셈 (in-place)
   */
  Vector multiply(Scalar scalar);

  /* ===== 디폴트 static 메서드 : 명세 26, 27 ===== */

  /**
   * 26. 전달받은 두 벡터의 덧셈이 가능하다.(길이가 같을때) (out-place)
   */
  static Vector add(Vector v1, Vector v2) {
    return null;
  }

  // 27. 전달받은 스칼라와 벡터의 곱셈이 가능하다(벡터의 모든 요소에 스칼라를 곱한다.)
  static Vector multiply(Scalar s, Vector v) {
    return null;
  }

  /**
   * 30. 자신을 열벡터 (nx1 행렬)로 변환
   */
  default Matrix toColumnMatrix() {
    return null;
  }

  /**
   * 31. 자신을 행벡터 (1xn 행렬)로 변환
   */
  default Matrix toRowMatrix() {
    return null;
  }
}