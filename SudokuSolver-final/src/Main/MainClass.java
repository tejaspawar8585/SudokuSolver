
package Main;

import SocketProgram.ServerS;
import java.util.logging.Level;
import java.util.logging.Logger;
import sudokusolver.*;

public class MainClass {
        
    int array[] = new int[80];    
    SudokuSolver ss = new SudokuSolver();
    ServerS server = new ServerS();
    
    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        mainClass.run();
        
    }
    
    public void run(){
        ss.us.run();
        while(true){
             try {
                   Thread.sleep(2000);
                } catch (InterruptedException ex) {
            }
            if(ss.us.flag == 0){
                
                
                ss.Solver();
                break;
            }
        }
    };
    
}
