package pl.edu.mimuw.matrix;

public class Full extends Matrix {
    private final double[][] values;
    // upewnic sie, ze wszystkie wymiary sa rowne
    public Full(double[][] values) {
        super(Shape.matrix(values.length, values[0].length));
        this.values = Matrix.copyTwoDiensionalArray(values);
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        return values[row][column];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                result.append(this.values[i][j]).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
