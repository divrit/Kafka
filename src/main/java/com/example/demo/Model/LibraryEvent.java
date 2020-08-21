package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LibraryEvent {
	private Integer libraryEventId;
	private LibraryEventType libraryEventType;
	private Book book;

}
