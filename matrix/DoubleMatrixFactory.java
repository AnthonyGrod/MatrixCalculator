package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }
  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values) {
    Shape.isValidMatrix(shape);
    if (values == null) {
      return new Zero(shape);
    }
    return new Sparse(shape, values); // Tu trzeba wpisać właściwą instrukcję
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null;
    Shape.areRowsTheSameLength(values);
    return new Full(values);
  }

  public static IDoubleMatrix identity(int size) {
    assert size > 0;
    Identity result = new Identity(size);
    return result;
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
    assert diagonalValues != null;
    // return new Diagonal(shape, values);
    Diagonal result = new Diagonal(diagonalValues);
    return result;
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    assert antiDiagonalValues != null;
    return new AntiDiagonal(antiDiagonalValues); // Tu trzeba wpisać właściwą instrukcję
  }

  public static IDoubleMatrix vector(double... values) {
    assert values != null;
    return new Vector(values); // Tu trzeba wpisać właściwą instrukcję
  }

  public static IDoubleMatrix zero(Shape shape) {
    assert shape != null;
    return new Zero(shape);
  }
}
