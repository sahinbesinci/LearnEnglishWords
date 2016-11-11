package com.example.sahin.learnenglishwords;

/**
 * Created by sahin on 21.10.2016.
 */

public class Kelime {
    private String Ingilizce;
    private String Turkce;
    private int Dogru;
    private int Yanlis;

    Kelime(String ing, String tur)
    {
        Ingilizce = ing;
        Turkce = tur;
        Dogru = 0;
        Yanlis = 0;
    }
    public void mixWord(String ing, String tur)
    {
        Ingilizce = tur;
        Turkce = ing;
    }

    public int getDogru(){return this.Dogru;}
    public void setDogru(){++this.Dogru;}
    public int getYanlis(){return this.Yanlis;}
    public void setYanlis(){++this.Yanlis;}
    public String getIngilizce(){return this.Ingilizce;}
    public String getTurkce(){return this.Turkce;}
    public void setIngilizce(String Ingilizce){this.Ingilizce = Ingilizce;}
    public void setTurkce(String Turkce){this.Turkce = Turkce;}
}
