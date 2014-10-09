package com.eric.playio2014techniques.ui.sessions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eric.playio2014techniques.R;
import com.eric.playio2014techniques.model.CollectionHelper;

import java.util.ArrayList;
import java.util.List;

public class BrowseSessionsActivity extends Activity implements CollectionView.CollectionViewCallbacks {

    private CollectionHelper mDataHelper;

    private List<MyGun> guns = new ArrayList<MyGun>();
    private CollectionView mCollectionView;

    private String[] names = {
        "M-16"
            ,"AK-47"
            ,"Springfield"
            ,"Stun"
            ,"M3"
            , "General Purpose Machine Gun"
            ,"AK-101"
            ,"Armalite AR-5"
            ,"HK MP7-PDW"
            ,"HK MP2000"
            ,"Musket"
            ,"MG45"
            ,"FG42"
            ,"M-1"
            ,"FAK-B"
            ,"Cake"
            ,"Sweet"
            ,"Sucks"
            ,"I wish I could"
            ,"This is stupid"
            ,"No other God"
            ,"Jesus Christ"
            ,"This is my God"
            ,"I like Tanks"
            ,"Sherman"
            ,"Tiger"
            ,"Panther"
            ,"Churchill"
            ,"Chinese whisper"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_sessions);

        mCollectionView = (CollectionView) findViewById(R.id.cvSessions);
        mCollectionView.passInCollectionViewCallbacks(this);

        float i = 0f;
        for(String name: names){
            MyGun gun = new MyGun();
            gun.name = name;
            gun.calibre = i;

            guns.add(gun);
             i+= 0.1f;
        }


        mDataHelper = new CollectionHelper(guns);
        mDataHelper.getCollectionDataAsync(0, CollectionView.NUMBER_OF_ITEMS_LOADED, mCollectionView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browse_sessions, menu);
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
    public View newCollectionItemView(Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.row_my_schedule, parent, false);
    }

    @Override
    public void bindCollectionItemView(Context context, View view, Object data) {
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvSubtitle = (TextView) view.findViewById(R.id.tvSubtitle);

        MyGun gun = (MyGun)data;

        tvTitle.setText(gun.name);
        tvSubtitle.setText(String.valueOf(gun.calibre));
    }

    @Override
    public int getServerListSize() {
        return names.length;
    }

    @Override
    public void loadMore(int skip, int top) {
        mDataHelper.getCollectionDataAsync(skip, top, mCollectionView);
    }


    public class MyGun{
        String name;
        float calibre;
    }
}
