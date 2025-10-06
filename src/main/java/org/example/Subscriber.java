package org.example;

public class Subscriber implements ISubscriber{
    String id;
    public Subscriber(String id){this.id = id;}

    public void onMessage(Message message) {
        System.out.println("Processing the message " + message.value);
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
        System.out.println("Done processing");
    }
}
