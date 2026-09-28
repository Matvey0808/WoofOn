package com.example.woofon.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

suspend fun sendMagicPacket(mac: String, broadcast: String, port: Int) {
    withContext(Dispatchers.IO) {
        val macAddress = parseMacAddress(mac)
        val packetByte = ByteArray(6 + 16 * macAddress.size)

        for (i in 0 until 6) {
            packetByte[i] = 0xff.toByte()
        }

        var i = 6
        while (i < packetByte.size) {
            macAddress.copyInto(packetByte, destinationOffset = i)
            i += macAddress.size
        }

        val address = InetAddress.getByName(broadcast)
        val packet = DatagramPacket(packetByte, packetByte.size, address, port)

        DatagramSocket().use { socket ->
            socket.broadcast = true
            socket.send(packet)
        }
    }
}

private fun parseMacAddress(mac: String): ByteArray {
    val hex = mac.replace(":", "").replace("-", "")
    require(hex.length == 12)

    val bytes = ByteArray(6)
    for (i in 0 until 6) {
        bytes[i] = hex.substring(i * 2, i * 2 + 2).toInt(16).toByte()
    }
    return bytes
}