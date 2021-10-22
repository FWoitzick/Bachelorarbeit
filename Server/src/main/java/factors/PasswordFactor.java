package factors;

public class PasswordFactor implements Factor {

    private String password = null;
    public static int code = 1;
    private static PasswordFactor INSTANCE;

    public String getAuthString() {
        return password;
    }

    public int getCode() {
        return code;
    }

    public boolean isReady() {
        return password != null;
    }

    public void loadFactor() {
        password = "TestPassword";
    }

    @Override
    public Factor getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new PasswordFactor();
        }
        return INSTANCE;
    }
}
