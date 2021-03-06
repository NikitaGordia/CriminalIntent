package com.nikitagordia.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
    private Button toLast, toFirst;
    private int l, r;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        int crimeId = getIntent().getIntExtra(EXTRA_CRIME_ID, 0);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        toLast = (Button) findViewById(R.id.to_last_button);
        toFirst = (Button) findViewById(R.id.to_first_button);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateButtons(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        toLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToItem(mCrimes.size() - 1);
            }
        });

        toFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToItem(0);
            }
        });

        mCrimes = CrimeLab.get(this);
        l = Integer.MAX_VALUE;
        r = 0;

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Nullable


            @Override
            public Fragment getItem(int position) {
                updateResult(position);
                return CrimeFragment.newInstance(mCrimes.getListPos(position));
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        goToItem(mCrimes.getListPos(crimeId));
    }

    private void updateResult(int pos) {
        l = Math.min(l, pos);
        r = Math.max(r, pos);
        setResult(REQUEST_LEN, new Intent().putExtra(EXTRA_L, l).putExtra(EXTRA_R, r));
    }

    public static Intent newIntent(Context packageContext, int crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    private void goToItem(int pos) {
        mViewPager.setCurrentItem(pos);
        updateButtons(pos);
    }

    private void updateButtons(int pos) {
        toFirst.setEnabled(true);
        toLast.setEnabled(true);
        if (pos == 0) toFirst.setEnabled(false);
        if (pos == mCrimes.size() - 1) toLast.setEnabled(false);
    }
}
