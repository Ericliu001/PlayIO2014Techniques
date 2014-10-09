package com.eric.playio2014techniques.ui.schedule;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.eric.playio2014techniques.R;
import com.eric.playio2014techniques.model.ScheduleHelper;
import com.eric.playio2014techniques.util.Config;

import java.util.HashSet;
import java.util.Set;


public class MyScheduleActivity extends Activity  implements MyScheduleFragment.Listener {




    private static final String ARG_CONFERENCE_DAY_INDEX = "com.eric.playio2014techniques.ARG_CONFERENCE_DAY_INDEX";
    ViewPager mViewPager = null;
    OurViewPagerAdapter mViewPagerAdapter = null;




    // The ScheduleHelper is responsible for feeding data in a format suitable to the Adapter.
    private ScheduleHelper mDataHelper;



    private Set<MyScheduleFragment> mMyScheduleFragments = new HashSet<MyScheduleFragment>();
    private MyScheduleAdapter[] mScheduleAdapters = new MyScheduleAdapter[Config.CONFERENCE_TOTAL_DAYS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);


        mDataHelper = new ScheduleHelper(this);

        for (int i = 0; i < Config.CONFERENCE_TOTAL_DAYS; i++){
            mScheduleAdapters[i] = new MyScheduleAdapter(this, null);
        };

        mViewPagerAdapter = new OurViewPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateData();
    }

    private void updateData() {
        for (int i = 0; i < Config.CONFERENCE_TOTAL_DAYS; i++) {
            mDataHelper.getScheduleDataAsync(mScheduleAdapters[i]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentViewCreated(ListFragment fragment) {
        int dayIndex = fragment.getArguments().getInt(ARG_CONFERENCE_DAY_INDEX, 0);

        fragment.setListAdapter(mScheduleAdapters[dayIndex]);
    }

    @Override
    public void onFragmentAttached(MyScheduleFragment fragment) {
        mMyScheduleFragments.add(fragment);
    }

    @Override
    public void onFragmentDetached(MyScheduleFragment fragment) {
        mMyScheduleFragments.remove(fragment);
    }


    private class OurViewPagerAdapter extends android.support.v13.app.FragmentPagerAdapter {


        public OurViewPagerAdapter(android.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.app.Fragment getItem(int position) {
            MyScheduleFragment frag = new MyScheduleFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_CONFERENCE_DAY_INDEX, position);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public int getCount() {
            return Config.CONFERENCE_TOTAL_DAYS;
        }
    }
}
