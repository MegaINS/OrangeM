package ru.megains.orangem.client.network

import ru.megains.orangem.client.OrangeMClient
import ru.megains.orangem.client.network.handler.NetHandlerStatusClient
import ru.megains.orangem.client.utils.Logger

import java.net.{InetAddress, UnknownHostException}
import ru.megains.orangem.common.network.{ConnectionState, NetworkManager}
import ru.megains.orangem.common.network.packet.handshake.client.CHandshake
import ru.megains.orangem.common.network.packet.status.client.CPacketServerQuery


class ServerPinger(orangeM: OrangeMClient) extends Logger[ServerPinger] {

    //  private val pingDestinations: util.List[NetworkManager] = Collections.synchronizedList[NetworkManager](Lists.newArrayList[NetworkManager])

    @throws[UnknownHostException]
    def ping(server: ServerData) {
        val serveraddress: ServerAddress = new ServerAddress(server.serverIP, 20000)
        // val networkmanager: NetworkManager = NetworkManager.createLocalClient(LocalAddress.ANY)

        val networkmanager: NetworkManager = NetworkManagerClient.createNetworkManagerAndConnect(InetAddress.getByName(serveraddress.getIP), serveraddress.getPort,orangeM)
        // pingDestinations.add(networkmanager)

        server.serverMOTD = "Pinging..."
        server.pingToServer = -1L
        server.playerList = null
        networkmanager.setNetHandler(new NetHandlerStatusClient(server,networkmanager) )
        try {
            networkmanager.sendPacket(new CHandshake(210, serveraddress.getIP, serveraddress.getPort, ConnectionState.STATUS))
            networkmanager.sendPacket(new CPacketServerQuery)

        } catch {
            case throwable: Throwable =>
                log.error("error",throwable)
                throwable.printStackTrace()
        }
    }
}
