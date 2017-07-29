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

    public CopyCatStateMachine() {
        this.stateMachineVert = new HashMap<Integer,Vertice>();
        this.stateMachineArest = new HashMap<List<Integer>,Aresta>();
    }

    public void setClientPort(int id, int porta) throws TException {
        int num;
        int server = 9090+id%9;
        Random rand = new Random();
        num = porta+rand.nextInt(server);
        System.out.println("ID Servidor -> "+(server));
        this.transport = new TSocket("localhost", porta);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public int getAtom() {
        if(atom.get() == 3)
            atom.set(0);
        return atom.getAndIncrement();
    }

    public boolean putVert(Commit<PutVertice> commit) throws TException, VerticeNotFound {
        try {
            setClientPort(commit.operation().getKey(), commit.operation().getPorta());
            return client.insereVertice(commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    public boolean putAresta(Commit<PutAresta> commit) throws TException, VerticeNotFound {
        try {
            setClientPort(commit.operation().getKey().get(0), commit.operation().getPorta());
            return client.insereAresta(commit.operation().getValue());
        } finally {
            commit.release();
        }
    }


    public Vertice getVert(Commit<GetVertice> commit) throws TException, VerticeNotFound {
        try {
            setClientPort(commit.operation().getKey(), commit.operation().getPorta());
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
            setClientPort(commit.operation().getKey().get(0), commit.operation().getPorta());
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
            setClientPort(commit.operation().getKey(), commit.operation().getPorta());
            client.removeVertice(commit.operation().getValue());
        } finally {
            commit.release();
        }
    }

    public void delAresta(Commit<DelAresta> commit) throws TException, ArestaNotFound{
        try {
            setClientPort(commit.operation().getKey().get(0), commit.operation().getPorta());
            client.removeAresta(commit.operation().getValue());
        } finally {
            commit.release();
        }
    }
}
