package com.nikitagordia.criminalintent;

import java.util.Date;
import java.util.Formatter;


/**
 * Created by root on 16.11.17.
 */

public class Crime {

    private static int CNT = 0;

    private int mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

    public Crime() {
        mId = CNT++;
        mDate = new Date();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public String getFormatDate() {
        return new Formatter().format("%1$tA, %1$tb %1$te, %1$tY ", mDate).toString();
    }

    public String getTime() {
        return new Formatter().format("%1$tH:%1$tM", mDate).toString();
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }
}
