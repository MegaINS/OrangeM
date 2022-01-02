package ru.megains.orangem.server.network.protocol

import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.{ChannelInitializer, ChannelOption}
import ru.megains.orangem.common.network.NetworkManager
import ru.megains.orangem.common.network.protocol.{OrangeMCodec, OrangeMMessageCodec}
import ru.megains.orangem.server.OrangeMGServer
import ru.megains.orangem.server.network.NetworkSystem
import ru.megains.orangem.server.network.handler.NetHandlerHandshake

class OrangeMGServerChannelInitializer(server:OrangeMGServer) extends ChannelInitializer[NioSocketChannel]{


    override def initChannel(ch: NioSocketChannel): Unit = {
        val networkManager = new NetworkManager(server)
        ch.pipeline()
                .addLast("serverCodec",new OrangeMCodec)
                .addLast("messageCodec",new OrangeMMessageCodec)
                .addLast("packetHandler", networkManager)
        ch.config.setOption(ChannelOption.TCP_NODELAY, Boolean.box(true))



        networkManager.setNetHandler(new NetHandlerHandshake(server, networkManager))
        NetworkSystem.networkManagers += networkManager
    }
}
