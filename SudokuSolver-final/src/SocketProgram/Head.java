
package SocketProgram;

import java.io.*;
import java.net.*;
import java.net.URL;

import javax.swing.JOptionPane;


public class Head {
    
    public boolean internetConn() throws IOException{
        
        try {
            URL url = new URL("https://www.facebook.com/");
            URLConnection Conn = url.openConnection();
            Conn.connect();
                return true;
           
        }catch(IOException e){
            return false;
        }
    }
    
    public void alert(){
        JOptionPane.showMessageDialog(null, "Internet Connection Error...");
    }
    
    public void conform(){
        JOptionPane.showMessageDialog(null, "Connected Succesfully");
    }
    
    public void sudokuError(){
        JOptionPane.showMessageDialog(null, "Error in SUDOKU...");
    }
    
    public void answer(){
        JOptionPane.showMessageDialog(null, "Your Answer Is Ready");
    }
    
    public void databaseError(){
        JOptionPane.showMessageDialog(null, "Database Connection Error...");
    }
    
    public void youWin(){
        JOptionPane.showMessageDialog(null, "You Win");
    }
   
    public void incompleteAnswer(){
        JOptionPane.showMessageDialog(null, "Your Answer Is Not Complete, Do it Then Press Check");
    }

}
