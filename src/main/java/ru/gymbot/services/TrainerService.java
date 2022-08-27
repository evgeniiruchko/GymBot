package ru.gymbot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gymbot.entities.Trainer;
import ru.gymbot.repositories.TrainerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }
}
