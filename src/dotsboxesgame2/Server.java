/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsboxesgame2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8000;
        System.out.println("Listening at port 8000");
        ServerSocket serverSocket = new ServerSocket(port);
        int clientID=1;
        //ExecutorService pool = Executors.newFixedThreadPool(10); 
        while (true) {
            Socket socket = null;
                socket = serverSocket.accept();
                System.out.println("Connection to server accepted");
                System.out.println("A new client is connected: "+socket);
                ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
                System.out.println("Assigning client to a thread");
                System.out.println("Client "+clientID+" has joined the game");
                MultiThread t = new MultiThread(socket, fromClient, toClient, clientID);
                t.start();
                clientID+=1;
        }
        /*while(true){
            System.out.println("Listening at port 8000");
            ServerSocket serversocket = new ServerSocket(8000);
            Socket socket = serversocket.accept();
            System.out.println("Connection to server accepted");
            
            MultiThread mt = new MultiThread(socket);
            mt.start();
            
            mt.sleep(2000);
            socket.close();
        }  */  
    }
    
    
}

