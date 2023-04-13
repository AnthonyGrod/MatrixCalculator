package pl.edu.mimuw.matrix;

import java.util.Objects;

public final class Shape {
  public final int rows;
  public final int columns;

  private Shape(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
  }

  void assertInShape(int row, int column) {
    assert row >= 0;
    assert row < rows;
    assert column >= 0;
    assert column < columns;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Shape shape = (Shape) o;
    return rows == shape.rows && columns == shape.columns;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rows, columns);
  }

  public static Shape vector(int size) {
    return Shape.matrix(size, 1);
  }

  public static Shape matrix(int rows, int columns) {
    assert columns > 0;
    assert rows > 0;
    return new Shape(rows, columns);
  }

  public static void isMultiplyingPossible(Shape shape1, Shape shape2) {
    assert shape1.columns == shape2.rows;
  }

  public static void isAddingAndSubstractingPossible(Shape shape1, Shape shape2) {
    assert (shape1.rows == shape2.rows);
    assert (shape1.columns == shape2.columns);
  }

  public static void assertGet(Shape shape, int givenRow, int givenColumn) {
    assert (givenRow >= 0 && givenRow < shape.rows) && (givenColumn >= 0 && givenColumn < shape.columns);
  }

  public static void isValidMatrix(Shape shape) {
    assert shape != null;
    assert shape.rows > 0 && shape.columns > 0;
  }

  public static void areRowsTheSameLength(double[][] values) {
    assert values != null;
    assert values.length > 0;
    assert values[0] != null;
    assert values[0].length > 0;
    int rowLength = values[0].length;
    for (int i = 1; i < values.length; i++) {
      assert values[i] != null;
      assert rowLength == values[i].length;
    }
  }
}
