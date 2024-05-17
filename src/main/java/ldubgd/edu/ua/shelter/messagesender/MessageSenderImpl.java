package ldubgd.edu.ua.shelter.messagesender;

import ldubgd.edu.ua.shelter.botlogic.BotRunmethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageSenderImpl implements MessageSender {
    private BotRunmethod botRunmethod;

    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            botRunmethod.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEditMessage(EditMessageText editMessageText) {
        try {
            botRunmethod.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendLocation(SendLocation sendLocation) {
        try {
            botRunmethod.execute(sendLocation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setBotRunmethod(BotRunmethod botRunmethod) {
        this.botRunmethod = botRunmethod;
    }
}
