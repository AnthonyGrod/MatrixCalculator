package pl.edu.mimuw.matrix;

public abstract class Matrix implements IDoubleMatrix {
    protected final Shape shape;
    public Matrix(Shape shape) {
        this.shape = Shape.matrix(shape.rows, shape.columns);
    }

    protected Matrix(int length) {
        this.shape = Shape.matrix(length, length);
    }

    public Shape shape() {
        return shape;
    }

    public double[][] data() {
        double[][] matrixArray = new double[this.shape.rows][this.shape.columns];
        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                matrixArray[i][j] = this.get(i, j);
            }
        }
        return matrixArray;
    }


    public IDoubleMatrix times(IDoubleMatrix other) {
        assert other != null && this != null;
        // bazując na wymiarach podanych macierzy, sprawdzamy, czy mnożenie jest możliwe
        Shape.isMultiplyingPossible(this.shape, other.shape());

        double[][] resultArray = new double[this.shape.rows][other.shape().columns];

        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                resultArray[i][j] = 0;
                for (int k = 0; k < other.shape().rows; k++) {
                    //result.set(i, k, result.get(i,k) + get(i,j) * other.get(j,k));
                    resultArray[i][j] += this.get(i, k) * other.get(k, j);
                }
            }
        }
        IDoubleMatrix resultMatrix = new Full(resultArray);
        return resultMatrix;
    }

    public IDoubleMatrix times(double scalar) {
        assert this != null;
        double[][] resultArray = new double[this.shape.rows][this.shape.columns];
        for (int i = 0; i < resultArray.length; i++) {
            for (int j = 0; j < resultArray[0].length; j++) {
                resultArray[i][j] = this.get(i, j) * scalar;
            }
        }
        // zwracamy macierz typu Full
        IDoubleMatrix resultMatrix = new Full(resultArray);
        return resultMatrix;
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert other != null && this != null;
        // upewniamy się, czy możemy wykonać operację dodawania
        Shape.isAddingAndSubstractingPossible(this.shape, other.shape());
        // wykonujemy operację dodawania macierzy
        double[][] resultArray = new double[this.shape.rows][this.shape.columns];
        for (int i = 0; i < resultArray.length; i++) {
            for (int j = 0; j < resultArray[0].length; j++) {
                resultArray[i][j] = this.get(i, j) + other.get(i, j);
            }
        }
        // zwracamy macierz typu Full
        IDoubleMatrix resultMatrix = new Full(resultArray);
        return resultMatrix;
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        // upewniamy się, czy możemy wykonać operację dodawania
        Shape.isAddingAndSubstractingPossible(this.shape, other.shape());

        // tworzymy macierz pomocniczą, która będzie przetrzymywać macierz other pomnożoną przez -1
        IDoubleMatrix tempMatrix = other.times(-1);
        // następnie dodajemy tę pomnożoną przez -1 macierz, aby wynikowym działaniem było odejmowanie
        return this.plus(tempMatrix);
    }

    public IDoubleMatrix plus(double scalar) {
        assert this != null;
        double[][] resultArray = new double[this.shape.rows][this.shape.columns];
        // do każdej komórki dodajemy scalar
        for (int i = 0; i < resultArray.length; i++) {
            for (int j = 0; j < resultArray[0].length; j++) {
                resultArray[i][j] = this.get(i, j) + scalar;
            }
        }
        // zwracamy macierz typu Full
        IDoubleMatrix resultMatrix = new Full(resultArray);
        return resultMatrix;
    }

    public IDoubleMatrix minus(double scalar) {
        // tu wystarczy dodać scalar pomnożony przez -1
        return this.plus(scalar * -1);
    }

    public double normOne() {
        // chcemy zwrócić maksymalną wartość sumy modułów poszczególnych kolumn macierzy
        double max = 0;
        double tempMax = 0;
        for (int i = 0; i < this.shape.columns; i++) {
            tempMax = 0;
            for (int j = 0; j < this.shape.rows; j++) {
                tempMax += Math.abs(this.get(j, i));
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }

    public double normInfinity() {
        // chcemy zwrócić maksymalną wartość sumy modułów poszczególnych wierszy macierzy
        double max = 0;
        double tempMax = 0;
        for (int i = 0; i < this.shape.rows; i++) {
            tempMax = 0;
            for (int j = 0; j < this.shape.columns; j++) {
                tempMax += Math.abs(this.get(i, j));
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }

    public double frobeniusNorm() {
        double norm = 0;
        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                norm += Math.pow(this.get(i, j), 2);
            }
        }
        return Math.sqrt(norm);
    }

    public static double[][] copyTwoDiensionalArray(double[][] array) {
        // funkcja kopiuje tablice dwuwymiarową
        double[][] copiedArray = new double[array.length][array[0].length];
        for (int i = 0; i < copiedArray.length; i++) {
            for (int j = 0; j < copiedArray[0].length; j++) {
                copiedArray[i][j] = array[i][j];
            }
        }
        return copiedArray;
    }

    public static double[] copyOneDiensionalArray(double[] array) {
        // funkcja kopiuje tablice jednowymiarową
        double[] copiedArray = new double[array.length];
        for (int i = 0; i < copiedArray.length; i++) {
            copiedArray[i] = array[i];
        }
        return copiedArray;
    }
}
