package org.example.sqs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
/**
 * This listener would typically be encapsulated in a separate API/Service but is included in project API for simplicity
 */
public class SqsListenerService {
    @Value("${queue_url}")
    private String queueUrl;
    @Value("${access_key}")
    private String awsAccessKey;
    @Value("${secret_access_key}")
    private String awsSecretKey;
    @Value("${aws_region}")
    private String awsRegion;
    private AmazonSQS amazonSQS;
    final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    private void postConstructor() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );
        this.amazonSQS = AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
        listen();
    }

    /**
     * Use basic approach for threading of AWS JDK calls
     */
    private void listen() {
        Thread listener = new Thread(() -> {
            while (true) {
                ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(queueUrl).withWaitTimeSeconds(10).withMaxNumberOfMessages(1);
                ReceiveMessageResult queueResult = amazonSQS.receiveMessage(messageRequest);
                List<Message> messages = queueResult.getMessages();
                for (Message message : messages) {
                    logger.info("\nReceive message from queue {}\n", message.getBody());
                    amazonSQS.deleteMessage(queueUrl, message.getReceiptHandle());
                }
            }
        });
        listener.start();
    }

}
