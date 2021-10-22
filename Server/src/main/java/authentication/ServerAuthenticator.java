package authentication;

import factors.Factor;
import message.Message;

import java.util.*;

public class ServerAuthenticator {

    private String username;
    //factor code, hash of factor authentication
    private Map<Integer, String> userFactorHashes = new HashMap<>();
    //factor code, tries
    private Map<Integer, Integer> triesPerFactor = new HashMap<>();
    //factor codes
    private Set<Integer> factorsNeeded = new HashSet<>();

    public ServerAuthenticator(){
    }


    public Message handleAuthenticationRequest(Message clientMessage) {
        username = clientMessage.getMessage();
        loadUserFactors();
        System.out.println("HandlingAuthRequest");
        Message message = new Message();
        message.setCode(2);
        message.setMessage(getRequiredFactors());
        return message;
    }

    private void loadUserFactors() {
        userFactorHashes.put(2, "jfkdsljfklsejfkeuwifjfskldmcvsufioef");
        factorsNeeded.add(2);
        userFactorHashes.put(1, "TestPassword");
        factorsNeeded.add(1);
    }

    public int checkFactors(Message message) {
        System.out.println("Checking Factors");
        ArrayList<Factor> factors = message.getFactor();
        for(int i = 0; i<factors.size(); i++){
            //if factor is not already in map, we put it in
            if(!triesPerFactor.containsKey(factors.get(i).getCode())) triesPerFactor.put(factors.get(i).getCode(),0);
            //Get how many tries factor had
            int tries = triesPerFactor.get(factors.get(i).getCode());
            //increment tries
            tries++;
            triesPerFactor.put(factors.get(i).getCode(),tries);
            if(tries > 3){
                System.out.println("Too many tries, aborting");
                return 0;
            }
            //if the factor is correct, we remove it from needed factors
            if(userFactorHashes.get(factors.get(i).getCode()).equals(factors.get(i).getAuthString())){
                factorsNeeded.remove(factors.get(i).getCode());
            }else{
                System.out.println("Got Factor: " + factors.get(i).getAuthString());
                System.out.println("Wanted Factor: " + userFactorHashes.get(factors.get(i).getCode()));
            }

        }
        //factors needed is empty if all factors were correct
        if(factorsNeeded.isEmpty()){
            System.out.println("Authenticated Client");
            return 1;
        }
        System.out.println("Need another round of factors");
        return 2;
    }

    public String getRequiredFactors() {
        String neededFactors = "";
        Iterator iter = factorsNeeded.iterator();
        while (iter.hasNext()){
            neededFactors = neededFactors + iter.next() + ",";
        }
        //remove last ","
        neededFactors.substring(0, neededFactors.length() - 1);
        System.out.println("I want these factors: " + neededFactors);
        return neededFactors;
    }

    public Message getAuthenticationSuccessfulMessage() {
        Message message = new Message();
        message.setCode(3);
        message.setMessage("Logged in as: " + username);
        return message;
    }

    public Message getFailMessage() {
        Message message = new Message();
        message.setCode(0);
        message.setMessage("There was an error during authentication: " + username);
        return message;
    }

    public Message getRequiredFactorMessage() {
        Message message = new Message();
        message.setCode(2);
        message.setMessage(getRequiredFactors());
        return message;
    }
}
