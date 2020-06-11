package DataBase;

public class Tvshow {
    private Integer id;
    private String name;
    private Integer episodenum;
    private String description;
    private Integer duree;
    private String type;
    private String link;
    private Integer year;
    private String coverimage;
    private String galeryimage1;
    private String galeryimage2;
    private String galeryimage3;
    private String galeryimage4;
    private String galeryimage5;
    private Integer nbrvues;

    public Tvshow() {

    }

    @Override
    public String toString() {
        return "Tvshow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", episodenum=" + episodenum +
                ", description='" + description + '\'' +
                ", duree=" + duree +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                ", year=" + year +
                ", coverimage='" + coverimage + '\'' +
                ", galeryimage1='" + galeryimage1 + '\'' +
                ", galeryimage2='" + galeryimage2 + '\'' +
                ", galeryimage3='" + galeryimage3 + '\'' +
                ", galeryimage4='" + galeryimage4 + '\'' +
                ", galeryimage5='" + galeryimage5 + '\'' +
                ", nbrvues=" + nbrvues +
                '}';
    }

    public Tvshow(Integer id, String name, Integer episodenum, String description, Integer duree, String type, String link, Integer year, String coverimage, String galeryimage1, String galeryimage2, String galeryimage3, String galeryimage4, String galeryimage5, Integer nbrvues) {
        this.id = id;
        this.name = name;
        this.episodenum = episodenum;
        this.description = description;
        this.duree = duree;
        this.type = type;
        this.link = link;
        this.year = year;
        this.coverimage = coverimage;
        this.galeryimage1 = galeryimage1;
        this.galeryimage2 = galeryimage2;
        this.galeryimage3 = galeryimage3;
        this.galeryimage4 = galeryimage4;
        this.galeryimage5 = galeryimage5;
        this.nbrvues = nbrvues;
    }
    public Tvshow(Integer id, String name, Integer episodenum, String description, Integer duree, String type, String link, Integer year, String coverimage, Integer nbrvues) {
        this.id = id;
        this.name = name;
        this.episodenum = episodenum;
        this.description = description;
        this.duree = duree;
        this.type = type;
        this.link = link;
        this.year = year;
        this.coverimage = coverimage;
        this.nbrvues = nbrvues;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEpisodenum() {
        return episodenum;
    }

    public void setEpisodenum(Integer episodenum) {
        this.episodenum = episodenum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getGaleryimage1() {
        return galeryimage1;
    }

    public void setGaleryimage1(String galeryimage1) {
        this.galeryimage1 = galeryimage1;
    }

    public String getGaleryimage2() {
        return galeryimage2;
    }

    public void setGaleryimage2(String galeryimage2) {
        this.galeryimage2 = galeryimage2;
    }

    public String getGaleryimage3() {
        return galeryimage3;
    }

    public void setGaleryimage3(String galeryimage3) {
        this.galeryimage3 = galeryimage3;
    }

    public String getGaleryimage4() {
        return galeryimage4;
    }

    public void setGaleryimage4(String galeryimage4) {
        this.galeryimage4 = galeryimage4;
    }

    public String getGaleryimage5() {
        return galeryimage5;
    }

    public void setGaleryimage5(String galeryimage5) {
        this.galeryimage5 = galeryimage5;
    }

    public Integer getNbrvues() {
        return nbrvues;
    }

    public void setNbrvues(Integer nbrvues) {
        this.nbrvues = nbrvues;
    }
}
