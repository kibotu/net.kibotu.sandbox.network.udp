package net.kibotu.sandbox.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpServer implements Runnable {

    public static void main(String[] args) {
        PingPoingMain.cout("Starting " + UdpServer.class.getSimpleName());
        start();
    }

    private DatagramSocket socket;

    public UdpServer() {
        try {
            socket = new DatagramSocket(PingPoingMain.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PingPoingMain.cout("Client [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> " + new String(packet.getData()).trim());
            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase("ping")) {
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        try {
            socket.send(new DatagramPacket(data, data.length, ipAddress, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UdpServer start() {
        UdpServer server = new UdpServer();
        new Thread(server).start();
        return server;
    }
}
