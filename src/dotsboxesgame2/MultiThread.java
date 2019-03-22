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
            String message;
            try {
                
                //System.out.println("Connection request from Client "+clientID+" recieved");
                message = this.fromClient.readUTF();
                if(message.equals("Yes")||message.equals("yes")||message.equals("YES")){
                    System.out.println("Client "+clientID+ this.s+" request to exit accepted");
                    System.out.println("Closing connection");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

                System.out.println("Client "+clientID+" says " + message);
                this.toClient.writeUTF("Hello Client "+clientID);
                this.toClient.flush();
                System.out.println("Message sent to Client "+clientID);
                
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
