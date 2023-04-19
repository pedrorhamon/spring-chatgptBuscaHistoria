package com.starking.chatgptnotas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.starking.chatgptnotas.services.ChatGptService;

import reactor.core.publisher.Mono;

/**
 * @author pedroRhamon
 *
 */
@RestController
public class ChatGPTController {
	
	@Autowired
	private ChatGptService service;
	
	@PostMapping("study-notes")
	public Mono<String> createStudyNotes(@RequestBody String topic) {
		return this.service.createStudyNotes(topic).map(
				response -> response.choices().get(0).text());
	}

}
