package pl.edu.mimuw.matrix;

public class Constant extends Matrix {
    private final double value;
    private final Shape shape;

    public Constant(double value, Shape shape) {
        super(shape);
        this.value = value;
        this.shape = shape;
    }

    @Override
    public double get(int row, int column) {
        Shape.assertGet(this.shape, row, column);
        return this.value;
    }

    @Override
    public double normOne() {
        return Math.abs(this.value * this.shape.rows);
    }

    @Override
    public double normInfinity() {
        return Math.abs(this.value * this.shape.columns);
    }

    @Override
    public double frobeniusNorm() {
        return Math.sqrt((this.shape.rows * this.shape.columns * Math.pow(this.value, 2)));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < shape.rows; i++) {
            if (shape.columns >= 3) {
                result.append(value).append("...").append(value);
            }
            else if (shape.columns == 2) {
                result.append(value).append(" ").append(value);
            }
            else if (shape.columns == 1) {
                result.append(value);
            }
            result.append('\n');
        }
        return result.toString();
    }
}
