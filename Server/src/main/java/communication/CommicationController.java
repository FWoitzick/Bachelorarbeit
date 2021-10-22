package communication;

import authentication.ServerAuthenticator;
import message.Message;

import java.io.*;
import java.net.Socket;

public class CommicationController implements Runnable {

    private int PORT = 8080;
    private Socket clientSocket = null;
    private Message clientMessage;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    ServerAuthenticator authenticator;

    public CommicationController(Socket client) {
        this.clientSocket = client;
        //Set up input and output

        try {
            InputStream inputStream = clientSocket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            OutputStream outputStream = clientSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            authenticator = new ServerAuthenticator();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("Found a Client");
        //receive message from server, blocks if no message was received
        Message messageToClient = null;
        boolean authenticated = false;

        while (!authenticated && !clientSocket.isClosed()) {
            clientMessage = readMessageFromClient();
            if (clientMessage.getCode() == 1) {
                messageToClient = authenticator.handleAuthenticationRequest(clientMessage);
                sendToClient(messageToClient);
            }
            if (clientMessage.getCode() == 2) {
                int status = authenticator.checkFactors(clientMessage);
                if (status == 2) {
                    messageToClient = authenticator.getRequiredFactorMessage();
                    sendToClient(messageToClient);
                }
                if (status == 1) {
                    messageToClient = authenticator.getAuthenticationSuccessfulMessage();
                    sendToClient(messageToClient);
                    authenticated = true;
                }
                if (status == 0) {
                    messageToClient = authenticator.getFailMessage();
                    sendToClient(messageToClient);
                    closeConnection();
                }

            }

        }

    }


    private void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message readMessageFromClient(){
        try {
            return (Message) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendToClient(Message message) {
        System.out.println("Sending: " + message.toString());

        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
