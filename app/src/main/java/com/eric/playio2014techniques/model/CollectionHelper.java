package com.eric.playio2014techniques.model;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HQ on 2014/10/9.
 */
public class CollectionHelper<T> {

    // The data source list
    private List<T> mList = null;

    public CollectionHelper(List<T> list) {
        mList = list; // get the data source
    }



    public interface CollecionHelperListener<T>{
       void appendToDataList(List<T> items);

    }

    public void getCollectionDataAsync(int skip, int top, final CollecionHelperListener<T> callbacks){
        new AsyncTask<Integer, Void, List<T>>(){


            @Override
            protected List<T> doInBackground(Integer... params) {

                int skip = params[0];
                int top = params[1];

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<T> appendedList = new ArrayList<T>();

                int i = 0;
                for(T t: mList){
                    if ( skip <= i && i < skip + top)
                        appendedList.add(t);
                    ++i;
                }

                return appendedList;
            }


            @Override
            protected void onPostExecute(List<T> items) {

                callbacks.appendToDataList(items);
            }
        }.execute(skip, top);

    }


}
