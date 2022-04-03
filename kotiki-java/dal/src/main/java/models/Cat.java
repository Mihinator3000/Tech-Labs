package models;

import enums.Color;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import tools.DalException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "cats")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String breed;

    @Enumerated(EnumType.STRING)
    private Color color;

    @ManyToOne
    @Cascade({
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.SAVE_UPDATE
    })
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private Owner owner;

    @ManyToMany
    @Cascade({
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.SAVE_UPDATE
    })
    @JoinTable(name = "cats_cats",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @ToString.Exclude
    private List<Cat> friends = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "cats_cats",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<Cat> friendsOf = new ArrayList<>();

    @Builder
    public Cat(int id,
               @NonNull String name,
               LocalDate birthDate,
               String breed,
               Color color,
               Owner owner,
               List<Cat> friends) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
        setFriends(friends);
    }

    public List<Cat> getFriends() {
        return Stream.concat(
                        friends.stream(),
                        friendsOf.stream())
                .collect(Collectors.toUnmodifiableList());
    }

    public void setFriends(List<Cat> friends) {
        if (friends != null)
            this.friends = friends.stream().distinct().collect(Collectors.toList());

        if (friendsOf != null)
            friendsOf.clear();
    }

    public void addFriend(Cat friend) {
        if (friend == null)
            throw new NullPointerException();

        if (friends.contains(friend) ||
                friendsOf.contains(friend))
            throw new DalException("Entity already a friend of a cat");

        friends.add(friend);
    }

    public void deleteFriend(int friendId) {
        if (friends.removeIf(f -> f.id == friendId) ||
                friendsOf.removeIf(f -> f.id == friendId))
            return;

        throw new DalException("Entity was not deleted");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        var cat = (Cat)o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
