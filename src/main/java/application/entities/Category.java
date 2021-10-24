package application.entities;

public class Category {

    private String name;
    private String details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
