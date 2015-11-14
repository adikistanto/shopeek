package com.istandev.shopeek;

/**
 * Created by ADIK on 12/11/2015.
 */
public class Toko {

    private String id_toko;
    private String nama_toko;
    private String alamat_toko;

    public void setTokoId (String id_toko)
    {
        this.id_toko = id_toko;
    }

    public String getTokoId()
    {
        return id_toko;
    }

    public void setTokoName (String nama_toko)
    {
        this.nama_toko = nama_toko;
    }

    public String getTokoName()
    {
        return nama_toko;
    }

    public void setTokoAlamat (String alamat_toko)
    {
        this.alamat_toko = alamat_toko;
    }

    public String getAlamatToko()
    {
        return alamat_toko;
    }
}




