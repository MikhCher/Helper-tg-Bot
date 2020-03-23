import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.*;

public class Bot extends TelegramLongPollingBot {
    private static final String BOT_NAME = "BSTU_Helper_Bot";
    private static final String TOKEN = "1025654575:AAFkxhiwVyRmdxadrR41AeJjoWmgjdNOHGg";
    private static final String ADMIN_ID = "489188261";
    private static final long ADMIN_ID_LONG = 489188261;

    private Map<String, Profile> users = new HashMap<String, Profile>();

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
        public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            switch (message.getText()) {
                case "/start": {
                    registration(message);
                    break;
                }
                case "/info": {
                    if (users.containsKey(message.getChatId().toString())) {
                    sendMsg(message, "Чтобы управлять мной используй следующие команды:\n\n" +
                            "/timetable - Вывод расписания занятий на каждый день");
                    } else {
                        sendMsg(message, "У тебя нет прав для этой команды :(");
                    }
                    break;
                }
                case "/timetable": {
                    if (users.containsKey(message.getChatId().toString())) {
                        try {
                            execute(sendTimetableMessage(update.getMessage().getChatId()));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        sendMsg(message, "У тебя нет прав для этой команды :(");
                    }
                    break;
                }
                default: {
                    if (message.getText().startsWith("/reg") && !users.containsKey(message.getChatId().toString())) {
                        String text = message.getText();
                        String[] data = new String[3];



                        String[] info = text.split(" ");
                        for (int i = 1; i < 4; i++) {
                            if (info[i] != null) {
                                data[i - 1] = info[i];
                            }
                        }

                        try {
                            execute(new SendMessage(message.getChatId(), "Твои данные были отправлены администратору на проверку, дождись его решения"));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }

                        SendMessage sendMessage = new SendMessage(ADMIN_ID, "Предоставить доступ к базе:\n" +
                                "" + data[0] + " " + data[1] + " " + data[2] +
                                "\n" + message.getChatId());

                        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
                        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
                        List<InlineKeyboardButton> row1 = new ArrayList<>();
                        InlineKeyboardButton accept = new InlineKeyboardButton(Smile.SELECT.get());
                        InlineKeyboardButton reject = new InlineKeyboardButton(Smile.X.get());
                        accept.setCallbackData("принять");
                        reject.setCallbackData("отклонить");
                        row1.add(accept);
                        row1.add(reject);
                        rows.add(row1);
                        keyboard.setKeyboard(rows);

                        sendMessage.setReplyMarkup(keyboard);

                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    sendMsg(message, "Упс... Что-то пошло не так");
                    break;
                }
            }
        } else if(update.hasCallbackQuery()){
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            deleteMessage.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
            try {
                execute(deleteMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            String data = update.getCallbackQuery().getData();
            switch (data) {
                case "понедельник":
                case "вторник":
                case "среда":
                case "четверг":
                case "пятница":
                case "суббота":
                    try {
                        execute(new SendMessage().setText(
                                getEvenTimetable(update.getCallbackQuery().getData()))
                                .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Начало регистрации":
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText("Отлично, перейдем к заполнению анкеты\n" +
                            "ВАЖНО! Заполни свои анкету строго по образцу\n\n" +
                            "/reg Иванов Иван 01.01.2000");
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "принять": {
                    String[] words = update.getCallbackQuery().getMessage().getText().split("\n");
                    String key = words[words.length - 1];
                    String[] user = words[words.length - 2].split(" ");
                    Profile.Post post = key.equals(ADMIN_ID) ? Profile.Post.ADMIN : Profile.Post.STUDENT;
                    users.put(key, new Profile(user[0], user[1], user[2], post));

                    try {
                        execute(new SendMessage(key, "Поздравляем, ты получил доступ к моим функциям\n" +
                                "Для подробной информации используй /info"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "отклонить": {
                    String[] words = update.getCallbackQuery().getMessage().getText().split("\n");
                    String key = words[words.length - 1];
                    try {
                        execute(new SendMessage(key, "К сожалению тебе было отказано в доступе :(\n" +
                                "Если возникла какая-то ошибка напиши в личку @MChered"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private void registration(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Итак, чтобы начать пользоваться мной, тебе надо зарегестрироваться\n" +
                "Не волнуйся, это займет немного времени\n" +
                "Как будешь готов, нажми на кнопку ниже");

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton registerButton = new InlineKeyboardButton("Начать регистрацию");
        registerButton.setCallbackData("Начало регистрации");

        List<InlineKeyboardButton> keyboardList = new ArrayList<>();
        keyboardList.add(registerButton);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardList);
        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage sendTimetableMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton mondayButton = new InlineKeyboardButton();
        InlineKeyboardButton tuesdayButton = new InlineKeyboardButton();
        InlineKeyboardButton wednesdayButton = new InlineKeyboardButton();
        InlineKeyboardButton thursdayButton = new InlineKeyboardButton();
        InlineKeyboardButton fridayButton = new InlineKeyboardButton();
        InlineKeyboardButton saturdayButton = new InlineKeyboardButton();

        mondayButton.setText("Понедельник");
        mondayButton.setCallbackData("понедельник");
        tuesdayButton.setText("Вторник");
        tuesdayButton.setCallbackData("вторник");
        wednesdayButton.setText("Среда");
        wednesdayButton.setCallbackData("среда");
        thursdayButton.setText("Четверг");
        thursdayButton.setCallbackData("четверг");
        fridayButton.setText("Пятница");
        fridayButton.setCallbackData("пятница");
        saturdayButton.setText("Суббота");
        saturdayButton.setCallbackData("суббота");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        keyboardButtonsRow1.add(mondayButton);
        keyboardButtonsRow1.add(thursdayButton);
        keyboardButtonsRow2.add(tuesdayButton);
        keyboardButtonsRow2.add(fridayButton);
        keyboardButtonsRow3.add(wednesdayButton);
        keyboardButtonsRow3.add(saturdayButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("На какой день показать расписание?").setReplyMarkup(inlineKeyboardMarkup);
    }

    private String getEvenTimetable(String data) {
        StringBuilder sb = new StringBuilder();
        switch (data) {
            case "понедельник": {
                sb.append("Расписание на понедельник:\n");
                for (Lesson lesson : EvenTimetable.MONDAY.getList()) {
                    sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                            .append("\nПреподаватель:\n").append(lesson.getTeacher().toString())
                            .append("\nАудитория: ").append(lesson.getClassroom())
                            .append("\n").append(lesson.getContinues()).append("\n--------------------------------------------------");
                }
                break;
            }
            case "вторник": {
                sb.append("Расписание на вторник:\n");
                for (Lesson lesson : EvenTimetable.TUESDAY.getList()) {
                    sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                            .append("\nПреподаватель:\n").append(lesson.getTeacher().toString())
                            .append("\nАудитория: ").append(lesson.getClassroom())
                            .append("\n").append(lesson.getContinues()).append("\n--------------------------------------------------");
                }
                break;
            }
            case "среда": {
                sb.append("Расписание на среду:\n");
                for (Lesson lesson : EvenTimetable.WEDNESDAY.getList()) {
                    sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                            .append("\nПреподаватель:\n").append(lesson.getTeacher().toString())
                            .append("\nАудитория: ").append(lesson.getClassroom())
                            .append("\n").append(lesson.getContinues()).append("\n--------------------------------------------------");
                }
                break;
            }
            case "четверг": {
                sb.append("Расписание на четверг:\n");
                for (Lesson lesson : EvenTimetable.THURSDAY.getList()) {
                    sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                            .append("\nПреподаватель:\n").append(lesson.getTeacher().toString())
                            .append("\nАудитория: ").append(lesson.getClassroom())
                            .append("\n").append(lesson.getContinues()).append("\n--------------------------------------------------");
                }
                break;
            }
            case "пятница": {
                sb.append("Расписание на пятницу:\n");
                for (Lesson lesson : EvenTimetable.FRIDAY.getList()) {
                    sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                            .append("\nПреподаватель:\n").append(lesson.getTeacher().toString())
                            .append("\nАудитория: ").append(lesson.getClassroom())
                            .append("\n").append(lesson.getContinues()).append("\n--------------------------------------------------");
                }
                break;
            }
            case "суббота": {
                sb.append("В эту субботу нет пар").append(Smile.RELIEVED.get()).append("\n");

                break;
            }
        }
        return sb.toString();
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        System.out.println(message.getChatId().toString());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }
}
