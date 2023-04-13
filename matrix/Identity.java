package pl.edu.mimuw.matrix;

public class Identity extends Matrix {
    public Identity(int size) {
        super(Shape.matrix(size, size));
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        other.shape().assertInShape(other.shape().rows, other.shape().columns);
        // wiemy, że mnożąc dowolną macierz (która ma akceptowalne wymiary przez id, dostaniemy tę samą macierz)
        return other;
    }

    @Override
    // Optymalizacja - mnożąc (macierz id) x (macierz diag) otrzymujemy tę samą macierz diag
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new Zero(shape);
        }
        // otrzymamy po prostu macierz, która na diagonali będzie mieć jednakową wartość
        double diagonalValues[];
        diagonalValues = new double[this.shape.rows];
        for (int i = 0; i < this.shape.rows; i++) {
            diagonalValues[i] = scalar;
        }
        Diagonal resultMatrix = new Diagonal(diagonalValues);
        return resultMatrix;
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        // na diagonali są same jedynki
        if (row == column) {
            return 1;
        }
        // dla innych pól macierzy jednostkowej zwracamy 0
        return 0;
    }

    @Override
    public double normOne() {
        // maksymalną sumą modułów wartości w danej kolumnie jest 1
        return 1;
    }

    @Override
    public double normInfinity() {
        // analogicznie jak normOne()
        return 1;
    }

    @Override
    public double frobeniusNorm() {
        return 1 * this.shape.rows;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Liczba rzędów: ").append(this.shape.rows).append(", Liczba wierszy: ");
        result.append(this.shape.columns).append("\n");
        for (int i = 0; i < this.shape.columns; i++) {
            if (i >= 3) {
                result.append("0...0 ");
            }
            else if (i == 2) {
                result.append("0 0 ");
            }
            else if (i == 1) {
                result.append("0 ");
            }
            result.append(1);
            if (shape.columns - i - 1 >= 3) {
                result.append(" 0...0");
            }
            else if (shape.columns - i - 1 == 2) {
                result.append(" 0 0");
            }
            else if (shape.columns - i - 1 == 1) {
                result.append(" 0");
            }
            result.append('\n');
        }
        return result.toString();
    }
}
