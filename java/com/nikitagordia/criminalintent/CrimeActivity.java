package com.nikitagordia.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class CrimeActivity extends SingleFragmentActivity {

    public static final String EXTRA_CRIME_ID = "com.nikitagordia.crime_id";

    @Override
    protected Fragment createFragment() {
        int crimeId = getIntent().getIntExtra(EXTRA_CRIME_ID, 0);
        return CrimeFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, int crimeId) {
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }
}
