package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;

public class Get implements Query<Object> {

    private static final long serialVersionUID = 7083193252544827065L;

    private Integer key;

    public Get(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
