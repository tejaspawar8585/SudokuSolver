package SocketProgram;

import sudokusolver.SudokuSolver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientS{
    private BufferedReader in;
    private PrintWriter out;
    Head head = new Head();
    public static String IPAddress;
    public String temp[][] = new String[5][4]; 

    public String temp1[][]= new String[1][4];
    public String username;
    
    public ClientS(String username) {
        this.username = username;
        ClientS.IPAddress = SudokuSolver.IPaddress;
    }
    
    public void run (){
        connectToServer();
    }
    
    public void connectToServer() {
        try {
            Socket socket = new Socket();
            System.out.println(IPAddress);
            socket.connect(new InetSocketAddress(IPAddress,1555));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            out.println(username);
           
        } catch (IOException ex) {
            System.out.println(ex);
            head.alert();
        }   
    }
    
    public void receiveData() throws IOException{
        out.println("scorecard");
            for(int i = 0;i<4;i++){
               temp1[0][i] = in.readLine();
                System.out.println(temp1[0][i]);
            }
            int j =0;
            while(j < 5){
                for(int i = 0;i<4;i++){
                    temp[j][i] = in.readLine();
                }
                j++;
            }
    }
    
    public void sendAnswer(String timer){
        out.println("Win");
        out.println(timer);
        System.out.println(timer);
    
    }
}
