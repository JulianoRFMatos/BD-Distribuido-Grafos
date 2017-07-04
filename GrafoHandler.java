/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import org.apache.thrift.TException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Juliano
 */
public class GrafoHandler implements GrafoBD.Iface {
    private List<Vertice> listaVertices;
    private List<Aresta> listaArestas;
    private MenorCaminho shortestPath;
    private AtomicBoolean inUse = new AtomicBoolean(false);
    private GrafoBD.Client clientHandler;
    private TTransport temp_transport;
    private TProtocol temp_protocol;
    private int total_servidores;
    private int serverId;
    private final int porta = 9090;

    public GrafoHandler(int total_servidores, int serverId) {
    	this.total_servidores = total_servidores;
    	this.listaVertices = new ArrayList<Vertice>();
    	this.listaArestas = new ArrayList<Aresta>();
    	this.serverId = serverId;
    }
    

    public TProtocol setClientPort(int servidor) throws TException {
    	System.out.println("Nro Servidor -> "+(porta+servidor)
    		+"\ntotal_servidores -> "+this.total_servidores);

        this.temp_transport = new TSocket("localhost", porta+servidor);
        this.temp_transport.open();
        this.temp_protocol = new TBinaryProtocol(temp_transport);
        return this.temp_protocol;
    }

    public void getVerticeServer(int nome_vertice) throws TException {
        int vertice_server = nome_vertice%this.total_servidores;
        this.clientHandler = new GrafoBD.Client(setClientPort(vertice_server));
        System.out.println("VERTICE_SERVER -> "+vertice_server);
    }

    public void getVerticeServer(Vertice vertice) throws TException {
        int vertice_server = vertice.getNome()%this.total_servidores;
        this.clientHandler = new GrafoBD.Client(setClientPort(vertice_server));
        System.out.println("VERTICE_SERVER -> "+vertice_server);
    }

    public void fechaClient() {
    	this.temp_transport.close();
    }

    public boolean verificaId(int serverId){
    	if(this.serverId == serverId%this.total_servidores)
    		return true;
    	else
    		return false;
    }

    @Override
    public void addElem(Vertice vertice) {
    	this.listaVertices.add(vertice);
    }

    @Override
    public List<Vertice> getListaVertices() {
    	System.out.println("instanciou lista\n");
        return this.listaVertices;
    }
    
    @Override
    public List<Aresta> getListaArestas() {
        return this.listaArestas;
    }
    
