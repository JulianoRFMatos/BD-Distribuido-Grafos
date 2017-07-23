package GrafoBD;

import GrafoBD.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import GrafoBD.Put;
import GrafoBD.Get;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.ConnectionStrategies;
import io.atomix.copycat.client.CopycatClient;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

public class CopyCat {
     CopycatServer copycatServer;
     CopycatServer copycatServer2;

     CopycatClient copycatClient;

     public CopyCat(int copycat_port1, int copycat_port2, int server) {

        CopycatServer copycatServer = CopycatServer.builder(
                    new Address("localhost", copycat_port1))
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

        copycatServer.bootstrap(new Address("localhost", copycat_port1)).join();
        //copycatServer.bootstrap(new Address("localhost", server)).join();
            
        CopycatServer copycatServer2 = CopycatServer.builder(
                    new Address("localhost", copycat_port2))
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

        copycatServer2.join(new Address("localhost", copycat_port1)).join();

        copycatClient = CopycatClient.builder()
              .withTransport(NettyTransport.builder()
                .withThreads(4)
                .build())
              .build();

        Collection<Address> cluster = Arrays.asList(
              new Address("localhost", copycat_port1),
              new Address("localhost", copycat_port2)
        );

        copycatClient.connect(cluster).join();
    }
}
