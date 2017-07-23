package GrafoBD;

import GrafoBD.PutVertice;
import GrafoBD.PutAresta;
import GrafoBD.GetVertice;
import GrafoBD.GetAresta;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class CopyCatStateMachine extends StateMachine {

    private Map<Integer, Vertice> stateMachineVert;
    private Map<List<Integer>, Aresta> stateMachineArest;

    public CopyCatStateMachine() {
        this.stateMachineVert = new HashMap<Integer, Vertice>() /*{
            private static final long serialVersionUID = 243496280539242747L;

            @Override
            public Vertice put(Integer key, Vertice value) {
                return super.put(key, value);
            }
        }*/;

        this.stateMachineArest = new HashMap<List<Integer>, Aresta>() /*{
            private static final long serialVersionUID = 243496280539242748L;

            @Override
            public Aresta put(List<Integer> key, Aresta value) {
                return super.put(key, value);
            }
        }*/;
    }

    public Vertice putv(Commit<PutVertice> commit) {
        try {
            return stateMachineVert.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    public Aresta puta(Commit<PutAresta> commit) {
        try {
            return stateMachineArest.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.close();
        }
    }


    public Vertice getv(Commit<GetVertice> commit) {
        try {
            return stateMachineVert.get(commit.operation().getKey());
        } finally {
            commit.release();
        }
    }

    public Aresta geta(Commit<GetAresta> commit) {
        try {
            return stateMachineArest.get(commit.operation().getKey());
        } finally {
            commit.release();
        }
    }
}
