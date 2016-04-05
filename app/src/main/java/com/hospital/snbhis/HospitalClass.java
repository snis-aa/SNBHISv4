package com.hospital.snbhis;


public class HospitalClass {
    private int id;
    private String name;
    private String city;
    private String address;
    private float location_r;
    private float cleanliness_r;
    private float treatments_r;
    private float prices_r;

    public int getid()    {        return(id);    }
    public void setid(int p)    {        id=p;    }

    public String getname()    {        return(name);    }
    public void setname(String p)    {        name=p;    }

    public String getcity()    {        return(city);    }
    public void setcity(String p)    {        city=p;    }

    public String getaddress()    {        return(address);    }
    public void setaddress(String p)    {        address=p;    }

    public float getlocation_r()    {        return(location_r);    }
    public void setlocation_r(float p)    {        location_r=p;    }

    public float getcleanliness_r()    {        return(cleanliness_r);    }
    public void setcleanliness_r(float p)    {        cleanliness_r=p;    }

    public float gettreatments_r()    {        return(treatments_r);    }
    public void settreatments_r(float p)    {        treatments_r=p;    }

    public float getprices_r()    {        return(prices_r);    }
    public void setprices_r(float p)    {        prices_r=p;    }
    public float getrate()
    {
        return((prices_r+location_r+cleanliness_r+treatments_r)/4);
    }


}
