package com.example.collegepicker;

public class FullDetails
{
    public String fname;
    public String lastname;
    public String Address,Mobilenumber,sex,email,department,city,pincode,cuttoff;
    public float tenth,twelfth;
    public FullDetails()
    {

    }
    public FullDetails(String fname, String lastname, String Address, String Mob, String sex, String email, String department, String city, String pincode, String cuttoff, float tenth, float twelfth)
    {
        this.fname=fname;
        this.lastname=lastname;
        this.Address=Address;
        Mobilenumber=Mob;
        this.sex=sex;
        this.email=email;
        this.department=department;
        this.tenth=tenth;
        this.twelfth=twelfth;
        this.city=city;
        this.pincode=pincode;
        this.cuttoff=cuttoff;
    }
}
