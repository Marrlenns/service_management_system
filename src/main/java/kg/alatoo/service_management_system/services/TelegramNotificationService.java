package kg.alatoo.service_management_system.services;

import kg.alatoo.service_management_system.entities.Student;
import kg.alatoo.service_management_system.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class TelegramNotificationService {

    private final String apiUrlBase;
    private final HttpClient client;
    private final StudentRepository studentRepository;

    public TelegramNotificationService(
            @Value("${telegram.bot.token}") String botToken,
            StudentRepository studentRepository
    ) {
        this.apiUrlBase = "https://api.telegram.org/bot" + botToken;
        this.client = HttpClient.newHttpClient();
        this.studentRepository = studentRepository;
    }

    /**
     * Отправка произвольного текста конкретному chatId.
     */
    public void sendMessage(long chatId, String text) {
        try {
            String body = "chat_id=" + chatId +
                    "&text=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrlBase + "/sendMessage"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            client.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            System.err.println("Ошибка отправки в Telegram: " + e.getMessage());
        }
    }

    /**
     * Удобный метод: отправить талон студенту по его ID.
     */
    public void sendTicketToStudent(Long studentId, String ticketCode, int categoryIndex) {
        if (studentId == null) {
            return;
        }

        Optional<Student> opt = studentRepository.findById(studentId);
        if (opt.isEmpty()) {
            System.out.println("sendTicketToStudent: студент с id=" + studentId + " не найден.");
            return;
        }

        Student student = opt.get();
        Long chatId = student.getTelegramChatId();
        if (chatId == null) {
            System.out.println("sendTicketToStudent: у студента id=" + studentId +
                    " нет привязанного telegram_chat_id.");
            return;
        }

        String fullName = (student.getFirstName() != null ? student.getFirstName() : "") +
                " " +
                (student.getLastname() != null ? student.getLastname() : "");

        String message = "🎟 Ваш талон: *" + ticketCode + "*\n" +
                "Категория: " + categoryIndex + "\n" +
                "Студент: " + fullName.trim();

        sendMessage(chatId, message);
    }
}
