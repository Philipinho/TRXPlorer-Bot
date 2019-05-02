import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class TX extends TronBot {

    private  final String API_URL = "https://api.trxplorer.io/v2/";
    private  OkHttpClient httpClient = new OkHttpClient();
    private  JSONParser jsonParser = new JSONParser();
    private  SendMessage sendMessage = new SendMessage();

    public  void getTransactionDetails(Update update){
        if (update.getMessage().getText().startsWith("/tx")){
            if (update.getMessage().getText().length() > 64){
                String hash = update.getMessage().getText().split(" ")[1];

                if (hash.length() == 64) {

                    Transaction tx = txInfo(hash);

                        sendMessage.setChatId(update.getMessage().getChatId())
                                .enableHtml(true)
                                .setText("<b>Hash: </b>" + tx.getHash() +
                                        "\n<b>From: </b>" + tx.getFrom() +
                                        "\n<b>To: </b>" + tx.getTo() +
                                        "\n<b>Block: </b>" + tx.getBlock() +
                                        "\n<b>Timestamp: </b>" + tx.getTimeStamp() +
                                        "\n<b>Confirmed: </b>" + tx.getStatus() +
                                        "\n<b>Type: </b>" + tx.getType());


                } else {
                    sendMessage.setChatId(update.getMessage().getChatId())
                            .setText("Invalid hash provided.");
                    System.out.println("Invalid hash provided.");
                }
            } else {
                sendMessage.setChatId(update.getMessage().getChatId())
                        .setText("Incomplete command.");
                System.out.println("Incomplete command.");
            }

        }
        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public  Transaction txInfo(String hash){
        Transaction tx = new Transaction();

        try {

            JSONObject jsonObject = (JSONObject) jsonParser.parse(doGETRequest("transaction/" + hash));
            String txhash = (String) jsonObject.get("hash");
            long block = (long) jsonObject.get("block");
            String from = (String) jsonObject.get("from");
            String to = (String) jsonObject.get("to");
            long timestamp = (long) jsonObject.get("timestamp");
            boolean status = (boolean) jsonObject.get("confirmed");
            String type = (String) jsonObject.get("typeLabel");

            tx.setHash(txhash);
            tx.setBlock(block);
            tx.setFrom(from);
            tx.setTo(to);
            tx.setTimeStamp(timestamp);
            tx.setStatus(status);
            tx.setType(type);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return tx;
    }

    public  String doGETRequest(String endPoint){
        final StringBuilder result = new StringBuilder();

        Request request = new Request.Builder()
                .url(API_URL + endPoint)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();

            result.append(response.body().string());
        } catch (IOException e) {
           // e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return result.toString();
    }
}
