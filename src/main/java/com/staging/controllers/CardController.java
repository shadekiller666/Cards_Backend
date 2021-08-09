package com.staging.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staging.models.Card;
import com.staging.services.CardService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
@RequestMapping("/cards")
public class CardController {
	private CardService cs;
	
	@Autowired
	public CardController(CardService cs) {
		this.cs = cs;
	}
	
	@GetMapping(produces = "application/json")
	public List<Card> getAll() {
		return this.cs.getAll();
	}
	
	@GetMapping("/card/{id}")
	public Card getById(@PathVariable("id") int id) {
		return this.cs.get(id);
	}
	
	@GetMapping("/suits/{suit}")
	public List<Card> getBySuit(@PathVariable("suit") int suit) {
		System.out.println(suit);
		return this.cs.getAllBySuit(suit);
	}
	
	@GetMapping("/card/{suit}/{value}")
	public Card getBySuitAndValue(@PathVariable("suit") String suit, @PathVariable("value") int value) {
		System.out.println("Stupid thing");
		return this.cs.getBySuitAndValue(suit, value);
	}
	
	@GetMapping("/shuffled")
	public List<Card> getShuffled() {
		List<Card> deck = this.cs.getAll();
		Collections.shuffle(deck);
		return deck;
	}
	
	@GetMapping("/draw")
	public Card draw() {
		List<Card> deck = this.getAll();
		return deck.get(new Random().nextInt(deck.size()));
	}
	
	@GetMapping("/draw/{amount}")
	public List<Card> draw(@PathVariable("amount") int amount) {
		List<Card> deck = this.getShuffled();
		if (amount > 52) amount = 52;
		
		List<Card> ret = new ArrayList<Card>(amount);
		for (int i = 0; i < amount; i++) ret.add(deck.get(i));
		return ret;
	}
}