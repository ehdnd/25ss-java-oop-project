package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;

public class ScalarImpl implements Scalar {

  // 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
  private BigDecimal scalarValue;

  // 01. 값 (String) 지정하여 스칼라 생성
  ScalarImpl(String bigDecimalString) {
  }

  // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
  ScalarImpl(String i, String j) {
  }
}