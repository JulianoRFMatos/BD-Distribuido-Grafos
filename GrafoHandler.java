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

/**
 *
 * @author Juliano
 */
public class GrafoHandler implements GrafoBD.Iface {
    private List<Vertice> listaVertices = new ArrayList<Vertice>();
    private List<Aresta> listaArestas = new ArrayList<Aresta>();
    private MenorCaminho shortestPath;
    private AtomicBoolean inUse = new AtomicBoolean(false);
    private GrafoBD.Client clientHandler;
    private int server_num = 0;
    
    public TProtocol setClientPort(int servidor) {
        TTransport temp_transport = new TTransport(localhost, 9090+servidor);
        TProtocol temp_protocol = new TProtocol(temp_transport);
        //GrafoBD.Client temp_client = new GrafoBD.Client(temp_protocol);

        return temp_protocol;
    }

    public void getVerticeServer(int nome_vertice) {
        int vertice_server = nome_vertice%3;
        clientHandler = new GrafoBD.Client(setClientPort(vertice_server));
    }

    public void getVerticeServer(Vertice vertice) {
        int vertice_server = vertice.getNome()%3;
        clientHandler = new GrafoBD.Client(setClientPort(vertice_server));
    }

    @Override
    public List<Vertice> getListaVertices() {
        if(inUse.compareAndSet(false,true)) {
            List<Vertice> lista = this.listaVertices;
            inUse.set(false);
        }
        return lista;
    }
    
    @Override
    public List<Aresta> getListaArestas() {
        if(inUse.compareAndSet(false,true)) {
            List<Aresta> lista = this.listaArestas;
            inUse.set(false);
        }
        return lista;
    }
    
    @Override
    public boolean insereVertice(Vertice vertice) {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            Iterator<Vertice> it = clientHandler.getListaVertices().iterator();

            while(it.hasNext()){
                if(it.next().getNome() == vertice.getNome()) {
                    inUse.set(false);
                    return false;
                }
            }    
            clientHandler.getListaVertices().add(vertice);
            inUse.set(false);
        }
    	return true;
    }

    @Override
    public Vertice buscaVerticeNome(int nome) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(nome);
            Vertice vertice;
            for(Vertice v : clientHandler.getListaVertices()) {
                if(v.getNome() == nome) {
                    vertice = v;
                }
            }
            inUse.set(false);
            return vertice;
        }
        throw new VerticeNotFound("Erro ao buscar vertice informado!");
    }
    
    @Override
    public void editaVerticeCor(Vertice vertice, int cor) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setCor(cor);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaVerticeDescr(Vertice vertice, String descricao) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setDescricao(descricao);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaVerticePeso(Vertice vertice, double peso) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
            clientHandler.buscaVerticeNome(vertice.getNome()).setPeso(peso);
            inUse.set(false);
        }
    }
    
    @Override
    public void removeVertice(Vertice vertice) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
        	Iterator<Aresta> it = clientHandler.listaArestasVertice(vertice).iterator();
        	while(it.hasNext()) {
        		clientHandler.removeAresta(it.next());
        	}
        	clientHandler.listaVertices.remove(vertice);
            inUse.set(false);
        }
    }
    
    @Override
    public boolean insereAresta(Aresta aresta) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
        	clientHandler.buscaVerticeNome(aresta.getFirstVert());
        	clientHandler.buscaVerticeNome(aresta.getSecondVert());

        	for(Aresta a : clientHandler.getListaArestas) {
        		if((a.getFirstVert() == aresta.getFirstVert() && a.getSecondVert() == aresta.getSecondVert())
        			|| (a.getSecondVert() == aresta.getFirstVert() && a.getFirstVert() == aresta.getSecondVert())) {
                    inUse.set(false);
        			return false;
                }
        	}
		    clientHandler.getListaArestas.add(aresta);
            inUse.set(false);
        }
		return false;
    }
    
    @Override
    public Aresta buscaArestaNome(int nomePrimeiroVert, int nomeSegundoVert) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(nomePrimeiroVert);
            Aresta aresta;
            for(Aresta a : clientHandler.getListaArestas) {
                if(a.getFirstVert() == nomePrimeiroVert && a.getSecondVert() == nomeSegundoVert)
                    aresta = a;
            }
            inUse.set(false);
            return aresta;
        }
        throw new ArestaNotFound("Erro ao buscar aresta informada!");
    }
    
    @Override
    public void editaArestaPeso(Aresta aresta, double peso) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setPeso(peso);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaArestaFlag(Aresta aresta, boolean flag) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setFlag(flag);
            inUse.set(false);
        }
    }
    
    @Override
    public void editaArestaDescr(Aresta aresta, String descricao) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setDescricao(descricao);
            inUse.set(false);
        }
    }
    
    @Override
    public void removeAresta(Aresta aresta) throws ArestaNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(aresta.getFirstVert());
            clientHandler.getListaArestas.remove(aresta);
            inUse.set(false);
        }
    }
    
    @Override
    public List<Aresta> listaArestasVertice(Vertice vertice) {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
        	List<Aresta> arestasVert = new ArrayList<>();
        	for(Aresta a : clientHandler.getListaArestas) {
        		if(a.getFirstVert() == vertice.getNome() || a.getSecondVert() == vertice.getNome())
        			arestasVert.add(a);
        	}
            inUse.set(false);
        }
        return arestasVert;
    }
    
    @Override
    public List<Vertice> listaVerticesVizinhos(Vertice vertice) throws VerticeNotFound {
        if(inUse.compareAndSet(false,true)) {
            getVerticeServer(vertice);
        	List<Vertice> verticesVizinhos = new ArrayList<>();
            for(Aresta a : listaArestas) {
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
        if(inUse.compareAndSet(false,true)) {
            List<Vertice> caminho = new ArrayList<>();
        	shortestPath = new MenorCaminho(this);
        	shortestPath.execute(comeco);
            caminho = shortestPath.getPath(fim);
            inUse.set(false);
        }
    	return caminho;
    }

    @Override
    public double distanciaPercorrida(Vertice fim) {
        if(inUse.compareAndSet(false,true)) {
            double distancia = shortestPath.getTotalDistance(fim);
            inUse.set(false);
        }
    	return distancia;
    }
}
