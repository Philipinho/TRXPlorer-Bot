import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TronBot extends TelegramLongPollingBot {
    private SendMessage sendMessage = new SendMessage();
    private final String botUserName = "";
    private final String botToken = "";

    @Override
    public void onUpdateReceived(Update update) {
        Bot bot = new Bot();

        if (update.getMessage().getText().equalsIgnoreCase("/start")){
            sendMessage.setChatId(update.getMessage().getChatId())
                    .setText("Welcome @"+ update.getMessage().getFrom().getUserName() + ", See /help for a list of commands.");
            return;
        } else if(update.getMessage().getText().startsWith("/help")){
            bot.getHelp(update);
            return;
        }
        else if (update.getMessage().getText().startsWith("/wallet")) {
            bot.showAccount(update);
            return;
        }

        else if (update.getMessage().getText().startsWith("/tx")){
            bot.getTransactionDetails(update);
            return;
        }
        else if(update.getMessage().getText().equalsIgnoreCase("/info")){
            bot.getInfo(update);
            return;
        }
        else {
            if (update.getMessage().isUserMessage()){
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setText("Invalid command. See /help");
                System.out.println("Invalid command.");
            }
        }

        try {
            if (sendMessage.getChatId() != null) {
                execute(sendMessage);
            }
        } catch (TelegramApiException e){
           e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername(){
        return botUserName;
    }


}
