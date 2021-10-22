import client.ClientController;
import server.ServerController;

public class BAClient {

    public static void main(String args[]){

        ServerController serverController = new ServerController();
        ClientController clientController = new ClientController();
        serverController.start();
        clientController.authenticateUser();

    }

}
