package tensor;

interface Matrix {
    Scalar get(int index);
    void set(int index, Scalar value);
    int rowSize();
    int colSize();
}