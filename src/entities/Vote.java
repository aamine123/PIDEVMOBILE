package entities;

public class Vote {
    private int id;
    private User id_user;
    private int vote;
    private Sujet idsujet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getId_user() {
        return id_user;
    }

    public void setId_user(User id_user) {
        this.id_user = id_user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public Sujet getIdsujet() {
        return idsujet;
    }

    public void setIdsujet(Sujet idsujet) {
        this.idsujet = idsujet;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", vote=" + vote +
                ", idsujet=" + idsujet +
                '}';
    }

    public Vote(int id, User id_user, int vote, Sujet idsujet) {
        this.id = id;
        this.id_user = id_user;
        this.vote = vote;
        this.idsujet = idsujet;
    }

    public Vote() {
    }
}
