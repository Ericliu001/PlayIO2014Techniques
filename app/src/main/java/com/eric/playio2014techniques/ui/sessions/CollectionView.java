package com.eric.playio2014techniques.ui.sessions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HQ on 2014/10/9.
 */
public class CollectionView<T> extends ListView{
    public static final int NUMBER_OF_ITEMS_LOADED = 20;
    // The representation of all data, no use now

    // data List
    private ArrayList<T> dataList = new ArrayList<T>();

    // The callback to supply View Layout and populate data into rows
    private CollectionViewCallbacks mCallbacks = null;

    private MyListAdapter mListAdapter = null;

    protected static final int VIEW_TYPE_LOADING = 0;
    protected static final int VIEW_TYPE_ACTIVE = 1;
    private int serverListSize = -1;
    private boolean isLoading = false;


    public void passInCollectionViewCallbacks(CollectionViewCallbacks callbacks){
        this.mCallbacks = callbacks;
    }

    public CollectionView(Context context) {
        this(context, null);
    }

    public CollectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mListAdapter = new MyListAdapter();
        setAdapter(mListAdapter);
        setOnScrollListener(new MyScrollListener());
    }

    public interface CollectionViewCallbacks<T>{


        View newCollectionItemView(Context context, ViewGroup parent);
        void bindCollectionItemView(Context context, View view, T data);

        int getServerListSize();
        void loadMore(int skip, int top);
    }


    public void loadFirstRound(){
        // TODO: load the first round of data
    }

    public void loadMoreResult(int skip, int top){
        if (mCallbacks != null){
            isLoading = true;
            serverListSize = mCallbacks.getServerListSize();
            mCallbacks.loadMore(skip, top);
        }
    }


    protected class MyListAdapter extends BaseAdapter {


        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItemViewType(position) == VIEW_TYPE_ACTIVE;
        }

        @Override
        public int getCount() {
            return dataList.size() + 1;
        }


        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position >= dataList.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVE;
        }

        @Override
        public T getItem(int position) {
            return (getItemViewType(position) == VIEW_TYPE_ACTIVE) ? dataList.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return (getItemViewType(position) == VIEW_TYPE_ACTIVE) ? position
                    : -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {



            if (getItemViewType(position) == VIEW_TYPE_LOADING){
                // indicate that the List is loading data
                return getFooterView(position, convertView, parent);
            }


            View dataRow =  getRowView(position, convertView, parent);


            return dataRow;
        }



    }

    private View getFooterView(int position, View convertView, ViewGroup parent) {
        if (position >= serverListSize && serverListSize >=0){
            // indicate that the list has reached the last row.
            return getLastRowView();
        }else {
            return getLoadingView();
        }

    }

    private View getLoadingView() {
        ProgressBar bar = new ProgressBar(getContext());
        return bar;
    }

    private View getLastRowView() {
        TextView tvLastRow = new TextView(getContext());
        tvLastRow.setText("Reached the last row.");
        tvLastRow.setGravity(Gravity.CENTER);
        return tvLastRow;
    }

    private View getRowView(int position, View convertView, ViewGroup parent) {
        if (mCallbacks == null){
            return convertView != null ? convertView : new View(getContext());
        }

        if (convertView == null){
            convertView = mCallbacks.newCollectionItemView(getContext(), parent);
        }

        mCallbacks.bindCollectionItemView(getContext(), convertView, dataList.get(position));

        return convertView;
    }


    private class MyScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (! isLoading
                    && (dataList.size() < serverListSize)
                    && (firstVisibleItem + visibleItemCount >= totalItemCount - 1)

                    ) {

                   loadMoreResult(dataList.size(), NUMBER_OF_ITEMS_LOADED);
            }
        }
    }


    public void appendToDataList(List<T> items){
        isLoading = false;
        dataList.addAll(items);
        if (mCallbacks != null) {
            serverListSize = mCallbacks.getServerListSize();
        }
        mListAdapter.notifyDataSetChanged();
    }

    public void refresh(){
        dataList.clear();
        setAdapter(new MyListAdapter());
        mListAdapter.notifyDataSetChanged();
    }
}
