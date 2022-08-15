package ru.gymbot.constans.bot;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum BotMessageEnum {
    //ответы на команды с клавиатуры
    HELP_MESSAGE("""
            \uD83D\uDC4B Ну, привет\uD83E\uDD78

            ❗ *Что Вы можете сделать:*
            ✅ пока ничего
            Удачи!

            Воспользуйтесь клавиатурой, чтобы начать работу\uD83D\uDC47"""),

    //ошибки при обработке callback-ов
    EXCEPTION_BAD_BUTTON_NAME_MESSAGE("Неверное значение кнопки. Крайне странно. Попробуйте позже"),
    EXCEPTION_DICTIONARY_NOT_FOUND_MESSAGE("Нет такого"),
    EXCEPTION_DICTIONARY_WTF_MESSAGE("Неожиданная ошибка. Сам в шоке"),
    EXCEPTION_TASKS_WTF_MESSAGE("Неожиданная ошибка. Сам в шоке"),
    EXCEPTION_TEMPLATE_WTF_MESSAGE("Неожиданная ошибка. Сам в шоке"),

    //прочие ошибки
    EXCEPTION_ILLEGAL_MESSAGE("Нет, к такому меня не готовили! Я всего лишь робот"),
    EXCEPTION_WHAT_THE_FUCK("Что-то пошло не так. Обратитесь к программисту");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}