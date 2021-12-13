package ru.megains.orangem.client.network

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import ru.megains.orangem.common.PacketProcess
import ru.megains.orangem.client.network.protocol.OrangeMChannelInitializer
import ru.megains.orangem.common.network.NetworkManager

import java.net.InetAddress

object NetworkManagerClient {

    def createNetworkManagerAndConnect( address: InetAddress, serverPort: Int,packetProcess: PacketProcess): NetworkManager = {
        val networkManager: NetworkManager = new NetworkManager(packetProcess)

        val nioEventLoopGroup: NioEventLoopGroup = new NioEventLoopGroup
        networkManager.nioEventLoopGroup = nioEventLoopGroup
        new Bootstrap()
                .group(nioEventLoopGroup)
                .handler(new OrangeMChannelInitializer(networkManager))
                .channel(classOf[NioSocketChannel])
                .connect(address, serverPort)
                .syncUninterruptibly()
        networkManager

    }

}
