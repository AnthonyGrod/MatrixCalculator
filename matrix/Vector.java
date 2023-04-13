package pl.edu.mimuw.matrix;

public class Vector extends Matrix {
    private final double[] values;

    public Vector(double[] values) {
        super(Shape.matrix(values.length, 1));
        this.values = Matrix.copyOneDiensionalArray(values);
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        return values[row];
    }

    @Override
    public double normOne() {
        // mamy jedną kolumne, więc wynikiem będize suma modułów pól wektora
        double result = 0;
        for (int i = 0; i < this.shape.rows; i++) {
            result += Math.abs(this.values[i]);
        }
        return result;
    }

    @Override
    public double normInfinity() {
        // wynikiem metody normInfinity() będzie maksymalna co do modułu komórka wektora
        //assert values != null;
        double result = this.values[0];
        for (int i = 1; i < this.shape.rows; i++) {
            if (Math.abs(values[i]) > result) {
                result = Math.abs(values[i]);
            }
        }
        return result;
    }

    @Override
    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < values.length; i++) {
            result += Math.pow(values[i], 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (double value: values) {
            result.append(value).append('\n');
        }
        return result.toString();
    }
}
