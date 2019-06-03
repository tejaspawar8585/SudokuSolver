package sudokusolver;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class Username extends Frame{
    
    public int flag;
    public String username;
    
    
    Label User = new Label("Username :");
    TextField name = new TextField();
    Button login = new Button("Login");
    
    public Username()  {
       
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
    
    public void setPosition(){
        setTitle("Login");
        setBackground(new Color(176,224,230));
        setBounds(400, 300, 350, 200);
        setResizable(false);
        setVisible(true);
        setLayout(null);
        
        User.setBounds(50, 40, 120, 30);
        User.setFont(new Font("Brush Script MT", 2, 20));
        add(User);
        
        name.setBounds(170, 40, 100, 30);
        add(name);
        
        login.setBounds(100, 100, 100, 30);
        add(login);
        
        flag = 3;
    }
    
    public void setAction() {
        login.addActionListener((ActionEvent e) -> {
            if(name.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter Valid Username");
            }else{
                 username = name.getText();
                 flag = 0;
                 this.dispose();
            }
            
        });
    }
}
