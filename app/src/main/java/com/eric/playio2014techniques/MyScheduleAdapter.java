package com.eric.playio2014techniques;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by HQ on 2014/10/8.
 * <p/>
 * Conforming to Android UI design guidelines presented at Google I/O 2010, if you want to have a fast and responsive UI,  heavy high latency operations should be moved out of the main thread and offloaded to another thread. One of the example provided by Mark Murphy’s CommonsWare involves calling AsyncTask to load images in the background and notifying the ListView to refresh itself upon completion, this is a good solution but there is a problem created by this approach, precisely the ListView will be bombarded with UI update requests when a large number of background-running image loading operations are completed. An alternative solution here is an extension to using AsyncTask alone, the idea is to use the interface called android.widget.AbsListView.RecyclerListener, keep a record of active displayed row in a list or what have you, and create a listener for the asynchronous image loading operation to intercept the event.
 * <p/>
 * Here’s how to keep track of an active displayed row:
 * 1. Immediately in the createView method of your ListView adapter, add a record info of the displayed row (you can use an ArrayList datastructure for example). Use setTag method to attach row position to the view.
 * 2. Implement the android.widget.AbsListView.RecyclerListener interface. Inside the onMovedToScapHeap(View v) get the row information from the view, assuming setTag() was called in step 1. you can use getTag() method. Remove a record of this view from the records (ArrayList)
 * <p/>
 * To display the image:
 * 3. Implement a listener for the asynchronous image loading to call upon completion. Retrieve the imageView, the Bitmap (or Drawable), and the row position. Retrieve the active row position from the records (ArrayList). If the row is still active then display the image.
 */
public class MyScheduleAdapter extends ArrayAdapter<String>{


    public MyScheduleAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }
}
