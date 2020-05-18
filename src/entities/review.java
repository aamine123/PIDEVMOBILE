package entities;

public class review {
    private int id;
    private User iduser;
    private Article idarticle;
    private int rate;
    private int etat;

    public review() {
    }

    public review(int id, User idduser, Article idarticle, int rate, int etat) {
        this.id = id;
        iduser = idduser;
        this.idarticle = idarticle;
        this.rate = rate;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduserr) {
        iduser = iduserr;
    }

    public Article getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Article idarticlee) {
        idarticle = idarticlee;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

}
