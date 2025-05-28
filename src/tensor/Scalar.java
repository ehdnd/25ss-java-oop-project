package tensor;

/*
# 스칼라의 자료구조
- 스칼라는 java.math.BigDecimal 타입 객체 하나를 가진다
 */

import java.math.BigDecimal;

public interface Scalar extends Cloneable {

  // 12. String으로 값을 지정
  void setValueFromString(String s);

  // 12. 값을 String으로 조회
  String getValueAsString();

  // 17. 객체 복제를 할 수 있다.
  Scalar clone();

  // 18. 스칼라는 다른 스칼라와 덧셈이 가능하다
  Scalar add(Scalar other);

  // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다.
  Scalar multiply(Scalar other);

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
  Scalar reciprocal();
}