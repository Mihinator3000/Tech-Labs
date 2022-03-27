package models;

import enums.Color;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    //@OnDelete(action = OnDeleteAction.NO_ACTION)
    private Owner owner;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cats_cats",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<Cat> friends;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cats_cats",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id"))
    private List<Cat> friendsOf;

    public List<Cat> getFriends() {
        return friends;
        /*return Stream.concat(
                        friends.stream(),
                        friendsOf.stream())
                .collect(Collectors.toList());*/
    }


    public Cat() {
    }

    public Cat(String name) {
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
/*@ManyToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<Cat> friends;*/



    public void setFriends(List<Cat> friends) {
        this.friends = friends;
    }
}
