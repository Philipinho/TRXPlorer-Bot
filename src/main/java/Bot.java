import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TronBot {
    private JSONParser jsonParser = new JSONParser();
    private SendMessage sendMessage = new SendMessage();
    private APIClient api = new APIClient();

    public void getHelp(Update update){
        sendMessage.setChatId(update.getMessage().getChatId())
                .enableHtml(true)
                .setText("Welcome @"+ update.getMessage().getFrom().getUserName() + ",\n I'm here to help you " +
                        "lookup transactions on TRXPlorer.io." +
                        "\n<b>Commands</b>" +
                        "\n/tx hash - to get transaction info" +
                        "\n/wallet address - to get address balance" +
                        "\n/info - to get transaction info & address balance");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public  void getTransactionDetails(Update update){
        if (update.getMessage().getText().startsWith("/tx")){
            if (update.getMessage().getText().length() > 64){
                String hash = update.getMessage().getText().split(" ")[1];
                Transaction tx = txInfo(hash);

                if (hash.length() == 64 ) {
                    if (tx.getType().equalsIgnoreCase("TransferAssetContract")){
                        sendMessage.setChatId(update.getMessage().getChatId())
                                .setReplyToMessageId(update.getMessage().getMessageId())
                                .setText("Sent " + tx.getAmount() + " LoveHearts to " + tx.getTo() + ".");
                    } else {

                       transactionMessage(update, tx);
                    }
                } else {
                    sendMessage.setChatId(update.getMessage().getChatId())
                            .setReplyToMessageId(update.getMessage().getMessageId())
                            .setText("Invalid hash provided.");
                    System.out.println("Invalid hash provided.");
                }
            } else {
                sendMessage.setChatId(update.getMessage().getChatId())
                        .enableMarkdown(true)
                        .setReplyToMessageId(update.getMessage().getMessageId())
                        .setText("Incomplete command. Use: ``` /tx HASH```");
                System.out.println("Incomplete command.");
            }

        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void showAccount(Update update){

        if (update.getMessage().getText().startsWith("/wallet")){
            if (update.getMessage().getText().length() == 42){
                String address = update.getMessage().getText().split(" ")[1];

                Account account = getAccountInfo(address);
                if (address.length() == 34){

                    accountMessage(update, account);

                } else {
                    sendMessage.setChatId(update.getMessage().getChatId())
                            .setReplyToMessageId(update.getMessage().getMessageId())
                            .setText("Invalid Tron address provided.");
                    System.out.println("Invalid Tron address provided.");
                }
            } else {
                sendMessage.setChatId(update.getMessage().getChatId())
                        .enableMarkdown(true)
                        .setReplyToMessageId(update.getMessage().getMessageId())
                        .setText("Incomplete command. Use: ``` /wallet ADDRESS```");
                System.out.println("Incomplete command.");
            }
        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void getInfo(Update update){

        if (update.getMessage().isReply() && update.getMessage().getText().equalsIgnoreCase("/info")
                && update.getMessage().getReplyToMessage().getText().contains("trxplorer.io/address/")){
            String address = update.getMessage().getReplyToMessage().getText().split("/")[4];

            Account account = getAccountInfo(address);
            System.out.println(account.getAddress());

            accountMessage(update,account);


        }  if (update.getMessage().isReply() && update.getMessage().getText().equalsIgnoreCase("/info")
                &&  update.getMessage().getReplyToMessage().getText().startsWith("T") && update.getMessage().getReplyToMessage().getText().length() == 34){
            String address = update.getMessage().getReplyToMessage().getText();

            Account account = getAccountInfo(address);

            accountMessage(update, account);


        } else if(update.getMessage().isReply() && update.getMessage().getText().equalsIgnoreCase("/info")
                && update.getMessage().getReplyToMessage().getText().contains("trxplorer.io/tx/")){

                String hash = update.getMessage().getReplyToMessage().getText().split("/")[4];

                Transaction tx = txInfo(hash);

                    if (tx.getType().equalsIgnoreCase("TransferAssetContract")){
                        sendMessage.setChatId(update.getMessage().getChatId())
                                .setReplyToMessageId(update.getMessage().getMessageId())
                                .setText("Sent " + tx.getAmount() + " LoveHearts to " + tx.getTo() + ".");
                    } else {
                        transactionMessage(update, tx);
                    }
        } else if(update.getMessage().isReply() && update.getMessage().getText().equalsIgnoreCase("/info")
                && update.getMessage().getReplyToMessage().getText().length() == 64){

            String hash = update.getMessage().getReplyToMessage().getText();

            Transaction tx = txInfo(hash);

            if (tx.getType().equals("TransferAssetContract")){
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setReplyToMessageId(update.getMessage().getMessageId())
                        .setText("Sent " + tx.getAmount() + " LoveHearts to " + tx.getTo() + ".");
            }

            else {
               transactionMessage(update, tx);
            }
        } else {
            sendMessage.setChatId(update.getMessage().getChatId())
                    .enableMarkdown(true)
                    .setReplyToMessageId(update.getMessage().getMessageId())
                    .setText("Incomplete command. Please use /info in a reply to a valid Tron transaction hash or wallet address.");
            System.out.println("Incomplete command.");
        }

        try{
            execute(sendMessage);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    private void transactionMessage(Update update, Transaction tx){
        sendMessage.setChatId(update.getMessage().getChatId())
                .setReplyToMessageId(update.getMessage().getMessageId())
                .enableHtml(true)
                .setText("<b>From: </b>" + tx.getFrom() +
                        "\n<b>To: </b>" + tx.getTo() +
                        "\n<b>Amount: </b>" + Utils.convertToDouble(tx.getAmount()) + " TRX" +
                        "\n<b>Asset Amount: </b>" + Utils.moneyFormat(tx.getAssetAmount()) + " " + tx.getAssetName() +
                        "\n<b>Date: </b>" + Utils.convertTimestamp(tx.getTimeStamp()) +
                        "\n<b>Confirmed: </b>" + Utils.checkConfirmation(tx.getStatus()) +
                        "\n<b>Type: </b>" + tx.getType());
    }

    private void accountMessage(Update update, Account account){
        sendMessage.setChatId(update.getMessage().getChatId())
                .setReplyToMessageId(update.getMessage().getMessageId())
                .setText("TRX Balance: " + Utils.convertToDouble(account.getBalance()) +
                        "\nFrozen Balance: " + Utils.convertToDouble(account.getFrozenBalance()) + " TRX");
    }

    private Account getAccountInfo(String walletAddress){
        Account account = new Account();
        long frozenBalance = 0;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(api.doGETRequest("account/" + walletAddress ));
            System.out.println(jsonObject);
            String name = (String) jsonObject.get("name");
            String address = (String) jsonObject.get("address");
            long balance = (long) jsonObject.get("balance");

            if (jsonObject.get("frozenBalance") != null) {
                frozenBalance = (long) jsonObject.get("frozenBalance");
            }

            account.setName(name);
            account.setAddress(address);
            account.setBalance(balance);
            account.setFrozenBalance(frozenBalance);

        } catch (ParseException e){
            e.printStackTrace();
        }

        return account;
    }

    private Transaction txInfo(String hash){
        Transaction tx = new Transaction();

        try {

            String assetName = "";
            long amount = 0;
            long assetAmount = 0;

            JSONObject jsonObject = (JSONObject) jsonParser.parse(api.doGETRequest("transaction/" + hash));
            System.out.println(jsonObject);

            String txhash = (String) jsonObject.get("hash");
            long block = (long) jsonObject.get("block");
            String from = (String) jsonObject.get("from");
            String to = (String) jsonObject.get("to");
            long timestamp = (long) jsonObject.get("timestamp");
            boolean status = (boolean) jsonObject.get("confirmed");
            String type = (String) jsonObject.get("typeLabel");

            if (type.equalsIgnoreCase("TransferAssetContract")){
                JSONArray jsonArray = (JSONArray) jsonParser.parse(api.doGETRequest("transaction/" + hash + "/contracts"));
                for (Object obj : jsonArray) {
                    JSONObject json = (JSONObject) obj;
                    assetName = (String) json.get("asset");
                    amount = (long) json.get("amount");
                }
            } else if (type.equalsIgnoreCase("TransferContract")) {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(api.doGETRequest("transaction/" + hash + "/contracts"));
                for (Object obj : jsonArray) {
                    JSONObject json = (JSONObject) obj;
                    amount = (long) json.get("amount");
                }
            } else if (type.equalsIgnoreCase("TriggerSmartContract")) {
                JSONArray jsonArray = (JSONArray) jsonParser.parse(api.doGETRequest("transaction/" + hash + "/contracts"));

                for (Object obj : jsonArray) {
                    JSONObject json = (JSONObject) obj;
                    to = (String) json.get("contractAddress");
                    amount = (long) json.get("callTokenValue");
                }

                    JSONArray array = (JSONArray) jsonParser.parse(api.doGETRequest("transaction/" + hash + "/trc20transfers"));
                    System.out.println(array);
                    for (Object in : array) {
                        JSONObject jsonValue = (JSONObject) in;
                        assetName = (String) jsonValue.get("token").toString().split(":")[1];
                        assetAmount = (long) jsonValue.get("amount");

                    }
            }

            tx.setHash(txhash);
            tx.setBlock(block);
            tx.setFrom(from);
            tx.setTo(to);
            tx.setAssetName(assetName);
            tx.setAmount(amount);
            tx.setTimeStamp(timestamp);
            tx.setStatus(status);
            tx.setAssetAmount(assetAmount);
            tx.setType(type);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tx;
    }
}
