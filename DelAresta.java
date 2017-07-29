package GrafoBD;

import GrafoBD.*;

import java.util.*;
import io.atomix.copycat.Command;

public class DelAresta implements Command<Aresta> {

    private static final long serialVersionUID = -7623701325395586198L;

    private List<Integer> key = new ArrayList<>();
    private Aresta value;
    private int porta;

    public DelAresta(int key_1, int key_2, Aresta aresta, int porta) {
        this.key.add(key_1);
        this.key.add(key_2);
        this.value = value;
        this.porta = porta;
    }

    public List<Integer> getKey() {
        return key;
    }

    public void setKey(List<Integer> key) {
        this.key = key;
    }

    public Aresta getValue() {
        return value;
    }

    public void setValue(Aresta value) {
        this.value = value;
    }

    public int getPorta() {
        return porta;
    }
}