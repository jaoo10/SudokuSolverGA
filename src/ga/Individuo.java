package ga;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jao
 */
public class Individuo {

    private int[] opcoes;//rever quanto a ordem
    private int sudoku[][];//rever quanto a ordem
    private int fitness;

    public Individuo(Sudoku s, int ordem) {
        this.fitness = 0;
        
        this.sudoku = new int[9][9];

        for (int i = 0; i < ordem * ordem; i++) {

            for (int j = 0; j < ordem * ordem; j++) {

                this.sudoku[i][j] = s.sud[i][j];
            }
        }

        for (int i = 0; i < ordem * ordem; i++) {
            
            ArrayList<Integer> opcoes = new ArrayList<>();
            
            for(int a=0; a < 9; a++){
                
                opcoes.add(a+1);
                
            }
 
            for(int j=0; j<ordem*ordem; j++){
                
                if(this.sudoku[i][j] != 0){
                    
                    opcoes.remove(new Integer(this.sudoku[i][j]));
                    
                }
                
            }

            for (int j = 0; j < ordem * ordem; j++) {

                if (this.sudoku[i][j] == 0) {

                    this.sudoku[i][j] = aleatorio(opcoes);
                    opcoes.remove(new Integer(this.sudoku[i][j]));
                    
                }

            }
        }

        calculaFit(ordem);

    }

    public Individuo(Individuo i1, int ordem) {
        this.fitness = i1.getFitness();
        this.opcoes = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        this.sudoku = new int[9][9];

        for (int i = 0; i < ordem * ordem; i++) {

            for (int j = 0; j < ordem * ordem; j++) {

                this.sudoku[i][j] = i1.getSudoku()[i][j];

            }
        }

        calculaFit(ordem);

    }

    public static int aleatorio(ArrayList<Integer> array) {

        Random r1 = new Random();

        int a = r1.nextInt(array.size());

        return array.get(a);

    }

    public void calculaFit(int ordem) {
        this.fitness = 0;
        int ord = ordem * ordem;

        //verifica as linhas
        for (int i = 0; i < ord; i++) {

            int[] repL = new int[ord];

            for (int j = 0; j < ord; j++) {

                repL[this.sudoku[i][j] - 1]++;

            }

            for (int k = 0; k < ord; k++) {

                if (repL[k] > 0) {

                    repL[k]--;
                    this.fitness += repL[k];

                }

            }

        }

        //verifica as colunas
        for (int j = 0; j < ord; j++) {

            int[] repC = new int[ord];

            for (int i = 0; i < ord; i++) {

                repC[this.sudoku[i][j] - 1]++;
            }

            for (int k = 0; k < ord; k++) {

                if (repC[k] > 0) {

                    repC[k]--;
                    this.fitness += repC[k];
                }

            }
        }

        for (int i = 0; i < ordem; i++) {

            for (int j = 0; j < ordem; j++) {

                this.fitness += verificaQuad(ordem * i, ordem * j, ordem);

            }

        }

    }

    public int verificaQuad(int lin, int col, int ordem) {

        int fit2 = 0;

        int ord2 = ordem * ordem;

        int[] repQ = new int[ord2];

        for (int i = lin; i < lin + ordem; i++) {

            for (int j = col; j < col + ordem; j++) {

                repQ[sudoku[i][j] - 1]++;

            }
        }

        for (int i = 0; i < ord2; i++) {

            if (repQ[i] > 0) {

                repQ[i]--;
                fit2 += repQ[i];
            }
        }

        return fit2;

    }

    public int[] getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(int[] opcoes) {
        this.opcoes = opcoes;
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
