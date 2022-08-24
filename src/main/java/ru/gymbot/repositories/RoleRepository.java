package ru.gymbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gymbot.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
