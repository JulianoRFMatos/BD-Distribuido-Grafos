package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;

public class GetVertice implements Query<Vertice> {

    private static final long serialVersionUID = 7083193252544827065L;

    private Integer key;

    public GetVertice(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }
    
    public void setKey(Integer key) {
        this.key = key;
    }
}
