package ch.abertschi.loris;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by abertschi on 19.11.16.
 */
public class TabAttack extends Fragment {

    private Handler mHandler;
    private Thread thread;
    private Handler mPenetrationHanle;
    private Thread mPenetrationThread;

    public static TabAttack newInstance() {
        return new TabAttack();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_attack, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ImageView img = (ImageView) getActivity().findViewById(R.id.face);
        img.setImageResource(R.mipmap.suspicious);

        View abort = view.findViewById(R.id.attack_abort);
        abort.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("button pressed");
                final Handler h = new Handler(Looper.getMainLooper());
                final Runnable r = new Runnable() {
                    public void run() {
                        if (mPenetrationThread != null) {
                            mPenetrationThread.interrupt();
                        }
                        FullscreenActivity.getInstance()
                                .getViewPager().setCurrentItem(0, true);
                    }
                };
                h.postDelayed(r, 500);

                return false;
            }
        });

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {

                TextView text = (TextView) getActivity().findViewById(R.id.txt_attach1);
                if (text == null) {
                    return;
                }
                System.out.println(inputMessage.what);
                switch (inputMessage.what) {
                    case ConnectivityRunnable.SUCCESS:
                        text.setText("Server available");
                        img.setImageResource(R.mipmap.happy);
                        break;
                    case ConnectivityRunnable.TIME_OUT:
                        text.setText("Server not responding " + new String(Character.toChars(0x1F64A)));
                        img.setImageResource(R.mipmap.shocked);
                        break;
                    case ConnectivityRunnable.EXCEPTION:
                        text.setText("Unknown status (" + inputMessage.obj + ")");
                        img.setImageResource(R.mipmap.suspicious);
                        break;
                    default:
                }
            }
        };

        mPenetrationHanle = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {

                TextView text = (TextView) getActivity().findViewById(R.id.txt_attach2);
                if (text == null) {
                    return;
                }
                if (inputMessage.obj != null) {
                    text.setText(inputMessage.obj.toString());
                }
            }
        };

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            if (thread == null) {
                thread = new Thread(new ConnectivityRunnable(mHandler, "192.168.0.248", 80));
                thread.start();
            }

            if (mPenetrationThread == null || mPenetrationThread.isInterrupted()) {
                mPenetrationThread = new Thread(new PenetrationTestRunnable("192.168.0.248", 80, mPenetrationHanle));
                mPenetrationThread.start();
            }
            final ImageView img = (ImageView) getActivity().findViewById(R.id.face);
            Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse_infinite);
            img.startAnimation(pulse);
        }
    }
}
