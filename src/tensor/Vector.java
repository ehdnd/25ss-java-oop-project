package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  - Collection 선택하자
 */

interface Vector {
    Scalar get(int index);        // 요소 조회
    void set(int index, Scalar value);  // 요소 지정
    int size();
}