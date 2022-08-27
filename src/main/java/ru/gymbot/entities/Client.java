package ru.gymbot.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number", nullable = false, length = 15)
    private String id;

    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "middle_name", length = 20)
    private String middleName;

    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "sex", length = 10)
    private String sex;

    @Column(name = "card_number")
    private Long cardNumber;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
//    @ToString.Exclude
//    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}