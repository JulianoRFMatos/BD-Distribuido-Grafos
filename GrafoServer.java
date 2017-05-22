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

/**
 *
 * @author Juliano
 */
public class GrafoServer {
    public static void main(String[] args) {
        try {
            GrafoHandler handler = new GrafoHandler();
            GrafoBD.Processor processor = new GrafoBD.Processor(handler);
            
            Runnable connectClient = new Runnable() {
                public void run() {
                    connectClient(processor);
                }
            };

            new Thread(connectClient).start();
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void connectClient(GrafoBD.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                                        .processor(processor));
            System.out.println("Starting up..");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
