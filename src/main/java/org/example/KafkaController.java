package org.example;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class KafkaController {

    Map<String, Topic> idToTopic;
    Map<String, List<TopicSubscriber>> idToTopicSubscriber;
    ExecutorService executor;




    public KafkaController(){
        idToTopic = new ConcurrentHashMap<>();
        idToTopicSubscriber = new ConcurrentHashMap<>();
        executor = Executors.newCachedThreadPool();
    }


    public Topic addTopic(String id){
        Topic topic = new Topic(id);
        idToTopic.put(id, topic);
        return topic;
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        if(!executor.awaitTermination(5, TimeUnit.SECONDS)){
            executor.shutdownNow();
        }
    }


    public void subscribe(String topic, ISubscriber subscriber) throws IllegalArgumentException{

        if(!idToTopic.containsKey(topic)){
            throw new IllegalArgumentException("Topic not available");
        }

        if(!idToTopicSubscriber.containsKey(topic)){
            idToTopicSubscriber.put(topic, new CopyOnWriteArrayList<>());
        }

        TopicSubscriber topicSubscriber = new TopicSubscriber(subscriber, idToTopic.get(topic));

        idToTopicSubscriber.get(topic).add(topicSubscriber);

        executor.submit(topicSubscriber);

    }




    public void publish(String topicId, Message message){

        Topic topic = idToTopic.getOrDefault(topicId, null);

        if(topic==null)
            return;

        topic.add(message);

        for(TopicSubscriber topicSubscriber: idToTopicSubscriber.get(topicId)){

            synchronized (topicSubscriber){
                topicSubscriber.notify();
            }
        }


    }



}
