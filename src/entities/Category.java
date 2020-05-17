package entities;

public class Category {
    private int id;
    private String name;
    private int idCategoryMother;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCategoryMother=" + idCategoryMother +
                '}';
    }

    public Category() {
    }

    public Category(int id, String name, int idCategoryMother) {
        this.id = id;
        this.name = name;
        this.idCategoryMother = idCategoryMother;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCategoryMother() {
        return idCategoryMother;
    }

    public void setIdCategoryMother(int idCategoryMother) {
        this.idCategoryMother = idCategoryMother;
    }
}
