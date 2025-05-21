package tensor;

/*
# 스칼라의 자료구조
- 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
 */

import java.math.BigDecimal;

public interface Scalar {

  BigDecimal bigDecimal = null;

  // 12. String으로 값을 지정
  void setValueFromString(String s);

  // 12. 값을 String으로 조회
  String getValueAsString();

  // 18. 스칼라는 다른 스칼라와 덧셈이 가능하다
  Scalar add(Scalar other);

  // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다.
  Scalar multiply(Scalar other);



}