package ga;

import java.io.File;

/**
 *
 * @author jao
 */
public class AGenetico {

    public AGenetico(int ordem, File arquivo, long MAX_CYCLES) {

        Sudoku sudoku = new Sudoku(arquivo, ordem);
        
        long ciclo = 0;

        Populacao p = new Populacao(50, sudoku, ordem);
        
        boolean solved = false;

        while ((p.pop.get(0).getFitness() != 0) && (ciclo < MAX_CYCLES)) {


            Individuo[] pais = p.selecao(p.pop);

            Individuo[] individuos = p.crossover(ordem, sudoku, pais);
        
            p.avaliacao(ordem, individuos, sudoku);

            p.pop.get(0).calculaFit(ordem);
            
            ciclo++;

        }
        
        if((p.pop.get(0).getFitness()) == 0){
            
            solved = true;
            
            System.out.print("Solved! :D");
            
        } else {
            
            System.out.print("Not solved :(");
            
        }
        
        System.out.println("");
        
        printSudoku(p.pop.get(0), ordem);
    }

    public void printSudoku(Individuo ind, int ordem) {

        for (int i = 0; i < ordem*ordem; i++) {

            for (int j = 0; j < ordem*ordem; j++) {

                System.out.print(ind.getSudoku()[i][j] + " ");

            }

            System.out.println();

        }

    }
    
}
