package net.kibotu.sandbox.network.udp;

import java.io.IOException;
import java.net.*;

public class UdpClient implements Runnable {

    public static void main(String [] args) {
        PingPoingMain.cout("Starting " + UdpClient.class.getSimpleName());
        start();
    }

    private InetAddress ipAddress;
    private DatagramSocket socket;

    public UdpClient(String ipAddress) {
        try {
            socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            byte [] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PingPoingMain.cout("Server > " + new String(packet.getData()).trim());

            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase("pong")) {
                //PingPoingMain.sleep(250);
                sendData("ping".getBytes());
            }
        }
    }

    public void sendData(byte [] data) {
        try {
            socket.send(new DatagramPacket(data, data.length, ipAddress, PingPoingMain.SERVER_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UdpClient start() {
        UdpClient client = new UdpClient(PingPoingMain.SERVER_IP);
        new Thread(client).start();
        return client;
    }
}
