package SocketProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import JDBCconnection.*;
import javax.swing.JOptionPane;

public class ServerS {
    public static int clientNumber;
     public void run() {
            try {
                clientNumber = 0;
                ServerSocket listener = new ServerSocket(1555);
                try {
                    while (true) {
                        
                            new newClient(listener.accept(), clientNumber++).start();
                    }
                }catch(Exception e){
                } finally {
                    listener.close();
                }
            }    catch (IOException ex) {
                System.out.println(ex);
        }
    }
     
  private static class newClient extends Thread{
      private Socket socket;
      private int clientNumber;
      public Connection con;
      ResultSet rs;
      Connect connect;
      PrintWriter out;
      BufferedReader in;
      
      public newClient(Socket socket, int clientNumber) {
           try{
               JOptionPane.showMessageDialog(null, "Client "+clientNumber+" with IP : "+socket.getInetAddress()+" is Connected to the Server");
                    
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sudoku","root","root"); 
           }catch(Exception e){
               
           }
            this.socket = socket;
            this.clientNumber = clientNumber;
        }
      
      
       public void run() {
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                Statement stmt =  con.createStatement();
                String username = in.readLine();
                
                connect = new Connect(username);
                
                String abc;
                while (true) {
                    abc = in.readLine();
                    if(abc.equals("Win")){
                        String tm = in.readLine();
                        connect.UpdateDatabase(tm);
                    }
                    if(abc.equals("scorecard")){
                        sendData();
                    }
                }
                
            } catch (IOException | SQLException e) {
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
       
      public void sendData(){
                connect.refresh();
                for(int i = 0; i < 4;i++){
                    out.println(connect.temp1[0][i]);
                }
                int j=0;
                while(j < 5){
                    for(int i = 0; i < 4;i++){
                        out.println(connect.temp[j][i]);
                    }
                    j++;
                }
      }
      
  }
  
}
