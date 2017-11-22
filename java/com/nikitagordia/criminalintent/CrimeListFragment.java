package com.nikitagordia.criminalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Formatter;
import java.util.List;

/**
 * Created by root on 18.11.17.
 */

public class CrimeListFragment extends Fragment {

    public static final String TAG = "myTag";
    public static final int REQUEST_LEN = 2;
    public static final String EXTRA_L = "com.nikitagordia.len_L";
    public static final String EXTRA_R = "com.nikitagordia.len_R";

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int boundL, boundR;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        boundL = boundR = 0;
        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(CrimeLab.get(getActivity()));
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            for (int i = boundL; i <= boundR; i++)
            mAdapter.notifyItemChanged(i); //TODO onChangeWindow
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LEN && data != null) {
            boundL = data.getIntExtra(EXTRA_L, 0);
            boundR = data.getIntExtra(EXTRA_R, 0);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mTitleTextView, mDateTextView;
        private ImageButton button;
        private CheckBox mSolved;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            button = (ImageButton) itemView.findViewById(R.id.police_call_button);
            mSolved = (CheckBox) itemView.findViewById(R.id.solved);

            if (viewType == 0) button.setVisibility(View.INVISIBLE); else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+380975055222")));
                    }
                });
            }
            mSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mCrime.setSolved(b);
                    button.setEnabled(!b);
                }
            });
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivityForResult(intent, REQUEST_LEN);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getFormatDate());
            button.setEnabled(!mCrime.isSolved());
            mSolved.setChecked(mCrime.isSolved());
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private CrimeLab mCrimes;

        public CrimeAdapter(CrimeLab crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater, parent, viewType);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            holder.bind(mCrimes.getCrime(mCrimes.getListPos(position)));
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (mCrimes.getCrime(mCrimes.getListPos(position)).isRequiresPolice() ? 1 : 0);
        }
    }
}
