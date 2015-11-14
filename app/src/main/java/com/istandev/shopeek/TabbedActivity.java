package com.istandev.shopeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class TabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    ListView list;
    JSONParser jParser = new JSONParser();
    ArrayList<Toko> daftar_toko = new ArrayList<Toko>();
    JSONArray daftarToko = null;
    String url_read_toko = "http://192.168.1.64/shopeek/read_toko.php";
    // JSON Node names, ini harus sesuai yang di API
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_TOKO = "toko";
    public static final String TAG_ID_TOKO = "id_toko";
    public static final String TAG_NAMA_TOKO = "nama_toko";
    public static final String TAG_ALAMAT_TOKO = "alamat_toko";
    private static final String TAG = "TabbedActivity";



    private CharSequence pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        final ActionBar actionBar = getSupportActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setTitle(mSectionsPagerAdapter.getPageTitle(0));

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // show the given tab
                mViewPager = (ViewPager) findViewById(R.id.pager);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        // Add 4 tabs, specifying the tab's text and TabListener

        for (int i = 0; i < 4; i++) {
            if (i==0){
                actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_shopping).setTabListener(tabListener));
            }
            else if (i==1){
                actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_store).setTabListener(tabListener));
            }
            else if (i==2){
                actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_map).setTabListener(tabListener));
            }
            else {
                actionBar.addTab(actionBar.newTab().setIcon(R.drawable.ic_shop_list).setTabListener(tabListener));
            }
        }


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getSupportActionBar().setSelectedNavigationItem(position);
                        getSupportActionBar().setTitle(mSectionsPagerAdapter.getPageTitle(position));
                    }
                });


        list = (ListView) findViewById(R.id.listView_toko3);
        //jalankan ReadMhsTask
        ReadTokoTask m= new ReadTokoTask();
        m.execute();
    }


    class ReadTokoTask extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TabbedActivity.this);
            pDialog.setMessage("Mohon Tunggu..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String returnResult = getTokoList(); //memanggil method getMhsList()
            return returnResult;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(result.equalsIgnoreCase("Exception Caught"))
            {
                Toast.makeText(TabbedActivity.this, "Unable to connect to server,please check your internet connection!", Toast.LENGTH_LONG).show();
            }

            if(result.equalsIgnoreCase("no results"))
            {
                Toast.makeText(TabbedActivity.this, "Data empty", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(TabbedActivity.this, "Mencoba menampilkan", Toast.LENGTH_LONG).show();
                list.setAdapter(new TokoAdapter(TabbedActivity.this, daftar_toko)); //Adapter menampilkan data toko ke dalam listView
            }
        }


        //method untuk memperoleh daftar mahasiswa dari JSON
        public String getTokoList()
        {
            Toko tempToko = new Toko();
            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            try {
                JSONObject json = jParser.makeHttpRequest(url_read_toko,"POST", parameter);
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) { //Ada record Data (SUCCESS = 1)
                    //Getting Array of daftar_mhs
                    daftarToko = json.getJSONArray(TAG_TOKO);
                    // looping through All daftar_mhs
                    for (int i = 0; i < daftarToko.length() ; i++){
                        JSONObject c = daftarToko.getJSONObject(i);
                        tempToko = new Toko();
                        tempToko.setTokoId(c.getString(TAG_ID_TOKO));
                        tempToko.setTokoName(c.getString(TAG_NAMA_TOKO));
                        tempToko.setTokoAlamat(c.getString(TAG_ALAMAT_TOKO));
                        daftar_toko.add(tempToko);
                    };
                    Log.v(TAG, "READ SUCCESS"+ daftarToko);
                    return "OK";
                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    return "no results";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception Caught";
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0 :return Fragment1.newInstance(position + 1);
                case 1 :return Fragment2.newInstance(position + 1);
                case 2 :return Fragment3.newInstance(position + 1);
                case 3 :return Fragment4.newInstance(position + 1);
            }
            return null;

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1);
                case 1:
                    return getString(R.string.title_section2);
                case 2:
                    return getString(R.string.title_section3);
                case 3:
                    return getString(R.string.title_section4);
            }
            return null;
        }
    }
}
