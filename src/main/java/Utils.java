import com.vdurmont.emoji.EmojiParser;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static String txConfirmed = EmojiParser.parseToUnicode(":white_check_mark:");
    private static String txNotConfirmed = EmojiParser.parseToUnicode(":x:");

    public static String convertToDouble(long value){
        DecimalFormat formatter = new DecimalFormat("#,###");
        int SUN_PER_TRX = 1000000;
        double amount = (double) value/SUN_PER_TRX;
        return formatter.format(amount);
    }

    public static String moneyFormat(long number){
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String checkConfirmation(boolean status){
        String emoji;
        if (status){
            emoji = txConfirmed;
        } else {
            emoji = txNotConfirmed;
        }
        return emoji;
    }

    public static String convertTimestamp(long timestamp){
       return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new Date(timestamp));
    }
}