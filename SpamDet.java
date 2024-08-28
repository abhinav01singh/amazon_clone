import java.util.HashMap;

import java.util.Map;

 

public class SpamDet {

   

    private Map<String, Integer> spamWordCounts;

    private Map<String, Integer> hamWordCounts;

    private int totalSpamMessages;

    private int totalHamMessages;

    private int totalSpamWords;

    private int totalHamWords;

   

    public void SpamDetector() {

        spamWordCounts = new HashMap<>();

        hamWordCounts = new HashMap<>();

        totalSpamMessages = 0;

        totalHamMessages = 0;

        totalSpamWords = 0;

        totalHamWords = 0;

    }

   

    public void train(String message, boolean isSpam) {

        String[] words = message.split("\\s+");

        Map<String, Integer> wordCounts = isSpam ? spamWordCounts : hamWordCounts;

        int totalMessages = isSpam ? ++totalSpamMessages : ++totalHamMessages;

        int totalWords = isSpam ? totalSpamWords : totalHamWords;

       

        for (String word : words) {

            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);

            totalWords++;

        }

       

        if (isSpam) {

            totalSpamWords += words.length;

        } else {

            totalHamWords += words.length;

        }

    }

   

    public double calculateSpamProbability(String message) {

        String[] words = message.split("\\s+");

        double spamProbability = 1.0;

        double hamProbability = 1.0;

       

        for (String word : words) {

            int spamCount = spamWordCounts.getOrDefault(word, 0);

            int hamCount = hamWordCounts.getOrDefault(word, 0);

            spamProbability *= (double) (spamCount + 1) / (totalSpamWords + spamWordCounts.size());

            hamProbability *= (double) (hamCount + 1) / (totalHamWords + hamWordCounts.size());

        }

       

        double totalSpamProbability = spamProbability * ((double) totalSpamMessages / (totalSpamMessages + totalHamMessages));

        double totalHamProbability = hamProbability * ((double) totalHamMessages / (totalSpamMessages + totalHamMessages));

       

        return totalSpamProbability / (totalSpamProbability + totalHamProbability);

    }

   

    public static void main(String[] args) {

        SpamDet detector = new SpamDet();

       

        // Train the detector with labeled messages

        detector.train("Buy Viagra now!!!", true);

        detector.train("Hello, how are you?", false);

        detector.train("Get rich quick!", true);

        detector.train("Meeting at 10 am tomorrow", false);

       

        // Test the detector with new messages

        String testMessage1 = "Viagra for sale, discount price";

        double spamProbability1 = detector.calculateSpamProbability(testMessage1);

        System.out.println("Probability that '" + testMessage1 + "' is spam: " + spamProbability1);

       

        String testMessage2 = "Hi, I hope you're doing well";

        double spamProbability2 = detector.calculateSpamProbability(testMessage2);

        System.out.println("Probability that '" + testMessage2 + "' is spam: " + spamProbability2);

    }

}