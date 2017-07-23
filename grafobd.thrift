namespace java GrafoBD

struct Vertice {
	1: i32 nome,
	2: i32 cor,
	3: string descricao,
	4: double peso,
	5: Pessoa pessoa,
	6: Filme filme
}

struct Aresta {
	1: i32 firstVert,
	2: i32 secondVert,
	3: double peso,
	4: bool flag,
	5: string descricao
}

struct Pessoa {
	1: string nome_pessoa,
	2: list<string> filmes_assistiu
}

struct Filme {
	1: string nome_filme,
	2: string genero,
	3: list<string> cast,
	4: list<string> pessoas_assistiram,
	5: double nota
}

exception VerticeNotFound {
	1: string errorMsgVertice;
}

exception ArestaNotFound {
	1: string errorMsgAresta;
}

service GrafoBD {
	map<i32,Vertice> getHashVertices(),
	map<list<i32>,Aresta> getHashArestas(),
	void setInUseFalse(),
	bool insereVertice(1: Vertice vertice),
	Vertice buscaVerticeNomeControle(1: i32 nome, 2: bool controle) throws (1: VerticeNotFound err),
	Vertice buscaVerticeNome(1: i32 nome) throws (1: VerticeNotFound err),
	void editaVerticeCor(1: Vertice vertice, 2: i32 cor) throws (1: VerticeNotFound err),
	void editaVerticeDescr(1: Vertice vertice, 2: string descricao) throws (1: VerticeNotFound err),
	void editaVerticePeso(1: Vertice vertice, 2: double peso) throws (1: VerticeNotFound err),
	void removeVertice(1: Vertice vertice) throws (1: ArestaNotFound err),
	bool insereAresta(1: Aresta aresta) throws (1: VerticeNotFound err),
	bool insereArestaReplica(1: Aresta aresta),
	Aresta buscaArestaNomeControle(1:i32 nomePrimeiroVert, 2:i32 nomeSegundoVert, 3: bool controle) throws (1: ArestaNotFound err),
	Aresta buscaArestaNome(1:i32 nomePrimeiroVert, 2:i32 nomeSegundoVert) throws (1: ArestaNotFound err),
	void editaArestaPeso(1: Aresta aresta, 2: double peso) throws (1: ArestaNotFound err),
	void editaArestaReplicaPeso(1: Aresta aresta, 2: double peso) throws (1: ArestaNotFound err),
	void editaArestaFlag(1: Aresta aresta, 2: bool flag) throws (1: ArestaNotFound err),
	void editaArestaReplicaFlag(1: Aresta aresta, 2: bool flag) throws (1: ArestaNotFound err),
	void editaArestaDescr(1: Aresta aresta, 2: string descricao) throws (1: ArestaNotFound err),
	void editaArestaReplicaDescr(1: Aresta aresta, 2: string descricao) throws (1: ArestaNotFound err),
	void removeArestaControle(1: Aresta aresta, 2: bool controle) throws (1: ArestaNotFound err),
	void removeAresta(1: Aresta aresta) throws (1: ArestaNotFound err),
	void removeArestaReplica(1: Aresta aresta) throws (1: ArestaNotFound err),
	list<Aresta> listaArestasVerticeControle(1: Vertice vertice, 2: bool controle),
	list<Aresta> listaArestasVertice(1: Vertice vertice),
	list<Vertice> listaVerticesVizinhos(1: Vertice vertice) throws (1: VerticeNotFound err)
}
