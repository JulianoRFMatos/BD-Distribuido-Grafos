package GrafoBD;

import GrafoBD.Put;
import GrafoBD.Get;
import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

import java.util.HashMap;
import java.util.Map;

public class CopyCatStateMachine extends StateMachine {

    private Map<Integer, Vertice> stateMachineVert;
    private Map<Integer, Aresta> stateMachineArest;

    public CopyCatStateMachine() {
        this.stateMachineVert = new HashMap<Integer, Vertice>(){
            private static final long serialVersionUID = 243496280539242747L;

            @Override
            public Vertice put(Integer key, Vertice value) {
                return super.put(key, value);
            }
        };

        this.stateMachineArest = new HashMap<Integer, Aresta>(){
            private static final long serialVersionUID = 243496280539242748L;

            @Override
            public Aresta put(Integer key, Aresta value) {
                return super.put(key, value);
            }
        };
    }

    public Vertice put(Commit<PutVertice> commit) {
        try {
            return stateMachineVert.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    /*public Aresta put(Commit<Put> commit) {
        try {
            return stateMachineArest.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.close();
        }
    }*/


    public Vertice get(Commit<Get> commit) {
        try {
            return stateMachineVert.get(commit.operation().getKey());
        } finally {
            commit.release();
            //commit.close();
        }
    }

    /*public Aresta getA(Commit<Get> commit) {
        try {
            return stateMachineArest.get(commit.operation().getKey());
        } finally {
            commit.release();
            //commit.close();
        }
    }*/
}
