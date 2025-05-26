package tensor;

import java.math.BigDecimal;

/*
 * 모든 메소드가 public static
 * 위의 디폴트 static 메소드를 구현하라고 한 스칼라, 벡터, 행렬의 연산 기능을 호출한 후 반환된 객체를 반환하는 메소드들
 * Tensor의 사용자는 Test.java로 상정
 * 같은 방식으로 사용될 메소드들을 Tensors 클래스에 추가 가능
 */

/*
 * 디폴드 메서드들
 * 스칼라의 연산
 * 전달받은 두 스칼라의 덧셈
 * 전달받은 두 스칼라의 곱셈
 * 벡터의 연산
 * 전달받은 두 벡터의 덧셈
 * 전달받은 스칼라와 벡터의 곱셈
 * 행렬의 연산
 * 전달받은 두 행렬의 덧셈
 * 전달받은 두 행렬의 곱셈
 */

public class Tensors {

    // ===== 스칼라 연산 =====

    // 두 스칼라의 덧셈 결과를 반환
    public static Scalar add(Scalar a, Scalar b) {
        return ScalarImpl.add(a, b);
    }

    // 두 스칼라의 곱셈 결과를 반환
    public static Scalar multiply(Scalar a, Scalar b) {
        return ScalarImpl.multiply(a, b);
    }

}