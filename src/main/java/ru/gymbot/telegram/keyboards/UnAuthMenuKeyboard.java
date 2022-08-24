package ru.gymbot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.gymbot.constans.bot.ButtonNameEnum;

import java.util.ArrayList;
import java.util.List;

@Component
public class UnAuthMenuKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton authButton = new KeyboardButton();
        authButton.setText(ButtonNameEnum.AUTH_BUTTON.getButtonName());
        authButton.setRequestContact(true);
        row1.add(authButton);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}