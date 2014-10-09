package com.eric.playio2014techniques.model;

/**
 * Created by HQ on 2014/10/8.
 */
public class ScheduleItem implements Cloneable {

    public String title = "";
    public String subTitle = "";


    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {

            return new ScheduleItem();
        }


    }
}
