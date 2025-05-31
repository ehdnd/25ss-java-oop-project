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
    Scalar sca01 = Factory.createScalar("323");
    printResult("323", sca01.toString());

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
    Vector vec05ans = Factory.createVector("323", 2);
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

    /* ================================================================
      13m. 행렬의 행/열 개수를 조회할 수 있다
    ================================================================ */
    printHeader("13m. 행렬의 행개수를 조회",
        "public int getRowSize()");
    Matrix m13 = Factory.createMatrix("0", 3, 4);  // 3×4
    printResult(3, m13.getRowSize());

    printHeader("13m. 행렬의 열개수를 조회",
        "public int getColSize()");
    printResult(4, m13.getColSize());

    /* ================================================================
   14s. 값 하나를 콘솔에 출력할 수 있다 (Scalar.toString)
   ================================================================ */
    printHeader("14s. 값 하나를 콘솔에 출력할 수 있다",
        "public String toString()");
    Scalar sc14 = Factory.createScalar("123.45");
    printResult("123.45", sc14.toString());

    /* ================================================================
       14v. 값들을 1차원 배열 모양으로 출력할 수 있다 (Vector.toString)
       ================================================================ */
    printHeader("14v. 값들을 1차원 배열 모양으로 출력할 수 있다",
        "public String toString()");
    Vector vec14 = Factory.createVector(
        List.of(Factory.createScalar("1"),
            Factory.createScalar("2"),
            Factory.createScalar("3")));
    printResult("1, 2, 3", vec14.toString());

    /* ================================================================
       14m. 값들을 2차원 배열 모양으로 출력할 수 있다 (Matrix.toString)
       ================================================================ */
    printHeader("14m. 값들을 2차원 배열 모양으로 출력할 수 있다",
        "public String toString()");
    Matrix mat14 = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("1"), Factory.createScalar("0")),
        List.of(Factory.createScalar("0"), Factory.createScalar("1"))
    ));
    printResult("1, 0\n0, 1", mat14.toString());

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

    /* 18. 스칼라는 다른 스칼라와 덧셈이 가능하다 (in-place) */
    printHeader("18. 스칼라는 다른 스칼라와 덧셈이 가능하다",
        "public void add(Scalar other)");
    Scalar s18a = Factory.createScalar("1.0");
    Scalar s18b = Factory.createScalar("2.0");
    s18a.add(s18b);                               // in-place
    printResult(Factory.createScalar("3.0"), s18a);

    /* 19. 스칼라는 다른 스칼라와 곱셈이 가능하다 (in-place) */
    printHeader("19. 스칼라는 다른 스칼라와 곱셈이 가능하다",
        "public void multiply(Scalar other)");
    Scalar s19a = Factory.createScalar("3.0");
    Scalar s19b = Factory.createScalar("4.0");
    s19a.multiply(s19b);                          // in-place
    printResult(Factory.createScalar("12.0"), s19a);

    /* 20. 벡터는 벡터와 덧셈이 가능하다 (in-place) */
    printHeader("20. 벡터는 벡터와 덧셈이 가능하다",
        "public void add(Vector other)");
    Vector v20a = Factory.createVector("3", 3);
    Vector v20b = Factory.createVector("2", 3);
    v20a.add(v20b);
    printResult(Factory.createVector("5", 3), v20a);

    /* 21. 벡터는 스칼라와 곱셈이 가능하다 (in-place) */
    printHeader("21. 벡터는 스칼라와 곱셈이 가능하다",
        "public void multiply(Scalar k)");
    Vector v21 = Factory.createVector("2", 4);
    Scalar k21 = Factory.createScalar("3");
    v21.multiply(k21);
    printResult(Factory.createVector("6", 4), v21);

    /* 22. 행렬은 행렬과 덧셈이 가능하다 (in-place) */
    printHeader("22. 행렬은 행렬과 덧셈이 가능하다",
        "public void add(Matrix other)");
    Matrix m22a = Factory.createMatrix("4", 2, 2);
    Matrix m22b = Factory.createMatrix("2", 2, 2);
    m22a.add(m22b);
    printResult(Factory.createMatrix("6", 2, 2), m22a);

    /* 23. 행렬은 행렬과 곱셈이 가능하다 (in-place) */
    printHeader("23. 행렬은 행렬과 곱셈이 가능하다",
        "public void mul(Matrix other)");
    Matrix m23a = Factory.createMatrix("3", 2, 3);   // 2×3
    Matrix m23b = Factory.createMatrix("2", 3, 2);   // 3×2
    m23a.mul(m23b);                                   // 2×2
    printResult(Factory.createMatrix("18", 2, 2), m23a);

    /* 24. 전달받은 두 스칼라의 덧셈 (static) */
    printHeader("24. 전달받은 두 스칼라의 덧셈",
        "public static Scalar Tensors.add(Scalar a, Scalar b)");
    Scalar s24a = Factory.createScalar("10.5");
    Scalar s24b = Factory.createScalar("2.5");
    Scalar res24 = Tensors.add(s24a, s24b);          // 결과 새 객체
    printResult(Factory.createScalar("13.0"), res24);

    /* 25. 전달받은 두 스칼라의 곱셈 (static) */
    printHeader("25. 전달받은 두 스칼라의 곱셈",
        "public static Scalar Tensors.multiply(Scalar a, Scalar b)");
    Scalar s25a = Factory.createScalar("3.5");
    Scalar s25b = Factory.createScalar("2");
    Scalar res25 = Tensors.multiply(s25a, s25b);
    printResult(Factory.createScalar("7"), res25);

    /* 26. 전달받은 두 벡터의 덧셈 (static) */
    printHeader("26. 전달받은 두 벡터의 덧셈",
        "public static Vector Tensors.add(Vector a, Vector b)");
    Vector v26a = Factory.createVector("5", 4);
    Vector v26b = Factory.createVector("3", 4);
    Vector res26 = Tensors.add(v26a, v26b);
    printResult(Factory.createVector("8", 4), res26);

    /* 27. (static) Scalar × Vector → Vector */
    printHeader("27. 전달받은 스칼라·벡터의 곱셈",
        "public static Vector Tensors.multiply(Scalar k, Vector v)");
    Scalar k27 = Factory.createScalar("10");
    Vector v27 = Factory.createVector("3", 4);
    Vector res27 = Tensors.multiply(k27, v27);
    printResult(Factory.createVector("30", 4), res27);

    /* 28. (static) 두 행렬 덧셈 – 래퍼·직접 둘 다 */
    printHeader("28. 전달받은 두 행렬의 덧셈",
        "public static Matrix Tensors.add(Matrix a, Matrix b)");
    Matrix m28a = Factory.createMatrix("3", 3, 3);
    Matrix m28b = Factory.createMatrix("2", 3, 3);
    Matrix res28 = Tensors.add(m28a, m28b);
    printResult(Factory.createMatrix("5", 3, 3), res28);


    /* 29. (static) 두 행렬 곱셈 – 래퍼·직접 둘 다 */
    printHeader("29. 전달받은 두 행렬의 곱셈",
        "public static Matrix Tensors.multiply(Matrix a, Matrix b)");
    Matrix m29a = Factory.createMatrix("3", 3, 3);
    Matrix m29b = Factory.createMatrix("2", 3, 3);
    Matrix res29 = Tensors.multiply(m29a, m29b);
    printResult(Factory.createMatrix("18", 3, 3), res29);

    // 30. n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
    printHeader("30. n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.", "public Matrix toColumnMatrix()");
    Vector vec30 = Factory.createVector("5", 3);
    Matrix expCol30 = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("5")),
        List.of(Factory.createScalar("5")),
        List.of(Factory.createScalar("5"))));
    printResult(expCol30, vec30.toColumnMatrix());

    // 31. n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
    printHeader("31. n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.", "public Matrix toRowMatrix()");
    Vector vec31 = Factory.createVector("3", 4);
    Matrix expRow31 = Factory.createMatrix(List.of(
        List.of(Factory.createScalar("3"),
            Factory.createScalar("3"),
            Factory.createScalar("3"),
            Factory.createScalar("3"))));
    printResult(expRow31, vec31.toRowMatrix());

    // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능) non static
    printHeader("32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)",
        "public Matrix concatHorizontally(Matrix other)");
    Matrix mat32A = Factory.createMatrix("11", 2, 2);
    Matrix exp32A = Factory.createMatrix("11", 2, 4);   // 2×2 → 2×4
    mat32A.concatHorizontally(mat32A);
    printResult(exp32A, mat32A);

    // 32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능) default static
    printHeader("32. 행렬은 다른 행렬과 가로로 합쳐질 수 있다(두 행렬의 행 수가 같아야 가능)",
        "public static Matrix Tensors.concatHorizontally(Matrix a, Matrix b)");
    Matrix mat32B1 = Factory.createMatrix("11", 2, 2);
    Matrix mat32B2 = Factory.createMatrix("11", 2, 2);
    Matrix out32B = Tensors.concatHorizontally(mat32B1, mat32B2);
    printResult(exp32A, out32B);                        // exp32A 재사용

    // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능) non static
    printHeader("33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)",
        "public Matrix concatVertically(Matrix other)");
    Matrix mat33A = Factory.createMatrix("17", 2, 2);
    Matrix exp33A = Factory.createMatrix("17", 4, 2);   // 2×2 → 4×2
    mat33A.concatVertically(mat33A);
    printResult(exp33A, mat33A);

    // 33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)
    printHeader("33. 행렬은 다른 행렬과 세로로 합쳐질 수 있다(두 행렬의 열 수가 같아야 가능)",
        "public static Matrix Tensors.concatVertically(Matrix a, Matrix b)");
    Matrix mat33B1 = Factory.createMatrix("17", 2, 2);
    Matrix mat33B2 = Factory.createMatrix("17", 2, 2);
    Matrix out33B = Tensors.concatVertically(mat33B1, mat33B2);
    printResult(exp33A, out33B);                        // exp33A 재사용

    // 34. 행렬은 특정 행을 벡터 추출해 주 수 있다.
    printHeader("34. 행렬은 특정 행을 벡터 추출해 주 수 있다.",
        "public Vector getRowVector(int row)");
    Matrix mat34r = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("12"), Factory.createScalar("22")),
        Arrays.asList(Factory.createScalar("32"), Factory.createScalar("42"))
    ));
    Vector vec34rExp = Factory.createVector(Arrays.asList(
        Factory.createScalar("32"), Factory.createScalar("42")));
    System.out.println(mat34r);
    printResult(vec34rExp, mat34r.getRowVector(1));

    // 35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다
    printHeader("35. 행렬은 특정 열을 벡터 형태로 추출해 줄 수 있다",
        "public Vector getColVector(int col)");
    Matrix mat35 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("14"), Factory.createScalar("24"),
            Factory.createScalar("34")),
        Arrays.asList(Factory.createScalar("44"), Factory.createScalar("54"),
            Factory.createScalar("64")),
        Arrays.asList(Factory.createScalar("74"), Factory.createScalar("84"),
            Factory.createScalar("94"))
    ));
    Vector vec35ans = Factory.createVector(Arrays.asList(
        Factory.createScalar("14"), Factory.createScalar("44"), Factory.createScalar("74")));
    printResult(vec35ans, mat35.getColVector(0));

    // 36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    printHeader("36. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.",
        "public Matrix subMatrix(int r0,int r1,int c0,int c1)");
    Matrix mat36ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("14"), Factory.createScalar("24")),
        Arrays.asList(Factory.createScalar("44"), Factory.createScalar("54"))
    ));
    printResult(mat36ans, mat35.subMatrix(0, 2, 0, 2));

    // 37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.
    printHeader("37. 행렬은 특정 범위의 부분 행렬을 추출해 줄 수 있다.",
        "public Matrix minor(int row,int col)");
    Matrix mat37ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("14"), Factory.createScalar("34")),
        Arrays.asList(Factory.createScalar("74"), Factory.createScalar("94"))
    ));
    printResult(mat37ans, mat35.minor(1, 1));

    // 38. 행렬은 전치행렬을 (새로 생성하여) 구해 줄 수 있다.
    printHeader("38. 행렬은 전치행렬을 (새로 생성하여) 구해 줄 수 있다.",
        "public Matrix transpose()");
    Matrix mat38ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("14"), Factory.createScalar("44"),
            Factory.createScalar("74")),
        Arrays.asList(Factory.createScalar("24"), Factory.createScalar("54"),
            Factory.createScalar("84")),
        Arrays.asList(Factory.createScalar("34"), Factory.createScalar("64"),
            Factory.createScalar("94"))
    ));
    printResult(mat38ans, mat35.transpose());

    // 39. 행렬은 대각 요소의 합을 구해줄 수 있다.
    printHeader("39. 행렬은 대각 요소의 합을 구해줄 수 있다.",
        "public Scalar trace()");
    System.out.println(mat35);
    printResult(Factory.createScalar("162"), mat35.trace());

    // 40. 정사각 행렬인지 반환
    printHeader("40. 정사각 행렬인지 반환", "public boolean isSquare()");
    System.out.println(mat35);
    printResult(Boolean.TRUE, mat35.isSquare());

    // 41. 상삼각 행렬인지 반환
    printHeader("41. 상삼각 행렬인지 반환", "public boolean isUpperTriangular()");
    Matrix mat41 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("2"),
            Factory.createScalar("3")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("5"),
            Factory.createScalar("6")),
        Arrays.asList(Factory.createScalar("0"), Factory.createScalar("0"),
            Factory.createScalar("9"))
    ));
    System.out.println(mat41);
    printResult(Boolean.TRUE, mat41.isUpperTriangular());

    // 42. 하삼각 행렬인지 반환
    printHeader("42. 하삼각 행렬인지 반환", "public boolean isLowerTriangular()");
    Matrix mat42 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("0"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("13"), Factory.createScalar("51"),
            Factory.createScalar("0")),
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("13"),
            Factory.createScalar("93"))
    ));
    System.out.println(mat42);
    printResult(Boolean.TRUE, mat42.isLowerTriangular());

    // 43. 단위 행렬(Identity)인지 반환
    printHeader("43. 단위 행렬(Identity)인지 반환", "public boolean isIdentity()");
    Matrix id43 = Factory.createMatrix(3);
    System.out.println(id43);
    printResult(Boolean.TRUE, id43.isIdentity());

    // 44. 영행렬(Zero)인지 반환
    printHeader("44. 영행렬(Zero)인지 반환", "public boolean isZero()");
    Matrix zero44 = Factory.createMatrix("0", 7, 7);
    System.out.println(zero44);
    printResult(Boolean.TRUE, zero44.isZero());

    // 45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.
    printHeader("45. 행렬의 두 행 row1과 row2의 위치를 맞교환한다.",
        "public void swapRows(int row1,int row2)");
    Matrix mat45 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("10"), Factory.createScalar("20"),
            Factory.createScalar("30")),
        Arrays.asList(Factory.createScalar("40"), Factory.createScalar("50"),
            Factory.createScalar("60")),
        Arrays.asList(Factory.createScalar("70"), Factory.createScalar("80"),
            Factory.createScalar("90"))
    ));
    Matrix mat45ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("40"), Factory.createScalar("50"),
            Factory.createScalar("60")),
        Arrays.asList(Factory.createScalar("10"), Factory.createScalar("20"),
            Factory.createScalar("30")),
        Arrays.asList(Factory.createScalar("70"), Factory.createScalar("80"),
            Factory.createScalar("90"))
    ));
    mat45.swapRows(0, 1);
    printResult(mat45ans, mat45);

    // 46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.
    printHeader("46. 행렬의 두 열 col1과 col2의 위치를 맞교환한다.",
        "public void swapColumns(int col1,int col2)");
    Matrix mat46 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("10"), Factory.createScalar("20"),
            Factory.createScalar("30")),
        Arrays.asList(Factory.createScalar("40"), Factory.createScalar("50"),
            Factory.createScalar("60")),
        Arrays.asList(Factory.createScalar("70"), Factory.createScalar("80"),
            Factory.createScalar("90"))
    ));
    Matrix mat46ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("20"), Factory.createScalar("10"),
            Factory.createScalar("30")),
        Arrays.asList(Factory.createScalar("50"), Factory.createScalar("40"),
            Factory.createScalar("60")),
        Arrays.asList(Factory.createScalar("80"), Factory.createScalar("70"),
            Factory.createScalar("90"))
    ));
    mat46.swapColumns(0, 1);
    printResult(mat46ans, mat46);

    // 47. 행렬은 특정 행에 상수배(스칼라)를 할 수 있다.
    printHeader("47. 행렬은 특정 행에 상수배(스칼라)를 할 수 있다.",
        "public void scaleRow(int row, Scalar k)");
    Matrix m47 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("21")),
        Arrays.asList(Factory.createScalar("31"), Factory.createScalar("41"))
    ));
    m47.scaleRow(1, Factory.createScalar("10"));
    Matrix expected47 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("21")),
        Arrays.asList(Factory.createScalar("310"), Factory.createScalar("410"))
    ));
    printResult(expected47, m47);

    // 48. 행렬은 특정 열에 상수배(스칼라)를 할 수 있다.
    printHeader("48. 행렬은 특정 열에 상수배(스칼라)를 할 수 있다.",
        "public void scaleColumn(int col, Scalar k)");
    Matrix m48 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("21")),
        Arrays.asList(Factory.createScalar("31"), Factory.createScalar("41"))
    ));
    m48.scaleColumn(0, Factory.createScalar("2"));
    Matrix expected48 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("22"), Factory.createScalar("21")),
        Arrays.asList(Factory.createScalar("62"), Factory.createScalar("41"))
    ));
    printResult(expected48, m48);

    // 49. 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.
    printHeader("49. 행렬은 특정 행에 다른 행의 상수배를 더할 수 있다.",
        "public void addMultipleOfRow(int target,int src,Scalar k)");
    Matrix m49 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("11"), Factory.createScalar("21")),
        Arrays.asList(Factory.createScalar("31"), Factory.createScalar("41"))
    ));
    m49.addMultipleOfRow(0, 1, Factory.createScalar("2"));
    Matrix expected49 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("73"), Factory.createScalar("103")),
        Arrays.asList(Factory.createScalar("31"), Factory.createScalar("41"))
    ));
    printResult(expected49, m49);

    // 50. 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.
    printHeader("50. 행렬은 특정 열에 다른 열의 상수배를 더할 수 있다.",
        "public void addMultipleOfColumn(int target,int src,Scalar k)");
    Matrix m50 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("2"), Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("4"), Factory.createScalar("3"))
    ));
    m50.addMultipleOfColumn(0, 1, Factory.createScalar("3"));
    Matrix expected50 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("5"), Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("13"), Factory.createScalar("3"))
    ));
    printResult(expected50, m50);

    // 51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환해줄 수 있다.
    printHeader("51. 행렬은 자신으로부터 RREF 행렬을 구해서 반환해줄 수 있다.",
        "public Matrix toReducedRowEchelonForm()");
    Matrix test51 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("1"),
            Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2"),
            Factory.createScalar("1")),
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("1"),
            Factory.createScalar("2"))
    ));
    Matrix rref51 = test51.toReducedRowEchelonForm();
    Matrix expect51 = Factory.createMatrix(3);   // 단위 행렬
    printResult(expect51, rref51);

    // 52. 행렬은 자신이 RREF 행렬인지 여부를 판별해줄 수 있다.
    printHeader("52. 행렬은 자신이 RREF 행렬인지 여부를 판별해줄 수 있다.",
        "public boolean isReducedRowEchelonForm()");
    Matrix test52 = Factory.createMatrix("1", 3, 4).toReducedRowEchelonForm();
    System.out.println(test52);
    boolean ok52 = test52.isReducedRowEchelonForm();
    printResult(Boolean.TRUE, ok52);

    // 53. 행렬은 자신의 행렬식을 구해줄 수 있다.
    printHeader("53. 행렬은 자신의 행렬식을 구해줄 수 있다.",
        "public Scalar determinant()");
    Matrix mat53 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("2"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("5"))
    ));
    printResult(Factory.createScalar("4"), mat53.determinant());

    // 54. 행렬은 자신의 역행렬을 구해줄 수 있다.
    printHeader("54. 행렬은 자신의 역행렬을 구해줄 수 있다.",
        "public Matrix inverse()");
    Matrix mat54 = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("1"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("5"))
    ));
    Matrix mat54ans = Factory.createMatrix(Arrays.asList(
        Arrays.asList(Factory.createScalar("-5"), Factory.createScalar("2")),
        Arrays.asList(Factory.createScalar("3"), Factory.createScalar("-1"))
    ));
    printResult(mat54ans, mat54.inverse());

  }
}