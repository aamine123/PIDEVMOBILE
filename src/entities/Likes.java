package entities;

import java.util.Date;

public class Likes {
    private int idLike;
    private int idPost;
    private int idU;
    private Date dateCreation;
    private int react;

    public Likes() {
    }

    public Likes(int idLike, int idPost, int idU, Date dateCreation, int react) {
        this.idLike = idLike;
        this.idPost = idPost;
        this.idU = idU;
        this.dateCreation = dateCreation;
        this.react = react;
    }

    public int getIdLike() {
        return idLike;
    }

    public void setIdLike(int idLike) {
        this.idLike = idLike;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public int getReact() {
        return react;
    }

    public void setReact(int react) {
        this.react = react;
    }


}
