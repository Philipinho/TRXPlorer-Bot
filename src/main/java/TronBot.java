import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TronBot extends TelegramLongPollingBot {
    private SendMessage sendMessage = new SendMessage();

    @Override
    public void onUpdateReceived(Update update) {
        TX tx = new TX();

        if (update.getMessage().getText().equalsIgnoreCase("/start")){
            sendMessage.setChatId(update.getMessage().getChatId())
                    .enableHtml(true)
                    .setText("Welcome @"+ update.getMessage().getFrom().getUserName() + " this is a test bot.\n" +
                           "\n<b>Example</b>" +
                            "/tx txhash - to get transaction info");
        } else if (update.getMessage().getText().startsWith("/tx")){
            tx.getTransactionDetails(update);
            return;
        } else {
            if (!update.getMessage().isGroupMessage()) {
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setChatId("Invalid command.");
            }
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return "725652429:AAE5IQdo55LNXDKxYhKa87O4Y-JG4WtJPz4";
    }

    @Override
    public String getBotUsername(){
        return "Tronex_sandbot";
    }


}
