package pl.edu.mimuw.matrix;
import java.util.*;

public class CompareMatrixCell implements Comparator<MatrixCellValue> {
    @Override
    public int compare(MatrixCellValue o1, MatrixCellValue o2) {
        if (o1.row < o2.row) {
            return -1;
        }
        else if (o1.row == o2.row) {
            if (o1.column < o2.column) {
                return -1;
            }
            else if (o1.column == o2.column) {
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
