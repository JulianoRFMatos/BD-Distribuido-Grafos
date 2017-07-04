namespace java GrafoBD

struct Vertice {
	1: required i32 nome,
	2: required i32 cor,
	3: required string descricao,
	4: required double peso
}

struct Aresta {
	1: required i32 firstVert,
	2: required i32 secondVert,
	3: required double peso,
	4: required bool flag,
	5: required string descricao
}

exception VerticeNotFound {
	1: string errorMsgVertice;
}

exception ArestaNotFound {
	1: string errorMsgAresta;
}

service GrafoBD {
	list<Vertice> getListaVertices(),
	list<Aresta> getListaArestas(),
	bool insereVertice(1: Vertice vertice),
	Vertice buscaVerticeNome(1: i32 nome) throws (1: VerticeNotFound err),
	void editaVerticeCor(1: Vertice vertice, 2: i32 cor) throws (1: VerticeNotFound err),
	void editaVerticeDescr(1: Vertice vertice, 2: string descricao) throws (1: VerticeNotFound err),
	void editaVerticePeso(1: Vertice vertice, 2: double peso) throws (1: VerticeNotFound err),
	void removeVertice(1: Vertice vertice) throws (1: ArestaNotFound err),
	bool insereAresta(1: Aresta aresta) throws (1: VerticeNotFound err),
	Aresta buscaArestaNome(1:i32 nomePrimeiroVert, 2:i32 nomeSegundoVert) throws (1: ArestaNotFound err),
	void editaArestaPeso(1: Aresta aresta, 2: double peso) throws (1: ArestaNotFound err),
	void editaArestaFlag(1: Aresta aresta, 2: bool flag) throws (1: ArestaNotFound err),
	void editaArestaDescr(1: Aresta aresta, 2: string descricao) throws (1: ArestaNotFound err),
	void removeAresta(1: Aresta aresta) throws (1: ArestaNotFound err),
	list<Aresta> listaArestasVertice(1: Vertice vertice),
	list<Vertice> listaVerticesVizinhos(1: Vertice vertice) throws (1: VerticeNotFound err),
	list<Vertice> procuraMenorCaminho(1: Vertice comeco, 2: Vertice fim),
	double distanciaPercorrida(1: Vertice fim),
	void addElem(1: Vertice vertice)
}