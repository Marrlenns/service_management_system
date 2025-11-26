package kg.alatoo.service_management_system.bot;

import kg.alatoo.service_management_system.entities.Student;
import kg.alatoo.service_management_system.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StudentLinkBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final String botToken;
    private final StudentRepository studentRepository;

    // Состояние: ждём ли от этого chatId ввод ID студента
    private final Map<Long, Boolean> waitingForStudentId = new ConcurrentHashMap<>();

    public StudentLinkBot(
            @Value("${telegram.bot.token}") String botToken,
            StudentRepository studentRepository
    ) {
        this.botToken = botToken;
        this.studentRepository = studentRepository;
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        // Нас интересуют только текстовые сообщения
        if (update == null || !update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText().trim();
        long chatId = update.getMessage().getChatId();

        // Команда /start — начало диалога
        if (text.equals("/start")) {
            waitingForStudentId.put(chatId, true);
            sendText(chatId,
                    "Здравствуйте! 👋\n" +
                            "Пожалуйста, отправьте ваш *ID студента* (только число).");
            return;
        }

        // Если бот ждёт ID от этого пользователя
        if (Boolean.TRUE.equals(waitingForStudentId.get(chatId))) {
            handleStudentIdInput(chatId, text);
        } else {
            // Если пользователь пишет что-то без /start или без состояния
            sendText(chatId,
                    "Чтобы привязать Telegram к системе очереди, сначала отправьте команду /start.");
        }
    }

    private void handleStudentIdInput(long chatId, String text) {
        long studentId;
        try {
            studentId = Long.parseLong(text);
        } catch (NumberFormatException ex) {
            sendText(chatId, "ID студента должен быть *числом*. Попробуйте ещё раз.");
            return;
        }

        Optional<Student> opt = studentRepository.findById(studentId);
        if (opt.isEmpty()) {
            sendText(chatId,
                    "Студент с ID `" + studentId + "` не найден.\n" +
                            "Проверьте ID и отправьте ещё раз.");
            return;
        }

        Student student = opt.get();
        student.setTelegramChatId(chatId);
        studentRepository.save(student);

        waitingForStudentId.put(chatId, false);

        String fullName = (student.getFirstName() != null ? student.getFirstName() : "") +
                " " +
                (student.getLastname() != null ? student.getLastname() : "");

        sendText(chatId,
                "Готово! ✅\n" +
                        "Ваш Telegram привязан к студенту:\n" +
                        fullName + " (ID: " + student.getId() + ").\n\n" +
                        "Теперь, когда вы будете брать талон в киоске,\n" +
                        "номер талона сможет приходить сюда, в Telegram.");
    }

    private void sendText(long chatId, String text) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode("Markdown")
                .build();

        try {
            telegramClient.execute(msg);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка отправки сообщения в Telegram: " + e.getMessage());
        }
    }

    // Просто логируем факт регистрации бота
    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Telegram-бот зарегистрирован. Состояние: " + botSession.isRunning());
    }
}
