package pl.edu.mimuw.matrix;

public class RowMatrix extends Matrix {
    private final double[] rowValues;
    public RowMatrix(double[] rowValues, Shape rowShape) {
        super(rowShape);
        this.rowValues = rowValues;
    }

    @Override
    public double get(int row, int column) {
        return this.rowValues[column];
    }

    @Override
    public double normOne() {
        // największa kolumna co do sumy modułów pól w tej kolumnie
        double max = 0;
        double tempMax = 0;
        for (int i = 0; i < this.shape.columns; i++) {
            tempMax = 0;
            for (int j = 0; j < this.shape.rows; j++) {
                tempMax += Math.abs(this.get(i, j));
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }

    @Override
    public double normInfinity() {
        double result = 0;
        for (int i = 0; i < rowValues.length; i++) {
            result += Math.abs(rowValues[i]);
        }
        return result;
    }

    @Override
    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < this.rowValues.length; i++) {
            result += Math.pow(this.rowValues[i], 2);
        }
        return Math.sqrt(result * this.shape.columns);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                result.append(rowValues[j]).append(" ");
            }
            result.append(('n'));
        }
        return result.toString();
    }
}
