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
    private TTransport transport = null;
    private TProtocol protocol = null;
    private GrafoBD.Client client = null;

    public CopyCatStateMachine() {
        //start(tport);
        this.stateMachineVert = new HashMap<Integer, Vertice>();
        this.stateMachineArest = new HashMap<List<Integer>, Aresta>();
    }

    public void start(int tport) {
        try{
            TTransport transport = null;
            int porta = tport;
            transport = new TSocket("localhost", porta);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new GrafoBD.Client(protocol);
        }catch(TTransportException t)
        {
        }
    }

    public void setClientPort(int server) throws TException {
        int porta;
        System.out.println("ID Servidor -> "+(server));
        this.transport = new TSocket("localhost", server);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        //return this.protocol;
        this.client = new GrafoBD.Client(protocol);
    }

    public Boolean putVert(Commit<PutVertice> commit) throws TException {
        try {
            setClientPort(commit.operation().getPorta());
            return client.insereVertice(commit.operation().getValue());
            //return stateMachineVert.put(commit.operation().getKey(), commit.operation().getValue());
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


    public Vertice getVert(Commit<GetVertice> commit) throws TException {
        try {
            setClientPort(commit.operation().getPorta());
            return client.buscaVerticeNomeControle(commit.operation().getKey(), false);
            //return stateMachineVert.get(commit.operation().getKey());
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
