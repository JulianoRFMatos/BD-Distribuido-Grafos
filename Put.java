package GrafoBD;

import io.atomix.copycat.Command;

/**
 * Created by gustavomahlow on 11/07/17.
 */
public class Put implements Command<String> {

    private static final long serialVersionUID = -7623701325395586197L;

    private Integer key;
    private String value;

    public Put(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
