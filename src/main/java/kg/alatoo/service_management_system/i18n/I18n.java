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
        String num = String.valueOf(index);
        return switch (lang) {
            case EN -> "Category " + num;
            default -> "Категория " + num;
        };
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
}
