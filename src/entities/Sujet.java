package entities;

import java.util.Date;
import java.util.Objects;

public class Sujet {
    private int id_f,id_user,Etat,nbre_jaime,strike;
    private String description_f;
    private Date date;

    public Sujet() {
    }
    public Sujet( int id_user, int Etat, int nbre_jaime, int strike, String description_f, Date date){
        this.id_user = id_user;
        this.Etat = Etat;
        this.nbre_jaime = nbre_jaime;
        this.strike = strike;
        this.description_f = description_f;
        this.date = date;

    }



    public int getId_f() {
        return id_f;
    }

    public void setId_f(int id_f) {
        this.id_f = id_f;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getEtat() {
        return Etat;
    }

    public void setEtat(int Etat) {
        this.Etat = Etat;
    }

    public int getNbre_jaime() {
        return nbre_jaime;
    }

    public void setNbre_jaime(int nbre_jaime) {
        this.nbre_jaime = nbre_jaime;
    }

    public int getStrike() {
        return strike;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public String getDescription_f() {
        return description_f;
    }

    public void setDescription_f(String description_f) {
        this.description_f = description_f;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sujet{" + "id_f=" + id_f + ", id_user=" + id_user + ", Etat=" + Etat + ", nbre_jaime=" + nbre_jaime + ", strike=" + strike + ", description_f=" + description_f + ", date=" + date + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sujet sujet = (Sujet) o;
        return id_f == sujet.id_f &&
                id_user == sujet.id_user &&
                Etat == sujet.Etat &&
                nbre_jaime == sujet.nbre_jaime &&
                strike == sujet.strike &&
                description_f.equals(sujet.description_f) &&
                date.equals(sujet.date);
    }


}
