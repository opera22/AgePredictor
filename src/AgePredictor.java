import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;


public class AgePredictor {

    public static void main(String[] args) {

        System.out.println("--------------------------------------------------\nWelcome to the Age Predictor, " +
                "powered by agify.io!\n--------------------------------------------------");
        System.out.println("Enter your name, and I'll guess your age:");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        final String url = "https://api.agify.io/?name=" + userInput.toLowerCase();

        readWebsite(url);

    }

    public static void readWebsite(String url){

        // I still need to set up the error-handling; the program blows up if the user enters an invalid name
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(5))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(AgePredictor::parse)
                .join();

    }

    public static String parse(String responseBody){

        JSONObject name = new JSONObject(responseBody);
        int age = name.getInt("age");
        int count = name.getInt("count");


        System.out.println("I think you are " + age + " years old.");
        System.out.println("By the way, there are " + count + " people with that name.");

        // the only reason this method returns a string instead of void is because the sendAsync method requires it
        // (not exactly sure why yet)
        return null;
    }
}


