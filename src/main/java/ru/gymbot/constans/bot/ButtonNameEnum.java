package ru.gymbot.constans.bot;

/**
 * Названия кнопок основной клавиатуры
 */
public enum ButtonNameEnum {
    HELP_BUTTON("Помощь"),
    START_BUTTON("Добро пожаловать!"),
    AUTH_BUTTON("Авторизоваться по номеру телефона"),
    GET_TRAINERS("Наши тренеры");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}