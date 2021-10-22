package message;

import factors.Factor;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    String message;
    int code;//1 = AuthRequest || 2 = FactorSenden/FactorRequest || 3 = Authentifiziert ||  0 = Fehler/Abgelehnt
    ArrayList<Factor> factors;


    public Message(){
        message = "";
        code = -1;
        factors = null;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Factor> getFactor() {
        return factors;
    }

    public void setFactor(ArrayList<Factor> factor) {
        this.factors = factor;
    }

    public String toString(){
        return "Message: " + message + " Code: " + code;
    }

}
