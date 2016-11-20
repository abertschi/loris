package ch.abertschi.loris;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by abertschi on 19.11.16.
 */
public class TabAttack extends Fragment {

    private Handler mHandler;
    private Thread thread;

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

        //Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse_infinite);
        //img.startAnimation(pulse);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {

                TextView text = (TextView) getActivity().findViewById(R.id.txt_attack_status);
                System.out.println(inputMessage.what);
                switch (inputMessage.what) {
                    case ConnectivityRunnable.SUCCESS:
                        text.setText("Responding unconditionally");
                        img.setImageResource(R.mipmap.happy);
                        break;
                    case ConnectivityRunnable.TIME_OUT:
                        text.setText("Time out");
                        img.setImageResource(R.mipmap.shocked);
                        break;
                    case ConnectivityRunnable.EXCEPTION:
                        text.setText("Unknown status");
                        img.setImageResource(R.mipmap.suspicious);
                        break;
                    default:
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
            final ImageView img = (ImageView) getActivity().findViewById(R.id.face);
            Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse_infinite);
            img.startAnimation(pulse);
        }
    }
}
