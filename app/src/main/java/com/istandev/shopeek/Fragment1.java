package com.istandev.shopeek;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fragment1 extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listView;
    private ArrayAdapter<String> mShopAdapter ;
    Context context;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment1 newInstance(int sectionNumber) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);

      /*  String[] data = {
                "Title 1","Title 2","Title 3","Title 4","Title 5","Title 6","Title 7",
                "Title 8","Title 9","Title 10","Title 11","Title 12","Title 13","Title 14",
        };

        List<String> listNews = new ArrayList<String>(Arrays.asList(data));
        mShopAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_shop, // The name of the layout ID.
                        R.id.nama_toko, // The ID of the textview to populate.
                        listNews);


        listView = (ListView) rootView.findViewById(R.id.list_shop_search);
        listView.setAdapter(mShopAdapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                String news = mNewsAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, news);
                startActivity(intent);
            }
        });*/
        return rootView;
    }
}