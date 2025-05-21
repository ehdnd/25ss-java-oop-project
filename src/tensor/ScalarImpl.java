package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;

class ScalarImpl implements Scalar, Comparable<Scalar> {

  // 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
  private BigDecimal scalarValue;

  // 01. 값 (String) 지정하여 스칼라 생성
  ScalarImpl(String bigDecimalString) {
  }

  // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
  ScalarImpl(String i, String j) {
  }


  // 12. 값을 지정/조회할 수 있다.
  // 12. 값을 지정
  @Override
  public void setValueFromString(String s) {
  }

  // 12. 값을 조회
  @Override
  public String getValueAsString() {
    return null;
  }

  // 14s. 값 하나를 콘솔에 출력할 수 있다.
  @Override
  public String toString() {
    return super.toString();
  }

  // 15. 객체의 동등성을 판단할 수 있다.
  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  // 16. 스칼라의 경우 값의 대소 비교를 할 수 있다.
  @Override
  public int compareTo(Scalar another) {
    return 0;
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}