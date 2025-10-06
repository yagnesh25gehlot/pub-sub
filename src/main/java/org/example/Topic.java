package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Topic {
    String id;
    List<Message> messages;

    public Topic(String id){
        this.id = id;
        messages = new ArrayList<>();
    }

    public synchronized void add(Message message){
        messages.add(message);
    }

    public synchronized List<Message> getMessages(){
        return Collections.unmodifiableList(messages);
    }
}
