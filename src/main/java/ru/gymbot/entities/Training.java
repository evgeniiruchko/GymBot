package ru.gymbot.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "trainings")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "title", length = 50)
    private String title;

    @NotNull
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;

    @Column(name = "is_individual", columnDefinition = "boolean default false")
    private boolean isIndividual;

    @Column(name = "photo", length = 200)
    private String photo;
}