package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

class ScalarImpl implements Scalar, Comparable<Scalar> {

  // 자료구조 - 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
  private BigDecimal scalarValue;
  private static final Random RANDOM = new Random();

  // 01. 값 (String) 지정하여 스칼라 생성
  ScalarImpl(String bigDecimalString) {
    scalarValue = new BigDecimal(bigDecimalString);
  }

  // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
  ScalarImpl(String i, String j) {
    BigDecimal lower = new BigDecimal(i);
    BigDecimal upper = new BigDecimal(j);
    BigDecimal range = upper.subtract(lower);
    // 0.0 <= factor < 1.0
    BigDecimal factor = BigDecimal.valueOf(RANDOM.nextDouble());
    scalarValue = lower.add(range.multiply(factor));
  }


  // 12. 값을 지정/조회할 수 있다.
  // 12. 값을 지정
  @Override
  public void setValueFromString(String s) {
    scalarValue = new BigDecimal(s);
  }

  // 12. 값을 조회
  @Override
  public String getValueAsString() {
    return scalarValue.toString();
  }

  // 14s. 값 하나를 콘솔에 출력할 수 있다.
  @Override
  public String toString() {
    return scalarValue.toPlainString();
  }

  // 15. 객체의 동등성을 판단할 수 있다.
  @Override
  public boolean equals(Object other) {
    return toString().equals(other.toString());
  }

  // 16. 스칼라의 경우 값의 대소 비교를 할 수 있다.
  @Override
  public int compareTo(Scalar other) {
    return scalarValue.compareTo(new BigDecimal(other.getValueAsString()));
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  public Scalar clone() {
    // BigDecimal이 불변(immutable)이므로 toString() → 새 BigDecimal 생성도 OK
    return new ScalarImpl(scalarValue.toString());
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

  // 추가. 역수
  @Override
  public Scalar reciprocal() {
    this.scalarValue = BigDecimal.ONE.divide(this.scalarValue, MathContext.DECIMAL128);
    return this;
  }

  // 추가. 헬퍼
  @Override
  public boolean isZero() {
    return scalarValue.compareTo(BigDecimal.ZERO) == 0;
  }

  // 추가. 헬퍼
  @Override
  public Scalar negate() {
    BigDecimal negated = scalarValue.negate();
    return new ScalarImpl(negated.toString());
  }
}