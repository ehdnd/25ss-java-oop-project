package tensor;

public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException() {
    super("경로가 잘못되었거나 csv 파일을 찾을 수 없습니다.");
  }
}
