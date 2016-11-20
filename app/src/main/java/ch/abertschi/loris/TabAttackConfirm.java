package ch.abertschi.loris;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by abertschi on 19.11.16.
 */
public class TabAttackConfirm extends Fragment {

    public static TabAttackConfirm newInstance() {
        return new TabAttackConfirm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_confirm, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ImageView img = (ImageView) getActivity().findViewById(R.id.face);
            img.setImageResource(R.mipmap.suspicious);

            Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse);
            img.startAnimation(pulse);

        }
    }

}
