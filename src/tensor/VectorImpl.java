package tensor;

/*
# 벡터의 자료구조
- 벡터는 논리적으로 스칼라 객체는 1차원 배열 구조로 관리
- Collection으로 관리

# 벡터의 생성 (default 접근 지정자 사용)
-
 */

import java.util.ArrayList;
import java.util.List;

class VectorImpl implements Vector {

  // 자료구조 - 벡터는 논리적으로 스칼라 객체를 1차원 배열 구조로 관리한다
  private List<Scalar> vectorValue;

  // =============================================================================
  // 생성자 (Constructors)
  // =============================================================================

  // 03. 지정한 하나의 값을 모든 요소의 값으로 하는 n-차원 벡터 생성
  VectorImpl(String bigDecimalString, int dimension) {
    validateStringValue(bigDecimalString);
    validateDimension(dimension);

    vectorValue = new ArrayList<>(dimension);
    for (int dim = 0; dim < dimension; ++dim) {
      vectorValue.add(new ScalarImpl(bigDecimalString));
    }
  }

  // 04. i 이상 j 미만의 무작위 값을 요소로 하는 n-차원 벡터 생성
  VectorImpl(String i, String j, int dimension) {
    validateStringValue(i);
    validateStringValue(j);
    validateDimension(dimension);

    vectorValue = new ArrayList<>(dimension);
    for (int dim = 0; dim < dimension; ++dim) {
      vectorValue.add(new ScalarImpl(i, j));
    }
  }

  // 05. 1차원 배열로부터 n-차원 벡터 생성
  VectorImpl(List<Scalar> dimOneList) {
    validateScalarList(dimOneList);

    vectorValue = new ArrayList<>(dimOneList.size());
    for (int dim = 0; dim < dimOneList.size(); ++dim) {
      vectorValue.add(dimOneList.get(dim).clone());
    }
  }

  // =============================================================================
  // 기본 접근자 메소드 (Basic Accessor Methods)
  // =============================================================================

  // 11v. 특정 위치의 요소를 지정/조회할 수 있다.
  // 11v. 조회: 지정한 인덱스 위치의 Scalar 값을 반환
  @Override
  public Scalar get(int index) {
    validateIndex(index);
    return vectorValue.get(index);
  }

  // 11.지정: 지정한 인덱스 위치에 Scalar 값을 설정
  @Override
  public void set(int index, Scalar value) {
    validateIndex(index);
    validateScalarArgument(value);
    vectorValue.set(index, value.clone());
  }

  // 13v. 차원의 개수를 조회할 수 있다.
  @Override
  public int size() {
    return vectorValue.size();
  }

  // 벡터의 내부 리스트에 직접 접근하는 메소드 (default)
  List<Scalar> getVectorValue() {
    return vectorValue;
  }

  // =============================================================================
  // 객체 기본 메소드 (Object Basic Methods)
  // =============================================================================

  // 14v. 값들을 1차원 배열 모양으로 출력할 수 있다.
  @Override
  public String toString() {
    if (vectorValue.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();

    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      sb.append(vectorValue.get(idx).toString());
      sb.append(", ");
    }
    sb.delete(sb.length() - 2, sb.length());

    return sb.toString();
  }

