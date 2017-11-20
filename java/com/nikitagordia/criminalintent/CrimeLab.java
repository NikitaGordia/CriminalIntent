package com.nikitagordia.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by root on 18.11.17.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList();
        Random r = new Random();
        for (int i = 1; i <= 100; i++) {
            Crime crime = new Crime();
            crime.setSolved(r.nextInt(4) == 0);
            crime.setRequiresPolice(r.nextInt(5) == 0);
            mCrimes.add(crime);
        }
        Collections.sort(mCrimes, new CrimeComp());
        for (int i = 0; i < mCrimes.size(); i++)
            mCrimes.get(i).setTitle("Crime #" + (i + 1));
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        Crime require = new Crime();
        require.setId(id);
        int x = Collections.binarySearch(mCrimes, require, new CrimeComp());
        if (x >= 0 && x < mCrimes.size()) return mCrimes.get(x); else return null;
    }
}

class CrimeComp implements Comparator<Crime> {

    @Override
    public int compare(Crime crime, Crime t1) {
        return crime.getId().compareTo(t1.getId());
    }
}