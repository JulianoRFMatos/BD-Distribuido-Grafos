package GrafoBD;

import GrafoBD.*;

import org.apache.thrift.TException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.error.ApplicationException;

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
    private int server = 9090;

    public CopyCatStateMachine() {

    }

    public void setClientPort() throws TException {
        
        System.out.println("\nCopycat -> ID Servidor -> "+server+"\n");
        this.transport = new TSocket("localhost", server);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public void setClientPort(int porta, boolean b) throws TException {
        
        System.out.println("\nCopycat -> ID Servidor -> "+porta+"\n");
        this.transport = new TSocket("localhost", porta);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public void setClientPort(int id) throws TException {

        int atual = 9090+(id%3);
        System.out.println("\nCopycat -> ID Servidor -> "+atual+"\n");
        this.transport = new TSocket("localhost", atual);
        this.transport.open();
        this.protocol = new TBinaryProtocol(transport);
        this.client = new GrafoBD.Client(protocol);
    }

    public void setServer(int id) {
        int id_atual = id%3;
        server = 0;
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
        return atom.getAndAdd(3);
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
            int serverV1 = server;
            boolean bool = false;
            for(int i = 0; i < 3; i++){
                setClientPort(serverV1, true);
                bool = client.insereAresta(commit.operation().getValue());

                setServer(commit.operation().getKey().get(1));
                server += getAtom();
                setClientPort();
                client.insereArestaReplica(commit.operation().getValue());

                serverV1 += 3;
            }
            return bool;
        } finally {
            commit.release();
        }
    }

    public Vertice getVert(Commit<GetVertice> commit) throws TException, VerticeNotFound {
        try {
            setClientPort();
            vertice = client.buscaVerticeNomeControle(commit.operation().getKey(), false);
            if(vertice != null)
                return vertice;
            else
                throw new VerticeNotFound("Vertice nao encontrado!");
        } finally {
            commit.release();
            server = 9090;
        }
    }

    public Aresta getAresta(Commit<GetAresta> commit) throws TException, ArestaNotFound {
        try {
            setClientPort();
            aresta = client.buscaArestaNomeControle(commit.operation().getKey().get(0),
                                                  commit.operation().getKey().get(1),
                                                  false);
            if(aresta != null)
                return aresta;
            else
                throw new ArestaNotFound("Aresta nao encontrada!");
        } finally {
            commit.release();
            server = 9090;
        }
    }

    public void updVert(Commit<UpdVertice> commit) throws TException, VerticeNotFound {
        try {
            setServer(commit.operation().getKey());
            for(int i = 0; i < 3; i++){
                setClientPort();
                if(commit.operation().getAtributo() instanceof Integer)
                	client.editaVerticeCor(commit.operation().getValue(), (int)commit.operation().getAtributo());
                if(commit.operation().getAtributo() instanceof String)
                	client.editaVerticeDescr(commit.operation().getValue(), (String)commit.operation().getAtributo());
                if(commit.operation().getAtributo() instanceof Double)
                	client.editaVerticePeso(commit.operation().getValue(), (double)commit.operation().getAtributo());
                server += 3;
            }
        } finally {
            commit.release();
            server = 9090;
        }
    }

	public void updAresta(Commit<UpdAresta> commit) throws TException, VerticeNotFound {
        try {
            setServer(commit.operation().getKey().get(0));
            for(int i = 0; i < 3; i++){
                setClientPort();
                if(commit.operation().getAtributo() instanceof Boolean)
                	client.editaArestaFlag(commit.operation().getValue(), (Boolean)commit.operation().getAtributo());
                if(commit.operation().getAtributo() instanceof String)
                	client.editaArestaDescr(commit.operation().getValue(), (String)commit.operation().getAtributo());
                if(commit.operation().getAtributo() instanceof Double)
                	client.editaArestaPeso(commit.operation().getValue(), (double)commit.operation().getAtributo());
                server += 3;
            }
        } finally {
            commit.release();
            server = 9090;
        }
    }

    public void delVertice(Commit<DelVertice> commit) throws TException, ArestaNotFound {
        try {
            for(int i = 0; i < 3; i++){
                setClientPort(commit.operation().getKey()+getAtom());
                client.removeVertice(commit.operation().getValue());
            }
        } finally {
            commit.release();
            server = 9090;
        }
    }

    public void delAresta(Commit<DelAresta> commit) throws TException, ArestaNotFound {
        try {
            setServer(commit.operation().getKey().get(0));
            int serverV1 = server;
            for(int i = 0; i < 3; i++){
                setClientPort(serverV1,true);
                client.removeArestaControle(commit.operation().getValue(),false);
                
                setServer(commit.operation().getKey().get(1));
                server += getAtom();
                setClientPort();
                client.removeArestaReplica(commit.operation().getValue());

                serverV1 += 3;
            }
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        } finally {
            commit.release();
            server = 9090;
        }
    }
}
