package sudokusolver;

import JDBCconnection.Connect;
import SocketProgram.ClientS;
import java.awt.Frame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import SocketProgram.Head;
import SocketProgram.ServerS;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SudokuSolver extends Frame{
    
    @Override
    public void paint(Graphics g){
       
        g.drawLine( 200, 0, 200, 500);
        g.drawLine( 600, 0, 600, 500);
        
        g.drawImage(bgImage.getImage(), 10, 20, this);
    }
    
    TextField t[] = new TextField[81];
    
    public int array[] = new int[81];
    
    String tm; // to save time in string 
    
    Head head = new Head();
    ServerS server = new ServerS();
    ClientS client;
    
    Connect connect;
    
    public Username us = new Username();
    
    public String textString = new String();
            
    ImageIcon bgImage = new ImageIcon("res/backgroundimage1.jpgp");
    
    String filePath = "res/SoundClips/ErrorSound.wav";
    
    public static String IPaddress = null;
    
    Button Answer = new Button("Answer");
    Button Submit = new Button("Submit");
    Button Clear  = new Button("Clear");
    Button Cross = new Button("CROSS PUZZLE");
    Button Normal = new Button("NORMAL PUZZLE");
    Button Stop = new Button("Stop");
    Button getSudoku = new Button("Get Sudoku");
    Button checkConn = new Button("Connect");
    Button information = new Button("Information");
    Button scorecard = new Button("Scorecard");
    Button checkAnswer = new Button("Check Answer");
    
    Label slogan = new Label("Sharp  Your Mind");
    Label comment = new Label("Comment : ");
    Label msgHor = new Label("**Same Number in Horizonatl Line");
    Label msgVer = new Label("**Same Number in Verticle Line");
    Label msgBox = new Label("**Same Number in 3x3 Box");
    Label msgNumber = new Label("**Only Number Allowed");
    Label msgOneNumber = new Label("**Only One Number Allowed");
    Label error = new Label("**This Sudoku CANNOT Solved");
    Label name = new Label("Created by Tejas Pawar");
    Label crossL = new Label("Cross Puzzle");
    Label normalL = new Label("Normal Puzzle");
    Label offline = new Label("Offline Options");
    Label online  = new Label("Online Options");
    Label timer = new Label("00:00:00");
    Label username = new Label("");
    
    Timer time = null;
    
    Font f1 = new Font("serif", Font.BOLD, 15);
    Font f2 = new Font("sanserif", Font.BOLD, 30);
    Font f3 = new Font("sanserif", Font.BOLD, 20);
    
    boolean crossMode ;
    boolean normalMode ;
    
    int counter,counter1;

    public SudokuSolver(){
    }
    
    public void Solver() {
        setPosition();
        
        addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {  
                dispose();  
            }  
        });

        setActions();
        
    }
    
    public void setPosition(){
        setTitle("Sudoku Solver");
        setBackground(new Color(176,224,230));
        setBounds(300, 100, 800, 500);
        setResizable(false);
        setVisible(true);
        setLayout(null);
       
        name.setBounds(110, 30, 200, 30);
        name.setFont(f1);
        //add(name);
        
        comment.setBounds(210, 420, 70, 30);
        add(comment);
        
        slogan.setBounds( 280, 30, 250, 50);
        slogan.setFont(f2);
        add(slogan);
        
        timer.setBounds(650, 330, 140, 30);
        timer.setFont(f3);
        add(timer);
        
        offline.setBounds(620, 60, 160, 30);
        offline.setFont(f3);
        add(offline);
        
        online.setBounds(20, 60, 160, 30);
        online.setFont(f3);
        add(online);
        
        username.setBounds(60, 100, 100, 30);
        username.setText(us.username);
        username.setFont(f1);
        add(username);
        
        msgBox.setBounds(280, 420, 300, 30);
        msgHor.setBounds(280, 420, 300, 30);
        msgVer.setBounds(280, 420, 300, 30);
        msgNumber.setBounds(280, 420, 300, 30);
        msgOneNumber.setBounds(280, 420, 300, 30);
        error.setBounds( 280, 420, 300, 30);
        
        msgBox.setFont(f1); 
        msgNumber.setFont(f1);
        msgVer.setFont(f1);
        msgHor.setFont(f1);
        msgOneNumber.setFont(f1);
        error.setFont(f1);
        
        unVisible();
        
        add(error);
        add(msgBox);
        add(msgHor);
        add(msgVer);
        add(msgNumber);
        add(msgOneNumber);
        
        Clear.setBounds(630, 150, 140, 40);
        Clear.setBackground(new Color(240,255,255));
        Clear.setFont(f1);
        add(Clear);
        
        Submit.setBounds(630, 210, 140, 40);
        Submit.setBackground(new Color(240,255,255));
        Submit.setFont(f1);
        add(Submit);
        
        Answer.setBounds(630, 270, 140, 40);
        Answer.setFont(f1);
        Answer.setBackground(new Color(240,255,255));
        add(Answer);
        
        Cross.setBounds(220 ,455 ,160 ,30 );
        add(Cross);
        
        Normal.setBounds(420 ,455 ,160 ,30 );
        add(Normal);
        
        getSudoku.setBounds(30 ,150 ,140 ,40 );
        getSudoku.setBackground(new Color(240,255,255));
        getSudoku.setFont(f1);
        getSudoku.setEnabled(true);
        add(getSudoku);
        
        checkAnswer.setBounds(30 ,210 ,140 ,40 );
        checkAnswer.setBackground(new Color(240,255,255));
        checkAnswer.setFont(f1);
        checkAnswer.setEnabled(true);
        add(checkAnswer);
        
        Stop.setBounds(30 ,270 ,140 ,40 );
        Stop.setBackground(new Color(240,255,255));
        Stop.setFont(f1);
        Stop.setEnabled(true);
        add(Stop);
        
        scorecard.setBounds(30 ,330 ,140 ,40 );
        scorecard.setBackground(new Color(240,255,255));
        scorecard.setFont(f1);
        scorecard.setEnabled(false);
        add(scorecard);
        
        checkConn.setBounds(30 ,390 , 140 ,40);
        checkConn.setBackground(new Color(240,255,255));
        checkConn.setFont(f1);
        checkConn.setEnabled(false);
        add(checkConn);
        
        
        information.setBounds(630, 450, 140, 40);
        information.setBackground(new Color(240,255,255));
        information.setFont(f1);
        add(information);
        
        int k =0; //to count number of textfield
        
        for(int i = 110; i < 424 ;i++){
            
            for(int j = 238;j < 563 ; j++){
                
                if(k < 81){
                    
                    t[k] = new TextField();
                    t[k].setBounds(j, i, 25, 25);
                    t[k].setText("-");
                    t[k].setFont(f1);
                    t[k].setBackground(new Color(240,255,240));
                    onlyOneNumber(t[k],k);
                    add(t[k]);
                    j = j + 35;
                    
                    if( k % 3 == 2){
                            j = j + 10;
                        }
                    
                    k++;
                    
                }
            }
            if(k == 27 || k == 54 ){
                i= i + 10;
            }
            i = i + 30;
        }
        crossMode = false;
        normalMode = true;
        
    }
    
    public void setActions(){
        Clear.addActionListener((ActionEvent ae) -> {
              clear();
        });
        
        Submit.addActionListener((ActionEvent ae) -> {
            submit();
        });
    
        Answer.addActionListener((ActionEvent ae) -> {
            answer();
            head.answer();
        });
        
        information.addActionListener((ActionEvent e) -> {
            try {
                openTextFile();
            } catch (IOException ex) {
            }
        });
    

        Normal.addActionListener((ActionEvent e) -> {
            crossMode = false;
            normalMode = true;
            mode(crossMode);
        });

        Cross.addActionListener((ActionEvent e) -> {
            crossMode = true;
            normalMode = false;
            mode(crossMode);
        });
        
        getSudoku.addActionListener((ActionEvent e) -> {
            clear();
            getSudoku.setEnabled(false);
            Answer.setEnabled(false);
            Clear.setEnabled(false);
            Submit.setEnabled(false);
            Normal.setEnabled(false);
            Cross.setEnabled(false);
            timer.setVisible(true);
            checkAnswer.setEnabled(true);
            randomSudoku();
            startTimer(true);
        });
        
        checkAnswer.addActionListener((ActionEvent e) -> {
            int i;
            for(i = 0; i< 81;i++){
                if(t[i].getText().equals("-")){
                    break;
                }
            }
            
            if(i == 81){
                head.youWin();
                startTimer(false);
                if(us.username.equals("ServerMode")){
                    connect.UpdateDatabase(tm);
                }else{
                   client.sendAnswer(tm);
                }
            }else{
                head.incompleteAnswer();
            }
        });
        
        Stop.addActionListener((ActionEvent e) -> {
            startTimer(false);
            checkAnswer.setEnabled(false);
            getSudoku.setEnabled(true);
            Answer.setEnabled(true);
            Clear.setEnabled(true);
            Submit.setEnabled(true);
            Normal.setEnabled(true);
            Cross.setEnabled(true);
        });
        
        scorecard.addActionListener((ActionEvent e) -> {
        if(us.username.equals("ServerMode")){
                connect.refresh();
                Scorecard sc = new Scorecard(connect);
                sc.run();
        }else{
                Scorecard sc = new Scorecard(client.temp, client.temp1,client);
                sc.run();
            }
        });
        
        checkConn.addActionListener((ActionEvent e) -> {
              try {
                if(head.internetConn()){
                    internetOption(true);
                    if(us.username.equals("ServerMode")){
                        connect = new Connect(us.username);
                        server.run();
                    }else{
                        IPaddress = JOptionPane.showInputDialog("Enter IP Address Of Server :");
                        client = new ClientS(us.username);
                        client.connectToServer();
                    }
                }
                if(!head.internetConn()){
                    head.alert();
                    internetOption(false);
                }
              } catch (IOException ex) {
                  head.alert();
                  internetOption(false);
              }
        });
        
        
    
    }
    
    public void startTimer(boolean flag){
        if(flag){
            counter = 0;
            time = new Timer(1000, (ActionEvent e) -> {

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                timer.setText(sdf.format(new Date(0, 0, 0, 0, 0, counter++)));
              });
            time.restart();
            time.start();
        }
       if(!flag){
           time.stop();
           tm = timer.getText();
        }
      
    }
    
    public void clear(){
         for(int i = 0 ; i < 81; i++){
                t[i].setBackground(new Color(240,255,240));
                t[i].setEditable(true);
                t[i].setText("-");
                crossMode = false;
                normalMode = true;
            }
    }
    
    public void answer(){
        
         int i,j,l,m;
            l = 0;
            boolean flag1 = true , flag2 = true ;
            
            try{
                for(i = 0 ; i < 81 ; i++){

                    flag1 = true;
                    while(!t[i].isEditable()){
                        i++;
                    }

                    for(j = 1;j <= 9; j++){
                       if(l != 0){
                           j = l+1;
                           if(j > 9){
                               break;
                           }
                       }
                       l = 0;


                       if(crossMode && (sameNoBox(i, j) || sameNoHorizonatal(i, j) || sameNoVerticle(i, j) || sameNoForward(i, j)||sameNoBackward(i, j))){
                           continue;
                       }else if(normalMode &&(sameNoBox(i, j) || sameNoHorizonatal(i, j) || sameNoVerticle(i, j))){
                           continue;
                       }else{
                           t[i].setText(String.valueOf(j));
                           flag1 = false;
                           break;
                       }

                    }
                    if(flag1){
                        if(t[i].isEditable()){
                            t[i].setText("-");
                        }
                        while(!t[i-1].isEditable()){
                            i--;
                        }
                        if(i >= 1){
                            l = Integer.parseInt(t[i-1].getText());
                        }
                        if(t[i].isEditable()){
                            t[i].setText("-");
                        }

                        //t[i-1].setText("-");

                        i = i-2;

                        if(i == -2){
                            flag2 = false;
                            break;
                        }
                   }
                }
            }catch(Exception e){
               
            }
               
            if(!flag2){
                unVisible();
                try {
                    blink(error);
                } catch (InterruptedException ex) {

                }
            }
    }
    
    public void submit(){
        for(int i = 0;i < 81 ; i++){
               if(!t[i].getText().equals("-")){
                   t[i].setEditable(false);
                   t[i].setBackground(new Color( 173,255,47));
               }
           }
    }
            
    private void onlyOneNumber(TextField t,int k){
        t.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                if(t.getText().equals("-")){
                    t.setText(null);
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(t.getText().equals("")){
                    t.setText("-");
                }
            }
        });
        
        t.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                boolean flag = false;
                if(!isBackslash(ke.getKeyChar())){
                   
                    int num = -1;
                    char ch = ke.getKeyChar();
                    
                    try{
                        num = Integer.parseInt(Character.toString(ch)); 
                    }catch(NumberFormatException e){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgNumber);
                            errorSound();
                            flag = true;
                        } catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }
                    if(flag){
                        
                    }else
                    if(ch < '0' || ch > '9'){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgNumber);
                            errorSound();
                        } catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }else

                    if(t.getText().length() >= 1){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgOneNumber);
                            errorSound();
                        }catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }else

                    if(sameNoHorizonatal(k ,num)){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgHor);
                            errorSound();
                        }catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }else
                        
                    if(sameNoVerticle(k,num)){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgVer);
                            errorSound();
                        } catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }else 
                        
                    if(sameNoBox(k, num)){
                        ke.consume();
                        unVisible();
                        try {
                            blink(msgBox);
                            errorSound();
                        } catch (InterruptedException ex) {
                        } catch (LineUnavailableException ex) {
                        } catch (IOException ex) {
                        } catch (UnsupportedAudioFileException ex) {
                        }
                    }else 
                        
                    if(crossMode)
                        if(sameNoForward(k, num) ){
                            ke.consume();
                            unVisible();
                            try {
                                blink(msgBox);
                                errorSound();
                            } catch (InterruptedException ex) {
                            } catch (LineUnavailableException ex) {
                            } catch (IOException ex) {
                            } catch (UnsupportedAudioFileException ex) {
                            }
                        }else if(sameNoBackward(k, num)){
                            ke.consume();
                            unVisible();
                            try {
                                blink(msgBox);
                                errorSound();
                            } catch (InterruptedException ex) {
                            } catch (LineUnavailableException ex) {
                            } catch (IOException ex) {
                            } catch (UnsupportedAudioFileException ex) {
                            }
                        }
                        
                    }else{
                    unVisible();
                }
            }
            
            @Override
            public void keyPressed(KeyEvent ke) {
                changeFocus(ke, k);
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
    }
    
    public void changeFocus(KeyEvent ke,int k){
        if(k == 80 && ke.getKeyCode() == KeyEvent.VK_RIGHT){
            t[0].requestFocus();
        }else if(k == 0 && ke.getKeyCode() == KeyEvent.VK_LEFT){
            t[80].requestFocus();
        }else if(k < 9 && ke.getKeyCode() == KeyEvent.VK_UP){
            t[k+72].requestFocus();
        }else if(k > 71 && ke.getKeyCode() == KeyEvent.VK_DOWN){
            t[k-72].requestFocus();
        }else if(ke.getKeyCode()== KeyEvent.VK_LEFT){
            t[k-1].requestFocus();
        }else if(ke.getKeyCode()== KeyEvent.VK_RIGHT){
            t[k+1].requestFocus();
        }else if(ke.getKeyCode()== KeyEvent.VK_UP){
            t[k-9].requestFocus();
        }else if(ke.getKeyCode() == KeyEvent.VK_DOWN){
            t[k+9].requestFocus();
        }
    }
    
    public void blink(Label msg) throws InterruptedException {
        msg.setVisible(true);
        Thread.sleep(100);
        msg.setVisible(false);
        Thread.sleep(100);
        msg.setVisible(true);
    }
    
    public void unVisible(){
        msgBox.setVisible(false);
        msgHor.setVisible(false);
        msgVer.setVisible(false);
        msgNumber.setVisible(false);
        msgOneNumber.setVisible(false);
        error.setVisible(false);
    }
    
    public void internetOption(boolean flag){
        if(flag){
            getSudoku.setEnabled(true);
            checkAnswer.setEnabled(false);
            Stop.setEnabled(true);
            scorecard.setEnabled(true);
            checkConn.setEnabled(false);
        }else{
            checkConn.setEnabled(true);
            checkAnswer.setEnabled(false);
            getSudoku.setEnabled(false);
            Stop.setEnabled(false);
            scorecard.setEnabled(false);
        }
    }
    
    public void getArray(){
         for(int i = 0; i < 81; i++){
            if(!t[i].getText().equals("-")){
                array[i] = Integer.parseInt(t[i].getText());
               
            }else{
                array[i] = 0;
                
            }
         }
    }//Converts TextField Into Array
    
    public void getString(){
        textString = Arrays.toString(array);
    }//Converts TextField Into String
    
    public boolean isBackslash(char ch){
        if(ch == '\b'){
            return true;
        }
        return false;
    }
    
    public boolean sameNoBox(int n,int num){
        
        int l = n;
        n = n - (n % 3);
        int j = n/9;
        int i = j%3;
                   
        while(i > 0){
            n = n - 9;
            i--;
        }
        
        for(i = 0;i < 9;i++){
            if(n != l){
                if(!t[n].getText().equals("-")){
                
                    if(Integer.parseInt(t[n].getText()) == num){
                        return true;
                    }
                }
            }
            if(n%3 == 2){
                n = n+7;
            }else{
                n++;
            }
        }
        return false;
    }
    
    public boolean sameNoVerticle(int n,int num){
        
        int i = 0;
        int j = n;
        for (i = 0;i < 9; i++){
            if(n > 80){
                n = n % 9;
            }
            
            if(n != j){
                if(!t[n].getText().equals("-")){
                
                    if(Integer.parseInt(t[n].getText()) == num){
                        return true;
                    }
                }
            }
          
            n = n + 9;
        }
        return false;
    }
    
    public boolean sameNoHorizonatal(int n,int num){
       
        int i = 0;
        int j = n / 9;
        
        for(j = j * 9; i < 9; j++){
      
            if(n != j){
       
                if(!t[j].getText().equals("-")){
        
                    if(Integer.parseInt(t[j].getText()) == num){
                       return true;
                    }
                }
            }
            i++;
        }
        
        return false;
    }
    
    public boolean sameNoForward(int n ,int num){
        int i =  0;
        
        for(i = 0 ;i < 81 ; i = i + 10){
            if(n % 10 == 0){
                if(n != i){
                    if(!t[i].getText().equals("-")){
                        if(Integer.parseInt(t[i].getText()) == num){
                           return true;
                        }
                    }
                }
            }
        }
       
        return false;
    }
    
    public boolean sameNoBackward(int n ,int num){
        int i =  0;
        for(i = 8 ;i < 73 ; i = i + 8){
            if(n % 8 == 0){
                if(n != i){
                    if(!t[i].getText().equals("-")){
                        if(Integer.parseInt(t[i].getText()) == num){
                           return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public void mode(boolean flag){
        if(flag){
            for(int i = 0;i < 81; i = i + 10){
                t[i].setBackground(new Color(0, 230, 230));
            }
            for(int i = 8;i < 73; i = i + 8){
                t[i].setBackground(new Color(0, 230, 230));
            }
        }else{
             for(int i = 0;i < 81; i = 10 + i){
                t[i].setBackground(new Color(240,255,240));
            }
            for(int i = 8;i < 73; i = 8 + i){
                t[i].setBackground(new Color(240,255,240));
            }
        }
        
    }
    
    public void randomSudoku(){
        Random r1 = new Random();
        int k;
        for(int i = 0 ; i < 9 ; i++){
            do{
            k = r1.nextInt((9 -1)+1)+1;
            t[i].setText(String.valueOf(k));
            }while(sameNoBox(i, k) || sameNoHorizonatal(i, k) || sameNoVerticle(i, k));
        }
        submit();
        answer();
        for(int i = 0 ; i < 9 ; i++){
             t[i].setEditable(true);
             t[i].setBackground(new Color(240,255,240));
        }
        for(int i = 0;i < 60;i++){
            k = r1.nextInt(81);
            t[k].setText("-");
        }
        submit();
    }
    
    public void errorSound() throws LineUnavailableException, LineUnavailableException, InterruptedException, IOException, UnsupportedAudioFileException {
        
            AudioInputStream ais;
            
            File file = new File(filePath);
           
            ais = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            
            try {
            clip.open(ais);
            }catch (IOException ex) {
            }
            clip.start();
            Thread.sleep(200);
            clip.stop();
    }

    public void openTextFile() throws IOException{
        File info = new File("res/Info.txt");
        info.setReadOnly();
        
        Desktop desktop = Desktop.getDesktop();
       
        if(info.exists()){
            desktop.open(info);
        }
    }
    
}
    