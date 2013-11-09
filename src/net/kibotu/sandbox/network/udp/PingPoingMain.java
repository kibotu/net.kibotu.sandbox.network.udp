package net.kibotu.sandbox.network.udp;

public class PingPoingMain {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 1337;

    public static void main(String [] args) {
        cout("Ping Pong");

        UdpServer.start();
        UdpClient.start().sendData("ping".getBytes());
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void cout(String output) {
        System.out.println(output);
    }
}
