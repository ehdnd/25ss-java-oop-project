package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;

class ScalarImpl implements Scalar {

  // 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
  private BigDecimal scalarValue;

  // 01. 값 (String) 지정하여 스칼라 생성
  ScalarImpl(String bigDecimalString) {
  }

  // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
  ScalarImpl(String i, String j) {
  }

  // 12. 값을 지정/조회할 수 있다.
  //값을 지정
  @Override
  public void setValueFromString(String s) {
  }

  //값을 조회
  @Override
  public String getValueAsString() {
    return null;
  }
  @Override
  public String toString() {
    return super.toString();
  }
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}