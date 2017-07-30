package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;
import java.util.*;

public class GetAresta implements Query<Aresta> {

    private static final long serialVersionUID = 7083193252544827065L;

    private List<Integer> key = new ArrayList<>();

    public GetAresta(int key_1, int key_2) {
        this.key.add(key_1);
        this.key.add(key_2);
    }

    public List<Integer> getKey() {
        return key;
    }

    public void setKey(List<Integer> key) {
        this.key = key;
    }
}
