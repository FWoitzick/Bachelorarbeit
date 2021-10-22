package client.authentication;

import factors.CookieFactor;
import factors.Factor;
import factors.PasswordFactor;
import message.Message;

import java.util.*;

public class Authenticator {


    private Map<Integer, Factor> possibleFactors = new HashMap<Integer, Factor>();
    private ArrayList<Factor> requestedFactors = new ArrayList<>();




    public Authenticator() {
        CookieFactor cookieFactor = new CookieFactor();
        PasswordFactor passwordFactor = new PasswordFactor();
        possibleFactors.put(cookieFactor.getCode(), cookieFactor.getInstance());
        possibleFactors.put(passwordFactor.getCode(), passwordFactor.getInstance());
    }

    //TODO neues Laden der Faktoren, am besten in der possible Factors map nur den String mappen, und dann
    //in einer methode bei possible factors die strings rein tun nachdem sie neu geladen wurden
    public ArrayList<Factor> getRequestedFactors() {
        for(int i = 0; i<requestedFactors.size(); i++){
            reloadFactorAuthString(requestedFactors.get(i));
            System.out.println(requestedFactors.get(i).getCode());
            System.out.println(requestedFactors.get(i).getAuthString());
        }
        return requestedFactors;

    }

    private String reloadFactorAuthString(Factor factor){
        return factor.getAuthString();
    }

    public String loadUserName() {
        return "testuser";
    }

    public int handleAuthenticationAnswer(Message authenticationAnswer) {
        if (authenticationAnswer.getCode() != 2) return 0;

        String[] authenticationFactorCodes;
        String factorCodeString = authenticationAnswer.getMessage();
        authenticationFactorCodes = factorCodeString.split(",");
        for (int i = 0; i < authenticationFactorCodes.length; i++){
            loadFactorAuthentication(possibleFactors.get(Integer.parseInt(authenticationFactorCodes[i])));
            requestedFactors.add(possibleFactors.get(Integer.parseInt(authenticationFactorCodes[i])));
        }
        return 1;
    }

    private void loadFactorAuthentication(Factor factor) {
        factor.loadFactor();
    }


}
