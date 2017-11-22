package com.nikitagordia.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created by root on 18.11.17.
 */

public class CrimeLab {

    private static final int SIZE = 10000;
    private static CrimeLab sCrimeLab;

    private Crime[] mCrimes;
    private int n;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new Crime[SIZE];
        Random r = new Random();
        n = 25;
        initCrimes();
    }

    public int getListPos(int pos) {
        return n - pos - 1;
    }

    public void add(Crime crime) {
        mCrimes[n++] = crime;
    }

    public Crime getCrime(int pos) {
        return mCrimes[pos];
    }

    public int size() {
        return n;
    }

    private void initCrimes() {
        long prevTime = 1504990800000L;
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setDate(new Date(prevTime + Math.abs(r.nextLong()) % 10000000000L));
            crime.setSolved(r.nextInt(4) == 0);
            crime.setRequiresPolice(r.nextInt(5) == 0);
            mCrimes[i] = crime;
        }
    }
}