  // 15. 객체의 동등성을 파악할 수 있다.
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Vector)) {
      return false;
    }

    Vector other = (Vector) obj;

    if (size() != other.size()) {
      return false;
    }

    for (int idx = 0; idx < size(); ++idx) {
      if (!get(idx).equals(other.get(idx))) {
        return false;
      }
    }
    return true;
  }

  // 17. 객체 복제를 할 수 있다.
  @Override
  public Vector clone() {
    List<Scalar> result = new ArrayList<>(vectorValue.size());

    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      result.add(vectorValue.get(idx).clone()); // 스칼라까지 재귀적으로 복제
    }
    return new VectorImpl(result); // Collection<Scalar> 생성자 호출
  }

  /**
   * 해시코드를 생성한다.
   */
  @Override
  public int hashCode() {
    int result = 1;
    for (Scalar scalar : vectorValue) {
      result = 31 * result + scalar.hashCode();
    }
    return result;
  }

  // =============================================================================
  // 인스턴스 산술 연산 메소드 (Instance Arithmetic Operations)
  // =============================================================================

  // 20. 길이가 같을 때 다른 벡터와 덧셈 (자신을 수정한 뒤 자신 반환)
  @Override
  public Vector add(Vector other) {
    validateVectorArgument(other);
    validateDimensionMatch(this.size(), other.size());

    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      // Scalar의 non‑static add 는 자신의 값을 바꾼 뒤 this를 리턴
      vectorValue.get(idx).add(other.get(idx));
    }
    return this;
  }

  // 21. 벡터를 다른 스칼라와 곱셈이 가능하다 (모든 요소에 스칼라 곱한 뒤 자신 반환)
  @Override
  public Vector multiply(Scalar scalar) {
    validateScalarArgument(scalar);

    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      vectorValue.get(idx).multiply(scalar); // in‑place 곱셈
    }
    return this;
  }

  // =============================================================================
  // 정적 산술 연산 메소드 (Static Arithmetic Operations)
  // =============================================================================

  // 26. 전달받은 두 벡터의 덧셈이 가능하다.(길이가 같을때) (out-place)
  static Vector add(Vector v1, Vector v2) {
    validateVectorArgument(v1);
    validateVectorArgument(v2);
    validateDimensionMatch(v1.size(), v2.size());

    Vector result = Factory.createVector("0", v1.size());
    for (int idx = 0; idx < v1.size(); ++idx) {
      Scalar sum = v1.get(idx).clone().add(v2.get(idx));
      result.set(idx, sum);
    }
    return result;
  }

  // 27. 전달받은 스칼라와 벡터의 곱셈이 가능하다(벡터의 모든 요소에 스칼라를 곱한다.)
  static Vector multiply(Scalar s, Vector v) {
    validateScalarArgument(s);
    validateVectorArgument(v);

    Vector result = Factory.createVector("0", v.size());
    for (int idx = 0; idx < v.size(); ++idx) {
      Scalar product = s.clone().multiply(v.get(idx));
      result.set(idx, product);
    }
    return result;
  }

  // =============================================================================
  // 변환 메소드 (Transformation Methods)
  // =============================================================================

  // 30. n-차원 벡터 객체는 자신으로부터 nx1 행렬을 생성하여 반환할 수 있다.
  @Override
  public Matrix toColumnMatrix() {
    List<List<Scalar>> rows = new ArrayList<>(vectorValue.size());
    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      rows.add(List.of(vectorValue.get(idx).clone())); // 각 스칼라를 행으로 만듦 (깊은 복사)
    }
    return new MatrixImpl(rows);
  }

  // 31. n-차원 벡터 객체는 자신으로부터 1xn 행렬을 생성하여 반환할 수 있다.
  @Override
  public Matrix toRowMatrix() {
    List<Scalar> rowCopy = new ArrayList<>(vectorValue.size());
    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      rowCopy.add(vectorValue.get(idx).clone()); // 깊은 복사
    }
    List<List<Scalar>> row = List.of(rowCopy); // 한 행에 모든 스칼라 포함
    return new MatrixImpl(row);
  }

  // =============================================================================
  // 유틸리티 메소드 (Utility Methods)
  // =============================================================================

  // 벡터가 영벡터인지 확인 (VectorImpl 전용 유틸리티)
  public boolean isZeroVector() {
    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      if (!vectorValue.get(idx).isZero()) {
        return false;
      }
    }
    return true;
  }

  // 벡터가 비어있는지 확인 (VectorImpl 전용 유틸리티)
  public boolean isEmpty() {
    return vectorValue.isEmpty();
  }

  // 벡터의 모든 요소가 양수인지 확인 (VectorImpl 전용 유틸리티)
  public boolean isAllPositive() {
    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      Scalar scalar = vectorValue.get(idx);
      if (!(scalar instanceof ScalarImpl) || !((ScalarImpl) scalar).isPositive()) {
        return false;
      }
    }
    return true;
  }

  // 벡터의 모든 요소가 음수인지 확인 (VectorImpl 전용 유틸리티)
  public boolean isAllNegative() {
    for (int idx = 0; idx < vectorValue.size(); ++idx) {
      Scalar scalar = vectorValue.get(idx);
      if (!(scalar instanceof ScalarImpl) || !((ScalarImpl) scalar).isNegative()) {
        return false;
      }
    }
    return true;
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
      throw new NullPointerException("Vector value cannot be null");
    }
    if (value.trim().isEmpty()) {
      throw new IllegalArgumentException("Vector value cannot be empty");
    }
  }

  /**
   * 차원 크기의 유효성을 검증한다.
   * 
   * @param dimension 검증할 차원 크기
   * @throws IllegalArgumentException 차원이 음수인 경우
   */
  private static void validateDimension(int dimension) {
    if (dimension < 0) {
      throw new IllegalArgumentException("Vector dimension cannot be negative: " + dimension);
    }
  }

  /**
   * 스칼라 리스트의 유효성을 검증한다.
   * 
   * @param scalars 검증할 스칼라 리스트
   * @throws NullPointerException     리스트가 null인 경우
   * @throws IllegalArgumentException 리스트에 null 요소가 있는 경우
   */
  private static void validateScalarList(List<Scalar> scalars) {
    if (scalars == null) {
      throw new NullPointerException("Scalar list cannot be null");
    }
    for (int i = 0; i < scalars.size(); i++) {
      if (scalars.get(i) == null) {
        throw new IllegalArgumentException("Scalar at index " + i + " cannot be null");
      }
    }
  }

  /**
   * 인덱스의 유효성을 검증한다.
   * 
   * @param index 검증할 인덱스
   * @throws IndexOutOfBoundsException 인덱스가 범위를 벗어난 경우
   */
  private void validateIndex(int index) {
    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException(
          "Index " + index + " is out of bounds for vector size " + size());
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

  /**
   * 벡터 인수의 유효성을 검증한다.
   * 
   * @param vector 검증할 벡터 객체
   * @throws NullPointerException 벡터가 null인 경우
   */
  private static void validateVectorArgument(Vector vector) {
    if (vector == null) {
      throw new NullPointerException("Vector argument cannot be null");
    }
  }

  /**
   * 두 벡터의 차원이 일치하는지 검증한다.
   * 
   * @param size1 첫 번째 벡터의 크기
   * @param size2 두 번째 벡터의 크기
   * @throws DimensionMismatchException 차원이 일치하지 않는 경우
   */
  private static void validateDimensionMatch(int size1, int size2) {
    if (size1 != size2) {
      throw new DimensionMismatchException(size1, size2);
    }
  }
}