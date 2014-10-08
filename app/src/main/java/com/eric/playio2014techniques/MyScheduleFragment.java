package com.eric.playio2014techniques;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;

/**
 * Created by HQ on 2014/10/8.
 */
public class MyScheduleFragment extends ListFragment {


    public interface Listener {
        public void onFragmentViewCreated(ListFragment fragment);

        public void onFragmentAttached(MyScheduleFragment fragment);

        public void onFragmentDetached(MyScheduleFragment fragment);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof  Listener){
            ((Listener)getActivity()).onFragmentViewCreated(this);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof Listener){
            ((Listener)getActivity()).onFragmentAttached(this);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof  Listener){
            ((Listener)getActivity()).onFragmentDetached(this);
        }
    }
}