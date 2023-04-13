package pl.edu.mimuw.matrix;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Comparator;

public class Sparse extends Matrix {
    private final MatrixCellValue[] values;
    private final ArrayList<ArrayList<MatrixCellValue>> sparseValues;

    public Sparse(Shape sparseShape, MatrixCellValue[] values) {
        super(sparseShape);
        this.values = values;
        assert sparseShape != null;
        // tworzymy listę list reprezentującą listę wierszy macierzy
        sparseValues = new ArrayList<ArrayList<MatrixCellValue>>();
        // kopiujemy zawartość values do tablicy pomocniczej i przy okazji sprawdzamy, czy indeksy komórek są poprawne
        MatrixCellValue[] valuesCopy = new MatrixCellValue[values.length];
        for (int i = 0; i < values.length; i++) {
            sparseShape.assertInShape(values[i].row, values[i].column);
            valuesCopy[i] = values[i];
        }
        // sortujemy te tablicę pomocniczą
        Arrays.sort(valuesCopy, new CompareMatrixCell());
        // teraz możemy przystąpić do dodawania komórek z tej posortowanej tablicy do listy list sparseValues
        assert valuesCopy != null && valuesCopy[0] != null;
        int currentRow = valuesCopy[0].row;
        this.sparseValues.add(new ArrayList<MatrixCellValue>());
        this.sparseValues.get(0).add(valuesCopy[0]);
        int listIndex = 0;
        for (int i = 1; i < valuesCopy.length; i++) {
            if (valuesCopy[i].row != currentRow) {
                currentRow = valuesCopy[i].row;
                listIndex++;
                this.sparseValues.add(new ArrayList<MatrixCellValue>());
                this.sparseValues.get(listIndex).add(valuesCopy[i]);
            } else {
                this.sparseValues.get(listIndex).add(valuesCopy[i]);
            }
        }
    }

