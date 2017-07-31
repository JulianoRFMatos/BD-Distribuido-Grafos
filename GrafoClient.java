/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.thrift.TException;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;
import io.atomix.copycat.error.ApplicationException;

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
        	
        	Random rand = new Random();
        	int p = 9090+rand.nextInt(9);
            TTransport transport = new TSocket("localhost", p);
            transport.open();

            // CLIENTE PASSA PORTA
            /*
            int porta = 0;
            TTransport transport = null;
            try {
            	System.out.print("Numero porta: ");
	            //porta = sc.nextInt();
	            porta = 9090;
	            transport = new TSocket("localhost", porta);
	            transport.open();
	        } catch (InputMismatchException ime) {
	        	System.out.println("Porta invalida!");
	        }
	        */
            
            TProtocol protocol = new TBinaryProtocol(transport);
            GrafoBD.Client client = new GrafoBD.Client(protocol);

            CopyCat copycat = new CopyCat().criaClient();

            Vertice vertice = null;
            Aresta aresta = null;
            
            List<Integer> arestaKey = new ArrayList<>();
            int nomeVert, nomeVert2, cor;
            double peso;
            String descricao;
            boolean flag;
            boolean continua = true;
            
            // PARA TESTES
			copycat.copycatClient.submit(new PutVertice(0, new Vertice(0,0,"0",0))).join();
			copycat.copycatClient.submit(new PutVertice(1, new Vertice(1,1,"1",1))).join();
			copycat.copycatClient.submit(new PutVertice(2, new Vertice(2,2,"2",2))).join();
			copycat.copycatClient.submit(new PutVertice(3, new Vertice(3,3,"3",3))).join();
			copycat.copycatClient.submit(new PutAresta(1, 0, new Aresta(1,0,10,false,"aresta"))).join();
			copycat.copycatClient.submit(new PutAresta(1, 2, new Aresta(1,2,3,true,"repetiu"))).join();
			copycat.copycatClient.submit(new PutAresta(2, 0, new Aresta(2,0,5,true,"2 para 0"))).join();
			copycat.copycatClient.submit(new PutAresta(2, 3, new Aresta(2,3,1,false,"2 para 3"))).join();
			copycat.copycatClient.submit(new PutAresta(3, 1, new Aresta(3,1,6,false,"3 para 1"))).join();

            do {
            	try {
            		int opcaoMenu = -1;
	                String atributo = "";
	                int opcao = -1;        

	                System.out.print("\n\n\n----------------------------------------"
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

	                                copycat.copycatClient.submit(new PutVertice(nomeVert, new Vertice(nomeVert, cor, descricao, peso))).join();
	                                //if(!client.insereVertice(new Vertice(nomeVert, cor, descricao, peso)))
	                                //	System.out.println("Vertice ja existe");
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
	                                System.out.println("aaaaaaaaaa");
	                                copycat.copycatClient.submit(new PutAresta(nomeVert, nomeVert2, new Aresta(nomeVert, nomeVert2, peso, flag, descricao))).join();	                                
	                                //if(!client.insereAresta(new Aresta(nomeVert, nomeVert2, peso, flag, descricao)))
	                                //	System.out.println("Aresta ja existe/invalida");
	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
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
	                  
	                        		vertice = copycat.copycatClient.submit(new GetVertice(nomeVert)).join();
	                            	if(vertice != null)
	                        			System.out.println(verticeToString(vertice));

	                            } 
	                            else if(opcao == 2) {
	                            	System.out.print("Qual aresta..."
	                            		+"\nVertice 1 e Vertice 2 (x y): ");
	                            	nomeVert = sc.nextInt();
	                            	nomeVert2 = sc.nextInt();

	                            	aresta = copycat.copycatClient.submit(new GetAresta(nomeVert,nomeVert2)).join();
	                        		System.out.println(arestaToString(aresta));

	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (Exception vnt) {
	                        	System.out.println("Valor buscado nao foi encontrado!");
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

	                                vertice = copycat.copycatClient.submit(new GetVertice(nomeVert)).join();

	                                System.out.println(verticeToString(vertice));

	                                System.out.println("\nAtributo a modificar...(cor,descricao,peso)");
	                                atributo = sc.next();
	                                sc.nextLine();

	                                if(atributo.equalsIgnoreCase("cor")) {
	                                	System.out.println("Nova cor (int)");
	                                    cor = sc.nextInt();
	                                    copycat.copycatClient.submit(new UpdVertice(vertice.getNome(),vertice,cor)).join();
	                                }

	                                if(atributo.equalsIgnoreCase("descricao")) {
	                                	System.out.println("Nova descricao (string)");
	                                    descricao = sc.nextLine();
	                                    copycat.copycatClient.submit(new UpdVertice(vertice.getNome(),vertice,descricao)).join();
	                                }

	                                if(atributo.equalsIgnoreCase("peso")) {
	                                	System.out.println("Novo peso (double)");
	                                    peso = sc.nextDouble();
	                                    copycat.copycatClient.submit(new UpdVertice(vertice.getNome(),vertice,peso)).join();
	                                }
	                            } else if(opcao == 2) {
	                                System.out.print("\nQual aresta..."
	                                    +"\nVertice 1 e Vertice 2 (x y): ");
	                                nomeVert = sc.nextInt();
	                                nomeVert2 = sc.nextInt();

	                                aresta = copycat.copycatClient.submit(new GetAresta(nomeVert,nomeVert2)).join();
	                                System.out.println(arestaToString(aresta));
	                                
	                                System.out.println("\nAtributo a modificar...(peso,flag,descricao)");
	                                atributo = sc.next();
	                                sc.nextLine();

	                                if(atributo.equalsIgnoreCase("peso")) {
	                                	System.out.println("Novo peso (double)");
	                                    peso = sc.nextDouble();
	                                    copycat.copycatClient.submit(new UpdAresta(nomeVert,nomeVert2,aresta,peso)).join();
	                                }

	                                if(atributo.equalsIgnoreCase("flag")) {
	                                	System.out.println("Nova flag (bidirecional -> true | direcionado -> false)");
	                                    flag = sc.nextBoolean();
	                                    copycat.copycatClient.submit(new UpdAresta(nomeVert,nomeVert2,aresta,flag)).join();
	                                }

	                                if(atributo.equalsIgnoreCase("descricao")) {
	                                	System.out.println("Nova descricao (string)");
	                                    descricao = sc.nextLine();
	                                    copycat.copycatClient.submit(new UpdAresta(nomeVert,nomeVert2,aresta,descricao)).join();
	                                }
	                            } else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        }  catch (Exception vnt) {
	                        	System.out.println("Valor buscado nao foi encontrado!");
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

	                                vertice = copycat.copycatClient.submit(new GetVertice(nomeVert)).join();
	                                copycat.copycatClient.submit(new DelVertice(vertice.getNome(), vertice)).join();
	                            }
	                            if(opcao == 2) {
	                                System.out.print("\nQual aresta..."
	                                    +"\nVertice 1 e Vertice 2 (x y): ");
	                                nomeVert = sc.nextInt();
	                                nomeVert2 = sc.nextInt();

	                                aresta = copycat.copycatClient.submit(new GetAresta(nomeVert,nomeVert2)).join();
	                                System.out.println("\n"+arestaToString(aresta));
	                                copycat.copycatClient.submit(new DelAresta(aresta.getFirstVert(),aresta.getSecondVert(),aresta)).join();
	                            }
	                            else {
	                                System.out.println("Retornando...");
	                                break;
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        }  catch (Exception vnt) {
	                        	System.out.println(vnt.getMessage());
	                        }

	                        break;
	                       
	                    case 5:
	                        //LISTA VERTICES DE ARESTA
	                    	int count = 1;
	                    	try {
	                    		System.out.print("\n----------------------------------------");
                                
                                System.out.print("\nQual aresta...\nVertice 1 e Vertice 2 (x y): ");
			                    nomeVert = sc.nextInt();
			                    nomeVert2 = sc.nextInt();

			                    if(copycat.copycatClient.submit(new GetAresta(nomeVert,nomeVert2)).join() != null) {
			                    	System.out.println("\nAresta encontrada! Vertices...\n");

									System.out.println("\nVertice 1\n" + verticeToString(copycat.copycatClient.submit(new GetVertice(nomeVert)).join()));
									System.out.println("\nVertice 2\n" + verticeToString(copycat.copycatClient.submit(new GetVertice(nomeVert2)).join()));
								}
								else
									System.out.println("\nlista vertices aresta falhou\n");
	                    	} catch (InputMismatchException ime) {
	                    		System.out.println("Valor digitado incorreto");
	                    		sc.nextLine();
	                    	}  catch (Exception vnt) {
	                        	System.out.println("Valor buscado nao foi encontrado!");
	                        }

	                        break;
	                        
	                    case 6:
	                        //LISTA ARESTAS DE VERTICE
	                        try {
	                            System.out.println("\n----------------------------------------"
	                                +"\nQual vertice...");
	                            nomeVert = sc.nextInt();

	                            Iterator<Aresta> it = client.listaArestasVerticeControle(
	                            						copycat.copycatClient.submit(new GetVertice(nomeVert)).join()
															,false).iterator();

	                            System.out.println("\nArestas do vertice "+nomeVert);
	                            while(it.hasNext()) {
	                            	System.out.println("\n" + arestaToString(it.next()));
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (Exception vnt) {
	                        	System.out.println("Valor buscado nao foi encontrado.");
	                        }

	                        break;
	                        
	                    case 7:
	                        //LISTA VERTICES VIZINHOS DE VERTICE
	                        try {
	                            System.out.println("----------------------------------------"
	                            	+"\nListar vertices vizinhos..\nQual vertice...");
	                            nomeVert = sc.nextInt();

	                            vertice = copycat.copycatClient.submit(new GetVertice(nomeVert)).join();
								Iterator<Vertice> it = client.listaVerticesVizinhos(vertice).iterator();

	                            System.out.println("\nVertices vizinhos ao vertice "+nomeVert);
	                            while(it.hasNext()) {
	                            	System.out.println("\n" + verticeToString(it.next()));
	                            }
	                        } catch (InputMismatchException ime) {
	                            System.out.println("Valor digitado incorreto!");
	                            sc.nextLine();
	                        } catch (Exception e) {
	                        	System.out.println(e.getMessage());
	                        }

	                        break;
	                    
                        case 8:
                        	try {
	                        	System.out.println("----------------------------------------"
	                        		+ "\nBuscar menor caminho..\nSair do vertice...");
	                            nomeVert = sc.nextInt();

	                            System.out.println("\nIr para vertice...");
	                            nomeVert2 = sc.nextInt();

	                            Iterator<Vertice> it = client.procuraMenorCaminho(client.buscaVerticeNomeControle(nomeVert,false),
	                            	client.buscaVerticeNomeControle(nomeVert2,false)).iterator();

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
	                        copycat.getCopycatClient().close();
	                        continua = false;
	                        break;
	                        
	                    default:
	                        System.out.println("Opcao invalida!");
	                }
	            } catch (InputMismatchException ime) {
		            System.out.println("Valor digitado invalido!");
		            sc.nextLine();
		        } catch (TApplicationException tae) {
		            System.out.println("Valor nao encontrado!");
		            sc.nextLine();
		        } catch (ApplicationException ae) {
		            System.out.println(ae.getMessage()+" asdfasdf");
		            client.setInUseFalse();
		            sc.nextLine();
		        }

            } while(continua);
            
            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }
}
