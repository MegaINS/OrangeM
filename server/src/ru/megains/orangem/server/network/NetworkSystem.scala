package ru.megains.orangem.server.network

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.{ChannelFuture, EventLoopGroup}
import ru.megains.orangem.common.network.NetworkManager
import ru.megains.orangem.server.OrangeMGServer
import ru.megains.orangem.server.network.protocol.OrangeMGServerChannelInitializer

import java.net.InetAddress
import scala.collection.mutable.ArrayBuffer

class NetworkSystem(server: OrangeMGServer) {

    var networkServer: ServerBootstrap = _
    var channelFuture: ChannelFuture = _
    val bossExec: EventLoopGroup = new NioEventLoopGroup(0)

    def startLan(address: InetAddress, port: Int): Unit = {


        networkServer = new ServerBootstrap()
                .group(bossExec)
                .localAddress(address, port)
                .channel(classOf[NioServerSocketChannel])
                .childHandler(new OrangeMGServerChannelInitializer(server))
        channelFuture = networkServer.bind.syncUninterruptibly()
       // channelFuture.channel().closeFuture().syncUninterruptibly()

    }

    def stop(): Unit ={
        bossExec.shutdownGracefully()
    }

    def networkTick():Unit = {

        NetworkSystem.networkManagers = NetworkSystem.networkManagers.flatMap(
            networkManager => {

                if (!networkManager.hasNoChannel) {
                    if (networkManager.isChannelOpen) {

                        try {
                            networkManager.processReceivedPackets()
                        } catch {
                            case exception: Exception =>
                                exception.printStackTrace()
                        }
                        Some(networkManager)
                    } else {
                        networkManager.checkDisconnected()
                        None
                    }
                } else {
                    Some(networkManager)
                }

            }

        )

    }


}

object NetworkSystem {
    var networkManagers: ArrayBuffer[NetworkManager] = new ArrayBuffer[NetworkManager]
}
