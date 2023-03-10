package org.example.restservice;

import com.google.gson.Gson;
import org.example.domain.Order;
import org.example.domain.OrderConfirmation;
import org.example.sns.SnsPublisherService;
import org.example.sqs.SqsListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@Component
public class RestController {
    @Autowired
    SnsPublisherService snsPublisherService;

    /**
     * REST API to create an Order
     */
    @PutMapping(
            value = "/order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addOrder(@RequestBody Order order) {
        String confirmation = snsPublisherService.publishMessage(new Gson().toJson(order));
        OrderConfirmation orderConfirmation = new OrderConfirmation(confirmation);
        return new ResponseEntity<>(new Gson().toJson(orderConfirmation), HttpStatus.OK);
    }

}
