package ru.gymbot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.gymbot.config.Settings;
import ru.gymbot.constans.bot.BotMessageEnum;
import ru.gymbot.entities.User;
import ru.gymbot.exceptions.NoSuchUserException;
import ru.gymbot.repositories.RoleRepository;
import ru.gymbot.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException(BotMessageEnum.EXCEPTION_TASKS_WTF_MESSAGE.getMessage()));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * метод проверяет пользователя в базе, если его нет, то записывает
     * @param message входящее сообщение
     */
    public void saveUserInDB(Message message) {
        if (userRepository.findById(message.getChatId()).isEmpty()) {
            User user = new User();
            user.setId(message.getFrom().getId());
            user.setBot(message.getFrom().getIsBot());
            user.setFirstName(message.getFrom().getFirstName());
            user.setLastName(message.getFrom().getLastName());
            user.setUsername(message.getFrom().getUserName());
            user.setRole(roleRepository.getReferenceById(Settings.DEFAULT_ROLE));
            saveUser(user);
        }
    }
}
