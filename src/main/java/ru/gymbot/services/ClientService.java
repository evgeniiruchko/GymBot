package ru.gymbot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gymbot.constans.bot.BotMessageEnum;
import ru.gymbot.entities.Client;
import ru.gymbot.repositories.ClientRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Client getClientByPhone(String phone) {
        return clientRepository.findById(phone)
                .orElseThrow(() -> new NoSuchElementException(BotMessageEnum.EXCEPTION_CLIENT_NOT_FOUND.getMessage()));
    }
}
