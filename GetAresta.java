package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;
import java.util.*;

public class GetAresta implements Query<Object> {

    private static final long serialVersionUID = 7083193252544827065L;

    private List<Integer> key;

    public GetAresta(List<Integer> key) {
        this.key = key;
    }

    public List<Integer> getKey() {
        return key;
    }

    public void setKey(List<Integer> key) {
        this.key = key;
    }
}
