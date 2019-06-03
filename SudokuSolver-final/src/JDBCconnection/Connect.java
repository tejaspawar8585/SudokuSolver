package JDBCconnection;

import java.sql.*;
import SocketProgram.Head;


public class Connect {

    Head head = new Head();
    
    public String temp[][] = new String[5][4]; 

    public String temp1[][]= new String[1][4];
    
    int maxNumber,maxRank;
    
    static String username  = null;
    static String time = null;
     
    public Connect(String username) {
        Connect.username = username;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull","root",""); 
            Statement stmt =  con.createStatement();
           
            addMeInDatabase(stmt,username);
            setTop5(stmt);
            you1(stmt);
            con.close();
        } catch (ClassNotFoundException ex) {
        } catch (SQLException ex) {
            head.databaseError();
        }
    
    }
        
    private void addMeInDatabase(Statement stmt,String username){
        try {
            ResultSet rs;
            rs = stmt.executeQuery("select * from main where UserName = '"+username+"';");
            if(!rs.next()){
                rs = stmt.executeQuery("select max(Number) from main;");
                rs.next();
                maxNumber = rs.getInt("max(Number)");
                
                rs = stmt.executeQuery("select max(Rank) from main;");
                rs.next();
                maxRank = rs.getInt("max(Rank)");
                stmt.executeUpdate("insert into main(Number,UserName,Time,Rank) values( "+(maxNumber +1)+" , '"+username+"' , 00 , "+(maxRank)+");");
            
            }
            
        } catch (SQLException ex) {
           
        }
    }
    
    public void UpdateDatabase(String time){
        Connect.time = time;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull","root",""); 
            Statement stmt =  con.createStatement();
            
            ResultSet rs = stmt.executeQuery("select Time from main where UserName ='"+username+"'");
            rs.next();
            Time oldTime = rs.getTime("Time");
            Time newTime = Time.valueOf(time);
            
            if(newTime.before(oldTime)){
                stmt.executeUpdate("update main set Time = '"+newTime+"' where UserName = '"+username+"' ");
            }
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
           head.databaseError();
         }
    } 
    
    private void setTop5(Statement stmt){
        int  i = 0;
        try {
            ResultSet rs = stmt.executeQuery("select * from main where Rank < 6 order by Rank");
    
            while(rs.next() && i<5){
                temp[i][0] = String.valueOf(rs.getInt(1));
                temp[i][1] = rs.getString(2);
                temp[i][2] = rs.getString(3);
                temp[i][3] = String.valueOf(rs.getInt(4));
                i++;
            }
        } catch (SQLException ex) {
      
        }        
    }
    
    private void you1(Statement stmt){
        try {
            ResultSet rs = stmt.executeQuery("Select * from main where UserName = '"+username+"';");
            if(rs.next()){
                temp1[0][0] = String.valueOf(rs.getInt(1));
                temp1[0][1] = rs.getString(2);
                temp1[0][2] = rs.getString(3);
                temp1[0][3] = String.valueOf(rs.getInt(4));
            }
            
        } catch (SQLException ex) {
        
        }
       
    }
    
    public void refresh(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull","root",""); 
            Statement stmt =  con.createStatement();
             
            int Rank = 1,i = 0,max;
            int number[] = new int[100];
            
            ResultSet rs = stmt.executeQuery("select Number from main where Time > 00 order by Time;");
            while(rs.next()){
                    number[i++] = rs.getInt("Number");
            }
           
            max = i;
            for(i =0 ; i<max ;i++){
                
                stmt.executeUpdate("update main set Rank ='"+ (Rank++) +"'where Number = '"+ number[i] +"' ;");
            }
           
            ResultSet maxRank = stmt.executeQuery("select max(Rank) from main");
            maxRank.next();
            i = maxRank.getInt("max(Rank)") +1;
            stmt.executeUpdate("update main set Rank = '"+i+"' where Time = 00;");
           
            setTop5(stmt);
            you1(stmt);
            con.close();
            
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
           head.databaseError();
        }
           
    }
}   