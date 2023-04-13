package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.sparse;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  public static void main(String[] args) {


    IDoubleMatrix result = sparse(matrix(10, 10),
            cell(0, 0, 6),
            cell(0, 1, 0),
            cell(0, 2, 0),
            cell(1, 0, 21),
            cell(1, 1, 0),
            cell(1, 2, 0));

    System.out.println(result + "\nnormOne(): \n" + result.normOne()
            + "\nnormInfinity(): \n" + result.normInfinity()
            + "\nforbeniousNorm(): \n" + result.frobeniusNorm());

  }
}