    @Override
    public boolean insereVertice(Vertice vertice) throws TException {
    	Iterator<Vertice> it;
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
        		System.out.println("\nverificaId falha\n");
        		getVerticeServer(vertice);

        		it = clientHandler.getListaVertices().iterator();
	            while(it.hasNext()){
	                if(it.next().getNome() == vertice.getNome()) {
	                    inUse.set(false);
	                    return false;
	                }
	            }
	            
	            clientHandler.addElem(vertice);
	            //if(clientHandler.getListaVertices().add(vertice))
	            	//System.out.println("\nadded\n");
	            //else
	            	System.out.println("\nwho knows wtf happened\n");
	            fechaClient();
        	}
        	else {
        		System.out.println("\nentrou else insert\n");
	            it = this.listaVertices.iterator();
	            while(it.hasNext()){
	                if(it.next().getNome() == vertice.getNome()) {
	                    inUse.set(false);
	                    return false;
	                }
	            }
	            this.listaVertices.add(vertice);
			}
			System.out.println("\nadded "+vertice.getNome()+"\n");
			inUse.set(false);
        }
    	return true;
    }

    @Override
    public Vertice buscaVerticeNome(int nome) throws VerticeNotFound,TException {
    	
        if(inUse.compareAndSet(false,true)) {
			try{
        		if(!verificaId(nome)) {
        			System.out.println("\nverificaId falha\n");
		            getVerticeServer(nome);

		            if(clientHandler.getListaVertices().isEmpty()){
		            	System.out.println("\nlista vazia\n");
		            }
		            else
		            	System.out.println("\nfoi inserido\n");
		            
		            for(Vertice v : clientHandler.getListaVertices()) {
		            	System.out.println("clientHandler nome: "+v.getNome()+"\n");
		                if(v.getNome() == nome) {
		                	inUse.set(false);
		                	System.out.println("\nbusca vai retornar v\n");
		                	fechaClient();
		                    return v;
		                }
		            }
		            System.out.println("\nbusca passou for\n");
		            fechaClient();
		        }
		        else {
		        	System.out.println("\nverifica id passa\n");
		        	for(Vertice v : listaVertices) {
		            	System.out.println("nome: "+v.getNome()+"\n");
		                if(v.getNome() == nome) {
		                	inUse.set(false);
		                    return v;
		                }
		            }
		            System.out.println("\nbusca passou for\n");
		        }
            } catch(TException te){
        		System.out.println("busca === "+te.getMessage());
        	}
        	inUse.set(false);
        }
        throw new VerticeNotFound("Erro ao buscar vertice informado!");
    }
    
    @Override
    public void editaVerticeCor(Vertice vertice, int cor) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setCor(cor);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaVerticeDescr(Vertice vertice, String descricao) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setDescricao(descricao);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaVerticePeso(Vertice vertice, double peso) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setPeso(peso);
            inUse.set(false);
        }
    }
    
    @Override
    public void removeVertice(Vertice vertice) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
        	Iterator<Aresta> it = clientHandler.listaArestasVertice(vertice).iterator();
        	while(it.hasNext()) {
        		clientHandler.removeAresta(it.next());
        	}
        	clientHandler.getListaVertices().remove(vertice);
            inUse.set(false);
        }
    }
    
    @Override
    public boolean insereAresta(Aresta aresta) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
        	clientHandler.buscaVerticeNome(aresta.getFirstVert());
        	clientHandler.buscaVerticeNome(aresta.getSecondVert());

        	for(Aresta a : clientHandler.getListaArestas()) {
        		if((a.getFirstVert() == aresta.getFirstVert() && a.getSecondVert() == aresta.getSecondVert())
        			|| (a.getSecondVert() == aresta.getFirstVert() && a.getFirstVert() == aresta.getSecondVert())) {
                    inUse.set(false);
        			return false;
                }
        	}
		    clientHandler.getListaArestas().add(aresta);
            inUse.set(false);
        }
		return false;
    }
    
    @Override
    public Aresta buscaArestaNome(int nomePrimeiroVert, int nomeSegundoVert) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(nomePrimeiroVert);
            Aresta aresta = new Aresta();
            for(Aresta a : clientHandler.getListaArestas()) {
                if(a.getFirstVert() == nomePrimeiroVert && a.getSecondVert() == nomeSegundoVert)
                    aresta = a;
            }
            inUse.set(false);
            return aresta;
        }
        throw new ArestaNotFound("Erro ao buscar aresta informada!");
    }
    
    @Override
    public void editaArestaPeso(Aresta aresta, double peso) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setPeso(peso);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaArestaFlag(Aresta aresta, boolean flag) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setFlag(flag);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaArestaDescr(Aresta aresta, String descricao) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setDescricao(descricao);
            inUse.set(false);
        }
    }
    
    @Override
    public void removeAresta(Aresta aresta) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.getListaArestas().remove(aresta);
            inUse.set(false);
        }
    }
    
    @Override
    public List<Aresta> listaArestasVertice(Vertice vertice) throws TException{
    	List<Aresta> arestasVert = new ArrayList<>();
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
        	arestasVert = new ArrayList<>();
        	for(Aresta a : clientHandler.getListaArestas()) {
        		if(a.getFirstVert() == vertice.getNome() || a.getSecondVert() == vertice.getNome())
        			arestasVert.add(a);
        	}
            inUse.set(false);
        }
        return arestasVert;
    }
    
    @Override
    public List<Vertice> listaVerticesVizinhos(Vertice vertice) throws VerticeNotFound,TException {
    	List<Vertice> verticesVizinhos = new ArrayList<>();
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            for(Aresta a : clientHandler.getListaArestas()) {
                if(a.getFirstVert() == vertice.getNome()) {
                	verticesVizinhos.add(clientHandler.buscaVerticeNome(a.getSecondVert()));
                }
                if(a.getSecondVert() == vertice.getNome()) {
                	verticesVizinhos.add(clientHandler.buscaVerticeNome(a.getFirstVert()));
                }
            }
            inUse.set(false);
        }
        return verticesVizinhos;
    }

    @Override
    public List<Vertice> procuraMenorCaminho(Vertice comeco, Vertice fim) {
    	List<Vertice> caminho = new ArrayList<>();
        if(inUse.compareAndSet(false,true)) {
        	shortestPath = new MenorCaminho(this);
        	shortestPath.execute(comeco);
            caminho = shortestPath.getPath(fim);
            inUse.set(false);
        }
    	return caminho;
    }

    @Override
    public double distanciaPercorrida(Vertice fim) {
    	double distancia = 0;
        if(inUse.compareAndSet(false,true)) {
            distancia = shortestPath.getTotalDistance(fim);
            inUse.set(false);
        }
    	return distancia;
    }
}
