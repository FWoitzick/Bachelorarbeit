package client.tpmconntection;

import factors.Factor;

public class TrustedPlatfromModulConnector {

    public String getLastUsedUsername(){
        return "testuser";
    }

    public void setUsername(){

    }

    public String getHashableForFactor(Factor factor){
        //hier wird der Faktor anhand des Codes im tpm nachgeschlagen und der gespeicherte Wert zurückgegeben

    }
}
