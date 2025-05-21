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
}