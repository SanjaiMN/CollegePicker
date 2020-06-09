package com.example.collegepicker;

import android.os.Parcel;
import android.os.Parcelable;

public class Collegedetails implements Parcelable
{
    public String name,city,link;
    int code;
    public float placment,infrastructure,academics,hostel,mincutoff;
    Collegedetails()
    {

    }
    Collegedetails(String name,String city,int code,float placment,float infrastructure,float academics,float hostel,float mincutoff,String link)
    {
        this.name=name;
        this.city=city;
        this.code=code;
        this.placment=placment;
        this.infrastructure=infrastructure;
        this.academics=academics;
        this.hostel=hostel;
        this.mincutoff=mincutoff;
        this.link=link;
    }

    protected Collegedetails(Parcel in) {
        name = in.readString();
        city = in.readString();
        link = in.readString();
        code = in.readInt();
        placment = in.readFloat();
        infrastructure = in.readFloat();
        academics = in.readFloat();
        hostel = in.readFloat();
        mincutoff = in.readFloat();
    }

    public static final Creator<Collegedetails> CREATOR = new Creator<Collegedetails>() {
        @Override
        public Collegedetails createFromParcel(Parcel in) {
            return new Collegedetails(in);
        }

        @Override
        public Collegedetails[] newArray(int size) {
            return new Collegedetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(link);
        dest.writeInt(code);
        dest.writeFloat(placment);
        dest.writeFloat(infrastructure);
        dest.writeFloat(academics);
        dest.writeFloat(hostel);
        dest.writeFloat(mincutoff);
    }
}
