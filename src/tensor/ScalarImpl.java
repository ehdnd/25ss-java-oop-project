package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;
import java.security.SecureClassLoader;

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

  // 스칼라의 연산(non-static 메소드로 구현)연산 결과는 자신의 새로운 값이 된다 .

  //18. 스칼라는 다른 스칼라와 덧셈이 가능하다
  @Override
  public Scalar add(Scalar other) {
    BigDecimal otherValue = new BigDecimal(other.getValueAsString());
    this.scalarValue = this.scalarValue.add(otherValue);
    return this;
  }

  // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다
  @Override
  public Scalar multiply(Scalar other) {
    BigDecimal otherValue = new BigDecimal(other.getValueAsString());
    this.scalarValue = this.scalarValue.multiply(otherValue);
    return this;
  }

  // 스칼라의 연산 디폴트 메소드로 구현연산 결과 스칼라는 새로 생성되어 반환( static ) ※
  // 24. 전달받은 두 스칼라의 덧셈이 가능하다.
  static Scalar add(Scalar a, Scalar b) {
    BigDecimal v1 = new BigDecimal(a.getValueAsString());
    BigDecimal v2 = new BigDecimal(b.getValueAsString());
    return new ScalarImpl(v1.add(v2).toString());
  }

  // 25. 전달받은 두 스칼라의 곱셈이 가능하다.
  static Scalar multiply(Scalar a, Scalar b) {
    BigDecimal v1 = new BigDecimal(a.getValueAsString());
    BigDecimal v2 = new BigDecimal(b.getValueAsString());
    return new ScalarImpl(v1.multiply(v2).toString());
  }


}