package ru.gymbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gymbot.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}