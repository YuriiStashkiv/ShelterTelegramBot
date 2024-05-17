package ldubgd.edu.ua.shelter.handlers;

import ldubgd.edu.ua.shelter.messagesender.MessageSender;
import ldubgd.edu.ua.shelter.models.Street;
import ldubgd.edu.ua.shelter.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ldubgd.edu.ua.shelter.algorithms.Algorithms.calculateDistance;

@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private StreetService streetService;
    @Autowired
    private final MessageSender messageSender;

    private double latitude;
    private double longitude;
    private List<Street> temp;

    public MessageHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(Message message) {

        if (message.hasText()) {
            if (message.getText().equals("/start")) {
                createMessageWithLocationButton(message,
                        "Вітаю, " + message.getFrom().getUserName() + "!\n" +
                                "Я готовий допомогти тобі знайти найближче укриття. " +
                                "Просто надішли мені свою локацію, і я підкажу тобі, куди йти.\n");
                createMessageWithLocationButton(message,
                        "Для цього просто натисни на кнопку 'Надіслати локацію' у самому низу.");
            }
            if (message.getText().equals("/showMyLocation")) {
                messageSender.sendLocation(SendLocation.builder().
                        chatId(String.valueOf(message.getChatId())).
                        latitude(latitude).
                        longitude(longitude).
                        build());
            }

            if (message.getText().equals("/ananas")) {
                createMessage(message,
                        "ananas",
                        true, 3, 3, null,
                        "/ananas", "ana", "ananas", "rkr", null, "/potato 2.0", "potato2.0",
                        "adam", "Romeo", "romeo", "ana", null, "ande", "nsr", null);
            }
        }
        if (message.hasLocation()) {
            double minDistance = Double.MAX_VALUE;
            latitude = message.getLocation().getLatitude();
            longitude = message.getLocation().getLongitude();
            temp = streetService.getNeededStreetsByUserCoordinates(longitude, latitude);
            Street nearestStreet = null;
            for (Street street : temp) {
                double tempLa = street.getLatitude();
                double tempLo = street.getLongitude();
                double distance = calculateDistance(latitude, longitude, tempLa, tempLo);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestStreet = street;
                }
            }
            createMessage(message, "Натисніть на карту, Вам прокладеться маршрут до даного укриття");
            messageSender.sendLocation(SendLocation.builder().
                    chatId(String.valueOf(message.getChatId())).
                    latitude(nearestStreet.getLatitude()).
                    longitude(nearestStreet.getLongitude()).
                    build());
            createMessage(message, "Адресса укриття " + nearestStreet.getStreetType() + " "
                    + nearestStreet.getStreetName() + " "
                    + nearestStreet.getHouseNumber());
        }
    }


    private void createMessage(Message message, String text) {
        messageSender.sendMessage(
                SendMessage.builder()
                        .chatId(String.valueOf(message.getChatId()))
                        .text(text)
                        .build()
        );
    }

    private void createMessageWithLocationButton(Message message, String text) {
        KeyboardButton button = new KeyboardButton("Надіслати локацію");
        button.setRequestLocation(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(button);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(Collections.singletonList(keyboardRow));
        replyKeyboardMarkup.setResizeKeyboard(true);

        SendMessage sendMessage = SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .text(text)
                .replyMarkup(replyKeyboardMarkup)
                .build();

        messageSender.sendMessage(sendMessage);
    }


    private void createMessage(Message message, String text, boolean isInLine,
                               int columns, int rows, String backData,
                               String... buttonNameAndData) {
        if (isInLine) {
            int b = 0;

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> column = new ArrayList<>();

            for (int ab = columns; ab > 0; ab--) {
                List<InlineKeyboardButton> row = new ArrayList<>();
                for (int ad = rows; ad > 0; ad--) {
                    if (buttonNameAndData[b] == null) {
                        b = b + 1;
                        continue;
                    }
                    InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(buttonNameAndData[b]);
                    inlineKeyboardButton.setCallbackData(buttonNameAndData[b + 1]);
                    b = b + 2;
                    row.add(inlineKeyboardButton);
                }
                column.add(row);
            }

            if (backData != null) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                List<InlineKeyboardButton> row = new ArrayList<>();
                inlineKeyboardButton.setText("\u2B05 Go back");
                inlineKeyboardButton.setCallbackData(backData);
                row.add(inlineKeyboardButton);
                column.add(row);
            }
            inlineKeyboardMarkup.setKeyboard(column);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            messageSender.sendMessage(sendMessage);
        } else {
            ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
            ArrayList<KeyboardRow> keyboardColumns = new ArrayList<KeyboardRow>();

            int a = 0;
            for (int ac = columns; ac > 0; ac--) {
                KeyboardRow keyboardRows = new KeyboardRow();
                for (int ar = rows; ar > 0; ar--) {
                    if (buttonNameAndData[a] == null) {
                        a++;
                        continue;
                    }
                    keyboardRows.add(buttonNameAndData[a]);
                    a++;
                }
                keyboardColumns.add(keyboardRows);
            }
            markup.setKeyboard(keyboardColumns);
            markup.setResizeKeyboard(true);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText(text);
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setReplyMarkup(markup);
            messageSender.sendMessage(sendMessage);
        }
    }
}