package tensor;

interface Matrix {
    Scalar get(int index); // 13. 요소 조회
    void set(int index, Scalar value); // 13. 요소 지정
    int getRowSize();
    int getColSize();
}