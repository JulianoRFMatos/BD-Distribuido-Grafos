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
public class GrafoHandlerHS implements GrafoBD.Iface {
    private HashMap<Integer,Vertice> hashVertices;
    private HashMap<List<Integer>,Aresta> hashArestas;
    private MenorCaminho shortestPath;
    private AtomicBoolean inUse = new AtomicBoolean(false);
    private GrafoBD.Client clientHandler;
    private TTransport temp_transport;
    private TProtocol temp_protocol;
    private int total_servidores;
    private int serverId;
    private final int porta = 9090;

    public GrafoHandlerHS(int total_servidores, int serverId) {    	
    	this.hashVertices = new HashMap<>();
    	this.hashArestas = new HashMap<>();
        this.total_servidores = total_servidores;
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

    public void fechaTransport() {
    	this.temp_transport.close();
        System.out.println("FECHOU TRANSPORT\n");
    }

    public boolean verificaId(int serverId){
    	if(this.serverId == serverId%this.total_servidores)
    		return true;
    	else
    		return false;
    }

    @Override
    public HashMap<Integer,Vertice> getHashVertices() {
        return this.hashVertices;
    }
    
    @Override
    public HashMap<List<Integer>,Aresta> getHashArestas() {
        return this.hashArestas;
    }
    
    @Override
    public boolean insereVertice(Vertice vertice) throws TException {
    	
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
        		getVerticeServer(vertice);
                if(clientHandler.insereVertice(vertice)) {
                    inUse.set(false);
                    return true;
                }
        	}
        	else {
                if(!this.hashVertices.containsKey(vertice.getNome())){
                	this.hashVertices.put(vertice.getNome(),vertice);
                    inUse.set(false);
                    System.out.println("\nadded vertice"+vertice.getNome()+"\n");
                    return true;
                }
			}
        }
        inUse.set(false);
    	return false;
    }

    @Override
    public Vertice buscaVerticeNome(int nome) throws VerticeNotFound, TException {  
    	try{
        	if(inUse.compareAndSet(false,true)) {
        		if(!verificaId(nome)) {
		            getVerticeServer(nome);
                    inUse.set(false);
                    return clientHandler.buscaVerticeNome(nome);
		        }
		        else {
		        	System.out.println("\nentrou buscaVerticeNome\n");
                    if(this.hashVertices.containsKey(nome)){
                        inUse.set(false);
                        System.out.println("\nreturning vertex -> "+nome);
                        return this.hashVertices.get(nome);
                    }
		    	}
		    }
        } catch(VerticeNotFound vnf){

    	} finally{
    		inUse.set(false);
    	}
        
        throw new VerticeNotFound("Erro ao buscar vertice informado!");
    }
    
    @Override
    public void editaVerticeCor(Vertice vertice, int cor) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
	            getVerticeServer(vertice);
                inUse.set(false);
                clientHandler.editaVerticeCor(vertice,cor);
	        }
	        else {
                inUse.set(false);
	        	buscaVerticeNome(vertice.getNome()).setCor(cor);
	        }
        }
    }
    
    @Override
    public void editaVerticeDescr(Vertice vertice, String descricao) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
        		getVerticeServer(vertice);
                inUse.set(false);
            	clientHandler.editaVerticeDescr(vertice,descricao);	
        	}
            else {
                inUse.set(false);
            	buscaVerticeNome(vertice.getNome()).setDescricao(descricao);
            }
        }
    }
    
    @Override
    public void editaVerticePeso(Vertice vertice, double peso) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
				getVerticeServer(vertice);
                inUse.set(false);
            	clientHandler.editaVerticePeso(vertice,peso);
        	}
            else {
                inUse.set(false);
            	buscaVerticeNome(vertice.getNome()).setPeso(peso);
            }
        }
    }
    
    // buscar e getserver em v1 da aresta do v2
    // TERMINAR removeVertice()
    @Override
    public void removeVertice(Vertice vertice) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	System.out.println("\nENTROU REMOVEVERTICE\n");
            if(!verificaId(vertice.getNome())) {
                getVerticeServer(vertice);
                inUse.set(false);
                clientHandler.removeVertice(vertice);
                System.out.println("\nREMOVEU VERTICE "+vertice.getNome()+"\n");
            }
            else {
            	System.out.println("\nVertice == "+vertice.getNome()+"\n");
            	Iterator<Aresta> it = listaArestasVertice(vertice).iterator();
            	while(it.hasNext()) {
            		removeAresta(it.next());
            	}


                //List<Aresta> la = listaArestasVertice(vertice);
                /*if(it.next() == null){
                	System.out.println("\nRETORNOU LISTA NULL\n");
                }
                System.out.println("\nRETORNOU LISTA ARESTAS\nla.get(0) == "+la.get(0));
                if(!la.isEmpty()){
                	System.out.println("\nLISTA NAO TA VAZIA\n");
                    for(Aresta a : la) {
                        removeAresta(a);
                    }
                }
                else
                	System.out.println("\nLISTA VAZIA NO REMOVEVERT\n");*/
                System.out.println("\nREMOVEU ARESTAS\n");
                inUse.set(false);
                hashVertices.remove(vertice.getNome());
            }
            inUse.set(false);
        }
    }
    
    // CONSERTAR VERIFICACAO NA CHAMADA DO INSERE EM getSecondVert()
    @Override
    public boolean insereAresta(Aresta aresta) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		System.out.println("\nADICIONANDO ARESTA 1\n");
    			getVerticeServer(aresta.getFirstVert());
	        	clientHandler.buscaVerticeNome(aresta.getFirstVert());

	        	/*System.out.println("\nENCONTROU PRIMEIRO VERTICE\n");
	        	clientHandler.buscaVerticeNome(aresta.getSecondVert());
	        	System.out.println("\nENCONTROU SEGUNDO VERTICE\n");*/

	    	   	clientHandler.insereArestaReplica(aresta);
	        	System.out.println("\nADICIONou REPLICA\n");
	        	inUse.set(false);
	        	clientHandler.insereAresta(aresta);
	        	return true;
        	}
            else {
            	System.out.println("\nENTROU INSERE ARESTA ELSE\n");

            	//buscaVerticeNome(aresta.getFirstVert());
            	//buscaVerticeNome(aresta.getSecondVert());

            	List<Integer> arestaKey = new ArrayList<>();
            	arestaKey.add(aresta.getFirstVert());
            	arestaKey.add(aresta.getSecondVert());
            	if(!hashArestas.containsKey(arestaKey)) {
            		hashArestas.put(arestaKey,aresta);
            		System.out.println("\nadded in server brah "+this.serverId+"\n");
            		inUse.set(false);
            		return true;
            	}
            }
            inUse.set(false);
        }
		return false;
    }

    @Override
    public boolean insereArestaReplica(Aresta aresta) throws VerticeNotFound, TException {
    	System.out.println("\nEntrou insere replica");
    	if(!verificaId(aresta.getSecondVert())) {
    		System.out.println("\nADICIONANDO ARESTA 2");
			getVerticeServer(aresta.getSecondVert());
			inUse.set(false);
			clientHandler.insereArestaReplica(aresta);
			return true;
    	}
    	else {
    		System.out.println("\nAresta replica else\n");
    		buscaVerticeNome(aresta.getSecondVert());
        	List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
        	if(!hashArestas.containsKey(arestaKey)) {
        		hashArestas.put(arestaKey,aresta);
        		System.out.println("\nadded in server brah "+this.serverId+"\n");
        		inUse.set(false);
        		return true;
        	}
        	inUse.set(false);
        }
        return false;
    }
    
    @Override
    public Aresta buscaArestaNome(int nomePrimeiroVert, int nomeSegundoVert) throws ArestaNotFound,TException {
    	try{
    		if(inUse.compareAndSet(false,true)) {
	        	if(!verificaId(nomePrimeiroVert)) {
	        		getVerticeServer(nomePrimeiroVert);
	        		inUse.set(false);
		            return clientHandler.buscaArestaNome(nomePrimeiroVert,nomeSegundoVert);
	        	}
	        	else {
	        		List<Integer> arestaKey = new ArrayList<>();
	            	arestaKey.add(nomePrimeiroVert);
	            	arestaKey.add(nomeSegundoVert);
	        		if(hashArestas.containsKey(arestaKey)) {
	        			inUse.set(false);
	        			return hashArestas.get(arestaKey);
	        		}
	        	}
			}
    	} catch(ArestaNotFound anf) {

    	} finally {
    		inUse.set(false);
    	}

        throw new ArestaNotFound("Erro ao buscar aresta informada!");
    }

    @Override
    public void editaArestaPeso(Aresta aresta, double peso) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		inUse.set(false);
        		clientHandler.editaArestaPeso(aresta,peso);
        	}
        	else {
        		inUse.set(false);
        		buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setPeso(peso);
        	}
        }
    }
    
    @Override
    public void editaArestaFlag(Aresta aresta, boolean flag) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		inUse.set(false);
        		clientHandler.editaArestaFlag(aresta,flag);
        	}
        	else {
        		inUse.set(false);
        		buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setFlag(flag);
        	}
        }
    }
    
    @Override
    public void editaArestaDescr(Aresta aresta, String descricao) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		inUse.set(false);
        		clientHandler.editaArestaDescr(aresta,descricao);
        	}
        	else {
        		inUse.set(false);
        		buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setDescricao(descricao);
        	}
        }
    }
    
    @Override
    public void removeAresta(Aresta aresta) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	System.out.println("\nENTROU REMOCAO ARESTA BABYYY\n");
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		clientHandler.removeArestaReplica(aresta);
        		inUse.set(false);
            	clientHandler.removeAresta(aresta);
        	}
        	else {
        		List<Integer> arestaKey = new ArrayList<>();
            	arestaKey.add(aresta.getFirstVert());
            	arestaKey.add(aresta.getSecondVert());
        		hashArestas.remove(arestaKey);
        		inUse.set(false);
        	}
        }
    }

    @Override
    public void removeArestaReplica(Aresta aresta) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getSecondVert())) {
        		getVerticeServer(aresta.getSecondVert());
        		inUse.set(false);
            	clientHandler.removeArestaReplica(aresta);
        	}
        	else {
        		List<Integer> arestaKey = new ArrayList<>();
            	arestaKey.add(aresta.getFirstVert());
            	arestaKey.add(aresta.getSecondVert());
        		hashArestas.remove(arestaKey);
        		inUse.set(false);
        	}
        }
    }
    
    @Override
    public List<Aresta> listaArestasVertice(Vertice vertice) throws TException{
        if(inUse.compareAndSet(false,true)) {
        	System.out.println("\nENTROU listaArestasVertice comeco\n");
        	if(!verificaId(vertice.getNome())) {
        		System.out.println("\nENTROU listaArestasVertice if(!..)\n");
        		getVerticeServer(vertice);
                inUse.set(false);
                return clientHandler.listaArestasVertice(vertice);
        	}
        	else {
                List<Aresta> arestasVert = new ArrayList<>();
                System.out.println("\nENTROU listaArestasVertice else\n");
                /*if(hashArestas.containsValue(vertice.getNome())){
                	arestas
                }*/
                
                for(Aresta a : hashArestas.values()) {
                	if(a.getFirstVert() == vertice.getNome() || a.getSecondVert() == vertice.getNome())
                        arestasVert.add(a);
                }

                /*for(Map.Entry<List<Integer>, Aresta> entry : hashArestas.entrySet()) {
                    if(entry.getValue().getFirstVert() == vertice.getNome() || entry.getValue().getSecondVert() == vertice.getNome())
                        arestasVert.add(entry.getValue());
                }*/
                System.out.println("\nINSTANCIOU listaArestasVertice arestasVert\n");
                inUse.set(false);
                return arestasVert;
            }
    	}
        //inUse.set(false);
        return null;
    }

    /*
    @Override
    public List<Vertice> listaVerticesVizinhos(Vertice vertice) throws VerticeNotFound,TException {
    	List<Vertice> verticesVizinhos = null;
        if(inUse.compareAndSet(false,true)) {
        	verticesVizinhos = new ArrayList<>();
        	if(!verificaId(vertice.getNome())) {
	            getVerticeServer(vertice);
	            for(Aresta a : clientHandler.getHashArestas()) {
	                if(a.getFirstVert() == vertice.getNome()) {
	                	verticesVizinhos.add(clientHandler.buscaVerticeNome(a.getSecondVert()));
	                }
	                if(a.getSecondVert() == vertice.getNome()) {
	                	verticesVizinhos.add(clientHandler.buscaVerticeNome(a.getFirstVert()));
	                }
	            }
	        }
	        else {
	        	for(Aresta a : hashArestas) {
	                if(a.getFirstVert() == vertice.getNome()) {
	                	verticesVizinhos.add(buscaVerticeNome(a.getSecondVert()));
	                }
	                if(a.getSecondVert() == vertice.getNome()) {
	                	verticesVizinhos.add(buscaVerticeNome(a.getFirstVert()));
	                }
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
    */
}