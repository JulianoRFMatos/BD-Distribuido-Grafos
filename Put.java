package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Command;

public class Put implements Command<Object> {

    private static final long serialVersionUID = -7623701325395586197L;

    private Integer key;
    private Vertice vertice;
    private Aresta aresta;

    public Put(Integer key, Vertice vertice) {
        this.key = key;
        this.vertice = vertice;
    }

    public Put(Integer key, Aresta aresta) {
        this.key = key;
        this.aresta = aresta;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Vertice getValue() {
        return vertice;
    }

    public void setValue(Vertice value) {
        this.vertice = value;
    }
}
