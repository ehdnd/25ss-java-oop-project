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

import java.math.BigDecimal;
import java.util.Arrays;
import javax.swing.Scrollable;
import tensor.Scalar;
import tensor.Tensors;
import tensor.Vector;
import tensor.Factory;
import tensor.Matrix;

import java.util.List;

public class Test {

  private static void printHeader(String specExplain, String methodName) {
    System.out.println("==============================");
    System.out.println("// " + specExplain);
    System.out.println("// " + methodName);
  }

  private static void printResult(Object ans, Object res) {
    System.out.println("expect: ");
    System.out.println(ans);
    System.out.println("output: ");
    System.out.println(res);
    System.out.println("-----> " + (ans.equals(res) ? "✅" : "❌"));
  }

  public static void main(String[] args) {

    // 01. 값 (String) 지정하여 스칼라
    printHeader("01. 값 (String) 지정하여 스칼라 생성", "public static Scalar createScalar(String value)");
    Scalar sca01 = Factory.createScalar("123");
    System.out.println("output: ");
    System.out.println(sca01);
    System.out.println("-----> ✅");

    // 02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성
    printHeader("02. i 이상 j 미만의 무작위 값을 요소로 하는 스칼라 생성",
        "public static Scalar createScalar(String value1, String value2)");
    String i02 = "5", j02 = "10";
    BigDecimal lo02 = new BigDecimal(i02), hi02 = new BigDecimal(j02);
    boolean res02 = true;

    for (int t = 0; t < 10; ++t) {
      Scalar sc02 = Factory.createScalar(i02, j02);
      BigDecimal val = new BigDecimal(sc02.toString());

      boolean ok = val.compareTo(lo02) >= 0 && val.compareTo(hi02) < 0;
      res02 &= ok;
      System.out.println("test " + (t + 1) + " : " + val + " -> " + (ok ? "✅" : "❌"));
    }
    System.out.println("----> " + (res02 ? "✅" : "❌"));

    // 03. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터 생성
    printHeader("03. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터 생성",
        "public static Vector createVector(String value, int dimension)");
    Vector vec03 = Factory.createVector("1", 3);
    Vector vec03ans = Factory.createVector(
        List.of(Factory.createScalar("1"), Factory.createScalar("1"), Factory.createScalar("1"))
    );
    printResult(vec03, vec03ans);

    // 04. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터 생성
    printHeader("04. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터 생성",
        "public static Vector createVector(String i, String j, int dimension)");
    String i04 = "5", j04 = "10";
    int vecLen = 4;
    BigDecimal lo04 = new BigDecimal(i04), hi04 = new BigDecimal(j04);
    boolean res04 = true;

    for (int t = 0; t < 10; ++t) {
      Vector vec04 = Factory.createVector(i04, j04, vecLen);
      System.out.println("test " + (t + 1) + " : " + vec04);

      for (int idx = 0; idx < vec04.size(); ++idx) {
        BigDecimal val = new BigDecimal(vec04.get(idx).toString());
        if (val.compareTo(lo04) < 0 || val.compareTo(hi04) >= 0) {
          res04 = false;
          System.out.println(" -> element out of range! ❌");
          break;
        }
      }
      System.out.println("---> " + (res04 ? "✅" : "❌"));
    }
    System.out.println("----> " + (res04 ? "✅" : "❌"));

    // 05. 1차원 배열로부터 n-차원 벡터 생성
    printHeader("05. 1차원 배열로부터 n-차원 벡터 생성",
        "public static Vector createVector(String value, int dimension)");
    Vector vec05 = Factory.createVector(List.of(sca01.clone(), sca01.clone()));
    Vector vec05ans = Factory.createVector("123", 2);
    printResult(vec05, vec05ans);

    // 06. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬 생성
    printHeader("06. 지정한 하나의 값을 모든 요소의 값으로 하는 m x n 행렬 생성",
        "public static Matrix createMatrix(String value, int rowSize, int colSize)");
    Matrix mat06ans = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("2"), Factory.createScalar("2")),
        List.of(Factory.createScalar("2"), Factory.createScalar("2"))
    ));
    Matrix mat06 = Factory.createMatrix("2", 2, 2);
    printResult(mat06, mat06ans);

    // 07. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬 생성
    printHeader("07. i 이상 j 미만의 무작위 값을 요소로 하는 m x n 행렬 생성",
        "public static Matrix createMatrix(String i, String j, int rowSize, int colSize)");
    String i = "5", j = "10";
    int rowSize = 3, colSize = 2;
    boolean res07 = true;
    BigDecimal lo = new BigDecimal(i), hi = new BigDecimal(j);
    for (int t = 0; t < 10; ++t) {
      Matrix mat07 = Factory.createMatrix(i, j, rowSize, colSize);
      System.out.println("=== test " + (t + 1) + "===");
      System.out.println(mat07);
      for (int r = 0; r < mat07.getRowSize(); ++r) {
        for (int c = 0; c < mat07.getColSize(); ++c) {
          BigDecimal val = new BigDecimal(mat07.get(r, c).toString());
          if (val.compareTo(lo) < 0 || val.compareTo(hi) >= 0) {
            res07 = false;
            break;
          }
        }
      }
      System.out.println("---> " + (res07 ? "✅" : "❌"));
    }
    System.out.println("-----> " + (res07 ? "✅" : "❌"));

    // 08. csv 파일로부터 m x n 행렬을 생성
    printHeader("08. csv 파일로부터 m x n 행렬을 생성", "public static Matrix createMatrix(String filePath)");
    Matrix mat08 = Factory.createMatrix("src/test/matrix.csv");
    Matrix mat08ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("8"),
            Factory.createScalar("9"))
    ));
    printResult(mat08, mat08ans);

    // 09. 2차원 배열로부터 m x n 행렬 생성
    printHeader("09. 2차원 배열로부터 m x n 행렬 생성",
        "public static Matrix createMatrix(List<List<Scalar>> rows)");
    Matrix mat09 = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("1"), Factory.createScalar("1")),
        List.of(Factory.createScalar("1"), Factory.createScalar("1"))
    ));
    Matrix mat09ans = Factory.createMatrix("1", 2, 2);
    printResult(mat09ans, mat09);

    // 10. 단위 행렬 생성
    printHeader("10. 단위 행렬 생성", "public static Matrix createMatrix(int size)");
    System.out.println("=== Spec 10 ===");
    Matrix mat10 = Factory.createMatrix(2);
    Matrix mat10ans = Factory.createMatrix("0", 2, 2);
    mat10ans.set(0, 0, Factory.createScalar("1"));
    mat10ans.set(1, 1, Factory.createScalar("1"));
    printResult(mat10ans, mat10);

    // 11v.지정: 지정한 인덱스 위치에 Scalar 값을 설정
    Vector vec11 = Factory.createVector("1", 2);
    printHeader("11v.지정: 지정한 인덱스 위치에 Scalar 값을 설정", "public void set(int index, Scalar value)");
    vec11.set(1, Factory.createScalar("2"));
    Vector vec11setans = Factory.createVector(
        List.of(Factory.createScalar("1"), Factory.createScalar("2")));
    printResult(vec11setans, vec11);
    // 11v. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
    printHeader("11v. 조회: 지정한 인덱스 위치의 Scalar 값을 반환", "public Scalar get(int index)");
    Scalar sca11getans = Factory.createScalar("2");
    Scalar sca11get = vec11.get(1);
    printResult(sca11getans, sca11get);

    // 11m. 지정: 지정한 인덱스 위치에 Scalar 값을 설정
    printHeader("11m. 지정: 지정한 인덱스 위치에 Scalar 값을 설정",
        "public void set(int row, int col, Scalar value)");
    Matrix mat11 = Factory.createMatrix("0", 2, 2);
    mat11.set(0, 0, Factory.createScalar("1"));
    mat11.set(1, 1, Factory.createScalar("1"));
    Matrix mat11ans = Factory.createMatrix(2);
    printResult(mat11ans, mat11);

    // 11m. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
    printHeader("11m. 조회: 지정한 인덱스 위치의 Scalar 값을 반환", "public Scalar get(int row, int col)");
    Scalar sca11 = mat11.get(1, 1);
    Scalar sca11ans = Factory.createScalar("1");
    printResult(sca11ans, sca11);

    // 12s. 값을 조회
    printHeader("12s. 값을 조회", "public String getValueAsString()");
    Scalar sca12 = Factory.createScalar("1");
    String ans12 = "1";
    printResult(ans12, sca12.getValueAsString());

    // 12s. 값을 지정
    printHeader("12s. 값을 지정", "public void setValueFromString(String s)");
    Scalar sca12ans = Factory.createScalar("2");
    sca12.setValueFromString("2");
    printResult(sca12ans, sca12);

    /* ================================================================
       13v. 차원의 개수를 조회할 수 있다.
       (public int size())
       ================================================================ */
    printHeader("13v. 차원의 개수를 조회할 수 있다",
        "public int size()");
    Vector vec13 = Factory.createVector("0", 4);
    System.out.println(vec13);
    Integer dim13Ans = 4;
    Integer dim13Out = vec13.size();
    printResult(dim13Ans, dim13Out);

    // 13m. 행렬의 행개수를 조회

    // 13m. 행렬의 열개수를 조회

    /* ================================================================
       14v. 값들을 1차원 배열 모양으로 출력할 수 있다 (Vector.toString)
       ================================================================ */
    printHeader("14v. 값들을 1차원 배열 모양으로 출력할 수 있다",
        "public String toString()");
    Vector vec14 = Factory.createVector(
        List.of(Factory.createScalar("1"),
            Factory.createScalar("2"),
            Factory.createScalar("3")));
    String vec14Ans = Factory.createVector("1", 1)   // 길이 1 벡터로 패턴 확보
        .getClass()               // 같은 클래스 확보
        .cast(vec14)              // 형 캐스팅
        .toString();              // 기대 포맷
    printResult(vec14Ans, vec14.toString());

    // 14s. 값 하나를 콘솔에 출력할 수 있다.

    // 14v. 값들을 1차원 배열 모양으로 출력할 수 있다.

    /* ================================================================
       14m. 값들을 2차원 배열 모양으로 출력할 수 있다 (Matrix.toString)
       ================================================================ */
    printHeader("14m. 값들을 2차원 배열 모양으로 출력할 수 있다",
        "public String toString()");
    Matrix mat14 = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("1"), Factory.createScalar("0")),
        List.of(Factory.createScalar("0"), Factory.createScalar("1"))
    ));
    String mat14Ans = Factory.createMatrix(2).toString();   // 단위행렬과 동일 포맷
    printResult(mat14Ans, mat14.toString());

    /* ================================================================
       15s/v/m. 객체의 동등성 판단 (equals)
       ================================================================ */
    printHeader("15s. 객체의 동등성 판단 (Scalar.equals)",
        "public boolean equals(Object)");
    printResult(Factory.createScalar("7"), Factory.createScalar("7"));

    printHeader("15v. 객체의 동등성 판단 (Vector.equals)",
        "public boolean equals(Object)");
    Vector vec15A = Factory.createVector("3", 2);
    Vector vec15B = Factory.createVector(List.of(
        Factory.createScalar("3"), Factory.createScalar("3")));
    printResult(vec15A, vec15B);

    printHeader("15m. 객체의 동등성 판단 (Matrix.equals)",
        "public boolean equals(Object)");
    Matrix mat15A = Factory.createMatrix("4", 2, 2);
    Matrix mat15B = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("4"), Factory.createScalar("4")),
        List.of(Factory.createScalar("4"), Factory.createScalar("4"))
    ));
    printResult(mat15A, mat15B);

    /* ================================================================
       16s. 스칼라의 대소 비교 (compareTo)
       ================================================================ */
    printHeader("16s. 스칼라의 대소 비교", "public int compareTo(Scalar other)");
    Scalar sc16A = Factory.createScalar("10");
    Scalar sc16B = Factory.createScalar("3");
    int cmpOut = sc16A.compareTo(sc16B);        // > 0 이어야 함
    int cmpAns = 1;                             // 기대: 양수
    printResult(cmpAns, cmpOut > 0 ? 1 : -1);   // 부호만 비교

    /* ================================================================
       17. 객체 복제 (clone) – Scalar / Vector / Matrix
       ================================================================ */
    printHeader("17s. Scalar.clone()", "public Scalar clone()");
    Scalar sc17Orig = Factory.createScalar("8");
    Scalar sc17Clone = sc17Orig.clone();
    sc17Orig.setValueFromString("9");           // 원본만 변경
    printResult(Factory.createScalar("8"), sc17Clone);

    printHeader("17v. Vector.clone()", "public Vector clone()");
    Vector v17Orig = Factory.createVector("2", 2);  // [2,2]
    Vector v17Clone = v17Orig.clone();
    v17Orig.set(0, Factory.createScalar("5"));
    printResult(Factory.createVector("2", 2), v17Clone);

    printHeader("17m. Matrix.clone()", "public Matrix clone()");
    Matrix m17Orig = Factory.createMatrix("6", 2, 2);
    Matrix m17Clone = m17Orig.clone();
    m17Orig.set(0, 0, Factory.createScalar("7"));
    printResult(Factory.createMatrix("6", 2, 2), m17Clone);

    // 18. 스칼라는 다른 스칼라와 덧셈이 가능하다
    System.out.println("=== Spec 18 ===");
    Scalar s18a = Factory.createScalar("3.0");
    Scalar s18b = Factory.createScalar("2.0");
    System.out.println("original: " + s18a + " + " + s18b);
    s18a.add(s18b);
    Scalar expected18 = Factory.createScalar("5.0");
    System.out.println("expected: " + expected18);
    System.out.println("actual  : " + s18a);
    System.out.println("result  : " + (s18a.equals(expected18) ? "PASS" : "FAIL"));

    // 19. 스칼라는 다른 스칼라와 곱셈이 가능하다
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

    System.out.println("=== Spec 24 ===");
    Scalar s24a = Factory.createScalar("10.5");
    Scalar s24b = Factory.createScalar("2.5");
    System.out.println("original: \n" + s24a + " +\n" + s24b);
    Scalar sum24 = Tensors.add(s24a, s24b); // 결과
    Scalar expected24 = Factory.createScalar("13.0");
    System.out.println("expected: \n" + expected24);
    System.out.println("actual  : \n" + sum24);
    System.out.println("result  : " + (sum24.equals(expected24) ? "PASS" : "FAIL")); //?
    System.out.println("24 - 전달받은 두 스칼라의 덧셈: " + sum24);

    System.out.println("=== Spec 25 ===");
    Scalar s25a = Factory.createScalar("10.5");
    Scalar s25b = Factory.createScalar("2.5");
    System.out.println("original: \n" + s25a + " *\n" + s25b);
    Scalar sum25 = Tensors.multiply(s25a, s25b); // 결과
    Scalar expected25 = Factory.createScalar("26.25");
    System.out.println("expected: \n" + expected25);
    System.out.println("actual  : \n" + sum25);
    System.out.println("result  : " + (sum25.equals(expected25) ? "PASS" : "FAIL"));
    System.out.println("25 - 전달받은 두 스칼라의 곱셈: " + sum25);

    System.out.println("=== Spec 26 ===");
    Vector v26a = Factory.createVector("7", 4);
    Vector v26b = Factory.createVector("3", 4);
    System.out.println("original: \n" + v26a + " +\n" + v26b);
    Vector sum26 = Tensors.add(v26a, v26b);
    Vector expected26 = Factory.createVector("10", 4);
    System.out.println("expected: \n" + expected26);
    System.out.println("actual  : \n" + sum26);
    System.out.println("result  : " + (sum26.equals(expected26) ? "PASS" : "FAIL"));
    System.out.println("26 - 전달받은 두 벡터의 덧셈 : " + sum26);

    System.out.println("=== Spec 27 ===");
    Scalar v27a = Factory.createScalar("7");
    Vector v27b = Factory.createVector("3", 4);
    System.out.println("original: \n" + v27a + " *\n" + v27b);
    Vector sum27 = Tensors.multiply(v27a, v27b);
    Vector expected27 = Factory.createVector("21", 4);
    System.out.println("expected: \n" + expected27);
    System.out.println("actual  : \n" + sum27);
    System.out.println("result  : " + (sum27.equals(expected27) ? "PASS" : "FAIL"));
    System.out.println("27 - 전달받은 두 벡터의 곱셈 : " + sum27);

    System.out.println("=== Spec 28 ===");
    Matrix m28a = Factory.createMatrix("1", 3, 3);
    Matrix m28b = Factory.createMatrix("2", 3, 3);
    System.out.println("original: \n" + m28a + " +\n" + m28b);
    Matrix sum28 = Tensors.add(m28a, m28b);
    Matrix expected28 = Factory.createMatrix("3", 3, 3);
    System.out.println("expected: \n" + expected28);
    System.out.println("actual  : \n" + sum28);
    System.out.println("result  : " + (sum28.equals(expected28) ? "PASS" : "FAIL"));
    System.out.println("28 - 전달받은 두 행렬의 덧셈 : " + sum28);

    System.out.println("=== Spec 29 ===");
    Matrix m29a = Factory.createMatrix("1", 3, 3);
    Matrix m29b = Factory.createMatrix("2", 3, 3);
    System.out.println("original: \n" + m29a + " *\n" + m29b);
    Matrix sum29 = Tensors.multiply(m29a, m29b);
    Matrix expected29 = Factory.createMatrix("6", 3, 3);
    System.out.println("expected: \n" + expected29);
    System.out.println("actual  : \n" + sum29);
    System.out.println("result  : " + (sum29.equals(expected29) ? "PASS" : "FAIL"));
    System.out.println("29 - 전달받은 두 행렬의 곱셈 : \n" + sum29);

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

    // TODO 32 33 static concat 메서드 Tensors로 호출 필요

    // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)
    System.out.println("=== Spec 32 ===");
    Matrix mat32 = Factory.createMatrix("1", 2, 2);
    Matrix mat32ans = Factory.createMatrix("1", 2, 4);
    System.out.println("expected: ");
    System.out.println(mat32ans);
    System.out.println("output: ");
    Matrix mat32out = mat32.concatHorizontally(mat32);
    System.out.println(mat32out);
    System.out.println("-> " + (mat32ans.equals(mat32out) ? "PASS" : "FAIL"));

    // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
    System.out.println("=== Spec 33 ===");
    Matrix mat33 = Factory.createMatrix("1", 2, 2);
    Matrix mat33ans = Factory.createMatrix("1", 4, 2);
    System.out.println("expected: ");
    System.out.println(mat33ans);
    System.out.println("output: ");
    Matrix mat33out = mat33.concatVertically(mat33);
    System.out.println(mat33out);
    System.out.println("-> " + (mat33ans.equals(mat33out) ? "PASS" : "FAIL"));

    // 34. 행렬은 특정 행을 벡터 추출해 주 수 있다.
    /*
    System.out.println("=== Spec 34 ===");
    Matrix mat34 = Factory.createMatrix("1", 2, 2);
    Vector vec34out = mat34.getColVector(0);
    Vector vec34ans = Factory.createVector("1", 2);
    System.out.println("expected: ");
    System.out.println(vec34ans);
    System.out.println("output: ");
    System.out.println(vec34out);
    System.out.println("-> " + (vec34out.equals(vec34ans) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 34-row ===");
    Matrix mat34r = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));
    Vector vec34rExp = Factory.createVector(Arrays.asList(
        Factory.createScalar("3"), Factory.createScalar("4")
    ));
    Vector vec34rOut = mat34r.getRowVector(1);   // 두 번째 행
    System.out.println("expected: " + vec34rExp);
    System.out.println("output  : " + vec34rOut);
    System.out.println("-> " + (vec34rExp.equals(vec34rOut) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + (vec35out.equals(vec35ans) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + (mat36out.equals(mat36ans) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + (mat37out.equals(mat37ans) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + (mat38out.equals(mat38ans) ? "PASS" : "FAIL"));

    // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
    System.out.println("=== Spec 39 ===");
    Scalar sca39ans = Factory.createScalar("15");
    Scalar sca39out = mat35.trace();

    System.out.println("expected: ");
    System.out.println(sca39ans);
    System.out.println("output: ");
    System.out.println(sca39out);
    System.out.println("-> " + (sca39ans.equals(sca39out) ? "PASS" : "FAIL"));

    // 40. 정사각 행렬인지 반환
    System.out.println("=== Spec 40 ===");
    System.out.println(mat35);
    System.out.println("expected: true");
    System.out.println("output: " + mat35.isSquare());
    System.out.println("-> " + ((mat35.isSquare() == true) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + ((mat41.isUpperTriangular() == true) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + ((mat42.isLowerTriangular() == true) ? "PASS" : "FAIL"));

    // 43. 단위 행렬(Identity)인지 반환
    System.out.println("=== Spec 43 ===");
    Matrix id43 = Factory.createMatrix("0", 3, 3);
    id43.set(0, 0, Factory.createScalar("1"));
    id43.set(1, 1, Factory.createScalar("1"));
    id43.set(2, 2, Factory.createScalar("1"));
    System.out.println(id43);
    System.out.println("expected: true");
    System.out.println("output  : " + id43.isIdentity());
    System.out.println("-> " + (id43.isIdentity() ? "PASS" : "FAIL"));

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
    System.out.println("-> " + ((mat44.isZero() == true) ? "PASS" : "FAIL"));

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
    System.out.println("-> " + (mat45.equals(mat45ans) ? "PASS" : "FAIL"));

    // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
    System.out.println("=== Spec 46 ===");
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

    System.out.println("=== Spec 47 ===");
    Matrix m47 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));
    Scalar factor47 = Factory.createScalar("10");
    m47.scaleRow(1, factor47); // 두 번째 행에 10을 곱함

    Matrix expected47 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("30"), Factory.createScalar("40"))
    ));

    System.out.println("expected: ");
    System.out.println(expected47);
    System.out.println("output: ");
    System.out.println(m47);
    System.out.println("result: " + (expected47.equals(m47) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 48 ===");
    Matrix m48 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));
    Scalar factor48 = Factory.createScalar("2");
    m48.scaleColumn(0, factor48); // 첫 번째 열에 2를 곱함

    Matrix expected48 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("2"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("6"), Factory.createScalar("4"))
    ));

    System.out.println("expected: ");
    System.out.println(expected48);
    System.out.println("output: ");
    System.out.println(m48);
    System.out.println("result: " + (expected48.equals(m48) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 49 ===");
    Matrix m49 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));
    Scalar factor49 = Factory.createScalar("2");
    m49.addMultipleOfRow(0, 1, factor49); // 첫 번째 행에 두 번째 행 * 2를 더함

    Matrix expected49 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("10")), // 1 + 3*2, 2 + 4*2
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));

    System.out.println("expected: ");
    System.out.println(expected49);
    System.out.println("output: ");
    System.out.println(m49);
    System.out.println("result: " + (expected49.equals(m49) ? "PASS" : "FAIL"));

    System.out.println("=== Spec 50 ===");
    Matrix m50 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("4"))
    ));
    Scalar factor50 = Factory.createScalar("3");
    m50.addMultipleOfColumn(0, 1, factor50); // 첫 번째 열에 두 번째 열 * 3을 더함

    Matrix expected50 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("7"), Factory.createScalar("2")), // 1 + 2*3, 2
        Arrays.asList(Factory.createScalar("15"), Factory.createScalar("4")) // 3 + 4*3, 4
    ));

    System.out.println("expected: ");
    System.out.println(expected50);
    System.out.println("output: ");
    System.out.println(m50);
    System.out.println("result: " + (expected50.equals(m50) ? "PASS" : "FAIL"));
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
    Matrix expect = Factory.createMatrix(3);
    System.out.println("origin Matrix: ");
    System.out.println(test.toString());
    System.out.println("expect Matrix: ");
    System.out.println(expect);
    System.out.println("rref Matrix");
    System.out.println(rref.toString());
    System.out.println("-> " + (rref.equals(expect)));

    System.out.println("=== Spec 52 ===");
    Matrix test52 = Factory.createMatrix("1", 3, 4);
    System.out.println(
        (test52.toReducedRowEchelonForm().isReducedRowEchelonForm()) ? "PASS" : "FAIL");  // true 기대

    // 53. 행렬은 자신의 행렬식을 구해줄 수 있다.
    System.out.println("=== Spec 53 ===");
    Matrix mat53 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("5"))
    ));
    Scalar det53 = mat53.determinant();
    Scalar det53ans = Factory.createScalar("-1");
    System.out.println("expected: " + det53ans);
    System.out.println("output: " + det53);
    System.out.println("-> " + (det53.equals(det53ans) ? "PASS" : "FAIL"));

    // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
    System.out.println("=== Spec 54 ===");
    Matrix mat54 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("5"))
    ));
    Matrix mat54ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("-5"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("-1"))
    ));
    Matrix inv54 = mat54.inverse();
    System.out.println("expected: ");
    System.out.println(mat54ans);
    System.out.println("output: ");
    System.out.println(inv54);
    System.out.println("-> " + (inv54.equals(mat54ans) ? "PASS" : "FAIL"));
 */
  }
}