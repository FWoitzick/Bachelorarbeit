package client.communication;

import factors.Factor;
import message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunicator {



    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final String HOST = "127.0.0.1";
    private final int PORT = 8080;



    public ClientCommunicator() {
        try {
            clientSocket = new Socket(HOST, PORT);
            OutputStream outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuthenticationRequest(String username) {
        try {
            Message authenticationRequestMessage = new Message();
            authenticationRequestMessage.setMessage(username);
            authenticationRequestMessage.setCode(1);
            objectOutputStream.writeObject(authenticationRequestMessage);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message receiveAnswerFromServer(){
        Message serverAnswer = new Message();
        try {
            serverAnswer = (Message) objectInputStream.readObject();
            return serverAnswer;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return serverAnswer;
    }


    public void sendFactors(ArrayList<Factor> requestedFactors) {
        Message requestedFactorsMessage = new Message();
        requestedFactorsMessage.setCode(2);
        requestedFactorsMessage.setFactor(requestedFactors);
        try {
            objectOutputStream.writeObject(requestedFactorsMessage);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
