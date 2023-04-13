package pl.edu.mimuw.matrix;

import java.util.Comparator;

public class CompareMatrixCellByColumns implements Comparator<MatrixCellValue> {
    @Override
    public int compare(MatrixCellValue o1, MatrixCellValue o2) {
        if (o1.column < o2.column) {
            return -1;
        }
        else if (o1.column == o2.column) {
            if (o1.row < o2.row) {
                return -1;
            }
            else if (o1.row == o2.row) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else {
            return 1;
        }
    }

}

