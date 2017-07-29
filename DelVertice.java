package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Command;

public class DelVertice implements Command<Vertice> {

    private static final long serialVersionUID = -7623701325395586197L;

    private Integer key;
    private Vertice vertice;
    private int porta;

    public DelVertice(Integer key, Vertice vertice, int porta) {
        this.key = key;
        this.vertice = vertice;
        this.porta = porta;
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

    public int getPorta() {
        return porta;
    }
}