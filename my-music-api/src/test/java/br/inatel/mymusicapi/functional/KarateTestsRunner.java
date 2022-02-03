package br.inatel.mymusicapi.functional;

import com.intuit.karate.junit5.Karate;

public class KarateTestsRunner {
	@Karate.Test
	Karate runSignupTests() {
		return Karate.run().relativeTo(getClass());
	}
}