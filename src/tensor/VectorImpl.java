package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체는 1차원 배열 구조로 관리
- Collection으로 관리

# 벡터의 생성 (default 접근 지정자 사용)
-
 */


import java.util.List;

public class VectorImpl implements Vector {

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
}