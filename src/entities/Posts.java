package entities;

import java.util.Date;

public class Posts {
    private int idPost;
    private int idU;
    private String description;
    private int nbrlikes;
    private int nbrcomments;
    private Date dateCreation;
    private String image_name;
    private int type;
    private int archive;

    public Posts() {

    }

    public Posts(int idPost, int idU, String description, int nbrlikes, int nbrcomments, Date dateCreation, String image_name, int type, int archive) {
        this.idPost = idPost;
        this.idU = idU;
        this.description = description;
        this.nbrlikes = nbrlikes;
        this.nbrcomments = nbrcomments;
        this.dateCreation = dateCreation;
        this.image_name = image_name;
        this.type = type;
        this.archive = archive;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbrlikes() {
        return nbrlikes;
    }

    public void setNbrlikes(int nbrlikes) {
        this.nbrlikes = nbrlikes;
    }

    public int getNbrcomments() {
        return nbrcomments;
    }

    public void setNbrcomments(int nbrcomments) {
        this.nbrcomments = nbrcomments;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }
}
