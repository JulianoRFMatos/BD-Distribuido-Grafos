package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;

public class GetVertice implements Query<Vertice> {

    private static final long serialVersionUID = 7083193252544827065L;

    private Integer key;
    private int porta;

    public GetVertice(Integer key, int porta) {
        this.key = key;
        this.porta = porta;
    }

    public Integer getKey() {
        return key;
    }
    
    public void setKey(Integer key) {
        this.key = key;
    }

    public int getPorta() {
        return porta;
    }
}
