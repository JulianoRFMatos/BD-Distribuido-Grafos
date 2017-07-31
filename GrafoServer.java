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
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Juliano
 */
public class GrafoServer {
    static final int port = 9090;
    static final int copycat_port = 5000;
    static AtomicInteger id = new AtomicInteger();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Numero servidores: ");
        //int nro_servers = sc.nextInt();
        int nro_servers = 9;
        CopyCat cc = new CopyCat();
        cc.criaServers();
        try {
            for(int i = 0; i < nro_servers; i++){
                if(i%3==0)
                    id.set(0);
                /*if(i == 3 || i == 6)
                    id.getAndIncrement();*/
                
                int n = i;
                GrafoHandlerHS handler = new GrafoHandlerHS(nro_servers,id.get());
                GrafoBD.Processor processor = new GrafoBD.Processor(handler);
                
                Runnable connectClient = new Runnable() {
                    public void run() {
                        connectClient(processor,n);
                    }
                };

                new Thread(connectClient).start();
                id.getAndIncrement();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void connectClient(GrafoBD.Processor processor, int nro_servers) {
        try {
            TServerTransport serverTransport = new TServerSocket(port+nro_servers);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                                        .processor(processor));

            int porta_atual = port+nro_servers;
            System.out.println("Starting up server.. "+porta_atual);

            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
