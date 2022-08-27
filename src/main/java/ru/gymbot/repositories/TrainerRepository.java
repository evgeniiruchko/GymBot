package ru.gymbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gymbot.entities.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}