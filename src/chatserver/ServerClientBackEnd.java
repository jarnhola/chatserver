/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import message.ChatMessage;
import message.RegisterUser;

/**
 *
 * @author Ohjelmistokehitys
 */
public class ServerClientBackEnd implements Runnable{
    
    private final Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String name;//Store name here

    public ServerClientBackEnd(Socket sock) {
        socket = sock;
    }
    
    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
                //Start to wait data
            while(socket.isConnected() && !socket.isClosed()){
                Object object = input.readObject();
                if(object instanceof ChatMessage){
                    /*ChatMessage cm = (ChatMessage)object;     // Tähän vois tehdä vaihtoentoisen ratkaisun !!!
                    if(cm.getChatMessage().equals("ThisIsNewUser")){
                        ChatServer.sendUserList();
                    }*/
                    ChatMessage cm = (ChatMessage)object;
                    ChatServer.broadcastMessage(cm);
                }
                else if(object instanceof RegisterUser){
                    RegisterUser user = (RegisterUser)object;
                    name = user.getUserName();
                    ChatServer.sendUserList();
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendMessage(ChatMessage data){
        try {
            output.writeObject(data);
            output.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
