package kea.exercise.studentadmin.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class House {
    @Id
    private String name;
    private String founder;
    private @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "colors", joinColumns = @JoinColumn(name = "house_id"))
    List<String> colors;

    public House(String name, String founder, List<String> colors) {
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    public House() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
