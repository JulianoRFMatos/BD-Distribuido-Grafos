package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Command;

public class PutAresta implements Command<Object> {

    private static final long serialVersionUID = -7623701325395586197L;

    private Integer key;
    private Aresta aresta;

    public PutAresta(Integer key, Aresta aresta) {
        this.key = key;
        this.aresta = aresta;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Aresta getValue() {
        return aresta;
    }

    public void setValue(Aresta value) {
        this.aresta = value;
    }
}
