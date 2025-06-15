package tensor;

/*
# 스칼라의 생성 (default 접근 지정자 사용)
01. 값 (String) 지정하여 스칼라 생성
02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
 */

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

class ScalarImpl implements Scalar, Comparable<Scalar>, Cloneable {

  // 자료구조 - 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
  private BigDecimal scalarValue;
  private static final Random RANDOM = new Random();

  // =============================================================================
  // 생성자 (Constructors)
  // =============================================================================

  // 01. 값 (String) 지정하여 스칼라 생성
  ScalarImpl(String bigDecimalString) {
    validateStringValue(bigDecimalString);
    scalarValue = new BigDecimal(bigDecimalString);
  }

  // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
  ScalarImpl(String i, String j) {
    validateStringValue(i);
    validateStringValue(j);

    BigDecimal lower = new BigDecimal(i);
    BigDecimal upper = new BigDecimal(j);

    // 범위 검증: lower < upper
    if (lower.compareTo(upper) >= 0) {
      throw new IllegalArgumentException(
          "Lower bound must be less than upper bound: " + i + " >= " + j);
    }

    BigDecimal range = upper.subtract(lower);
    // 0.0 <= factor < 1.0
    BigDecimal factor = BigDecimal.valueOf(RANDOM.nextDouble());
    scalarValue = lower.add(range.multiply(factor));
  }

  // =============================================================================
  // 기본 접근자 메소드 (Basic Accessor Methods)
  // =============================================================================

  // 12. 값을 지정/조회할 수 있다.
  // 12. 값을 지정
  @Override
  public void setValueFromString(String s) {
    validateStringValue(s);
    scalarValue = new BigDecimal(s);
  }

  // 12. 값을 조회
  @Override
  public String getValueAsString() {
    return scalarValue.toString();
  }

  // =============================================================================
  // 객체 기본 메소드 (Object Basic Methods)
  // =============================================================================

  // 14s. 값 하나를 콘솔에 출력할 수 있다.
  @Override
  public String toString() {
    return scalarValue.toPlainString();
  }

  // 15s. 객체의 동등성을 판단할 수 있다.
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Scalar)) {
      return false;
    }
    return this.compareTo((Scalar) obj) == 0;
  }

  // 16. 스칼라의 경우 값의 대소 비교를 할 수 있다.
  @Override
  public int compareTo(Scalar other) {
    validateScalarArgument(other);
    return scalarValue.compareTo(new BigDecimal(other.getValueAsString()));
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  public Scalar clone() {
    // BigDecimal이 불변(immutable)이므로 toString() → 새 BigDecimal 생성도 OK
    return new ScalarImpl(scalarValue.toString());
  }

  /**
   * 해시코드를 생성한다.
   */
  @Override
  public int hashCode() {
    return scalarValue.hashCode();
  }

  // =============================================================================
  // 인스턴스 산술 연산 메소드 (Instance Arithmetic Operations)
  // 스칼라의 연산(non-static 메소드로 구현)연산 결과는 자신의 새로운 값이 된다 .
  // =============================================================================

  // 18. 스칼라는 다른 스칼라와 덧셈이 가능하다
  @Override
  public Scalar add(Scalar other) {
    validateScalarArgument(other);
    BigDecimal otherValue = new BigDecimal(other.getValueAsString());
    this.scalarValue = this.scalarValue.add(otherValue);
    return this;
  }

  // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다
  @Override
  public Scalar multiply(Scalar other) {
    validateScalarArgument(other);
    BigDecimal otherValue = new BigDecimal(other.getValueAsString());
    this.scalarValue = this.scalarValue.multiply(otherValue);
    return this;
  }

  // 추가. 역수
  @Override
  public Scalar reciprocal() {
    if (isZero()) {
      throw new ArithmeticException("Cannot compute reciprocal of zero");
    }
    this.scalarValue = BigDecimal.ONE.divide(this.scalarValue, MathContext.DECIMAL128);
    return this;
  }

  // 추가. 헬퍼
  @Override
  public Scalar negate() {
    BigDecimal negated = scalarValue.negate();
    return new ScalarImpl(negated.toString());
  }

  // =============================================================================
  // 정적 산술 연산 메소드 (Static Arithmetic Operations)
  // 스칼라의 연산 디폴트 메소드로 구현연산 결과 스칼라는 새로 생성되어 반환( static ) ※
  // =============================================================================

  // 24. 전달받은 두 스칼라의 덧셈이 가능하다.
  static Scalar add(Scalar a, Scalar b) {
    validateScalarArgument(a);
    validateScalarArgument(b);

    BigDecimal v1 = new BigDecimal(a.getValueAsString());
    BigDecimal v2 = new BigDecimal(b.getValueAsString());
    return new ScalarImpl(v1.add(v2).toString());
  }

  // 25. 전달받은 두 스칼라의 곱셈이 가능하다.
  static Scalar multiply(Scalar a, Scalar b) {
    validateScalarArgument(a);
    validateScalarArgument(b);

    BigDecimal v1 = new BigDecimal(a.getValueAsString());
    BigDecimal v2 = new BigDecimal(b.getValueAsString());
    return new ScalarImpl(v1.multiply(v2).toString());
  }

  // =============================================================================
  // 유틸리티 메소드 (Utility Methods)
  // =============================================================================

  // 추가. 헬퍼
  @Override
  public boolean isZero() {
    return scalarValue.compareTo(BigDecimal.ZERO) == 0;
  }

  // 숫자인지 검증하는 메소드 추가
  public boolean isPositive() {
    return scalarValue.compareTo(BigDecimal.ZERO) > 0;
  }

  // 음수인지 검증하는 메소드 추가
  public boolean isNegative() {
    return scalarValue.compareTo(BigDecimal.ZERO) < 0;
  }

  // BigDecimal 값에 직접 접근하는 메소드 (package-private)
  BigDecimal getBigDecimalValue() {
    return scalarValue;
  }

  // =============================================================================
  // 검증 메소드 (Validation Methods)
  // =============================================================================

  /**
   * 문자열 값의 유효성을 검증한다.
   * 
   * @param value 검증할 문자열 값
   * @throws NullPointerException     값이 null인 경우
   * @throws IllegalArgumentException 값이 빈 문자열이거나 유효한 숫자가 아닌 경우
   */
  private static void validateStringValue(String value) {
    if (value == null) {
      throw new NullPointerException("Scalar value cannot be null");
    }
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException("Scalar value cannot be empty");
    }
    // BigDecimal 생성을 시도하여 유효성 검증
    try {
      new BigDecimal(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid numeric value: " + value, e);
    }
  }

  /**
   * 스칼라 인수의 유효성을 검증한다.
   * 
   * @param scalar 검증할 스칼라 객체
   * @throws NullPointerException 스칼라가 null인 경우
   */
  private static void validateScalarArgument(Scalar scalar) {
    if (scalar == null) {
      throw new NullPointerException("Scalar argument cannot be null");
    }
  }
}