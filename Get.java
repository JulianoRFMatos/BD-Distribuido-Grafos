package GrafoBD;

import io.atomix.copycat.Query;

/**
 * Created by gustavomahlow on 11/07/17.
 */
public class Get implements Query<Integer> {

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
