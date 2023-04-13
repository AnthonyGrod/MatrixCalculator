package pl.edu.mimuw.matrix;

public class Diagonal extends Matrix {
    private final double diagonalValues[];

    public Diagonal(double diagonalValues[]) {
        super(diagonalValues.length);
        this.diagonalValues = Matrix.copyOneDiensionalArray(diagonalValues);
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        // na diagonali są same jedynki
        if (row == column) {
            return this.diagonalValues[row];
        }
        // dla innych pól macierzy jednostkowej zwracamy 0
        return 0;
    }

    @Override
    public double normOne() {
        /* chcemy numer wiersza, w którym znajduje się największy (co do modułu) element.
         * Jest tak, bo w danym wierszu jest co najwyżej jedna niezerowa wartość */
        double maxElement = Math.abs(diagonalValues[0]);
        for (int i = 1; i < diagonalValues.length; i++) {
            if (Math.abs(diagonalValues[i]) > maxElement) {
                maxElement = Math.abs(diagonalValues[i]);
            }
        }
        return maxElement;
    }

    @Override
    public double normInfinity() {
        // wynikiem metody normInfinity() będzie wynik metody normOne()
        return this.normOne();
    }

    @Override
    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < diagonalValues.length; i++) {
            result += Math.pow(diagonalValues[i], 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < this.diagonalValues.length; i++) {
            if (i >= 3) {
                result.append("0...0 ");
            }
            else if (i == 2) {
                result.append("0 0 ");
            }
            else if (i == 1) {
                result.append("0 ");
            }
            result.append(diagonalValues[i]);
            if (diagonalValues.length - i - 1 >= 3) {
                result.append(" 0...0");
            }
            else if (diagonalValues.length - i - 1 == 2) {
                result.append(" 0 0");
            }
            else if (diagonalValues.length - i - 1 == 1) {
                result.append(" 0");
            }
            result.append('\n');
        }
        return result.toString();
    }
}
