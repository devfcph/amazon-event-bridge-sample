package org.streaming.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;

public class EventBridgeMain {
    private static String accessKey = "access-key";
    private static String secretKey = "secret-key";
    public static void main(String[] args) {

        System.out.println("Hello world!");
        AmazonEventBridge client = AmazonEventBridgeClient.builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();

        System.getProperty("aws.accessKeyId", "access-key");
        System.getProperty("aws.secretKey", "secret-key");
        putEvents(client);
    }

    public static void putEvents(AmazonEventBridge client) {
        String detail = stringEvent();
        PutEventsRequestEntry requestEntry = new PutEventsRequestEntry();
        requestEntry.withSource("user-event") // El nombre del recurso, en este caso es el package del proyecto
                .withDetailType("user-preferences") // El nombre del parámetro configurado en el evento
                .withDetail(detail) // El nombre del detalle del evento
                .withEventBusName("my-event-bus");

        PutEventsRequest request = new PutEventsRequest();
        request.withEntries(requestEntry);

        try {
            PutEventsResult result = client.putEvents(request);
            System.out.println(result.toString());
        }
        catch (Exception e ) {
            System.out.println(e);
        }

    }

    private static String stringEvent() {
        String newLine = System.getProperty("line.separator");
        return String.join(newLine,
                "{",
                "\"username\": \"admin\",",
                "\"country\": \"México\",",
                "\"age\": \"25\"",
                "}");
    }
}