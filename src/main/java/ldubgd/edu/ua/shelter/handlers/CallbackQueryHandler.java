package ldubgd.edu.ua.shelter.handlers;

import ldubgd.edu.ua.shelter.messagesender.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CallbackQueryHandler implements Handler<CallbackQuery> {

    private final MessageSender messageSender;

    public CallbackQueryHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(CallbackQuery callbackQuery) {
        switch (callbackQuery.getData()){
            case "hi":
                createEditMessage(callbackQuery, "goodbye");
        }
    }

    private void createEditMessage(CallbackQuery callbackQuery, String text) {
        messageSender.sendEditMessage(
                EditMessageText.builder()
                        .chatId(String.valueOf(callbackQuery.getMessage().getChatId()))
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .text(text)
                        .build()
        );
    }


    private void createEditMessage(CallbackQuery callbackQuery, String text,
                               int columns, int rows, boolean backButton, String dataForBack,
                               String... buttonNameAndData) {
        if (backButton) {
            int a = 0;
            var editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
            editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
            editMessageText.setText(text);

            var inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> column = new ArrayList<>();

            for (int ab = columns; ab > 0; ab--) {
                List<InlineKeyboardButton> row = new ArrayList<>();
                for (int ad = rows; ad > 0; ad--) {
                    var inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(buttonNameAndData[a]);
                    inlineKeyboardButton.setCallbackData(buttonNameAndData[a + 1]);
                    a = a + 2;
                    row.add(inlineKeyboardButton);
                }
                column.add(row);
            }

            var inlineKeyboardButton = new InlineKeyboardButton();
            List<InlineKeyboardButton> row = new ArrayList<>();
            inlineKeyboardButton.setText("\u2B05 Go back");
            inlineKeyboardButton.setCallbackData(dataForBack);
            row.add(inlineKeyboardButton);
            column.add(row);

            inlineKeyboardMarkup.setKeyboard(column);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
            messageSender.sendEditMessage(editMessageText);
        } else {
            int b = 0;
            var editMessageText = new EditMessageText();
            editMessageText.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
            editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
            editMessageText.setText(text);

            var inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> column = new ArrayList<>();

            for (int ab = columns; ab > 0; ab--) {
                List<InlineKeyboardButton> row = new ArrayList<>();
                for (int ad = rows; ad > 0; ad--) {
                    var inlineKeyboardButton = new InlineKeyboardButton();
                    inlineKeyboardButton.setText(buttonNameAndData[b]);
                    inlineKeyboardButton.setCallbackData(buttonNameAndData[b + 1]);
                    b = b + 2;
                    row.add(inlineKeyboardButton);
                }
                column.add(row);
            }
            inlineKeyboardMarkup.setKeyboard(column);
            editMessageText.setReplyMarkup(inlineKeyboardMarkup);
            messageSender.sendEditMessage(editMessageText);
        }
    }
}