    @Override
    public double get(int row, int column) {
        // sprawdzamy, czy podane współrzędne komórki są prawidłowe
        Shape.assertGet(this.shape, row, column);
        // iterujemy się po danym wierszu macierzy i sprawdzamy, czy występuje w nim niezerowy element w kolumnie column
        for (int i = 0; i < this.sparseValues.size(); i++) {
            if (this.sparseValues.get(i).get(0).row == row) {
                for (int j = 0; j < this.sparseValues.get(i).size(); j++) {
                    if (this.sparseValues.get(i).get(j).column == column) {
                        return this.sparseValues.get(i).get(j).value;
                    }
                }
                /* jeśli wyszliśmy z wewnętrznego fora to znaczy, że nie było komórki o szukanych przez
                 * nas współrzędnych, więc jej wartością jest 0
                 */
                return 0;
            }
        }
        return 0;
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        if (scalar == 0) {
            return new Zero(this.shape);
        }
        MatrixCellValue[] resultArray = new MatrixCellValue[values.length];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = MatrixCellValue.cell(values[i].row, values[i].column, values[i].value * scalar);
        }
        Shape newShape = Shape.matrix(this.shape.rows, this.shape.columns);
        return new Sparse(newShape, resultArray);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        // sprawdzamy, czy możemy wykonać dodawanie
        Shape.isAddingAndSubstractingPossible(this.shape, other.shape());
        // implementacja optymalizacji na mnożenie dwóch macierzy typu Sparse
        if (other.getClass() == Sparse.class) {
            /* teraz this.sparseValues to lista wierszy pierwszej macierzy, a listOfColumns to lista kolumn drugiej.
             * Tworzymy pomocniczą listę niezerowych komórek wynikowej macierzy */
            ArrayList<MatrixCellValue> resultMatrixCellsList = new ArrayList<MatrixCellValue>();
            double currentCell = 0;
            /* zmienna ktora nam bedize mowic, w jakiej kolumnie znalezlismy dwie niezerowe komorki na tych samych
             * wspolrzednych w obu macierzach */
            int columnFound = -1;
            // tworzymy pomocniczą listę niezerowych komórek wynikowej macierzy
            for (int i = 0; i < this.sparseValues.size(); i++) {
                for (int j = 0; j < ((Sparse) other).sparseValues.size(); j++) {
                    //urrentCell = 0;
                    //columnFound = -1;
                    // sprawdzamy czy jesteśmy w tym samym rzędzie
                    if (this.sparseValues.get(i).get(0).row == ((Sparse) other).sparseValues.get(j).get(0).row) {
                        /* tutaj mamy zagwarantowane, że jesteśmy w tym samym rzędzie. Teraz znajdujemy (jeśli są)
                         * dwie komórki o takiej samej kolumnie */
                        int iterator1 = 0;
                        int iterator2 = 0;
                        while (iterator1 < this.sparseValues.get(i).size() &&
                                iterator2 < ((Sparse) other).sparseValues.get(j).size()) {
                            // iterujemy sie naraz po obu wierszach
                            if (this.sparseValues.get(i).get(iterator1).column ==
                                    ((Sparse) other).sparseValues.get(j).get(iterator2).column) {
                                // tu natrafilismy na komorki o takich samych wspolrzednych
                                if (this.sparseValues.get(i).get(iterator1).value +
                                        ((Sparse) other).sparseValues.get(j).get(iterator2).value != 0) {
                                    // sprawdzilismy czy wartosci sie nie zerują
                                    resultMatrixCellsList.add(MatrixCellValue.cell(
                                            this.sparseValues.get(i).get(iterator1).row,
                                            this.sparseValues.get(i).get(iterator1).column,
                                            this.sparseValues.get(i).get(iterator1).value +
                                                    ((Sparse) other).sparseValues.get(j).get(iterator2).value));
                                }
                                iterator1++;
                                iterator2++;
                            } else {
                                // jesli komorki nie maja takich samych wspolrzednych to je dodajemy osobno
                                resultMatrixCellsList.add(MatrixCellValue.cell(
                                        this.sparseValues.get(i).get(iterator1).row,
                                        this.sparseValues.get(i).get(iterator1).column,
                                        this.sparseValues.get(i).get(iterator1).value));
                                iterator1++;
                                resultMatrixCellsList.add(MatrixCellValue.cell(
                                        ((Sparse) other).sparseValues.get(i).get(iterator1).row,
                                        ((Sparse) other).sparseValues.get(i).get(iterator1).column,
                                        ((Sparse) other).sparseValues.get(i).get(iterator1).value));
                                iterator2++;
                            }
                        }
                    }
                }
            }
            // używamy pomocniczą listę komórek, aby skopiować jej zawartość do tablicy
            MatrixCellValue[] resultMatrixCellsArray = new MatrixCellValue[resultMatrixCellsList.size()];
            for (int i = 0; i < resultMatrixCellsList.size(); i++) {
                resultMatrixCellsArray[i] = resultMatrixCellsList.get(i);
            }
            return new Sparse(this.shape, resultMatrixCellsArray);
        }
        else {
            return super.plus(other);
        }

    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        // implementacja optymalizacji na mnożenie dwóch macierzy typu Sparse
        if (other.getClass() == Sparse.class) {
            ArrayList<ArrayList<MatrixCellValue>> listOfColumns = ((Sparse) other).createListOfColumns();
            // bazując na wymiarach podanych macierzy, sprawdzamy, czy mnożenie jest możliwe
            Shape.isMultiplyingPossible(this.shape, other.shape());
            // tworzymy shape macierzy, która powstanie w wyniku mnożenia
            Shape resultShape = Shape.matrix(this.shape.rows, other.shape().columns);

            /* teraz this.sparseValues to lista wierszy pierwszej macierzy, a listOfColumns to lista kolumn drugiej.
             * Tworzymy pomocniczą listę niezerowych komórek wynikowej macierzy */
            ArrayList<MatrixCellValue> resultMatrixCellsList = new ArrayList<MatrixCellValue>();
            double currentCell = 0;
            // zmienna ktora bedzie nam mowic, jaka jest kolumna danej niezerowej komórki w danym wierszu
            int actualColumn = 0;
            // iterujemy się po wszystkich niezerowych wierszach pierwszej macierzy
            for (int i = 0; i < this.sparseValues.size(); i++) {
                // iterujemy się po wszystkich niezerowych kolumnach drugiej macierzy
                for (int j = 0; j < listOfColumns.size(); j++) {
                    currentCell = 0;
                    // iterujemy się po wszystkich niezerowych wartościach w danym wierszu pierwszej macierzy
                    for (int k = 0; k < this.sparseValues.get(i).size(); k++) {
                        actualColumn = this.sparseValues.get(i).get(k).column;
                        for (int l = 0; l < listOfColumns.get(j).size(); l++) {
                            if (listOfColumns.get(j).get(l).row == actualColumn) {
                                currentCell += this.sparseValues.get(i).get(k).value
                                        * listOfColumns.get(j).get(l).value;
                            }
                        }
                    }
                    if (currentCell != 0) {
                        resultMatrixCellsList.add(MatrixCellValue.cell(i, j, currentCell));
                    }
                }
            }
            // używamy pomocniczą listę komórek, aby skopiować jej zawartość do tablicy
            MatrixCellValue[] resultMatrixCellsArray = new MatrixCellValue[resultMatrixCellsList.size()];
            for (int i = 0; i < resultMatrixCellsList.size(); i++) {
                resultMatrixCellsArray[i] = resultMatrixCellsList.get(i);
            }
            return new Sparse(resultShape, resultMatrixCellsArray);
        }
        else {
            return super.times(other);
        }

    }

    @Override
    public double normOne() {
        // chcemy zwrócić maksymalną wartość sumy modułów poszczególnych kolumn macierzy
        double max = 0;
        double tempMax = 0;
        // iterujemy się po wszystkich kolumnach macierzy
        for (int i = 0; i < this.shape.columns; i++) {
            tempMax = 0;
            for (int j = 0; j < this.values.length; j++) {
                if (this.values[j].column == i) {
                    tempMax += Math.abs(this.values[j].value);
                }
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }

    @Override
    public double normInfinity() {
        // chcemy zwrócić maksymalną wartość sumy modułów poszczególnych wierszy macierzy
        double max = 0;
        double tempMax = 0;
        for (int i = 0; i < this.shape.rows; i++) {
            tempMax = 0;
            for (int j = 0; j < values.length; j++) {
                if (values[j].row == i) {
                    tempMax += Math.abs(values[j].value);
                }
            }
            if (tempMax > max) {
                max = tempMax;
            }
        }
        return max;
    }

    @Override
    public double frobeniusNorm() {
        double norm = 0;
        // sumujemy kwadraty wszystkich niezerowych pól macierzy
        for (int i = 0; i < this.values.length; i++) {
            norm += Math.pow(this.values[i].value, 2);
        }
        return Math.sqrt(norm);
    }

    public ArrayList<ArrayList<MatrixCellValue>> createListOfColumns() {
        ArrayList<ArrayList<MatrixCellValue>> listOfColumns = new ArrayList<ArrayList<MatrixCellValue>>();
        // kopiujemy zawartość values do tablicy pomocniczej i przy okazji sprawdzamy, czy indeksy komórek są poprawne
        MatrixCellValue[] valuesCopy = new MatrixCellValue[values.length];
        for (int i = 0; i < values.length; i++) {
            this.shape.assertInShape(values[i].row, values[i].column);
            valuesCopy[i] = values[i];
        }
        // sortujemy te tablicę pomocniczą
        Arrays.sort(valuesCopy, new CompareMatrixCellByColumns());

        // teraz możemy przystąpić do dodawania komórek z tej posortowanej tablicy do listy list sparseValues
        assert valuesCopy != null && valuesCopy[0] != null;
        int currentColumn = valuesCopy[0].column;
        listOfColumns.add(new ArrayList<MatrixCellValue>());
        listOfColumns.get(0).add(valuesCopy[0]);
        int listIndex = 0;
        for (int i = 1; i < valuesCopy.length; i++) {
            if (valuesCopy[i].column != currentColumn) {
                currentColumn = valuesCopy[i].column;
                listIndex++;
                listOfColumns.add(new ArrayList<MatrixCellValue>());
                listOfColumns.get(listIndex).add(valuesCopy[i]);
            } else {
                listOfColumns.get(listIndex).add(valuesCopy[i]);
            }
        }
        return listOfColumns;
    }


    @Override
    public String toString() {
        String wynik = "";
        for (ArrayList<MatrixCellValue> list: sparseValues) {
            wynik += list.toString();
            wynik += '\n';
        }
        return wynik;
    }
}