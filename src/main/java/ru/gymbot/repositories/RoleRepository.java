package ru.gymbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gymbot.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
