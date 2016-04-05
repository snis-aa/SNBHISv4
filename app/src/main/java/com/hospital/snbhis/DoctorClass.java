package com.hospital.snbhis;


public class DoctorClass {

    private int iddr;
    private String namedr;
    private int idhsp;
    private String drdegree;
    private String drexp;
    private double fee;
    private String speciality;
    private String description;
    private float exp_r;
    private float know_r;
    private float treat_r;


    public int getiddr()    {        return(iddr);    }
    public void setiddr(int p)    {        iddr=p;    }

    public String getnamedr()    {        return(namedr);    }
    public void setnamedr(String p)    {        namedr=p;    }

    public int getidhsp()    {        return(idhsp);    }
    public void setidhsp(int p)    {        idhsp=p;    }

    public String getdrdegree()    {        return(drdegree);    }
    public void setdrdegree(String p)    {        drdegree=p;    }

    public String getspeciality()    {        return(speciality);    }
    public void setspeciality(String p)    {        speciality=p;    }

    public double getfee()    {        return(fee);    }
    public void setfee(double p)    {        fee=p;    }

    public String getdrexp()    {        return(drexp);    }
    public void setdrexp(String p)    {        drexp=p;    }

    public String getdescription()    {        return(description);    }
    public void setdescription(String p)    {        description=p;    }

    public float getexp_r()    {        return(exp_r);    }
    public void setexp_r(float p)    {        exp_r=p;    }

    public float getknow_r()    {        return(know_r);    }
    public void setknow_r(float p)    {        know_r=p;    }

    public float gettreat_r()    {        return(treat_r);    }
    public void settreat_r(float p)    {        treat_r=p;    }


    public float getrate()
    {
        return((exp_r+know_r+treat_r)/3);
    }
}
