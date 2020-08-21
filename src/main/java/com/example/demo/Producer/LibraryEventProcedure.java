package com.example.demo.Producer;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.demo.Model.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventProcedure {
	
	@Autowired
	KafkaTemplate<Integer, String>  kafkaTemplate;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public SendResult<Integer, String> sendLibraryEvent(LibraryEvent le) throws Exception {
		
		Integer key=le.getLibraryEventId();
		String value=objectMapper.writeValueAsString(le);
		SendResult<Integer, String> sendResult=null;
		
		//send Default methods returns listenabe future ------------------------because there is batch, when the batch gets full
		// ---------------- only then it will send the message      OR          .ms---->we can set the seconds -->ms
		
		try {
			sendResult = kafkaTemplate.sendDefault(key, value).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			log.error("InterruptedException | ExecutionException e message and the exception is {} ",e.getMessage());
			throw e;

		}
		
//		lf.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
//
//			@Override
//			public void onFailure(Throwable ex) {
//				// TODO Auto-generated method stub
//				handleFailure(key, value,ex);
//				
//			}
//
//			
//
//			@Override
//			public void onSuccess(SendResult<Integer, String> result) {
//				// TODO Auto-generated method stub
//				handleSuccess(key,value,result);
//				
//			}
//		});
//		
		return sendResult;
	}
//		private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
//			log.info("message sent successful for the key : {} and the value is {} and partitio is {}",key ,value,result.getRecordMetadata().partition());
//		}
//		
//		private void handleFailure(Integer key, String value, Throwable ex) {
//			// TODO Auto-generated method stub
//			log.error("Error sending the message and the exception is {} ",ex.getMessage());
//			try {
//				throw ex;
//			}
//			catch(Throwable throable) {
//				log.error("Error in onFailure : {}", throable.getMessage());
//			}
//		}
		
		
	

}
