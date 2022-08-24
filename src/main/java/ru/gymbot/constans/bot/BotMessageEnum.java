package ru.gymbot.constans.bot;

/**
 * Текстовые сообщения, посылаемые ботом
 */
public enum BotMessageEnum {
    //ответы на команды с клавиатуры
    START_MESSAGE("""
            Привет!\uD83D\uDC4B Приседать будешь? \uD83C\uDFCB
            
            Я бы тоже поприседал, но я робот\uD83E\uDD16
            
            Воспользуйся клавиатурой снизу\uD83D\uDC47"""),
    HELP_MESSAGE("""
            ❗ *Что Вы можете сделать:*
            ✅ пока ничего

            Воспользуйся клавиатурой снизу\uD83D\uDC47"""),
    NON_COMMAND_MESSAGE("Меня этому не учили.\uD83E\uDD37\u200D♂️\nПожалуйста, воспользуйтесь клавиатурой\uD83D\uDC47"),
    ALONE_CONTACT("мне чужой телефон не нужен \uD83D\uDC6E\u200D♀️"),
    SUCCEFFUL_AUTHORIZATION("Здравствуйте, %s %s \uD83E\uDD1D\n ✅ Успешная авторизация"),

    //ошибки при обработке callback-ов
    EXCEPTION_CLIENT_NOT_FOUND("Я не нашёл клиента с таким номером телефона\n" +
            "Возможно, в анкете был указан другой номер"),
    EXCEPTION_TASKS_WTF_MESSAGE("Неожиданная ошибка. Сам в шоке"),

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