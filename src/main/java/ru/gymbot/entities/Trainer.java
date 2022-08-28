package ru.gymbot.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "trainers")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "first_name", length = 20)
    private String firstName;

    @Column(name = "middle_name", length = 20)
    private String middleName;

    @NotNull
    @Column(name = "last_name", length = 20)
    private String lastName;

    @Column(name = "photo", length = 200)
    private String photo;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;
}