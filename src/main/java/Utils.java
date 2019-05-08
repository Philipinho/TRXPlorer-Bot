import com.vdurmont.emoji.EmojiParser;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static String tx_confirmed = EmojiParser.parseToUnicode(":white_check_mark:");
    private static String not_confirmed = EmojiParser.parseToUnicode(":x:");

    public static String convertToDouble(long value){

        DecimalFormat formatter = new DecimalFormat("#,###");
        int divisor = 1000000;
        double amount = (double) value/divisor;
        return formatter.format(amount);
    }

    public static String moneyFormat(long number){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String checkConfirmation(boolean status){
        String emoji = "";
        if (status){
            emoji = tx_confirmed;
        } else {
            emoji = not_confirmed;
        }
        return emoji;
    }

    public static String convertTimestamp(long timestamp){


       String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new Date(timestamp));
        return date;
    }

}
