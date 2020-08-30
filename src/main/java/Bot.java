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
                    try {
                        execute(new SendMessage(message.getChatId(), "Чтобы посмотреть расписание, используй /timetable"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "/info": {
                    try {
                        execute(new SendMessage(message.getChatId(), "Чтобы управлять мной используй следующие команды:\n\n" +
                                "/timetable - Вывод расписания занятий на каждый день\n"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                case "/timetable": {
                    try {
                        execute(sendTimetableMessage(update.getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                default: {
                    try {
                        execute(new SendMessage(message.getChatId(), "Упс... Что-то пошло не так"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
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
                                getTimetable(update.getCallbackQuery().getData()))
                                .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;

            }
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

    private void printTimetable(StringBuilder sb, Lesson lesson) {
        sb.append("\n").append(lesson.getName()).append(" ").append(lesson.getType())
                .append("\nПреподаватель:\n").append(lesson.getTeacher1().toString()).append("      ").append(lesson.getTeacher2().toString())
                .append("\nАудитория: ").append(lesson.getClassroom())
                .append("\n").append(lesson.getContinues()).append("\n----------------------------------------------------------------");
    }

    private int getWeekNumber() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private String getTimetable(String data) {
        boolean even = getWeekNumber() % 2 == 0;
        StringBuilder sb = new StringBuilder();
        switch (data) {
            case "понедельник": {
                sb.append("Расписание на понедельник:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.MONDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.MONDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
            case "вторник": {
                sb.append("Расписание на вторник:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.TUESDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.TUESDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
            case "среда": {
                sb.append("Расписание на среду:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.WEDNESDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.WEDNESDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
            case "четверг": {
                sb.append("Расписание на четверг:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.THURSDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.THURSDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
            case "пятница": {
                sb.append("Расписание на пятницу:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.FRIDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.FRIDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
            case "суббота": {
                sb.append("Расписание на субботу:\n");
                if (even) {
                    for (Lesson lesson : UnevenTimetable.SATURDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                } else {
                    for (Lesson lesson : EvenTimetable.SATURDAY.getList()) {
                        printTimetable(sb, lesson);
                    }
                }
                break;
            }
        }
        return sb.toString();
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
