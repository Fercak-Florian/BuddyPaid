package com.paymybuddy.buddypaid.workclasses;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FormCommentTest {
	
	@Test
	public void testIsEmptyReturnFalse() {
		/* ARRANGE */
		FormComment formComment = new FormComment("message pour l'utilisateur");
		/* ACT */
		boolean result = formComment.isEmpty();
		/* ASSERT */
		assertThat(result).isFalse();
	}
	
	@Test
	public void testIsEmptyReturnTrue() {
		/* ARRANGE */
		FormComment formComment = new FormComment("");
		/* ACT */
		boolean result = formComment.isEmpty();
		/* ASSERT */
		assertThat(result).isTrue();
	}
	
	@Test
	public void testIfMessageIsCleared() {
		/* ARRANGE */
		FormComment formComment = new FormComment("message pour l'utilisateur");
		/* ACT */
		formComment.clearMessage();
		String result = formComment.getMessage();
		/* ASSERT */
		assertThat(result).isEmpty();
	}
}