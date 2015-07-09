
package ga;

import java.util.Scanner;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jao
 */
public class Sudoku {
    
    
    public int sud[][]; //verificar a ordem
    
    
    
    
    public Sudoku(File arquivo, int ordem){
        
        this.sud = new int[ordem*ordem][ordem*ordem]; //verificar a ordem
        
        Scanner scan = null;
        try {
            scan = new Scanner(arquivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(scan.hasNext()){
            
            for(int i=0; i<ordem*ordem; i++){
                
                for(int j=0; j<ordem*ordem; j++){
                    
                    this.sud[i][j] = scan.nextInt();

                }
                
            }
            
        }
  
    }
    
    public int[][] getSud(){
        
        return this.sud;
        
    }
    
    
    
    
    
    
}
