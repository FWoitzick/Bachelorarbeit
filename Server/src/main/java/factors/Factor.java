package factors;

import java.io.Serializable;

public interface Factor extends Serializable {

    public String getAuthString();
    public int getCode();
    public boolean isReady();
    public void loadFactor();
    public Factor getInstance();

}
