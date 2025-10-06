package org.example;

public class Publisher implements IPublisher{

    KafkaController kafkaController;
    String id;

    public Publisher(KafkaController kafkaController, String id){
        this.kafkaController = kafkaController;
        this.id = id;
    }


    @Override
    public void publish(String topic, Message message){
        kafkaController.publish(topic, message); // this
    }
}
