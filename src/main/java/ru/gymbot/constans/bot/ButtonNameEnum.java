package ru.gymbot.constans.bot;

/**
 * Названия кнопок основной клавиатуры
 */
public enum ButtonNameEnum {
    HELP_BUTTON("Помощь"),
    START_BUTTON("Добро пожаловать!"),
    AUTH_BUTTON("Авторизоваться по номеру телефона");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}