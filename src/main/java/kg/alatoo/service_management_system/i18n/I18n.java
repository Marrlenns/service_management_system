package kg.alatoo.service_management_system.i18n;

public final class I18n {

    private I18n() {}

    public static String mainStudent(Language lang) {
        switch (lang) {
            case EN: return "Student";
            case RU:
            default: return "Студент";
        }
    }

    public static String mainTeacher(Language lang) {
        return switch (lang) {
            case KY -> "Мугалим";
            case EN -> "Teacher";
            default -> "Преподаватель";
        };
    }

    public static String mainOther(Language lang) {
        return switch (lang) {
            case KY -> "Ата-эне / Башкалар";
            case EN -> "Parent / Other";
            default -> "Родитель / Другое";
        };
    }

    public static String studentIdTitle(Language lang) {
        return switch (lang) {
            case KY -> "Студент ID";
            case EN -> "Student ID";
            default -> "ID студента";
        };
    }

    public static String studentIdPrompt(Language lang) {
        return switch (lang) {
            case KY -> "ID жазыңыз";
            case EN -> "Enter your ID";
            default -> "Введите ваш ID";
        };
    }

    public static String teacherEmailTitle(Language lang) {
        return switch (lang) {
            case KY -> "Мугалимдин email'и";
            case EN -> "Teacher email";
            default -> "Email преподавателя";
        };
    }

    public static String teacherEmailPrompt(Language lang) {
        return switch (lang) {
            case KY -> "Email жазыңыз";
            case EN -> "Enter your email";
            default -> "Введите ваш email";
        };
    }

    public static String buttonEnter(Language lang) {
        return switch (lang) {
            case KY -> "Кирүү";
            case EN -> "Enter";
            default -> "Вход";
        };
    }

    public static String buttonBack(Language lang) {
        return switch (lang) {
            case KY -> "Артка";
            case EN -> "Back";
            default -> "Назад";
        };
    }

    public static String buttonDone(Language lang) {
        return switch (lang) {
            case KY -> "Бүттү";
            case EN -> "Done";
            default -> "Готово";
        };
    }

    public static String welcomePlain(Language lang) {
        return switch (lang) {
            case KY -> "Кош келиңиз";
            case EN -> "Welcome";
            default -> "Добро пожаловать";
        };
    }

    public static String welcomeWithName(Language lang, String name) {
        return switch (lang) {
            case KY -> "Кош келиңиз, " + name;
            case EN -> "Welcome " + name;
            default -> "Добро пожаловать, " + name;
        };
    }

    public static String ticketTitle(Language lang) {
        return switch (lang) {
            case KY -> "Сиздин номериңиз";
            case EN -> "Your number";
            default -> "Ваш номер";
        };
    }

    public static String ticketHint(Language lang) {
        return switch (lang) {
            case KY -> "Сураныч, кезегиңизди күтүңүз";
            case EN -> "Please wait for your turn";
            default -> "Пожалуйста, ожидайте своей очереди";
        };
    }

    public static String categoryLabel(Language lang, int index) {
        return categoryTitle(lang, index);
    }

    public static String roleStudent(Language lang) {
        return switch (lang) {
            case EN -> "Student";
            default -> "Студент";
        };
    }

    public static String roleTeacher(Language lang) {
        return switch (lang) {
            case KY -> "Мугалим";
            case EN -> "Teacher";
            default -> "Преподаватель";
        };
    }

    public static String roleOther(Language lang) {
        return switch (lang) {
            case KY -> "Конок";
            case EN -> "Guest";
            default -> "Гость";
        };
    }

    public static String alertEnterId(Language lang) {
        return switch (lang) {
            case KY -> "ID жазыңыз";
            case EN -> "Please enter ID";
            default -> "Введите ID";
        };
    }

    public static String alertIdMustBeNumber(Language lang) {
        return switch (lang) {
            case KY -> "ID сан болушу керек";
            case EN -> "ID must be a number";
            default -> "ID должен быть числом";
        };
    }

    public static String alertStudentNotFound(Language lang) {
        return switch (lang) {
            case KY -> "Мындай ID менен студент табылган жок";
            case EN -> "Student with this ID was not found";
            default -> "Студент с таким ID не найден";
        };
    }

    public static String alertEnterEmail(Language lang) {
        return switch (lang) {
            case KY -> "Email жазыңыз";
            case EN -> "Please enter email";
            default -> "Введите email";
        };
    }

    public static String alertTeacherNotFound(Language lang) {
        return switch (lang) {
            case KY -> "Мындай email менен мугалим табылган жок";
            case EN -> "Teacher with this email was not found";
            default -> "Преподаватель с таким email не найден";
        };
    }

    public static String alertLoginOrOtherFirst(Language lang) {
        return switch (lang) {
            case KY -> "Адегенде кирүү же Other тандаңыз";
            case EN -> "Please login first or choose Other";
            default -> "Сначала выполните вход или выберите Other";
        };
    }

    public static String alertDbErrorPrefix(Language lang) {
        return switch (lang) {
            case KY -> "Маалымат базасына кирүү катасы:\n";
            case EN -> "Database error:\n";
            default -> "Ошибка доступа к БД:\n";
        };
    }

    public static String alertCategoryErrorPrefix(Language lang) {
        return switch (lang) {
            case KY -> "Категория коду берилгенде ката:\n";
            case EN -> "Error while generating ticket code:\n";
            default -> "Ошибка при выдаче кода категории:\n";
        };
    }

