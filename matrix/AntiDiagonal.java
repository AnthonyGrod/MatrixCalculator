package pl.edu.mimuw.matrix;

public class AntiDiagonal extends Matrix {
    private final double[] antiDiagonalValues;

    public AntiDiagonal(double[] antiDiagonalValues) {
        super(antiDiagonalValues.length);
        this.antiDiagonalValues = Matrix.copyOneDiensionalArray(antiDiagonalValues);
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        // sprawdzamy, czy sprawdzana komórka leży na antydiagonali
        if (column == this.antiDiagonalValues.length - row + 1) {
            // jeśli tak to zwracamy wartość z podanej tablicy dwuwymiarowej
            return antiDiagonalValues[row];
        }
        // w przeciwnym wypadku komórka jest poza antydiagonalą, więc zwracamy 0
        return 0;
    }

    @Override
    public double normOne() {
        /* chcemy numer wiersza, w którym znajduje się największy (co do modułu) element.
         * Jest tak, bo w danym wierszu jest co najwyżej jedna niezerowa wartość */
        double maxElement = Math.abs(antiDiagonalValues[0]);
        for (int i = 1; i < antiDiagonalValues.length; i++) {
            if (Math.abs(antiDiagonalValues[i]) > maxElement) {
                maxElement = Math.abs(antiDiagonalValues[i]);
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
        for (int i = 0; i < antiDiagonalValues.length; i++) {
            result += Math.pow(antiDiagonalValues[i], 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < this.shape.rows; i++) {
            if (this.shape.rows - i - 1 >= 3) {
                result.append("0...0 ");
            }
            else if (this.shape.rows - i - 1 == 2) {
                result.append("0 0 ");
            }
            else if (this.shape.rows - i - 1 == 1) {
                result.append("0 ");
            }
            result.append(antiDiagonalValues[i]);
            if (i >= 3) {
                result.append(" 0...0");
            }
            else if (i == 2) {
                result.append(" 0 0");
            }
            else if (i == 1) {
                result.append(" 0");
            }
            result.append('\n');
        }

        return result.toString();
    }

}
