package com.istandev.shopeek;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ADIK on 12/11/2015.
 */
public class TokoAdapter extends BaseAdapter {
    private Activity activity;
    //private ArrayList<HashMap<String, String>> data;
    private ArrayList<Toko> data_toko=new ArrayList<Toko>();

    private static LayoutInflater inflater = null;

    public TokoAdapter(Activity a, ArrayList<Toko> d) {
        activity = a; data_toko = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return data_toko.size();
    }
    public Object getItem(int position) {
        return data_toko.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_shop, null);
       // TextView id_mhs = (TextView) vi.findViewById(R.id.id_toko);
        TextView nama_toko = (TextView) vi.findViewById(R.id.nama_toko);
        TextView alamat_toko = (TextView) vi.findViewById(R.id.alamat_toko);

        Toko daftar_toko = data_toko.get(position);
       // id_mhs.setText(daftar_toko.getTokoId());
        nama_toko.setText(daftar_toko.getTokoName());
        alamat_toko.setText(daftar_toko.getAlamatToko());

        return vi;
    }
}
