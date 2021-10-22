import authentication.ServerAuthenticator;
import communication.CommicationController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    private int PORT   = 8080;
    private ServerSocket serverSocket = null;
    private boolean isStopped    = false;
    private Thread runningThread= null;
    private ServerAuthenticator authenticator;

    public void run() {
        System.out.println("Starting Server");
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {

            }
            System.out.println("Starting new thread");
            new CommicationController(clientSocket).run();
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
        }
    }
}