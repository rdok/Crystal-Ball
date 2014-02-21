package me.rdokollari.crystalball;

import java.util.Random;

public class CrystalBall {

	private String mAnswers[] = { "It is certain", "It is decidely so",
			"All signs say YES", "The stars are not aligned", "My replay is no",
			"It is doubtful", "Better not tell you now",
			"Concetrate and ask again", "Unable to answer now" };

	/**
	 * 
	 * @return a random generator answer.
	 */
	public String getRandomAnswer() {

		// Randomly select one of the three answers: Yes, No, or Maybe
		Random randomGenerator = new Random(); // construct a new random generator

		return mAnswers[randomGenerator.nextInt(mAnswers.length)];
	}
}
