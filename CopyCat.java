package GrafoBD;

import GrafoBD.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class CopyCat {
     CopycatServer copycatServer_total;
     CopycatServer copycatServer1;
     CopycatServer copycatServer2;
     CopycatServer copycatServer3;

     CopycatClient copycatClient;

     Collection<Address> cluster_total = Arrays.asList(
              new Address("localhost", 5000),
              new Address("localhost", 5001),
              new Address("localhost", 5002)
        );

     public CopyCat() {

        /*Collection<Address> cluster_1 = Arrays.asList(
              new Address("localhost", 9090),
              new Address("localhost", 9093),
              new Address("localhost", 9096)
        );

        Collection<Address> cluster_2 = Arrays.asList(
              new Address("localhost", 9091),
              new Address("localhost", 9094),
              new Address("localhost", 9097)
        );

        Collection<Address> cluster_3 = Arrays.asList(
              new Address("localhost", 9092),
              new Address("localhost", 9095),
              new Address("localhost", 9098)
        );

        copycatServer1 = CopycatServer.builder(
                    new Address("localhost", porta_cc))
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

        copycatServer1.bootstrap().join();
        //copycatServer1.join(cluster_1).join();
        
        System.out.println("copycat 1");
            
         copycatServer2 = CopycatServer.builder(
                    new Address("localhost", porta_cc+1))
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

        //copycatServer2.bootstrap().join();
        //copycatServer2.join(cluster_2).join();
        copycatServer2.join(new Address("localhost", porta_cc)).join();

        System.out.println("copycat 2");

        copycatServer3 = CopycatServer.builder(
                    new Address("localhost", porta_cc+2))
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

        //copycatServer3.bootstrap().join();
        //copycatServer3.join(cluster_3).join();
        copycatServer3.join(new Address("localhost", porta_cc)).join();
        System.out.println("copycat 3");

        copycatClient = CopycatClient.builder()
              .withTransport(NettyTransport.builder()
                .withThreads(4)
                .build())
              .build();

        System.out.println("copycat 4");
        copycatClient.connect(cluster_total).join();
        System.out.println("copycat 5");*/
    }

    public void criaServers() {
        copycatServer1 = CopycatServer.builder(
                    new Address("localhost", 5000))
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

        copycatServer1.bootstrap().join();
        //copycatServer1.join(cluster_1).join();
        
        System.out.println("copycat sv1 criado..");
            
         copycatServer2 = CopycatServer.builder(
                    new Address("localhost", 5001))
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

        //copycatServer2.bootstrap().join();
        //copycatServer2.join(cluster_2).join();
        copycatServer2.join(new Address("localhost", 5000)).join();

        System.out.println("copycat sv2 criado..");

        copycatServer3 = CopycatServer.builder(
                    new Address("localhost", 5002))
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

        //copycatServer3.bootstrap().join();
        //copycatServer3.join(cluster_3).join();
        copycatServer3.join(new Address("localhost", 5000)).join();
        System.out.println("copycat sv3 criado..");
    }

    public CopyCat criaClient() {
        copycatClient = CopycatClient.builder()
              .withTransport(NettyTransport.builder()
                .withThreads(4)
                .build())
              .build();

        copycatClient.connect(cluster_total).join();
        System.out.println("copycatClient criado!");

        return this;
    }

    public CopycatClient getCCclient() {
      return this.copycatClient;
    }
}
