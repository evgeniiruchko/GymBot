package ru.gymbot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gymbot.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

}