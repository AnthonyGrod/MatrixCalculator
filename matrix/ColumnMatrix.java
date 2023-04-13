package pl.edu.mimuw.matrix;

public class ColumnMatrix extends Matrix {
    private final double[] columnValues;
    public ColumnMatrix(double[] columnValues, Shape rowShape) {
        super(rowShape);
        this.columnValues = columnValues;
    }

    @Override
    public double get(int row, int column) {
        return this.columnValues[column];
    }

    @Override
    public double normInfinity() {
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
    public double normOne() {
        double result = 0;
        for (int i = 0; i < columnValues.length; i++) {
            result += Math.abs(columnValues[i]);
        }
        return result;
    }

    @Override
    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < this.columnValues.length; i++) {
            result += Math.pow(this.columnValues[i], 2);
        }
        return Math.sqrt(result * this.shape.columns);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < shape.rows; i++) {
            if (shape.columns >= 3) {
                result.append(columnValues[i]).append("...").append(columnValues[i]);
            }
            else if (shape.columns == 2) {
                result.append(columnValues[i]).append(" ").append(columnValues[i]);
            }
            else if (shape.columns == 1) {
                result.append(columnValues[i]);
            }
            result.append('\n');
        }
        return result.toString();
    }
}
