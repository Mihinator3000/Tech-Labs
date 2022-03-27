package models;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany()
    @org.hibernate.annotations.Cascade({
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.REFRESH,
            org.hibernate.annotations.CascadeType.SAVE_UPDATE,
            org.hibernate.annotations.CascadeType.REPLICATE,
            org.hibernate.annotations.CascadeType.LOCK,
            org.hibernate.annotations.CascadeType.DETACH
    })
    @JoinColumn(name = "owner_id")
    //@OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Cat> cats;

    /*@PrePersist
    private void prePersist() {
        cats.forEach(c -> c.setOwner(this));
    }*/

    /*@PreRemove
    private void preRemove() {
        System.out.println("HUH!");
        cats.forEach(c -> c.setOwner(null));
    }

    @PostRemove
    private void postRemove() {
        cats.forEach(c -> c.setOwner(null));
    }*/

    public Owner() {
    }

    public Owner(String name) {
        this.name = name;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        var owner = (Owner)o;
        return Objects.equals(id, owner.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "birthDate = " + birthDate + ")";
    }
}
