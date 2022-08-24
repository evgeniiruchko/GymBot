package ru.gymbot.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.gymbot.constans.bot.BotMessageEnum;
import ru.gymbot.constans.bot.ButtonNameEnum;
import ru.gymbot.entities.Client;
import ru.gymbot.entities.User;
import ru.gymbot.services.ClientService;
import ru.gymbot.services.UserService;
import ru.gymbot.telegram.GymBot;
import ru.gymbot.telegram.keyboards.AuthMenuKeyboard;
import ru.gymbot.telegram.keyboards.UnAuthMenuKeyboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;


@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MessageHandler {
    UnAuthMenuKeyboard unAuthMenuKeyboard;
    AuthMenuKeyboard authMenuKeyboard;
    UserService userService;
    ClientService clientService;
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
            return getStartMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.AUTH_BUTTON.getButtonName())) {
            return authorize(chatId, message.getContact());
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
        return generateSendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
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
        return generateSendMessage(chatId, String.format(BotMessageEnum.SUCCEFFUL_AUTHORIZATION.getMessage(), client.getFirstName(), client.getMiddleName()));
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
            return authMenuKeyboard.getMainMenuKeyboard();
        } else {
            return unAuthMenuKeyboard.getMainMenuKeyboard();
        }
    }
}
