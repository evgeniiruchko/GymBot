package ru.gymbot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gymbot.entities.Training;
import ru.gymbot.repositories.TrainingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public List<Training> findAllGroupTraining() {
        return trainingRepository.findAllByIsIndividual(false);
    }
}
