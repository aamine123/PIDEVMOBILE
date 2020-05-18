package entities;

import java.util.Date;

public class Commentaire {
    private int id_com,id_f;
    private User id_user;
    private String description_com;
    private Date date_com;

    public Commentaire() {
    }

    public Commentaire(int id_com, User idd_user, int id_f, String description_com, Date date_com) {
        this.id_com = id_com;
        this.id_f=id_f;
        id_user = idd_user;
        this.description_com = description_com;
        this.date_com = date_com;
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User idd_user) {
        id_user = idd_user;
    }

    public int getId_f() {
        return id_f;
    }

    public void setId_f(int id_f) {
        this.id_f = id_f;
    }

    public String getDescription_com() {
        return description_com;
    }

    public void setDescription_com(String description_com) {
        this.description_com = description_com;
    }

    public Date getDate_com() {
        return date_com;
    }

    public void setDate_com(Date date_com) {
        this.date_com = date_com;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id_com=" + id_com + ", id_user=" + id_user + ", id_f=" + id_f + ", description_com=" + description_com + ", date_com=" + date_com + '}';
    }



}
