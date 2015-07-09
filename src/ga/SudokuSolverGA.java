package ga;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author jao
 */
public class SudokuSolverGA {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        File arquivo = new File("50-clue-1.sudoku.txt");
        
        AGenetico ag1 = new AGenetico(3, arquivo, 200000);
        
    }
    
}
