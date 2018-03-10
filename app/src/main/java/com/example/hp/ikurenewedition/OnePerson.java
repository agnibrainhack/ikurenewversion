package com.example.hp.ikurenewedition;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.hp.ikurenewedition.fragments.BloodPressureFragment;
import com.example.hp.ikurenewedition.fragments.BloodSugarFragment;
import com.example.hp.ikurenewedition.fragments.CheckupFragment;
import com.example.hp.ikurenewedition.fragments.ECGFragment;
import com.example.hp.ikurenewedition.fragments.PrescriptionFragment;
import com.example.hp.ikurenewedition.fragments.VitalsFragment;

/**
 * Created by root on 15/2/18.
 */


public class OnePerson extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    // Titles of the individual pages (displayed in tabs)
    private final String[] PAGE_TITLES = new String[]{
            "Checkups",
            "Prescriptions",
            "B.P Reports",
            "Sugar Reports",
            "Vital Reports",
            "ECG Reports"
    };

    // The fragments that are used as the individual pages
    private final android.support.v4.app.Fragment[] PAGES = new android.support.v4.app.Fragment[]{
            new CheckupFragment(),
            new PrescriptionFragment(),
            new BloodPressureFragment(),
            new BloodSugarFragment(),
            new VitalsFragment(),
            new ECGFragment()//

    };

    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();
        String patient = intent.getStringExtra("patient_name");
        // Set the Toolbar as the activity's app bar (instead of the default ActionBar)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(patient);
        setSupportActionBar(toolbar);

        // Connect the ViewPager to our custom PagerAdapter. The PagerAdapter supplies the pages
        // (fragments) to the ViewPager, which the ViewPager needs to display.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(6);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Connect the tabs with the ViewPager (the setupWithViewPager method does this for us in
        // both directions, i.e. when a new tab is selected, the ViewPager switches to this page,
        // and when the ViewPager switches to a new page, the corresponding tab is selected)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }


public class MyPagerAdapter extends FragmentPagerAdapter {



    public MyPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {

      super(fragmentManager);
    }

    @Override
    public int getCount() {
        return PAGES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return PAGES[position];
    }
}
}