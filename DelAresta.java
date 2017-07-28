package GrafoBD;

import GrafoBD.*;

import java.util.*;
import io.atomix.copycat.Command;

public class DelAresta implements Command<Object> {

    private static final long serialVersionUID = -7623701325395586198L;

    private List<Integer> key;
    private Aresta value;

    public DelAresta(List<Integer> key, Aresta value) {
        this.key = key;
        this.value = value;
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
}