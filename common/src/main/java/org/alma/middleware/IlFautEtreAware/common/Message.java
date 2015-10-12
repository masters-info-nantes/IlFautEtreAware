package org.alma.middleware.IlFautEtreAware.common;

import java.io.Serializable;

/**
 * Created by Maxime on 02/10/2015.
 */
public class Message implements Serializable {
    private String author;
    private String message;

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
    }

    private Message(){
    }

    public String getAuthor(){
        return this.author;
    }

    public String getMessage(){
        return this.message;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setMessage(String message){
        this.message = message;
    }
    
    public String toString(){
    	return author+": "+message;
    }
}
