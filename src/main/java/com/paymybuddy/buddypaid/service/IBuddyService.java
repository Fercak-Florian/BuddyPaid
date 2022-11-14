package com.paymybuddy.buddypaid.service;

import java.util.Optional;

import com.paymybuddy.buddypaid.model.Buddy;

public interface IBuddyService {
	Optional<Buddy> getBuddy(Integer id);
	Iterable<Buddy>getBuddiesByUserId(int id);
}
