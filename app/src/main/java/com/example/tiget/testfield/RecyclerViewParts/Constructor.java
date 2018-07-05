package com.example.tiget.testfield.RecyclerViewParts;


import android.net.Uri;

import java.io.Serializable;




public class Constructor implements Serializable{
    public String type;
    public String AuthorName;
    public String SongName;
    public String SongUri;

    public Constructor(String type, String AuthorName, String SongName, String SongUri){
        this.type = type;
        this.AuthorName = AuthorName;
        this.SongName = SongName;
        this.SongUri = SongUri;

    }

}
