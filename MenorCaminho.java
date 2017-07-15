/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Juliano
 */
public class MenorCaminho {
    private final List<Vertice> vertices;
    private final List<Aresta> arestas;
    private Set<Vertice> settledNodes; //vertices por onde ja passou
    private Set<Vertice> unSettledNodes; //vertices que ainda nao foram percorridos
    private Map<Vertice, Vertice> predecessores;
    private Map<Vertice, Double> distancia;

    public MenorCaminho(List<Vertice> lv, List<Aresta> la) {
        // create a copy of the array so that we can operate on this array
        this.vertices = new ArrayList<>(lv);
        this.arestas = new ArrayList<>(la);
    }

    public synchronized void execute(Vertice source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distancia = new HashMap<>();
        predecessores = new HashMap<>();
        distancia.put(source, new Double(0));
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertice node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private synchronized void findMinimalDistances(Vertice node) {
        List<Vertice> adjacentNodes = getNeighbors(node);
        for (Vertice target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distancia.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessores.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private synchronized double getDistance(Vertice node, Vertice target) {
        for (Aresta edge : arestas) {
            if (edge.getFirstVert() == node.getNome() && edge.getSecondVert() == target.getNome()) {
                return edge.getPeso();
            }
            if(edge.isFlag()) {
                if(edge.getSecondVert() == node.getNome() && edge.getFirstVert() == target.getNome())
                    return edge.getPeso();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private synchronized List<Vertice> getNeighbors(Vertice node) {
        List<Vertice> neighbors = new ArrayList<>();

        for (Aresta edge : arestas) {
            if (edge.getFirstVert() == node.getNome() && !isSettled(getVertex(edge.getSecondVert()))) {
                neighbors.add(getVertex(edge.getSecondVert()));
            }
            if(edge.isFlag()) {
                if (edge.getSecondVert() == node.getNome() && !isSettled(getVertex(edge.getFirstVert()))) {
                    neighbors.add(getVertex(edge.getFirstVert()));
                }
            }
        }
        return neighbors;
    }
    
    private synchronized Vertice getVertex(int nomeVertice) {
        for(Vertice vertex : vertices) {
            if(vertex.getNome() == nomeVertice)
                return vertex;
        }
        return null;
    }

    private synchronized Vertice getMinimum(Set<Vertice> vertexes) {
        Vertice minimum = null;
        for (Vertice vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private synchronized boolean isSettled(Vertice vertex) {
        return settledNodes.contains(vertex);
    }

    private synchronized double getShortestDistance(Vertice destination) {
        Double d = distancia.get(destination);
        if (d == null) {
            return Float.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public synchronized LinkedList<Vertice> getPath(Vertice target) {
        LinkedList<Vertice> path = new LinkedList<>();
        Vertice step = target;
        // check if a path exists
        if (predecessores.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessores.get(step) != null) {
            step = predecessores.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    public synchronized double getTotalDistance(Vertice target) {
        return distancia.get(target);
    }
}
