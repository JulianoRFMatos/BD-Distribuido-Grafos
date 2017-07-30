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
import java.util.concurrent.atomic.AtomicInteger;

public class CopyCatStateMachine extends StateMachine {

    private Map<Integer, Vertice> stateMachineVert;
    private Map<List<Integer>, Aresta> stateMachineArest;
    private TTransport transport = null;
    private TProtocol protocol = null;
    private GrafoBD.Client client = null;

    private Vertice vertice = null;
    private Aresta aresta = null;
    private AtomicInteger atom = new AtomicInteger();
    private int server = -1;

    public CopyCatStateMachine() {
        this.stateMachineVert = new HashMap<Integer,Vertice>();
        this.stateMachineArest = new HashMap<List<Integer>,Aresta>();
    }

    public void setClientPort() throws TException {
        
        System.out.println("\nID Servidor -> "+(server)+"\n");
        this.transport = new TSocket("localhost", server);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public void setClientPort(int id) throws TException {
        int atual = 9090+(id%9);
        System.out.println("\nID Servidor -> "+(atual)+"\n");
        this.transport = new TSocket("localhost", atual);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public void setServer(int id) {
        int id_atual = id%3;
        server = -1;
        switch(id_atual) {
            case 0:
                server = 9090;
                break;
            case 1:
                server = 9091;
                break;
            case 2:
                server = 9092;
                break;
        }
    }

    public int getAtom() {
        if(atom.get() == 9) {
            System.out.println("\nRESETOU ATOM\n");
            atom.set(0);
        }
        return atom.addAndGet(3);
    }

    public boolean putVert(Commit<PutVertice> commit) throws TException, VerticeNotFound {
        try {
            setServer(commit.operation().getKey());
            boolean bool = false;
            for(int i = 0; i < 3; i++){
                setClientPort();
                bool = client.insereVertice(commit.operation().getValue());
                server += 3;
            }
            return bool;
        } finally {
            commit.release();
        }
    }

    public boolean putAresta(Commit<PutAresta> commit) throws TException, VerticeNotFound {
        try {
            setServer(commit.operation().getKey().get(0));
            boolean bool = false;
            for(int i = 0; i < 3; i++){
                setClientPort();
                bool = client.insereAresta(commit.operation().getValue());
                server += 3;
            }
            return bool;
        } finally {
            commit.release();
        }
    }


    public Vertice getVert(Commit<GetVertice> commit) throws TException, VerticeNotFound {
        try {
            setClientPort(commit.operation().getKey());
            vertice = client.buscaVerticeNomeControle(commit.operation().getKey(), false);
            if(vertice != null)
                return vertice;
            else
                throw new VerticeNotFound("Vertice nao encontrado!");
        } finally {
            commit.release();
        }
    }

    public Aresta getAresta(Commit<GetAresta> commit) throws TException, ArestaNotFound {
        try {
            setClientPort(commit.operation().getKey().get(0));
            aresta = client.buscaArestaNomeControle(commit.operation().getKey().get(0),
                                                  commit.operation().getKey().get(1),
                                                  false);
            if(aresta != null)
                return aresta;
            else
                throw new ArestaNotFound("Aresta nao encontrada!");
        } finally {
            commit.release();
        }
    }

    public void delVertice(Commit<DelVertice> commit) throws TException, ArestaNotFound {
        try {
            setServer(commit.operation().getKey());
            for(int i = 0; i < 3; i++){
                setClientPort();
                client.removeVertice(commit.operation().getValue());
                server += 3;
            }
        } finally {
            commit.release();
        }
    }

    public void delAresta(Commit<DelAresta> commit) throws TException, ArestaNotFound{
        try {
            setServer(commit.operation().getKey().get(0));
            for(int i = 0; i < 3; i++){
                setClientPort();
                client.removeAresta(commit.operation().getValue());
                server += 3;
            }
        } finally {
            commit.release();
        }
    }
}
