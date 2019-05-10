# TRXPlorer-Bot
Telegram bot for looking up transactions on the Tron blockchain via [TRXPlorer.io](https://trxplorer.io) API.

# Build requirements
* Gradle
* JDK 8

# Build project
Before building, place your bot username and token in the `/src/main/java/TronBot.java` file.
```
git clone https://github.com/philipinho/trxplorer-bot.git
$ cd trxplorer-bot
$ gradle build
```

# Running the bot
```$ java -jar build/libs/trxplorer-bot-1.0-SNAPSHOT.jar```

To run bot continuously after closing the session use `nohup`

```$ nohup java -jar build/libs/trxplorer-bot-1.0-SNAPSHOT.jar &```
