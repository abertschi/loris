package ch.abertschi.loris;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by abertschi on 19.11.16.
 */
public class TabHostDetails extends Fragment {

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View confirmHostName = view.findViewById(R.id.confirm_hostname);
        confirmHostName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("button pressed");

                EditText hostname = (EditText) view.findViewById(R.id.editText);
                Editable text = hostname.getText();
                if (text.toString().trim().isEmpty()) {
                    MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                            .title("Hold on!")
                            .content("Enter a valid host name to continue.")
                            .positiveText("Got it")
                            .show();
                } else {
                    final Handler h = new Handler(Looper.getMainLooper());
                    final Runnable r = new Runnable() {
                        public void run() {
                            FullscreenActivity.getInstance()
                                    .getViewPager().setCurrentItem(2, true);
                        }
                    };
                    h.postDelayed(r, 500);
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_hostname, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getActivity() != null) {
                ImageView img = (ImageView) getActivity().findViewById(R.id.face);
                if (img != null) {
                    img.setImageResource(R.mipmap.suspicious);
                }
            }
        }
    }
}
