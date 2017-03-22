package org.akanksha.rest.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.akanksha.rest.messenger.database.DatabaseClass;
import org.akanksha.rest.messenger.exception.DataNotFoundException;
import org.akanksha.rest.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages=DatabaseClass.getMessages();
	
	public MessageService(){
		Calendar cal = Calendar.getInstance();
		Date d=new Date();
		cal.setTime(d);
		messages.put(1L, new Message(1, "Hello World",cal.getTime() ,"AKanksha"));
		messages.put(2L, new Message(2, "Hello Java",cal.getTime() ,"AKanksha"));
	}
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear =new ArrayList<>();
		Calendar cal=Calendar.getInstance();
		for(Message message: messages.values()){
		cal.setTime(message.getCreated());
		if(cal.get(Calendar.YEAR)==year){
			messagesForYear.add(message);
		}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		List<Message> messagesForPagination =new ArrayList<>(messages.values());
		if(start + size>  messagesForPagination.size())
			return new ArrayList<>(messages.values());
		
		return messagesForPagination.subList(start, start+size);
	}
	
	
	public Message getMessage(long id){
		Message newMessage=messages.get(id);
		if(newMessage==null){
			throw new DataNotFoundException("Message empty for id "+id+".");
		}
		
		return newMessage;
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size()+1);
		Calendar cal = Calendar.getInstance();
		Date d=new Date();
		cal.setTime(d);
		message.setCreated(cal.getTime());
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message){
		if(message.getId()<=0){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		Date d=new Date();
		cal.setTime(d);
		message.setCreated(cal.getTime());
		messages.put(message.getId(), message);
		return message;
	}
	
	public void removeMessage(long id){
		 messages.remove(id);
	}
}
