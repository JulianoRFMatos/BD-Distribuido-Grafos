/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import java.util.*;

/**
 *
 * @author Juliano
 */
public class GrafoHandler implements GrafoBD.Iface {
    private List<Vertice> listaVertices = new ArrayList<Vertice>();
    private List<Aresta> listaArestas = new ArrayList<Aresta>();
    private MenorCaminho shortestPath;
    
    @Override
    public synchronized List<Vertice> getListaVertices() {
        return this.listaVertices;
    }
    
    @Override
    public synchronized List<Aresta> getListaArestas() {
        return this.listaArestas;
    }
    
    @Override
    public synchronized boolean insereVertice(Vertice vertice) {
    	Iterator<Vertice> it = listaVertices.iterator();
    	while(it.hasNext()){
    		if(it.next().getNome() == vertice.getNome()) {
    			return false;
    		}
    	}
    	listaVertices.add(vertice);
    	return true;
    }

    @Override
    public synchronized Vertice buscaVerticeNome(int nome) throws VerticeNotFound {
        for(Vertice v : listaVertices) {
            if(v.getNome() == nome) {
                return v;
            }
        }
        throw new VerticeNotFound("Erro ao buscar vertice informado!");
    }
    
    @Override
    public synchronized void editaVerticeCor(Vertice vertice, int cor) throws VerticeNotFound {
		buscaVerticeNome(vertice.getNome()).setCor(cor);
    }
    
    @Override
    public synchronized void editaVerticeDescr(Vertice vertice, String descricao) throws VerticeNotFound {
    	buscaVerticeNome(vertice.getNome()).setDescricao(descricao);
    }
    
    @Override
    public void editaVerticePeso(Vertice vertice, double peso) throws VerticeNotFound {
    	buscaVerticeNome(vertice.getNome()).setPeso(peso);
    }
    
    @Override
    public synchronized void removeVertice(Vertice vertice) throws ArestaNotFound {
    	Iterator<Aresta> it = listaArestasVertice(vertice).iterator();
    	while(it.hasNext()) {
    		removeAresta(it.next());
    	}
    	listaVertices.remove(vertice);
    }
    
    @Override
    public synchronized boolean insereAresta(Aresta aresta) throws VerticeNotFound {
    	buscaVerticeNome(aresta.getFirstVert());
    	buscaVerticeNome(aresta.getSecondVert());

    	for(Aresta a : listaArestas) {
    		if((a.getFirstVert() == aresta.getFirstVert() && a.getSecondVert() == aresta.getSecondVert())
    			|| (a.getSecondVert() == aresta.getFirstVert() && a.getFirstVert() == aresta.getSecondVert()))
    			return false;
    	}

	    if(aresta.getFirstVert() != aresta.getSecondVert()) {
		    listaArestas.add(aresta);
			return true;
		}
		return false;
    }
    
    @Override
    public synchronized Aresta buscaArestaNome(int nomePrimeiroVert, int nomeSegundoVert) throws ArestaNotFound {
        for(Aresta a : listaArestas) {
            if(a.getFirstVert() == nomePrimeiroVert && a.getSecondVert() == nomeSegundoVert)
                return a;
        }
        throw new ArestaNotFound("Erro ao buscar aresta informada!");
    }
    
    @Override
    public synchronized void editaArestaPeso(Aresta aresta, double peso) throws ArestaNotFound {
		buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setPeso(peso);
    }
    
    @Override
    public synchronized void editaArestaFlag(Aresta aresta, boolean flag) throws ArestaNotFound {
		buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setFlag(flag);
    }
    
    @Override
    public synchronized void editaArestaDescr(Aresta aresta, String descricao) throws ArestaNotFound {
    	buscaArestaNome(aresta.getFirstVert(), aresta.getSecondVert()).setDescricao(descricao);
    }
    
    @Override
    public synchronized void removeAresta(Aresta aresta) throws ArestaNotFound {
    	listaArestas.remove(aresta);
    }
    
    @Override
    public synchronized List<Aresta> listaArestasVertice(Vertice vertice) {
    	List<Aresta> arestasVert = new ArrayList<>();
    	for(Aresta a : listaArestas) {
    		if(a.getFirstVert() == vertice.getNome() || a.getSecondVert() == vertice.getNome())
    			arestasVert.add(a);
    	}
    	return arestasVert;
    }
    
    @Override
    public synchronized List<Vertice> listaVerticesVizinhos(Vertice vertice) throws VerticeNotFound {
    	List<Vertice> verticesVizinhos = new ArrayList<>();
        for(Aresta a : listaArestas) {
            if(a.getFirstVert() == vertice.getNome()) {
            	verticesVizinhos.add(buscaVerticeNome(a.getSecondVert()));
            }
            if(a.getSecondVert() == vertice.getNome()) {
            	verticesVizinhos.add(buscaVerticeNome(a.getFirstVert()));
            }
        }
        return verticesVizinhos;
    }

    @Override
    public synchronized List<Vertice> procuraMenorCaminho(Vertice comeco, Vertice fim) {
    	shortestPath = new MenorCaminho(this);
    	shortestPath.execute(comeco);
    	return shortestPath.getPath(fim);
    }

    @Override
    public synchronized double distanciaPercorrida(Vertice fim) {
    	return shortestPath.getTotalDistance(fim);
    }
}