    public static String categoryTitle(Language lang, int index) {
        return switch (lang) {
            case EN -> switch (index) {
                case 1 -> "Documents";
                case 2 -> "Academic life";
                case 3 -> "AIU System";
                case 4 -> "Finance";
                case 5 -> "Student Life";
                default -> "Category " + index;
            };
            case RU -> switch (index) {
                case 1 -> "Документы";
                case 2 -> "Академическая жизнь";
                case 3 -> "Система AIU";
                case 4 -> "Финансы";
                case 5 -> "Студенческая жизнь";
                default -> "Категория " + index;
            };
            case KY -> switch (index) {
                case 1 -> "Документтер";
                case 2 -> "Академиялык жашоо";
                case 3 -> "AIU системасы";
                case 4 -> "Каржы";
                case 5 -> "Студенттик жашоо";
                default -> "Категория " + index;
            };
        };
    }

    public static String categoryDescription(Language lang, int index) {
        return switch (lang) {
            case EN -> switch (index) {
                case 1 -> """
                    Includes:
                    • ID cards
                    • study certificates
                    • transcripts
                    • applications (withdrawal, academic leave, etc.)
                    • military certificate requests
                    • diploma–related requests
                    """;
                case 2 -> """
                    Includes:
                    • withdrawal
                    • readmission
                    • internal and external transfer
                    • change of faculty or major
                    • academic performance (grades, schedule, retakes)
                    • credit compatibility
                    """;
                case 3 -> """
                    Includes:
                    • MyAlatoo platform issues
                    • corporate email access
                    • Wi-Fi problems
                    • website errors
                    • registration and login problems
                    """;
                case 4 -> """
                    Includes:
                    • contract payments
                    • invoices and receipts
                    • scholarship status
                    • financial clearance
                    """;
                case 5 -> """
                    Includes:
                    • events and student clubs
                    • participation requests
                    • campus activities
                    • announcements and engagement
                    """;
                default -> "";
            };
            case RU -> switch (index) {
                case 1 -> """
                    Включает:
                    • студенческие ID-карты
                    • справки с места учёбы
                    • транскрипты
                    • заявления (отчисление, академ. отпуск и т.п.)
                    • запросы военный билет / справка
                    • запросы, связанные с дипломом
                    """;
                case 2 -> """
                    Включает:
                    • отчисление
                    • восстановление
                    • внутренний и внешний перевод
                    • смену факультета или специальности
                    • академическую успеваемость (оценки, расписание, пересдачи)
                    • совместимость кредитов
                    """;
                case 3 -> """
                    Включает:
                    • проблемы с платформой MyAlatoo
                    • доступ к корпоративной почте
                    • проблемы с Wi-Fi
                    • ошибки на сайте
                    • проблемы с регистрацией и входом в систему
                    """;
                case 4 -> """
                    Включает:
                    • оплату по контракту
                    • счета и квитанции
                    • статус стипендии
                    • финансовый клиренс
                    """;
                case 5 -> """
                    Включает:
                    • мероприятия и студенческие клубы
                    • заявки на участие
                    • активность на кампусе
                    • объявления и вовлечённость студентов
                    """;
                default -> "";
            };
            case KY -> switch (index) {
                case 1 -> """
                    Камтыйт:
                    • студенттик ID карталар
                    • окуу жайдан маалымкаттар
                    • транскрипттер
                    • арыздар (чыгаруу, академиялык өргүү ж.б.)
                    • аскердик билет боюнча өтүнүчтөр
                    • дипломго байланышкан суроолор
                    """;
                case 2 -> """
                    Камтыйт:
                    • чыгаруу
                    • калыбына келтирүү
                    • ички жана тышкы которуу
                    • факультет же адистикти алмаштыруу
                    • академиялык жетишкендик (баалар, расписание, кайра тапшыруу)
                    • кредиттердин шайкештиги
                    """;
                case 3 -> """
                    Камтыйт:
                    • MyAlatoo платформасына байланышкан көйгөйлөр
                    • корпоративдик почтага кирүү
                    • Wi-Fi көйгөйлөрү
                    • сайттагы каталар
                    • катталуу жана кирүү маселелери
                    """;
                case 4 -> """
                    Камтыйт:
                    • контракттык төлөмдөр
                    • эсеп-фактуралар жана квитанциялар
                    • стипендиянын статусу
                    • финансылык клиренс
                    """;
                case 5 -> """
                    Камтыйт:
                    • иш-чаралар жана студенттик клубдар
                    • катышуу өтүнүчтөрү
                    • кампустагы активдүүлүк
                    • билдирүүлөр жана студенттик катышуу
                    """;
                default -> "";
            };
        };
    }

    public static String buttonContinue(Language lang) {
        return switch (lang) {
            case EN -> "Continue";
            case RU -> "Продолжить";
            case KY -> "Улантуу";
        };
    }

    public static String buttonCancel(Language lang) {
        return switch (lang) {
            case EN -> "Cancel";
            case RU -> "Отмена";
            case KY -> "Бекитпөө";
        };
    }

    public static String ticketPhotoHint(Language lang) {
        return switch (lang) {
            case KY -> "Эгерде сиз биздин Telegram-ботко катталбасаңыз, талондун номерин унутуп калбаш үчүн сүрөткө тартып коюңуз.";
            case EN -> "If you are not subscribed to our Telegram bot, we recommend taking a photo of your ticket number so you don’t forget it.";
            default -> "Если вы не подписаны на нашего Telegram-бота, рекомендуем сфотографировать номер талона, чтобы не забыть его.";
        };
    }

    public static String categorySelectHint(Language lang) {
        return switch (lang) {
            case EN -> "Please choose the category that best matches your question.";
            case KY -> "Сураныч, сурооңузга эң туура келген категорияны тандаңыз.";
            default -> "Пожалуйста, выберите категорию, которая лучше всего подходит к вашему вопросу.";
        };
    }


}
