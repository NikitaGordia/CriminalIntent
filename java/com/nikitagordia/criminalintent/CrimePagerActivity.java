package com.nikitagordia.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.nikitagordia.criminalintent.CrimeListFragment.EXTRA_L;
import static com.nikitagordia.criminalintent.CrimeListFragment.EXTRA_R;
import static com.nikitagordia.criminalintent.CrimeListFragment.REQUEST_LEN;

/**
 * Created by root on 20.11.17.
 */

public class CrimePagerActivity extends AppCompatActivity {

    public static final String EXTRA_CRIME_ID = "com.nikitagordia.crime_id";

    private ViewPager mViewPager;
    private CrimeLab mCrimes;
    private int l, r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.get(this);
        l = Integer.MAX_VALUE;
        r = 0;

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.getCrime(position);
                updateResult(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        mViewPager.setCurrentItem(mCrimes.getCrimePos(crimeId));
    }

    private void updateResult(int pos) {
        l = Math.min(l, pos);
        r = Math.max(r, pos);
        setResult(REQUEST_LEN, new Intent().putExtra(EXTRA_L, l).putExtra(EXTRA_R, r));
    }

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
