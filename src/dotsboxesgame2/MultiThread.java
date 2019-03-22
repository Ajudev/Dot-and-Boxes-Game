/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsboxesgame2;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class MultiThread extends Thread {
    Socket s;
    ObjectInputStream fromClient;
    ObjectOutputStream toClient;
    int clientID;
    
    public MultiThread(Socket s, ObjectInputStream fromClient, ObjectOutputStream toClient, int clientID) throws IOException{
        this.s = s;
        this.fromClient =fromClient;
        this.toClient = toClient;
        this.clientID = clientID;
    }
    @Override
    public void run(){
        while (true) {
            String choice;
            try {
                
                System.out.println("Connection request from Client "+clientID+" recieved");
                this.toClient.writeUTF("Hello Client "+clientID);
                this.toClient.flush();
                this.toClient.writeUTF("What type of grid do you want to play in? Please write the answer in x and y dimensions seperately");
                this.toClient.flush();
                int x = fromClient.readInt();
                System.out.println(x);
                int y = fromClient.readInt();
                System.out.println(y);
                int[][] grid = new int[x][y];
                System.out.println("Going to create the grid");
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j]=0;
                    }
                } 
                this.toClient.writeObject(grid);
                this.toClient.flush();
                System.out.println("Grid sent to client "+clientID);
                
                choice = this.fromClient.readUTF();
                if(choice.equals("Yes")||choice.equals("yes")||choice.equals("YES")){
                    System.out.println("Client "+clientID+ this.s+" request to exit accepted");
                    System.out.println("Closing connection");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                
            } catch (Exception e) {
                System.err.println(e.toString());
                System.out.println("Nothing inside the message");
                System.out.println("Closing connection");
                try {
                    this.s.close();
                } catch (IOException ex) {
                    Logger.getLogger(MultiThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Goodbye Client "+clientID);
                System.out.println("Connection Closed");
                break;
            }
        }
        try{
            System.out.println("Closing ObjectInput and Output Streams");
            this.fromClient.close();
            this.toClient.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
