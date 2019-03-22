/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsboxesgame2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class DotsBoxesGame2 extends Application {
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    Socket socket;
    @Override
    public void start(Stage primaryStage) {
        try{
            Scanner s = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            socket = new Socket(ip,8000);
            System.out.println("Connection request sent");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            while (true) {
                output.writeUTF("Hello World from Client");
                System.out.println("Message sent to server");
                output.flush();
                String message = input.readUTF();
                System.out.println("Server says " + message);
                System.out.println("Do you want to quit: ");
                String choice = s.next();
                output.writeUTF(choice);
                if (choice.equals("YES") || choice.equals("Yes") || choice.equals("yes")) {
                    System.out.println("Closing connection");
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }    
            }
            output.close();            
            input.close();
            socket.close();
        }
        catch(Exception e){
            System.err.println(e.toString());
        }       
        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
