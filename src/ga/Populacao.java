package ga;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author jao
 */
public class Populacao {

    public int tamanho;
    ArrayList<Individuo> pop;
    ArrayList<Individuo> listaInd;
    PriorityQueue<Individuo> q1;

    public Populacao(int tam, Sudoku s, int ordem) {

        this.tamanho = tam;
        this.pop = new ArrayList<>(tamanho);
        this.listaInd = new ArrayList<>(tamanho);
        this.q1 = new PriorityQueue<>(50, new Comparator<Individuo>() {
            @Override
            public int compare(Individuo t, Individuo t1) {
                Integer tx = new Integer(t.getFitness());
                Integer t2x = new Integer(t1.getFitness());
                return tx.compareTo(t2x);
            }
        });
        geraPop(tam, s, ordem);

        for (int i = 0; i < tamanho; i++) {

            this.listaInd.add(q1.poll());

        }

        this.pop = this.listaInd;

    }

    public void geraPop(int tamanhoPop, Sudoku s, int ordem) {

        for (int i = 0; i < tamanhoPop; i++) {

            Individuo i1 = new Individuo(s, ordem);
            i1.calculaFit(ordem);

            this.q1.add(i1);

        }

    }

    public int getTamanho() {

        return this.tamanho;

    }

    public static int aleatorio(ArrayList<Integer> array) {

        Random r1 = new Random();

        int a = r1.nextInt(array.size());

        return array.get(a);

    }

    public Individuo selTorneio(ArrayList<Individuo> p, int k, int tam) {

        Individuo best = null;

        Random r2 = new Random();

        for (int i = 0; i < k; i++) {

            Individuo ind = p.get(r2.nextInt(tam));

            if ((best == null) || ind.getFitness() > best.getFitness()) {

                best = ind;

            }

        }

        return best;

    }

    public Individuo[] selecao(ArrayList<Individuo> p) {

        int k = 2;

        Individuo pai1, pai2;

        ArrayList<Individuo> i2 = new ArrayList<Individuo>();

        for (int i = 0; i < pop.size(); i++) {



            i2.add(this.pop.get(i));


        }

        pai1 = selTorneio(i2, k, i2.size() - 1);
        i2.remove(pai1);


        pai2 = selTorneio(i2, k, i2.size() - 1);

        Individuo[] pais = new Individuo[2];
        pais[0] = pai1;
        pais[1] = pai2;

        return pais;
    }

    public Individuo[] crossover(int ordem, Sudoku s, Individuo[] pais) {

        double ord = Math.floor((ordem * ordem) / 2);
        Individuo pai1 = pais[0];
        Individuo pai2 = pais[1];
        int ord2 = (int) ord;
        Individuo[] indv = new Individuo[2];
        Individuo filho1 = new Individuo(s, ordem);
        Individuo filho2 = new Individuo(s, ordem);
        indv[0] = filho1;
        indv[1] = filho2;
        for (int i = 0; i < ord2; i++) {

            for (int j = 0; j < ordem*ordem; j++) {

                filho1.getSudoku()[i][j] = pai1.getSudoku()[i][j];
                filho2.getSudoku()[i][j] = pai2.getSudoku()[i][j];
            }

        }

        for (int i = ord2 + 1; i < ordem * ordem; i++) {

            for (int j = 0; j < ordem*ordem; j++) {

                filho1.getSudoku()[i][j] = pai2.getSudoku()[i][j];
                filho2.getSudoku()[i][j] = pai1.getSudoku()[i][j];
            }
        }

        filho1.calculaFit(ordem);
        filho2.calculaFit(ordem);
        return indv;

    }

    public Individuo mutacao(Individuo f, int ordem, Sudoku s) {

        double prob;

        Random r3 = new Random();

        prob = r3.nextDouble();

        for (int i = 0; i < ordem * ordem; i++) {

            if (prob < 0.9) {

                permutaLinha(i, s, f, ordem);

            }



        }

        return f;
    }

    public void permutaLinha(int linha, Sudoku s, Individuo ind, int ordem) {

        ArrayList<Integer> op = new ArrayList<>();

        for (int j = 0; j < ordem * ordem; j++) {

            op.add(j + 1);

        }

        for (int j = 0; j < ordem * ordem; j++) {

            if (s.sud[linha][j] != 0) {

                op.remove(new Integer(s.sud[linha][j]));

            }

        }

        for (int j = 0; j < ordem * ordem; j++) {

            if (s.sud[linha][j] == 0) {

                ind.getSudoku()[linha][j] = aleatorio(op);
                op.remove(new Integer(ind.getSudoku()[linha][j]));
                
               

            }

        }

    }

    public void avaliacao(int ordem, Individuo[] individuos, Sudoku s) {

        Individuo filho1 = individuos[0];
        Individuo filho2 = individuos[1];

        filho1 = mutacao(filho1, ordem, s);

        filho2 = mutacao(filho2, ordem, s);

        filho1.calculaFit(3);
        filho2.calculaFit(3);
        if (filho1.getFitness() < this.pop.get(getTamanho() - 1).getFitness()) {

            this.pop.set((this.pop.size() - 1), filho1);

        }

        if (filho2.getFitness() < this.pop.get(this.pop.size() - 2).getFitness()) {

            this.pop.set((this.pop.size() - 2), filho2);

        }

        this.q1.clear();

        for (int i = 0; i < this.pop.size(); i++) {

            this.q1.add(this.pop.get(i));

        }
        this.pop.clear();
        for (int i = 0; i < tamanho; i++) {

            this.listaInd.add(this.q1.poll());

        }

        this.pop = listaInd;



    }
}
