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

import GrafoBD.Put;
import GrafoBD.Get;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.CompletableFuture;

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
    private final int porta_cc = 5000;
    private int porta_cc1;
    private int porta_cc2;
    private final String msgErroVertice = "Erro ao buscar vertice informado!";
    private final String msgErroAresta = "Erro ao buscar aresta informada!";

    private CopycatClient copycatClient;
    private CopycatClient copycatClient2;
    private CopyCat cct;

    public GrafoHandlerHS(int total_servidores, int serverId, int copycat_port1, int copycat_port2) {    	
    	this.hashVertices = new HashMap<>();
    	this.hashArestas = new HashMap<>();
        this.total_servidores = total_servidores;
    	this.serverId = serverId;
        this.porta_cc1 = porta_cc+copycat_port1;
        this.porta_cc2 = porta_cc+copycat_port2;

        System.out.println("\n aaaaaaaaaaaaaaa \n");

        cct = new CopyCat(porta_cc1, porta_cc2, porta+serverId);

        /*CopycatServer copycatServer = CopycatServer.builder(
                        new Address("localhost", 5000))//copycat_port+copycat_port1))
                    .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                    .withStorage(
                            Storage.builder()
                                    .withStorageLevel(StorageLevel.MEMORY)
                                    .build()
                    )
                    .withStateMachine(CopyCatStateMachine::new)
                    .build();

            copycatServer.bootstrap(new Address("localhost", porta+serverId)).join();

            System.out.println("\n qqqqqqqqqqqqqqqqqq \n");

            CopycatServer copycatServer2 = CopycatServer.builder(
                        new Address("localhost", 5001))//copycat_port+copycat_port2))
                    .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                    .withStorage(
                            Storage.builder()
                                    .withStorageLevel(StorageLevel.MEMORY)
                                    .build()
                    )
                    .withStateMachine(CopyCatStateMachine::new)
                    .build();

            copycatServer2.join(new Address("localhost", 5000)).join();

        CopycatClient copycatClient = CopycatClient.builder()
              .withTransport(NettyTransport.builder()
                .withThreads(4)
                .build())
              .build();

            Collection<Address> cluster = Arrays.asList(
                //new Address("localhost", porta+serverId)
                  new Address("localhost", porta_cc+porta_cc1),
                  new Address("localhost", porta_cc+porta_cc2)
            );

            System.out.println("\n bbbbbbbbbbbbbbbb \n");
            copycatClient.connect(cluster).join();*/

        /*CopycatClient copycatClient = CopycatClient.builder()
          .withTransport(NettyTransport.builder()
            .build())
          .build();

        Collection<Address> cluster = Arrays.asList(
            new Address("localhost", porta+serverId)
              //new Address("localhost", porta_cc+copycat_port1),
              //new Address("localhost", porta_cc+copycat_port2)
        );

        CompletableFuture<CopycatClient> future = copycatClient.connect(cluster);
        future.join();*/


        //copycatClient.connect(new Address("localhost", porta_cc+copycat_port1));

        /*copycatClient = CopycatClient.builder(new Address("localhost", porta_cc+copycat_port1))
                .withConnectionStrategy(ConnectionStrategies.FIBONACCI_BACKOFF) porta_cc+copycat_port1
                .build();*/
        
        /*copycatClient2 = CopycatClient.builder(new Address("localhost", porta_cc+copycat_port2))
                //.withConnectionStrategy(ConnectionStrategies.FIBONACCI_BACKOFF)
                .build();*/

        
        //copycatClient2.connect().join();
    }

    @Override
    public int instanciaCCclient(int val) {
        int p1;
        int p2;
        p1 = porta_cc+porta_cc1;
        p2 = porta_cc+porta_cc2;

        if(val==1)
            return p1;
        else
            return p2;
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

    // INUTILIZADO
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
    public void setInUseFalse() {
    	this.inUse.set(false);
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

                    cct.copycatClient.submit(new PutVertice(vertice.getNome(), vertice)).join();
                    inUse.set(false);
                    System.out.println("\nADICIONOU VERTICE "+vertice.getNome()+"\n");
                    return true;
                }
			}
			inUse.set(false);
        }
    	return false;
    }

    @Override
    public Vertice buscaVerticeNomeControle(int nome, boolean controle) throws VerticeNotFound, TException {
    	if(controle) {
    		return buscaVerticeNome(nome);
    	}
    	else {
    		try {
    			Thread.sleep(250);
	    		if(inUse.compareAndSet(false,true)) {
	    			Vertice v = buscaVerticeNome(nome);

	    			inUse.set(false);
	    			return v;
	    		}
	    	} catch (Exception ie) {
		    	inUse.set(false);
		    	System.out.println(ie.getMessage());
		    }
	    }

	    return null;
    }

    @Override
    public Vertice buscaVerticeNome(int nome) throws VerticeNotFound, TException { 
		if(!verificaId(nome)) {
            getVerticeServer(nome);
            return clientHandler.buscaVerticeNome(nome);
        }
        else {
            if(this.hashVertices.containsKey(nome)){
                System.out.println("VERTICE GET ->> "+cct.copycatClient.submit(new GetVertice(this.hashVertices.get(nome).getNome())).join());
                System.out.println("\nRETORNANDO VERTICE -> "+nome);
                return this.hashVertices.get(nome);
            }
            else
            	throw new VerticeNotFound(msgErroVertice);
    	}
	}	
    
    @Override
    public void editaVerticeCor(Vertice vertice, int cor) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
	            getVerticeServer(vertice);
                clientHandler.editaVerticeCor(vertice,cor);
                inUse.set(false);
	        }
	        else {
	        	buscaVerticeNome(vertice.getNome()).setCor(cor);
                cct.copycatClient.submit(new PutVertice(vertice.getNome(), vertice.setCor(cor))).join();
                inUse.set(false);
	        }
        }
    }
    
    @Override
    public void editaVerticeDescr(Vertice vertice, String descricao) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
        		getVerticeServer(vertice);
            	clientHandler.editaVerticeDescr(vertice,descricao);
            	inUse.set(false);
        	}
            else {
            	buscaVerticeNome(vertice.getNome()).setDescricao(descricao);
                cct.copycatClient.submit(new PutVertice(vertice.getNome(), vertice.setDescricao(descricao))).join();
                inUse.set(false);
            }
        }
    }
    
    @Override
    public void editaVerticePeso(Vertice vertice, double peso) throws VerticeNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(vertice.getNome())) {
				getVerticeServer(vertice);
            	clientHandler.editaVerticePeso(vertice,peso);
            	inUse.set(false);
        	}
            else {
            	buscaVerticeNome(vertice.getNome()).setPeso(peso);
                cct.copycatClient.submit(new PutVertice(vertice.getNome(), vertice.setPeso(peso))).join();
            	inUse.set(false);
            }
        }
    }
    
    @Override
    public void removeVertice(Vertice vertice) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
            if(!verificaId(vertice.getNome())) {
                getVerticeServer(vertice);
                clientHandler.removeVertice(vertice);
                inUse.set(false);
            }
            else {
            	Iterator<Aresta> it = listaArestasVerticeControle(vertice,true).iterator();
            	while(it.hasNext()) {
            		removeArestaControle(it.next(),true);
            	}
                hashVertices.remove(vertice.getNome());
                inUse.set(false);
            }
        }
    }
    
    @Override
    public boolean insereAresta(Aresta aresta) throws VerticeNotFound, TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
    			getVerticeServer(aresta.getFirstVert());
	        	clientHandler.insereAresta(aresta);
	        	inUse.set(false);
	        	return true;
        	}
            else {
            	buscaVerticeNomeControle(aresta.getFirstVert(),true);
            	buscaVerticeNomeControle(aresta.getSecondVert(),true);

            	insereArestaReplica(aresta);
            	List<Integer> arestaKey = new ArrayList<>();
            	arestaKey.add(aresta.getFirstVert());
            	arestaKey.add(aresta.getSecondVert());

            	if(!hashArestas.containsKey(arestaKey)) {
            		hashArestas.put(arestaKey,aresta);
                    cct.copycatClient.submit(new PutAresta(arestaKey, aresta)).join();
                    //cct.copycatClient.submit(new Put(aresta.getFirstVert(), aresta)).join();
            		System.out.println("\nINSERIU ARESTA NO SERVER "+this.serverId+"\n");
                    System.out.println("\naresta..\n"
                        +"aresta 1 = "+aresta.getFirstVert()
                        +"\naresta 2 = "+aresta.getSecondVert()+"\n");
            		inUse.set(false);
            		return true;
            	}
            	else
            		System.out.println("\ninserearesta falhou\n"
            			+"aresta 1 = "+aresta.getFirstVert()
            			+"\naresta 2 = "+aresta.getSecondVert()+"\n");

            	inUse.set(false);           	
            }
        }
		return false;
    }

    @Override
    public boolean insereArestaReplica(Aresta aresta) throws VerticeNotFound, TException {
    	if(!verificaId(aresta.getSecondVert())) {
			getVerticeServer(aresta.getSecondVert());
			clientHandler.insereArestaReplica(aresta);
			return true;
    	}
    	else {
        	List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
        	if(!hashArestas.containsKey(arestaKey)) {
        		hashArestas.put(arestaKey,aresta);
                cct.copycatClient.submit(new PutAresta(arestaKey, aresta)).join();
        		System.out.println("\nINSERIU REPLICA NO SERVER "+this.serverId+"\n");
        		return true;
        	}
        }
        return false;
    }
    
    @Override
    public Aresta buscaArestaNomeControle(int nomePrimeiroVert, int nomeSegundoVert, boolean controle) throws ArestaNotFound,TException {
    	if(controle) {
    		return buscaArestaNome(nomePrimeiroVert,nomeSegundoVert);
    	}
    	else {
    		try {
	    		Aresta a = new Aresta();
	    		Thread.sleep(250);
	    		if(inUse.compareAndSet(false,true)) {
		    		a = buscaArestaNome(nomePrimeiroVert,nomeSegundoVert);
		    		inUse.set(false);
		    		return a;
		    	}
		    } catch (Exception anf) {
		    	inUse.set(false);
		    	System.out.println(anf.getMessage());
		    }
	    }
	    return null;
    }

    @Override
    public Aresta buscaArestaNome(int nomePrimeiroVert, int nomeSegundoVert) throws ArestaNotFound,TException {
    	if(!verificaId(nomePrimeiroVert)) {
    		getVerticeServer(nomePrimeiroVert);
            return clientHandler.buscaArestaNome(nomePrimeiroVert,nomeSegundoVert);
    	}
    	else {
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(nomePrimeiroVert);
        	arestaKey.add(nomeSegundoVert);
    		if(hashArestas.containsKey(arestaKey)) {
                System.out.println("\nARESTA GET -> "+cct.copycatClient.submit(
                    new GetAresta(arestaKey)).join());

    			return hashArestas.get(arestaKey);
    		}
    		else
    			throw new ArestaNotFound(msgErroAresta);
    	}
    }

    @Override
    public void editaArestaPeso(Aresta aresta, double peso) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		clientHandler.editaArestaPeso(aresta,peso);
        		inUse.set(false);
        	}
        	else {
        		Aresta a = buscaArestaNomeControle(aresta.getFirstVert(), aresta.getSecondVert(), true).setPeso(peso);
        		editaArestaReplicaPeso(a,peso);
        		inUse.set(false);
        	}
        }
    }

    @Override
    public void editaArestaReplicaPeso(Aresta aresta, double peso) throws ArestaNotFound,TException {
    	if(!verificaId(aresta.getSecondVert())) {
    		getVerticeServer(aresta.getSecondVert());
    		clientHandler.editaArestaReplicaPeso(aresta,peso);
    	}
    	else {
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
        	hashArestas.replace(arestaKey,aresta);
    	}
    }
    
    @Override
    public void editaArestaFlag(Aresta aresta, boolean flag) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		clientHandler.editaArestaFlag(aresta,flag);
        		inUse.set(false);
        	}
        	else {
        		Aresta a = buscaArestaNomeControle(aresta.getFirstVert(), aresta.getSecondVert(), true).setFlag(flag);
        		editaArestaReplicaFlag(a,flag);
        		inUse.set(false);
        	}
        }
    }

    @Override
    public void editaArestaReplicaFlag(Aresta aresta, boolean flag) throws ArestaNotFound,TException {
    	if(!verificaId(aresta.getSecondVert())) {
    		getVerticeServer(aresta.getSecondVert());
    		clientHandler.editaArestaReplicaFlag(aresta,flag);
    	}
    	else {
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
        	hashArestas.replace(arestaKey,aresta);
    	}
    }
    
    @Override
    public void editaArestaDescr(Aresta aresta, String descricao) throws ArestaNotFound,TException {
        if(inUse.compareAndSet(false,true)) {
        	if(!verificaId(aresta.getFirstVert())) {
        		getVerticeServer(aresta.getFirstVert());
        		clientHandler.editaArestaDescr(aresta,descricao);
        		inUse.set(false);
        	}
        	else {
        		Aresta a = buscaArestaNomeControle(aresta.getFirstVert(), aresta.getSecondVert(), true).setDescricao(descricao);
        		editaArestaReplicaDescr(a,descricao);
        		inUse.set(false);
        	}
        }
    }

    @Override
    public void editaArestaReplicaDescr(Aresta aresta, String descricao) throws ArestaNotFound,TException {
    	if(!verificaId(aresta.getSecondVert())) {
    		getVerticeServer(aresta.getSecondVert());
    		clientHandler.editaArestaReplicaDescr(aresta,descricao);
    	}
    	else {
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
        	hashArestas.replace(arestaKey,aresta);
    	}
    }

    @Override
    public void removeArestaControle(Aresta aresta, boolean controle) throws ArestaNotFound, TException {
    	if(controle)
    		removeAresta(aresta);
    	else {
    		if(inUse.compareAndSet(false,true)) {
    			removeAresta(aresta);
    			inUse.set(false);
    		}
    	}
    }
    
    @Override
    public void removeAresta(Aresta aresta) throws ArestaNotFound, TException {
    	if(!verificaId(aresta.getFirstVert())) {
    		getVerticeServer(aresta.getFirstVert());      		
        	clientHandler.removeAresta(aresta);
    	}
    	else {
    		removeArestaReplica(aresta);
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
    		hashArestas.remove(arestaKey);
    		System.out.println("\nremoveu aresta em sv "+serverId);
    	}
    }

    @Override
    public void removeArestaReplica(Aresta aresta) throws ArestaNotFound, TException {
    	if(!verificaId(aresta.getSecondVert())) {
    		getVerticeServer(aresta.getSecondVert());
        	clientHandler.removeArestaReplica(aresta);
    	}
    	else {
    		List<Integer> arestaKey = new ArrayList<>();
        	arestaKey.add(aresta.getFirstVert());
        	arestaKey.add(aresta.getSecondVert());
    		hashArestas.remove(arestaKey);
    		System.out.println("\nremoveu replica aresta em sv "+serverId);
    	}
    }
    
    @Override
    public List<Aresta> listaArestasVerticeControle(Vertice vertice, boolean passa) throws TException {
    	if(passa)
    		return listaArestasVertice(vertice);
    	else {
    		if(inUse.compareAndSet(false,true)) {
    			List<Aresta> a = listaArestasVertice(vertice);
    			inUse.set(false);
    			return a;
    		}
    	}
    	return null;
    }

    @Override
    public List<Aresta> listaArestasVertice(Vertice vertice) throws TException {
    	if(!verificaId(vertice.getNome())) {
    		getVerticeServer(vertice);
            return clientHandler.listaArestasVertice(vertice);
    	}
    	else {
            List<Aresta> arestasVert = new ArrayList<>();
            for(Aresta a : hashArestas.values()) {
            	if(a.getFirstVert() == vertice.getNome() || a.getSecondVert() == vertice.getNome())
                    arestasVert.add(a);
            }
            return arestasVert;
        }
    }

    @Override
    public synchronized List<Vertice> listaVerticesVizinhos(Vertice vertice) throws VerticeNotFound,TException {
    	List<Vertice> verticesVizinhos = null;
        	verticesVizinhos = new ArrayList<>();
        	if(!verificaId(vertice.getNome())) {
	            getVerticeServer(vertice);
	            return clientHandler.listaVerticesVizinhos(vertice);
	        }
	        else {
	        	for(Aresta a : listaArestasVertice(vertice)) {
	        		System.out.println("\nARESTA "+a.getFirstVert());
	        		if(a.getFirstVert() == vertice.getNome()) {
	                	verticesVizinhos.add(buscaVerticeNome(a.getSecondVert()));
	                }
	                if(a.getSecondVert() == vertice.getNome()) {
	                	verticesVizinhos.add(buscaVerticeNome(a.getFirstVert()));
	                }
	        	}
	        }
        return verticesVizinhos;
    }

    @Override
    public List<Vertice> procuraMenorCaminho(Vertice comeco, Vertice fim) throws TException {
    	if(inUse.compareAndSet(false,true)) {
	    	List<Vertice> lv = new ArrayList<>();
	    	List<Aresta> la = new ArrayList<>();

	    	for(int i = 0; i < this.total_servidores; i++) {
	    		if(!verificaId(i)) {
	    			getVerticeServer(i);
					
					for(Vertice v : clientHandler.getHashVertices().values()) {
		    			lv.add(v);
		            }

		    		for(Aresta a : clientHandler.getHashArestas().values()) {
		    			if(!la.contains(a))
		            		la.add(a);
		            }
	        	}
	        	else {
	        		for(Vertice v : hashVertices.values()) {
		    			lv.add(v);
		            }

		    		for(Aresta a : hashArestas.values()) {
		    			if(!la.contains(a))
		            		la.add(a);
		            }
	        	}
	    	}

	    	List<Vertice> caminho = new ArrayList<>();
	    	shortestPath = new MenorCaminho(lv,la);
	    	shortestPath.execute(comeco);
	    	System.out.println("\nCHAMOU EXECUTE\n");
	        caminho = shortestPath.getPath(fim);
	        inUse.set(false);
	        return caminho;
	    }

    	return null;
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