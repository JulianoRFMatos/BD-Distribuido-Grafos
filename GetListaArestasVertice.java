package GrafoBD;

import GrafoBD.*;

import io.atomix.copycat.Query;

public class GetListaArestasVertice implements Query<Object> {

    private static final long serialVersionUID = 7083193252544827065L;

    private Vertice vertice;

    public GetListaArestasVertice(Vertice vertice) {
        this.vertice = vertice;
    }

    public Vertice getVertice() {
        return vertice;
    }
    
    public void setVertice(Vertice vertice) {
        this.vertice = vertice;
    }
}
