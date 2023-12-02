package com.fitness.courses.global.constants;

public final class HTTPResponseConstants {

    private HTTPResponseConstants() {
    }

    /**
     * Код успешного HTTP-ответа
     */
    public static final int OK = 200;

    /**
     * Код HTTP-ответа для случая, если запрос некорректен
     */
    public static final int BAD_REQUEST = 400;

    /**
     * Код HTTP-ответа для случая, если пользователь не авторизован
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * Код HTTP-ответа для случая, если доступ к ресурсу запрещен
     */
    public static final int FORBIDDEN = 403;

    /**
     * Код HTTP-ответа для случая, если ресурс не найден
     */
    public static final int NOT_FOUND = 404;

    /**
     * Код HTTP-ответа для случая, если произошел конфликт запроса с состоянием сервера
     */
    public static final int CONFLICT = 409;

    /**
     * Код HTTP-ответа, если произошла внутрення ошибка сервера
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
}
