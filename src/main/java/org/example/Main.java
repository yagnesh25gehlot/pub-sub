package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        KafkaController kafkaController = new KafkaController();
        // Create topics.
        Topic topic1 = kafkaController.addTopic("Topic1");
        Topic topic2 = kafkaController.addTopic("Topic2");

        // Create subscribers.
        ISubscriber subscriber1 = new Subscriber("Subscriber1");
        Subscriber subscriber2 = new Subscriber("Subscriber2");
        Subscriber subscriber3 = new Subscriber("Subscriber3");
        // Subscribe: subscriber1 subscribes to both topics,
        // subscriber2 subscribes to topic1, and subscriber3 subscribes to topic2.

        kafkaController.subscribe(topic1.id, subscriber1);
        kafkaController.subscribe(topic2.id, subscriber1);
        kafkaController.subscribe( topic1.id, subscriber2);
        kafkaController.subscribe( topic2.id, subscriber3);
        // Create publishers.
        Publisher publisher1 = new Publisher(kafkaController, "Publisher1");
        Publisher publisher2 = new Publisher(kafkaController, "Publisher2");
        // Publish some messages.
        publisher1.publish(topic1.id, new Message("Message m1"));
        publisher1.publish(topic1.id, new Message("Message m2"));
        publisher2.publish(topic2.id, new Message("Message m3"));

        // Allow time for subscribers to process messages.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        publisher2.publish(topic2.id, new Message("Message m4"));
        publisher1.publish(topic1.id, new Message("Message m5"));
        // Reset offset for subscriber1 on topic1 (for example, to re-process messages).
        // Allow some time before shutting down.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        kafkaController.shutdown();
    }
}