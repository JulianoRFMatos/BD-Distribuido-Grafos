/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GrafoBD;

import GrafoBD.*;

import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author Juliano
 */
public class GrafoServer {
    static final int port = 9090;
    static final int copycat_port = 5000;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Numero servidores: ");
        int nro_servers = 3; //sc.nextInt();

        try {
            for(int i = 0; i < nro_servers; i++){
                int n = i;
                int copycat_port1 = n+i;
                int copycat_port2 = n+i+1;
                GrafoHandlerHS handler = new GrafoHandlerHS(3,n,copycat_port1,copycat_port2);
                GrafoBD.Processor processor = new GrafoBD.Processor(handler);
                
                Runnable connectClient = new Runnable() {
                    public void run() {
                        connectClient(processor,n,copycat_port1,copycat_port2);
                    }
                };

                new Thread(connectClient).start();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void connectClient(GrafoBD.Processor processor, int nro_servers, int copycat_port1, int copycat_port2) {
        try {
            TServerTransport serverTransport = new TServerSocket(port+nro_servers);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                                        .processor(processor));

            int cpport = copycat_port+copycat_port1;
            System.out.println("\nCopycatServer 1 .. "+cpport);
            cpport = copycat_port+copycat_port2;
            System.out.println("CopycatServer 2 .. "+cpport);

            //CopycatServer copycatServer = CopycatServer.builder(new Address("localhost", copycat_port+copycat_port1))
            

            int porta_atual = port+nro_servers;
            System.out.println("Starting up server.. "+porta_atual);
            server.serve();

            CopycatServer copycatServer = CopycatServer.builder(new Address("localhost", port+nro_servers))
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

            //copycatServer.bootstrap().join();
            

            CopycatServer copycatServer2 = CopycatServer.builder(new Address("localhost", port+nro_servers))
                    .withStorage(
                            Storage.builder()
                                    .withStorageLevel(StorageLevel.MEMORY)
                                    .build()
                    )
                    .withStateMachine(CopyCatStateMachine::new)
                    .build();
            
            CompletableFuture<CopycatServer> future1 = copycatServer.bootstrap();
            future1.join();
            
            CompletableFuture<CopycatServer> future2 = copycatServer2.join(new Address("localhost", port+nro_servers));
            future2.join();

            //copycatServer.join(new Address("localhost", port+nro_servers)).join();
            //copycatServer2.join(new Address("localhost", port+nro_servers)).join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
