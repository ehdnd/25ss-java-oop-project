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

import tensor.Scalar;
import tensor.Vector;
import tensor.Factory;
import tensor.Matrix;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.lang.CloneNotSupportedException;

public class Test {

  public static void main(String[] args) {
    /*
    System.out.println("=== Spec 01 ===");
    Scalar scalar01 = Factory.createScalar("3.14");
    System.out.println("01 - 문자열을 통한 스칼라 생성: " + scalar01);

    System.out.println("=== Spec 02 ===");
    Scalar scalar02 = Factory.createScalar("2", "5");
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
    Matrix matrix06 = Factory.createMatrix("1", 3, 2); // 3x2 모두 같은 값
    System.out.println("06 - 하나의 값으로 m x n 행렬 생성: " + matrix06);

    System.out.println("=== Spec 07 ===");
    Matrix matrix07 = Factory.createMatrix("1.0", "2.0", 3, 4);
    System.out.println("07 - i이상 j미만의 무작위 값을 요소로 하는 m x n 행렬 생성: " + matrix07);

    System.out.println("=== Spec 08 ===");
    Matrix matrix08 = Factory.createMatrix("src/matrix.csv");
    System.out.println("08 - CSV로부터 행렬 생성: " + matrix08);

    System.out.println("=== Spec 09 ===");
    Matrix matrix09 = Factory.createMatrix(List.of(
        List.of(scalar01, scalar02),
        List.of(scalar02, scalar01)
    ));
    System.out.println("09 - 2차원 리스트로부터 행렬 생성: " + matrix09);

    System.out.println("=== Spec 10 ===");
    Matrix matrix10 = Factory.createMatrix(3);
    System.out.println("10 - Matrix 07의 단위행렬 생성: " + matrix10);

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
    System.out.println(
        "13m - 행렬 크기(rows x cols): " + matrix06.getRowSize() + "x" + matrix06.getColSize());

    System.out.println("=== Spec 14 ===");
    System.out.println("14s - 스칼라 toString(): " + scalar01);
    System.out.println("14v - 벡터 toString(): " + vector03);
    System.out.println("14m - 행렬 toString(): \n" + matrix06);

    System.out.println("=== Spec 15 ===");
    Scalar s1 = Factory.createScalar("5");
    Scalar s2 = Factory.createScalar("5");
    Vector v1 = Factory.createVector("2", 3);
    Vector v2 = Factory.createVector("2", 3);
    Matrix m1 = Factory.createMatrix("0", 2, 2);
    Matrix m2 = Factory.createMatrix("0", 2, 2);
    System.out.println("15s - Scalar equals: " + s1.equals(s2));
    System.out.println("15v - Vector equals: " + v1.equals(v2));
    System.out.println("15m - Matrix equals: \n" + m1.equals(m2));

    System.out.println("=== Spec 16 ===");
    List<Scalar> list = Arrays.asList(s1, scalar01, scalar12);
    list.sort(null); // Comparable 구현 여부 테스트
    System.out.println("16 - 스칼라 정렬: " + list);

    //  이런식으로 정확하게 복사되었는지 확인해야하지 않을까요?
    System.out.println("=== Spec 17 ===");
    Scalar cloneS = (Scalar) scalar01.clone();
    Vector cloneV = (Vector) vector03.clone();
    Matrix cloneM = (Matrix) matrix06.clone();
    System.out.println("17s - Scalar clone");
    System.out.println("origin Scalar: " + scalar01 + ", " + "Scalar clone: " + cloneS);
    System.out.println("origin Scalar == clone Scalar : " + scalar01.equals(cloneS));
    System.out.println("17v - Vector clone: " + cloneV);
    System.out.println("17m - Matrix clone: \n" + cloneM);

    System.out.println("=== Spec 18 ===");
    Scalar s18a = Factory.createScalar("3.0");
    Scalar s18b = Factory.createScalar("2.0");
    System.out.println("original: " + s18a + " + " + s18b);
    s18a.add(s18b);
    Scalar expected18 = Factory.createScalar("5.0");
    System.out.println("expected: " + expected18);
    System.out.println("actual  : " + s18a);
    System.out.println("result  : " + (s18a.equals(expected18) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 19 ===");
    Scalar s19a = Factory.createScalar("3.0");
    Scalar s19b = Factory.createScalar("2.0");
    System.out.println("original: " + s19a + " * " + s19b);
    s19a.multiply(s19b);
    Scalar expected19 = Factory.createScalar("6.00");
    System.out.println("expected: " + expected19);
    System.out.println("actual  : " + s19a);
    System.out.println("result  : " + (s19a.equals(expected19) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 20 ===");
    Vector v20a = Factory.createVector("1", 3);
    Vector v20b = Factory.createVector("2", 3);
    System.out.println("original: " + v20a + " + " + v20b);
    v20a.add(v20b);
    Vector expected20 = Factory.createVector("3", 3);
    System.out.println("expected: " + expected20);
    System.out.println("actual  : " + v20a);
    System.out.println("result  : " + (v20a.equals(expected20) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 21 ===");
    Vector v21 = Factory.createVector("2", 4);
    Scalar s21 = Factory.createScalar("3");
    System.out.println("original: " + v21 + " * " + s21);
    v21.multiply(s21);
    Vector expected21 = Factory.createVector("6", 4);
    System.out.println("expected: " + expected21);
    System.out.println("actual  : " + v21);
    System.out.println("result  : " + (v21.equals(expected21) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 22 ===");
    Matrix m22a = Factory.createMatrix("1", 2, 2);
    Matrix m22b = Factory.createMatrix("2", 2, 2);
    System.out.println("original: \n" + m22a + " +\n" + m22b);
    m22a.add(m22b);
    Matrix expected22 = Factory.createMatrix("3", 2, 2);
    System.out.println("expected: \n" + expected22);
    System.out.println("actual  : \n" + m22a);
    System.out.println("result  : " + (m22a.equals(expected22) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 23 ===");
    Matrix m23a = Factory.createMatrix("1", 2, 3); // 2x3
    Matrix m23b = Factory.createMatrix("2", 3, 2); // 3x2
    System.out.println("original: \n" + m23a + " *\n" + m23b);
    m23a.mul(m23b); // 2x2 결과
    Matrix expected23 = Factory.createMatrix("6", 2, 2);
    System.out.println("expected: \n" + expected23);
    System.out.println("actual  : \n" + m23a);
    System.out.println("result  : " + (m23a.equals(expected23) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 30 ===");
    Vector vec30 = Factory.createVector("5", 3); // [5, 5, 5]
    Matrix expectedColMatrix = Factory.createMatrix(Arrays.asList(
            List.of(Factory.createScalar("5")),
            List.of(Factory.createScalar("5")),
            List.of(Factory.createScalar("5"))
    ));
    Matrix actualColMatrix = vec30.toColumnMatrix();
    System.out.println("expected: ");
    System.out.println(expectedColMatrix);
    System.out.println("output: ");
    System.out.println(actualColMatrix);
    System.out.println("result: " + (expectedColMatrix.equals(actualColMatrix) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 31 ===");
    Vector vec31 = Factory.createVector("7", 4); // [7, 7, 7, 7]
    Matrix expectedRowMatrix = Factory.createMatrix(Arrays.asList(
            Arrays.asList(
                    Factory.createScalar("7"),
                    Factory.createScalar("7"),
                    Factory.createScalar("7"),
                    Factory.createScalar("7")
            )
    ));
    Matrix actualRowMatrix = vec31.toRowMatrix();
    System.out.println("expected: ");
    System.out.println(expectedRowMatrix);
    System.out.println("output: ");
    System.out.println(actualRowMatrix);
    System.out.println("result: " + (expectedRowMatrix.equals(actualRowMatrix) ? "PASS" : "FAIL"));
*/
    // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
    System.out.println("=== Spec 32 ===");
    Matrix mat32 = Factory.createMatrix("1", 2, 2);
    Matrix mat32ans = Factory.createMatrix("1", 2, 4);
    System.out.println("expected: ");
    System.out.println(mat32ans);
    System.out.println("output: ");
    Matrix mat32out = mat32.concatHorizontally(mat32);
    System.out.println(mat32out);
    System.out.println("-> " + mat32ans.equals(mat32out));

    // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
    System.out.println("=== Spec 33 ===");
    Matrix mat33 = Factory.createMatrix("1", 2, 2);
    Matrix mat33ans = Factory.createMatrix("1", 4, 2);
    System.out.println("expected: ");
    System.out.println(mat33ans);
    System.out.println("output: ");
    Matrix mat33out = mat33.concatVertically(mat33);
    System.out.println(mat33out);
    System.out.println("-> " + mat33ans.equals(mat33out));

    // 34. 행렬은 특정 행을 벡터 추출해 주 수 있다.
    System.out.println("=== Spec 34 ===");
    Matrix mat34 = Factory.createMatrix("1", 2, 2);
    Vector vec34out = mat34.getColVector(0);
    Vector vec34ans = Factory.createVector("1", 2);
    System.out.println("expected: ");
    System.out.println(vec34ans);
    System.out.println("output: ");
    System.out.println(vec34out);
    System.out.println("-> " + vec34out.equals(vec34ans));

    // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
    System.out.println("=== Spec 35 ===");
    Matrix mat35 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("8"),
            Factory.createScalar("9"))
    ));
    Vector vec35out = mat35.getColVector(0);
    Vector vec35ans = Factory.createVector(Arrays.asList(
        Factory.createScalar("1"),
        Factory.createScalar("4"),
        Factory.createScalar("7")
    ));
    System.out.println("expected: ");
    System.out.println(vec35ans);
    System.out.println("output: ");
    System.out.println(vec35out);
    System.out.println("-> " + vec35out.equals(vec35ans));

    // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    System.out.println("=== Spec 36 ===");
    Matrix mat36out = mat35.subMatrix(0, 2, 0, 2);
    Matrix mat36ans = Factory.createMatrix(
        Arrays.asList(
            Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
            Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"))
        )
    );
    System.out.println("expected: ");
    System.out.println(mat36ans);
    System.out.println("output: ");
    System.out.println(mat36out);
    System.out.println("-> " + mat36out.equals(mat36ans));

    // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    System.out.println("=== Spec 37 ===");
    Matrix mat37out = mat35.minor(1, 1);
    Matrix mat37ans = Factory.createMatrix(
        Arrays.asList(
            Arrays.asList(Factory.createScalar("1"), Factory.createScalar("3")),
            Arrays.asList(Factory.createScalar("7"), Factory.createScalar("9"))
        )
    );
    System.out.println("expected: ");
    System.out.println(mat37ans);
    System.out.println("output: ");
    System.out.println(mat37out);
    System.out.println("-> " + mat37out.equals(mat37ans));

    // 38. 행렬은 전치행렬을 구해 줄 수 있다.
    System.out.println("=== Spec 38 ===");
    Matrix mat38out = mat35.transpose();
    Matrix mat38ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("4"),
            Factory.createScalar("7")),
        Arrays.asList(Factory.createScalar("2"), Factory.createScalar("5"),
            Factory.createScalar("8")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("6"),
            Factory.createScalar("9"))
    ));
    System.out.println("expected: ");
    System.out.println(mat38ans);
    System.out.println("output: ");
    System.out.println(mat38out);
    System.out.println("-> " + mat38out.equals(mat38ans));

    // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
    System.out.println("=== Spec 39 ===");
    Scalar sca39ans = Factory.createScalar("15");
    Scalar sca39out = mat35.trace();

    System.out.println("expected: ");
    System.out.println(sca39ans);
    System.out.println("output: ");
    System.out.println(sca39out);
    System.out.println("-> " + sca39ans.equals(sca39out));

    // 40. 정사각 행렬인지 반환
    System.out.println("=== Spec 40 ===");
    System.out.println(mat35);
    System.out.println("expected: true");
    System.out.println("output: " + mat35.isSquare());
    System.out.println("-> " + (mat35.isSquare() == true));

    // 41. 상삼각 행렬인지 반환
    System.out.println("=== Spec 41 ===");
    Matrix mat41 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("9"))
    ));
    System.out.println(mat41);
    System.out.println("expected: true");
    System.out.println("output: " + mat41.isUpperTriangular());
    System.out.println("-> " + (mat41.isUpperTriangular() == true));

    // 42. 하삼각 행렬인지 반환
    System.out.println("=== Spec 42 ===");
    Matrix mat42 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("0"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("5"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("1"),
            Factory.createScalar("9"))
    ));
    System.out.println(mat42);
    System.out.println("expected: true");
    System.out.println("output: " + mat42.isLowerTriangular());
    System.out.println("-> " + (mat42.isLowerTriangular() == true));

    // 43. 단위 행렬(Identity)인지 반환
    System.out.println("=== Spec 43 ===");
    Matrix mat43 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("0"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("1"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("1"))
    ));
    System.out.println(mat43);
    System.out.println("expected: true");
    System.out.println("output: " + mat43.isIdentity());
    System.out.println("-> " + (mat43.isIdentity() == true));

    // 44. 영행렬(Zero)인지 반환
    System.out.println("=== Spec 44 ===");
    Matrix mat44 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("0"))
    ));
    System.out.println(mat44);
    System.out.println("expected: true");
    System.out.println("output: " + mat44.isZero());
    System.out.println("-> " + (mat44.isZero() == true));

    // 45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.
    System.out.println("=== Spec 45 ===");
    Matrix mat45 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("8"),
            Factory.createScalar("9"))
    ));
    Matrix mat45ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("8"),
            Factory.createScalar("9"))
    ));
    mat45.swapRows(0, 1);
    System.out.println("expected: ");
    System.out.println(mat45ans);
    System.out.println("output: ");
    System.out.println(mat45);
    System.out.println("-> " + mat45.equals(mat45ans));

    // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
    System.out.println("=== Spec 45 ===");
    Matrix mat46 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("8"),
            Factory.createScalar("9"))
    ));
    Matrix mat46ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("2"), Factory.createScalar("1"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("5"), Factory.createScalar("4"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("8"), Factory.createScalar("7"),
            Factory.createScalar("9"))
    ));
    mat46.swapColumns(0, 1);
    System.out.println("expected: ");
    System.out.println(mat46ans);
    System.out.println("output: ");
    System.out.println(mat46);
    System.out.println("-> " + (mat46.equals(mat46ans) ? "PASS" : "FAIL"));

    /*
    // HACK : csv 파일로 행렬 제작 수정
    // XXX : 테스트 하드코딩 시 분수 계산 없는 RREF 제작 필요 -> 논의 필요
    System.out.println("=== Spec 51 ===");
    List<List<Scalar>> data = Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("1"),
            Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("1"),
            Factory.createScalar("2"))
    );
    Matrix test = Factory.createMatrix(data);
    Matrix rref = test.toReducedRowEchelonForm();
    System.out.println("origin Matrix");
    System.out.println(test.toString());
    System.out.println("expect Matrix");
    System.out.println(Factory.createMatrix(3));
    System.out.println("rref Matrix");
    System.out.println(rref.toString());

    System.out.println("=== Spec 52 ===");
    Matrix test52 = Factory.createMatrix("1", 3, 4);
    System.out.println(test52.toReducedRowEchelonForm().isReducedRowEchelonForm());  // true 기대
    */
  }
}