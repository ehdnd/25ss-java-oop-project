package test;

/*
TODO
 * Factory 를 이용하여 스칼라 벡터 행렬 객체를 얻어낸다
 * 스칼라 벡터 행렬 객체의 참조 타입은 인터페이스 타입만을 사용한다
   * 즉 소스에서 구현 클래스 는 등장하지 않는다
 */

/*
NOTE
 * Tensor 메서드 최소 1회 호출 - 함수의 동작 확인 가능하도록
 * 로직은 팀별로 제작, 구체적 행렬의 값은 개별로 준비
 * 1차 제출 시 Test.java 제출, 한 명만 tensor 제출 가능
 * 명세 15번 함수 테스트 -> 각 문항에 따라 성공 / 실패 출력
 * equals 사용해서 true / false -> 성공 / 실패 출력
 * 출력 화면만으로 결과를 이해할 수 있도록 제작필요
 * toString -> 행렬 출력하도록 오버라이드
 */

import tensor.Factory;
import tensor.Matrix;
import tensor.Scalar;
import tensor.Vector;
import tensor.Tensors;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.lang.CloneNotSupportedException;

public class Test {
    public static void main(String[] args) {
        System.out.println("=== Spec 01 ===");
        Scalar scalar01 = Factory.createScalar("3.14");
        System.out.println("01 - 문자열을 통한 스칼라 생성: " + scalar01);

        System.out.println("=== Spec 02 ===");
        Scalar scalar02 = Factory.createScalar("2","5");
        System.out.println("02 - 무작위 값 기반 스칼라 생성 : " + scalar02);

        System.out.println("=== Spec 03 ===");
        Vector vector03 = Factory.createVector("1", 5);
        System.out.println("03 - 하나의 값을 반복한 n-차원 벡터 생성: " + vector03);

        System.out.println("=== Spec 04 ===");
        Vector vector04 = Factory.createVector("1.0", "2.0", 3);
        System.out.println("04 - i이상 j미만의 무작위 값을 요소로 하는 n-차원 벡터 생성: " + vector04);

        System.out.println("=== Spec 05 ===");
        Vector vector05 = Factory.createVector(List.of(scalar01, scalar02));
        System.out.println("05 - 1차원 배열로부터 n-차원 벡터를 생성할 수 있다. : " + vector05);

        System.out.println("=== Spec 06 ===");
        Matrix matrix06 = Factory.createMatrix("1" , 3, 2); // 3x2 모두 같은 값
        System.out.println("06 - 하나의 값으로 m x n 행렬 생성: " + matrix06);

        System.out.println("=== Spec 07 ===");
        Matrix matrix07 = Factory.createMatrix("1.0", "2.0", 3, 4);
        System.out.println("07 - i이상 j미만의 무작위 값을 요소로 하는 m x n 행렬 생성: " + matrix07);

        System.out.println("=== Spec 08 ===");
        String csv = "1,2,3\n4,5,6";
        //Matrix matrix08 = Factory.createMatrix();
        //System.out.println("08 - CSV로부터 행렬 생성: " + matrix08);

        System.out.println("=== Spec 09 ===");
        Matrix matrix09 = Factory.createMatrix(List.of(
            List.of(scalar01, scalar02),
            List.of(scalar02, scalar01)
        ));
        System.out.println("09 - 2차원 리스트로부터 행렬 생성: " + matrix09);

        System.out.println("=== Spec 10 ===");
        //Matrix matrix10 = Factory.createMatrix(3);
        //System.out.println("10 - Matrix 07의 단위행렬 생성: " + matrix10);

        System.out.println("=== Spec 11 ===");
        Scalar element11v = vector03.get(2); // 3번째 요소 가져오기
        Scalar element11m = matrix06.get(1, 0); // 2행 1열 요소
        System.out.println("11v - 벡터 특정 위치 조회: " + element11v);
        System.out.println("11m - 행렬 특정 위치 조회: " + element11m);

        System.out.println("=== Spec 12 ===");
        Scalar scalar12 = Factory.createScalar("7.77");
        System.out.println("스칼라 값 지정하기 전 : " + scalar12);
        scalar12.setValueFromString("8.88");
        System.out.println("12 - 스칼라 값 지정 후 : " + scalar12);
        System.out.println("12 - 스칼라 값 조회 :  " + scalar12.getValueAsString());

        System.out.println("=== Spec 13 ===");
        System.out.println("13v - 벡터 크기(size): " + vector03.size());
        System.out.println("13m - 행렬 크기(rows x cols): " + matrix06.getRowSize() + "x" + matrix06.getColSize());

        System.out.println("=== Spec 14 ===");
        System.out.println("14s - 스칼라 toString(): " + scalar01);
        System.out.println("14v - 벡터 toString(): " + vector03);
        System.out.println("14m - 행렬 toString(): " + matrix06);

        System.out.println("=== Spec 15 ===");
        Scalar s1 = Factory.createScalar("5");
        Scalar s2 = Factory.createScalar("5");
        Vector v1 = Factory.createVector("2", 3);
        Vector v2 = Factory.createVector("2", 3);
        Matrix m1 = Factory.createMatrix("0", 2, 2);
        Matrix m2 = Factory.createMatrix("0", 2, 2);
        System.out.println("15s - Scalar equals: " + s1.equals(s2));
        System.out.println("15v - Vector equals: " + v1.equals(v2));
        System.out.println("15m - Matrix equals: " + m1.equals(m2));

        System.out.println("=== Spec 16 ===");
        List<Scalar> list = Arrays.asList(s1, scalar01, scalar12);
        list.sort(null); // Comparable 구현 여부 테스트
        System.out.println("16 - 스칼라 정렬: " + list);

        System.out.println("=== Spec 17 ===");

            Scalar cloneS = (Scalar) scalar01.clone();
            Vector cloneV = (Vector) vector03.clone();
            Matrix cloneM = (Matrix) matrix06.clone();
            System.out.println("17s - Scalar clone: " + cloneS);
            System.out.println("17v - Vector clone: " + cloneV);
            System.out.println("17m - Matrix clone: " + cloneM);

    }
}