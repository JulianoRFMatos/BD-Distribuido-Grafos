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

import java.util.*;

/**
 *
 * @author Juliano
 */
public class GrafoServer {
    static final int port = 9090;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Numero servidores: ");
        int nro_servers = 3;//sc.nextInt();
        try {
            for(int i = 0; i < nro_servers; i++){
                int n = i;
                GrafoHandlerHS handler = new GrafoHandlerHS(nro_servers,n);
                GrafoBD.Processor processor = new GrafoBD.Processor(handler);
                
                Runnable connectClient = new Runnable() {
                    public void run() {
                        connectClient(processor,n);
                    }
                };

                new Thread(connectClient).start();
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
            int porta = port+nro_servers;
            //int port = nro_servers;
            System.out.println("Starting up server.. "+porta);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
