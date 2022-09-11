package ru.gymbot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.gymbot.config.Settings;
import ru.gymbot.constans.bot.BotMessageEnum;
import ru.gymbot.constans.bot.ButtonNameEnum;
import ru.gymbot.entities.Client;
import ru.gymbot.entities.Trainer;
import ru.gymbot.entities.Training;
import ru.gymbot.entities.User;
import ru.gymbot.services.ClientService;
import ru.gymbot.services.TrainerService;
import ru.gymbot.services.TrainingService;
import ru.gymbot.services.UserService;
import ru.gymbot.telegram.GymBot;
import ru.gymbot.telegram.keyboards.MenuKeyboard;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MessageHandler {
    MenuKeyboard menuKeyboard;
    UserService userService;
    ClientService clientService;
    TrainerService trainerService;
    TrainingService trainingService;
    @Lazy
    GymBot gymBot;

    public BotApiMethod<?> answerMessage(Message message) {
        Long chatId = message.getChatId();
        if (message.hasContact()) {
            return authorize(chatId, message.getContact());
        }

        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start") || inputText.equals(ButtonNameEnum.START_BUTTON.getButtonName())) {
            userService.saveUserInDB(message);
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.HELP_BUTTON.getButtonName())) {
            return getHelpMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.AUTH_BUTTON.getButtonName())) {
            return authorize(chatId, message.getContact());
        } else if (inputText.equals(ButtonNameEnum.GET_TRAINERS.getButtonName())) {
            return getTrainers(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_TRAININGS.getButtonName())) {
            return getTrainings(chatId);
        } else {
            return new SendMessage(chatId.toString(), BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    /**
     * Стартовое сообщение.
     *
     * @param chatId - ИД пользователя
     * @return сообщение
     */
    private SendMessage getStartMessage(Long chatId) {
        return generateSendMessage(chatId, BotMessageEnum.START_MESSAGE.getMessage());
    }

    /**
     * Сообщение при нажатии на кнопку "Помощь".
     *
     * @param chatId - ИД пользователя
     * @return сообщение
     */
    private SendMessage getHelpMessage(Long chatId) {
        return generateSendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
    }

    /**
     * Метод возвращает информацию обо всех тренерах. Если есть фото, то отправляет фото с описанием,
     * если фото нет, то обычный текст. Для каждого тренера отдельное сообщение.
     * @param chatId ИД пользователя
     * @return список тренеров
     */
    private SendMessage getTrainers(Long chatId) {
        List<Trainer> trainers = trainerService.findAllTrainers();
        if (trainers.isEmpty()) {
            return generateSendMessage(chatId, BotMessageEnum.EXCEPTION_EMPTY_TRAINERS.getMessage());
        }
        trainers.forEach(trainer -> {
            String text = trainer.getFirstName() + "\n\n" +
                    trainer.getDescription();
            File file = new File(Settings.PATH_TRAINER_PHOTOS + trainer.getPhoto());
            if (file.isFile() && text.length() <= Settings.MAX_LENGTH_DESCRIPTION_PHOTO) {
                gymBot.sendPhoto(chatId, text, file.getPath());
            } else {
                gymBot.sendMessage(chatId, generateSendMessage(chatId, text));
            }
        });
        return new SendMessage();
    }

    private SendMessage getTrainings(Long chatId) {
        List<Training> trainings = trainingService.findAllGroupTraining();
        if (trainings.isEmpty()) {
            return generateSendMessage(chatId, BotMessageEnum.EXCEPTION_EMPTY_TRAININGS.getMessage());
        }
        trainings.forEach(training -> {
            String text = training.getTitle() + "\n\n" +
                    training.getDescription();
            File file = new File(Settings.PATH_TRAINING_PHOTOS + training.getPhoto());
            if (file.isFile() && text.length() <= Settings.MAX_LENGTH_DESCRIPTION_PHOTO) {
                gymBot.sendPhoto(chatId, text, file.getPath());
            } else {
                gymBot.sendMessage(chatId, generateSendMessage(chatId, text));
            }
        });
        return new SendMessage();
    }


    /**
     * метод генерирует сообщение и возвращает его и клавиатуру
     *
     * @param chatId  ИД пользователя
     * @param message тест сообщения
     * @return Сообщение для отправки
     */
    private SendMessage generateSendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboard(chatId));
        return sendMessage;
    }

    /**
     * Метод принимает контакт, проверяет, принадлежит ли номер телефона контакта отправителю
     * Потом ищет клиента по номеру телефона, если находит,
     * то в User'а записывает номер телефона, и пользователь считается авторизованным
     *
     * @param chatId  ИД пользователя
     * @param contact контакт
     * @return сообщение о результате авторизации
     */
    private SendMessage authorize(Long chatId, Contact contact) {
        if (!Objects.equals(chatId, contact.getUserId())) {
            return generateSendMessage(chatId, BotMessageEnum.ALONE_CONTACT.getMessage());
        }
        Client client = clientService.getClientByPhone(contact.getPhoneNumber());
        User user = userService.findUserById(chatId);
        user.setClient(client);
        userService.saveUser(user);
        return generateSendMessage(chatId, String.format(BotMessageEnum.SUCCESSFUL_AUTHORIZATION.getMessage(), client.getFirstName()));
    }

    /**
     * Метод определяет, авторизирован ли пользователь, и в зависимости от этого возвращает нужную клавиатуру
     *
     * @param chatId - ИД пользователя
     * @return клавиатуру
     */
    private ReplyKeyboardMarkup keyboard(Long chatId) {
        User user = userService.findUserById(chatId);
        if (Optional.ofNullable(user.getClient()).isPresent()) {
            return menuKeyboard.getMainMenuKeyboard(true);
        } else {
            return menuKeyboard.getMainMenuKeyboard(false);
        }
    }
}
