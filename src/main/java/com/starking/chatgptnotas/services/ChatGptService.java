package com.starking.chatgptnotas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import reactor.core.publisher.Mono;

/**
 * @author pedroRhamon
 *
 */

@Service
public class ChatGptService {
	
	private WebClient webClient;
	
	public ChatGptService(WebClient.Builder builder, @Value("${openai.api.key}") String apiKey) {
		this.webClient = builder
				.baseUrl("https://api.openai.com/v1/completions")
				.defaultHeader("Content-type", "application/json")
				.defaultHeader("Authorization", String.format("Bearer %s", apiKey))
				.build();
	}

	public Mono<ChatGPTResponse> createStudyNotes(String topic) {
		ChatGPTRequest request = createStudyRequest(topic);
		
		return webClient.post().bodyValue(request)
				.retrieve()
				.bodyToMono(ChatGPTResponse.class);
	}
	
	private ChatGPTRequest createStudyRequest(String topic) {
		String question = "Conte uma história nova do Batman da DC" + topic;
		
		return new ChatGPTRequest("text-davinci-003", question, 0.3, 2000,  1.0, 0.0, 0.0);
	}



	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	record ChatGPTRequest(String model, String prompt, Double temperature, Integer maxTokens, Double topP, Double frequencyPenalty, Double presencePenalty) {
		
	}
	
	record ChatGPTResponse(List<Choice> choices) {
		
	}
	
	record Choice(String text) {
		
	}
}
