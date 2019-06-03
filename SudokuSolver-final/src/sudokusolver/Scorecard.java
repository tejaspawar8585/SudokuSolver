package sudokusolver;

import javax.swing.*;
import java.awt.*;
import JDBCconnection.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import SocketProgram.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Scorecard extends Frame{

    SudokuSolver s = new SudokuSolver(); 
    Connect connect ;
    ClientS client;
    
    String colomn[] = new String[]{"No." , "Player" , "Time" , "Rank"};
    
    JButton refresh = new JButton("Refresh");
   
    JTable top5;
    JTable you1;
   
    Label top = new Label("Top scorer");
    Label you = new Label("Your Score");
    
    boolean flag;
    public Scorecard(Connect connect) {
        this .connect = connect;
        this.top5 = new JTable();
        top5 = new JTable(connect.temp, colomn);
        you1 = new JTable(connect.temp1, colomn);
        flag = true;
    }
    public Scorecard(String temp[][],String temp1[][],ClientS client){
        top5 = new JTable(temp, colomn);
        you1 = new JTable(temp1, colomn);
        this.client = client;
        flag = false;
    }
    
    public void run(){
        setPosition();
        
        addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {  
                dispose();  
            }  
        });
        
        setAction();
    }
    
    public void setAction(){
        
        refresh.addActionListener((ActionEvent e) -> {
            if(flag){
                connect.refresh();
            }
            if(!flag){
                try {
                    client.receiveData();
                } catch (IOException ex) {
                }
            }
            top5.repaint();
            you1.repaint();
        });
    }
    
    public void setPosition(){
        setTitle("Scorecard");
        setBackground(new Color(176,224,230));
        setBounds(350, 150, 600, 400);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        
        top.setBounds(40, 40, 150, 30);
        top.setFont(s.f3);
        add(top);
        
        you.setBounds(40 ,250 , 150, 30);
        you.setFont(s.f3);
        add(you);
   
        refresh.setBounds(400, 40, 100, 30);
        refresh.setFont(s.f1);
        add(refresh);
        
        top5.setBounds(40, 90, 550, 140);
        JScrollPane sp = new JScrollPane(top5);
        sp.setBounds(40, 90, 550, 140);
        add(sp);
        
        you1.setBounds(40, 290, 550, 40);
        JScrollPane sp1 = new JScrollPane(you1);
        sp1.setBounds(40, 290, 550, 40);
        add(sp1);
       
    }
    
}
