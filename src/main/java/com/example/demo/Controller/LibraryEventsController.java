package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.LibraryEvent;
import com.example.demo.Model.LibraryEventType;
import com.example.demo.Producer.LibraryEventProcedure;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LibraryEventsController {
	
	@Autowired
	LibraryEventProcedure producer;
	
	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws Exception{
		
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		 producer.sendLibraryEvent(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	
	
	
	@PutMapping("/v1/libraryevent")
    public ResponseEntity putLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws Exception{
		
		if(libraryEvent.getLibraryEventId()==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please pass Event Id");
		}
		libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
		producer.sendLibraryEvent(libraryEvent);
		return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
	}

}
