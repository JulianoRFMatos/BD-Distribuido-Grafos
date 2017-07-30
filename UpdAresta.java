package GrafoBD;

import GrafoBD.*;

import java.util.*;
import io.atomix.copycat.Command;

public class UpdAresta implements Command<Object> {

    private static final long serialVersionUID = -7623701325395586197L;

    private List<Integer> key = new ArrayList<>();
    private Aresta aresta;
    private Object atributo;

    public UpdAresta(int key_1, int key_2, Aresta aresta, Object atributo) {
        this.key.add(key_1);
        this.key.add(key_2);
        this.aresta = aresta;
        this.atributo = atributo;
    }

    public List<Integer> getKey() {
        return key;
    }

    public void setKey(List<Integer> key) {
        this.key = key;
    }

    public Aresta getValue() {
        return aresta;
    }

    public void setValue(Aresta value) {
        this.aresta = value;
    }

    public Object getAtributo() {
        return atributo;
    }
}
