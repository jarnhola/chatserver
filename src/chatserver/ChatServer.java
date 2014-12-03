/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import message.ChatMessage;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ChatServer {

    static ArrayList<ServerClientBackEnd> clients = new ArrayList();
    
    public static void main(String[] args) {
        try {
            // Käynnistetään serveri kuuntelemaan porttia 3010
            ServerSocket server = new ServerSocket(3010);
            
            // Käynnistetään kuuntelemaan ja odottamaan yhteyksiä****************************************
            while(true){                                                                              //*
                Socket temp = server.accept(); // Luo serveripäähän jokaiselle clientille oman socketin *
                ServerClientBackEnd backEnd = new ServerClientBackEnd(temp);                          //*
                clients.add(backEnd);                                                                 //*
                Thread t = new Thread(backEnd);                                                       //*
                t.setDaemon(true);              // Toissijainen säie, Java Virtual Machine tuhoaa tämän.*
                t.start();                      // Ajetaan run-funktio server-päässä                    *
            }//******************************************************************************************
        } catch (IOException ex) {                                                                    
            ex.printStackTrace();
        }
    }
    
    public static void broadcastMessage(ChatMessage cm){
        
        for(ServerClientBackEnd temp: clients){     // Käydään läpi kaikki clientit
            temp.sendMessage(cm);                   // ja lähetetään niille viesti
        }
        
    }
    
    public static void sendUserList(){
  /*      RequestRegisterUserResponse userList = new RequestRegisterUserResponse();
        for(ServerClientBackEnd temp: clients){
            userList.setName(temp.getName());
        }
        
        for(ServerClientBackEnd temp: clients){
            temp.sendMessage(userList);
        }*/
    }
    
}
