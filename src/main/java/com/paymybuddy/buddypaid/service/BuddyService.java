package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.paymybuddy.buddypaid.model.Buddy;
import com.paymybuddy.buddypaid.repository.IBuddyRepository;

@Service
public class BuddyService implements IBuddyService{
	
	private IBuddyRepository buddyRepository;
	
	public BuddyService(IBuddyRepository buddyRepository) {
		this.buddyRepository = buddyRepository;
	}

	@Override
	public Optional<Buddy> getBuddy(Integer id) {
		return buddyRepository.findById(id);
	}

	@Override
	public Iterable<Buddy> getBuddiesByUserId(int id) {
		return buddyRepository.findByUserId(id);
	}
}
