package ch.abertschi.loris;

import android.os.Handler;
import android.os.Message;

/**
 * Created by abertschi on 20.11.16.
 */

import android.os.Handler;
import android.os.Message;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by abertschi on 19.11.16.
 */
public class ConnectivityRunnable implements Runnable {

    public static final int TIME_OUT = 1;
    public static final int EXCEPTION = -1;
    public static final int SUCCESS = 0;
    public static final int UNKNOWN = 2;

    private Handler handler;
    private String ip;
    private int port;

    public ConnectivityRunnable(Handler handler, String ip, int port) {
        this.handler = handler;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Message m = Message.obtain();

            URL url = null;
            try {
                if (!ip.startsWith("http:") && !ip.startsWith("https:")) {
                    ip = "http://" + ip;
                }
                System.out.println(ip);
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) new URL(ip + ":" + port).openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(1000); //set timeout to 5 seconds

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    m.what = SUCCESS;
                } else {
                    m.what = UNKNOWN;
                }
            } catch (java.net.SocketTimeoutException e) {
                m.what = TIME_OUT;

            } catch (java.io.IOException e) {
                m.what = EXCEPTION;
                e.printStackTrace();
            }

            handler.sendMessage(m);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
