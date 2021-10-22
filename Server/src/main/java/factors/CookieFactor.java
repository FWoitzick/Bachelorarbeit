package factors;

public class CookieFactor implements Factor {


    private String cookieString = null;
    public static int code = 2;
    private static int test = 0;
    private static CookieFactor INSTANCE;

    public String getAuthString() {
        return cookieString;
    }

    public int getCode() {
        return code;
    }

    public boolean isReady() {
        return cookieString != null;
    }


    public void loadFactor() {
        if(test == 1){
            cookieString = "jfkdsljfklsejfkeuwifjfskldmcvsufioef";
        }else if (test == 0){
            cookieString ="djkfksdljflkdsjajweiofuiosdjfjsdiofjoa";
        }else{
            cookieString = "jfkdsljfklsejfkeuwifjfskldmcvsufioef";
        }
        test++;
    }

    @Override
    public Factor getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CookieFactor();
        }
        return INSTANCE;
    }


}
