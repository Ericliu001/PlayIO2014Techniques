package com.eric.playio2014techniques.model;

import android.content.Context;
import android.os.AsyncTask;

import com.eric.playio2014techniques.ui.schedule.MyScheduleAdapter;

import java.util.ArrayList;

/**
 * Created by HQ on 2014/10/8.
 */
public class ScheduleHelper {


    private Context mContext;

    public ScheduleHelper(Context mContext) {
        this.mContext = mContext;
    }



    public ArrayList<ScheduleItem> getScheduleData(){
        ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>();
        String[] titles = {
                "Japan"
                ,"China"
                ,"America"
                ,"Africa"
                ,"Magic Land"
                ,"Australia"
        };

        for(String s : titles){
            ScheduleItem item = new ScheduleItem();
            item.title = s;
            result.add(item);


        }

        return result;
    }


    public void getScheduleDataAsync(final MyScheduleAdapter adapter){
        new AsyncTask<Void, Void, ArrayList<ScheduleItem>>(){


            @Override
            protected ArrayList<ScheduleItem> doInBackground(Void... params) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return getScheduleData();
            }


            @Override
            protected void onPostExecute(ArrayList<ScheduleItem> scheduleItems) {
                adapter.updateItems(scheduleItems);
            }
        }.execute();
    }

}
