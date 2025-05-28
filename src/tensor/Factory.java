package tensor;

import java.util.List;

/*TODO
 * 행렬, 벡터, 스칼라를 생성한다
 * 모든 메서드가 public static
 * 생성 자체는 구현 클래스 내부에서 담당
 * Factory의 static 메서드는 구현 클래스에 객체 생성을 요청 -> 획득한 객체를 반환만
 */

public class Factory {

  public static Scalar createScalar(String value) {
      return new ScalarImpl(value);
  }

  public static Vector createVector(List<Scalar> values) {
      return new VectorImpl(values);
  }

  public static Matrix createMatrix(List<List<Scalar>> rows) {
      return new MatrixImpl(rows);
  }
  
}