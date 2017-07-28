package GrafoBD;

import GrafoBD.*;

import org.apache.thrift.TException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

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
    private GrafoBD.Client client;

    public CopyCatStateMachine() {
        start();
        this.stateMachineVert = new HashMap<Integer, Vertice>()
        /*{
            private static final long serialVersionUID = 243496280539242747L;

            @Override
            public Vertice put(Integer key, Vertice value) {
                return super.put(key, value);
            }
        }*/
        ;

        this.stateMachineArest = new HashMap<List<Integer>, Aresta>() /*{
            private static final long serialVersionUID = 243496280539242748L;

            @Override
            public Aresta put(List<Integer> key, Aresta value) {
                return super.put(key, value);
            }
        }*/
        ;
    }

    public void start() {
        try{
            TTransport transport = null;
            int porta = 9090;
            transport = new TSocket("localhost", porta);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new GrafoBD.Client(protocol);
        }catch(TTransportException t)
        {
        }
    }

    public Vertice putVert(Commit<PutVertice> commit) throws TException{
        try {
            client.insereVertice(commit.operation().getValue());
            return stateMachineVert.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    public Aresta putAresta(Commit<PutAresta> commit) {
        try {
            return stateMachineArest.put(commit.operation().getKey(), commit.operation().getValue());
        } finally {
            commit.close();
        }
    }


    public Vertice getVert(Commit<GetVertice> commit) {
        try {
            return stateMachineVert.get(commit.operation().getKey());
        } finally {
            commit.release();
        }
    }

    public Aresta getAresta(Commit<GetAresta> commit) {
        try {
            return stateMachineArest.get(commit.operation().getKey());
        } finally {
            commit.release();
        }
    }
}
