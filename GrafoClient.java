/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import java.util.*;
import org.apache.thrift.TException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

/**
 *
 * @author Juliano
 */
public class GrafoClient {

	static synchronized String verticeToString(Vertice vertice) {
		return "Nome: " + vertice.getNome() + "\nCor: " + vertice.getCor()
			+ "\nDescricao: " + vertice.getDescricao() + "\nPeso "+vertice.getPeso();
	}

	static synchronized String arestaToString(Aresta aresta) {
		return "Vertice 1: " + aresta.getFirstVert() + "\nVertice 2: " + aresta.getSecondVert()
	        + "\npeso: " + aresta.getPeso() + "\nBidirecional: " + aresta.isFlag() + "\nDescricao: " + aresta.getDescricao();
	}

    public static void main(String[] args) throws Exception {
        try {
        	Scanner sc = new Scanner(System.in);

        	// RANDOMIZANDO PORTA
        	/*
        	Random rand = new Random();
            TTransport transport = new TSocket("localhost", 9090+rand.nextInt(3));
            transport.open();
            */

            // CLIENTE PASSA PORTA
            int porta = sc.nextInt();
            TTransport transport = new TSocket("localhost", porta);
            transport.open();
            
            TProtocol protocol = new TBinaryProtocol(transport);
            GrafoBD.Client client = new GrafoBD.Client(protocol);
            
            Vertice vertice;
            Aresta aresta;
            
            int nomeVert, nomeVert2, cor;
            double peso;
            String descricao;
            boolean flag;
            boolean continua = true;
            
            do {
            	try {
            		int opcaoMenu = -1;
	                String atributo = "";
	                int opcao = -1;        
	                
	                System.out.print("\n----------------------------------------"
	                        + "\n1 - Create"
	                        + "\n2 - Read"
	                        + "\n3 - Update"
	                        + "\n4 - Delete"
	                        + "\n5 - Listar vertices de uma aresta"
	                        + "\n6 - Listar aresta de um vertice"
	                        + "\n7 - Listar vertices vizinhos de um vertice"
	                        + "\n8 - Menor caminho entre dois vertices"
	                        + "\n0 - Sair"
	                        + "\nOpcao: ");                
	                opcaoMenu = sc.nextInt();
	                
	                switch(opcaoMenu) {
	                    case 1:
	                        //CREATE
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nCriar:\n1 - Vertice\n2 - Aresta\n0 - Voltar ao menu");
	                            opcao = sc.nextInt();

	                            if(opcao == 1) {
	                                System.out.print("Novo Vertice"
	                                        + "\nnome: ");
	                                nomeVert = sc.nextInt();

	                                System.out.print("cor: ");
	                                cor = sc.nextInt();
	                                sc.nextLine();

	                                System.out.print("descricao: ");
	                                descricao = sc.nextLine();

	                                System.out.print("peso: ");
	                                peso = sc.nextDouble();

	                                if(!client.insereVertice(new Vertice(nomeVert, cor, descricao, peso)))
	                                	System.out.println("Vertice ja existe");
	                            } 
	                            else if(opcao == 2) {
	                                System.out.print("Nova Aresta"
	                                        + "\nvertice 1: ");
	                                nomeVert = sc.nextInt();

	                                System.out.print("vertice 2: ");                 
	                                nomeVert2 = sc.nextInt();

	                                System.out.print("peso: ");
	                                peso = sc.nextDouble();

	                                System.out.print("bidirecional? (true/false): ");
	                                flag = sc.nextBoolean();
	                                sc.nextLine();

	                                System.out.print("descricao: ");
	                                descricao = sc.nextLine();
	                                
	                                if(!client.insereAresta(new Aresta(nomeVert, nomeVert2, peso, flag, descricao)))
	                                	System.out.println("Aresta ja existe/invalida");
	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnf) {
	                        	System.out.println(vnf.errorMsgVertice);
	                        }

	                        break;
	                        
	                    case 2:
	                        //READ
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nRead:\n1 - Vertices\n2 - Arestas\n0 - Voltar ao menu");
	                            opcao = sc.nextInt();
	                            
	                            if(opcao == 1) {
	                            	System.out.println("Nome do vertice... ");
	                            	nomeVert = sc.nextInt();
	                        	
	                            	vertice = client.buscaVerticeNome(nomeVert);
	                        		System.out.println(verticeToString(vertice));

	                            } 
	                            else if(opcao == 2) {
	                            	System.out.print("Qual aresta..."
	                            		+"\nVertice 1 e Vertice 2 (x y): ");
	                            	nomeVert = sc.nextInt();
	                            	nomeVert2 = sc.nextInt();

	                            	aresta = client.buscaArestaNome(nomeVert,nomeVert2);
	                        		System.out.println(arestaToString(aresta));

	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }

	                        break;
	                        
	                    case 3:
	                        //UPDATE
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nUpdate:\n1 - Vertice\n2 - Aresta\n0 - Voltar ao menu");
	                            opcao = sc.nextInt();
	                            
	                            if(opcao == 1) {
	                                System.out.print("\nQual vertice... : ");
	                                nomeVert = sc.nextInt();
	                                vertice = client.buscaVerticeNome(nomeVert);
	                                
	                                System.out.println(verticeToString(vertice));

	                                System.out.println("\nAtributo a modificar...(cor,descricao,peso)");
	                                atributo = sc.next();
	                                sc.nextLine();

	                                if(atributo.equalsIgnoreCase("cor")) {
	                                	System.out.println("Nova cor (int)");
	                                    cor = sc.nextInt();
	                                    client.editaVerticeCor(vertice,cor);
	                                }

	                                if(atributo.equalsIgnoreCase("descricao")) {
	                                	System.out.println("Nova descricao (string)");
	                                    descricao = sc.nextLine();
	                                    client.editaVerticeDescr(vertice,descricao);
	                                }

	                                if(atributo.equalsIgnoreCase("peso")) {
	                                	System.out.println("Novo peso (double)");
	                                    peso = sc.nextDouble();
	                                    client.editaVerticePeso(vertice,peso);
	                                }
	                            } else if(opcao == 2) {
	                                System.out.print("\nQual aresta..."
	                                    +"\nVertice 1 e Vertice 2 (x y): ");
	                                nomeVert = sc.nextInt();
	                                nomeVert2 = sc.nextInt();

	                                aresta = client.buscaArestaNome(nomeVert,nomeVert2);
	                                System.out.println(arestaToString(aresta));
	                                
	                                System.out.println("\nAtributo a modificar...(peso,flag,descricao)");
	                                atributo = sc.next();
	                                sc.nextLine();

	                                if(atributo.equalsIgnoreCase("peso")) {
	                                	System.out.println("Novo peso (double)");
	                                    peso = sc.nextDouble();
	                                    client.editaArestaPeso(aresta,peso);
	                                }

	                                if(atributo.equalsIgnoreCase("flag")) {
	                                	System.out.println("Nova flag (bidirecional -> true | direcionado -> false)");
	                                    flag = sc.nextBoolean();
	                                    client.editaArestaFlag(aresta,flag);
	                                }

	                                if(atributo.equalsIgnoreCase("descricao")) {
	                                	System.out.println("Nova descricao (string)");
	                                    descricao = sc.nextLine();
	                                    client.editaArestaDescr(aresta,descricao);
	                                }
	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }
	                        
	                        break;
	                        
	                    case 4:
	                        //DELETE
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nDeletar:\n1 - Vertice\n2 - Aresta\n0 - Voltar ao menu");
	                            opcao = sc.nextInt();
	                        
	                            if(opcao == 1) {
	                            	System.out.println("\nAo remover um vertice, arestas ligadas a ele tambem serao removidas!");
	                                System.out.println("Qual vertice...");
	                                nomeVert = sc.nextInt();

	                                client.removeVertice(client.buscaVerticeNome(nomeVert));
	                            }
	                            if(opcao == 2) {
	                                System.out.print("\nQual aresta..."
	                                    +"\nVertice 1 e Vertice 2 (x y): ");
	                                nomeVert = sc.nextInt();
	                                nomeVert2 = sc.nextInt();

	                                client.removeAresta(client.buscaArestaNome(nomeVert,nomeVert2));
	                            }
	                            else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }

	                        break;
	                        
	                    case 5:
	                        //LISTA VERTICES DE ARESTA
	                    	int count = 1;
	                    	try {
	                    		System.out.print("\n----------------------------------------");
	                    		if(client.getListaArestas().isEmpty()) {
	                    			System.out.println("Nao foi inserida nenhuma aresta!");
	                    			break;
	                    		}

	                    		for(Aresta a : client.getListaArestas()) {
	                    			System.out.println("Aresta #" + count++ + " -> " + a.getFirstVert()
	                    				+ " | " + a.getSecondVert());
	                    		}
                                
                                System.out.print("\nQual aresta...\nVertice 1 e Vertice 2 (x y): ");
			                    nomeVert = sc.nextInt();
			                    nomeVert2 = sc.nextInt();

			                    if(client.buscaArestaNome(nomeVert,nomeVert2) != null) { 
									System.out.println("\nVertice 1\n" + verticeToString(client.buscaVerticeNome(nomeVert)));
									System.out.println("\nVertice 2\n" + verticeToString(client.buscaVerticeNome(nomeVert2)));
								}
	                    	} catch (InputMismatchException ime) {
	                    		System.out.println("Valor digitado incorreto");
	                    		sc.nextLine();
	                    	} catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }

	                        break;
	                        
	                    case 6:
	                        //LISTA ARESTAS DE VERTICE
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nQual vertice...");
	                            nomeVert = sc.nextInt();

	                            Iterator<Aresta> it = client.listaArestasVertice(client.buscaVerticeNome(nomeVert)).iterator();

	                            System.out.println("\nArestas do vertice "+nomeVert);
	                            while(it.hasNext()) {
	                            	System.out.println("\n" + arestaToString(it.next()));
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }

	                        break;
	                        
	                    case 7:
	                        //LISTA VERTICES VIZINHOS DE VERTICE
	                        try {
	                            System.out.println("----------------------------------------"
	                            	+"\nListar vertices vizinhos..\nQual vertice...");
	                            nomeVert = sc.nextInt();

	                            Iterator<Vertice> it = client.listaVerticesVizinhos(client.buscaVerticeNome(nomeVert)).iterator();

	                            System.out.println("\nVertices vizinhos ao vertice "+nomeVert);
	                            while(it.hasNext()) {
	                            	System.out.println("\n" + verticeToString(it.next()));
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (ArestaNotFound ant) {
	                        	System.out.println(ant.errorMsgAresta);
	                        }

	                        break;

                        case 8:
                        	try {
	                        	System.out.println("----------------------------------------"
	                        		+ "\nBuscar menor caminho..\nSair do vertice...");
	                            nomeVert = sc.nextInt();

	                            System.out.println("\nIr para vertice...");
	                            nomeVert2 = sc.nextInt();

	                            Iterator<Vertice> it = client.procuraMenorCaminho(client.buscaVerticeNome(nomeVert),
	                            	client.buscaVerticeNome(nomeVert2)).iterator();

	                            System.out.println("\nCaminho a percorrer...");
	                            while(it.hasNext()) {
	                            	System.out.print(it.next().getNome() + " ");
	                            }
	                            System.out.println("Distancia.. "+client.distanciaPercorrida(client.buscaVerticeNome(nomeVert2)));
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (VerticeNotFound vnt) {
	                        	System.out.println(vnt.errorMsgVertice);
	                        } catch (TApplicationException tae) {
	                        	System.out.println("Nao existe caminho possivel entre os dois vertices");
	                        }

                        	break;
	                        
	                    case 0:
	                        System.out.println("Saindo....");
	                        continua = false;

	                        break;
	                        
	                    default:
	                        System.out.println("Opcao invalida!");
	                }
	            } catch (InputMismatchException ime) {
		            System.out.println("Valor digitado invalido!");
		            sc.nextLine();
		        }
            } while(continua);
            
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
