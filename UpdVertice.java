package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Command;

public class UpdVertice implements Command<Object> {

    private static final long serialVersionUID = -7623701325395586197L;

    private Integer key;
    private Vertice vertice;
    private Object atributo;

    public UpdVertice(Integer key, Vertice vertice, Object atributo) {
        this.key = key;
        this.vertice = vertice;
        this.atributo = atributo;
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

    public Object getAtributo() {
        return atributo;
    }
}
