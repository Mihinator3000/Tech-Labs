package org.itmo.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.itmo.models.auth.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "owners")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany
    @Cascade({
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.SAVE_UPDATE
    })
    @JoinColumn(name = "owner_id")
    @ToString.Exclude
    private List<Cat> cats = new ArrayList<>();

    @OneToOne(mappedBy = "owner")
    @ToString.Exclude
    private User user;

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
}
