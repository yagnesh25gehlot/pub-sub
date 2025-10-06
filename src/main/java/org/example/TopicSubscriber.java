package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class TopicSubscriber implements Runnable{

    ISubscriber subscriber;
    Topic topic;
    AtomicInteger counter;

    public TopicSubscriber(ISubscriber subscriber, Topic topic){
        this.topic = topic;
        this.subscriber = subscriber;
        counter = new AtomicInteger(0);
    }


    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()){
            synchronized (this){

                while(topic.getMessages().size()-1 < counter.get()){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            int value = counter.getAndIncrement();
            Message message = topic.getMessages().get(value);

            subscriber.onMessage(message);

        }
    }

}
