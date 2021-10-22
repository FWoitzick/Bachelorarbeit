package client;

import client.authentication.Authenticator;
import client.communication.ClientCommunicator;
import factors.Factor;
import message.Message;


import java.util.ArrayList;

public class Client {


    private Authenticator authenticator;
    private ClientCommunicator socket;

    public Client() {

    }

    public static void main(String[] args){
        Authenticator authenticator = new Authenticator();
        ClientCommunicator socket = new ClientCommunicator();
        Message serverAnswer;
        //get username
        String username = authenticator.loadUserName();
        //send client.authentication request to server
        System.out.println("Sending authenticationRequest to server");
        socket.sendAuthenticationRequest(username);
        //receive answer
        serverAnswer = socket.receiveAnswerFromServer();
        System.out.println("got server answer");
        System.out.println("Code: " + serverAnswer.getCode());
        System.out.println("Message: " + serverAnswer.getMessage());
        //handle answer
        while(serverAnswer.getCode() != 3){
            if(serverAnswer.getCode() == 0) ;
            System.out.println("sending requested Factors: ");
            authenticator.handleAuthenticationAnswer(serverAnswer);
            ArrayList<Factor> requestedFactors = authenticator.getRequestedFactors();
            socket.sendFactors(requestedFactors);
            System.out.println("Waiting for server answer");
            serverAnswer = socket.receiveAnswerFromServer();
            System.out.println("Message from Server: " + serverAnswer.getMessage());
        }
    }


    public int authenticateUser() {
        Message serverAnswer;
        //get username
        String username = authenticator.loadUserName();
        //send client.authentication request to server
        socket.sendAuthenticationRequest(username);
        //receive answer
        serverAnswer = socket.receiveAnswerFromServer();
        //handle answer
        while(serverAnswer.getCode() != 3){
            if(serverAnswer.getCode() == 0) return 0;

            authenticator.handleAuthenticationAnswer(serverAnswer);
            ArrayList<Factor> requestedFactors = authenticator.getRequestedFactors();
            socket.sendFactors(requestedFactors);
        }
        return 1;
    }
}
