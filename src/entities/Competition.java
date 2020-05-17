package entities;

import java.util.Date;

public class Competition {


    private int idcomp ;
    private  String namecomp ;
    private  String desccomp;
    private int nbrmaxspec;
    private int nbrmaxpar;
    private  String location;
    private Date startingdate;
    private Date endingdate;
    private float pricecomp;
    private int idcat;
    private int  etat;
    private int  nbrparticipant ;
    private int  nbrspec ;
    private  String  image_name ;
    private int idU;
    private  String lat;
    private  String lng;

    public Competition() {
    }

    public Competition(int idcomp, String namecomp, String desccomp, int nbrmaxspec, int nbrmaxpar, String location, Date startingdate, Date endingdate, float pricecomp, int idcat, int etat, int nbrparticipant, int nbrspec, String image_name, int idU, String lat, String lng) {
        this.idcomp = idcomp;
        this.namecomp = namecomp;
        this.desccomp = desccomp;
        this.nbrmaxspec = nbrmaxspec;
        this.nbrmaxpar = nbrmaxpar;
        this.location = location;
        this.startingdate = startingdate;
        this.endingdate = endingdate;
        this.pricecomp = pricecomp;
        this.idcat = idcat;
        this.etat = etat;
        this.nbrparticipant = nbrparticipant;
        this.nbrspec = nbrspec;
        this.image_name = image_name;
        this.idU = idU;
        this.lat = lat;
        this.lng = lng;
    }

    public int getIdcomp() {
        return idcomp;
    }

    public String getNamecomp() {
        return namecomp;
    }

    public String getDesccomp() {
        return desccomp;
    }

    public int getNbrmaxspec() {
        return nbrmaxspec;
    }

    public int getNbrmaxpar() {
        return nbrmaxpar;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartingdate() {
        return startingdate;
    }

    public Date getEndingdate() {
        return endingdate;
    }

    public float getPricecomp() {
        return pricecomp;
    }

    public int getIdcat() {
        return idcat;
    }

    public int getEtat() {
        return etat;
    }

    public int getNbrparticipant() {
        return nbrparticipant;
    }

    public int getNbrspec() {
        return nbrspec;
    }

    public String getImage_name() {
        return image_name;
    }

    public int getIdU() {
        return idU;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setIdcomp(int idcomp) {
        this.idcomp = idcomp;
    }

    public void setNamecomp(String namecomp) {
        this.namecomp = namecomp;
    }

    public void setDesccomp(String desccomp) {
        this.desccomp = desccomp;
    }

    public void setNbrmaxspec(int nbrmaxspec) {
        this.nbrmaxspec = nbrmaxspec;
    }

    public void setNbrmaxpar(int nbrmaxpar) {
        this.nbrmaxpar = nbrmaxpar;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartingdate(Date startingdate) {
        this.startingdate = startingdate;
    }

    public void setEndingdate(Date endingdate) {
        this.endingdate = endingdate;
    }

    public void setPricecomp(float pricecomp) {
        this.pricecomp = pricecomp;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setNbrparticipant(int nbrparticipant) {
        this.nbrparticipant = nbrparticipant;
    }

    public void setNbrspec(int nbrspec) {
        this.nbrspec = nbrspec;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "idcomp=" + idcomp +
                ", namecomp='" + namecomp + '\'' +
                ", desccomp='" + desccomp + '\'' +
                ", nbrmaxspec=" + nbrmaxspec +
                ", nbrmaxpar=" + nbrmaxpar +
                ", location='" + location + '\'' +
                ", startingdate=" + startingdate +
                ", endingdate=" + endingdate +
                ", pricecomp=" + pricecomp +
                ", idcat=" + idcat +
                ", etat=" + etat +
                ", nbrparticipant=" + nbrparticipant +
                ", nbrspec=" + nbrspec +
                ", image_name='" + image_name + '\'' +
                ", idU=" + idU +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                '}';
    }
}
