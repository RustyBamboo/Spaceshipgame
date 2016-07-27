package resources;

import java.util.Random;

public class NameGen {
	public static String[] greekAlphabet = { "Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota", "Kappa", "Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau",
			"Upsilon", "Phi", "Chi", "Psi", "Omega" };
	public static Random r = new Random();

	/**
	 * Generate a random name with a Greek letter, 2 numbers, another letter,
	 * and 3 more numbers
	 * 
	 * @return Random name
	 */
	public static String genName() {
		String ret = "";
		ret += greekAlphabet[r.nextInt(greekAlphabet.length)] + " ";
		ret += r.nextInt(10) + "" + r.nextInt(10) + ": ";
		ret += greekAlphabet[r.nextInt(greekAlphabet.length)] + " ";
		ret += r.nextInt(10) + "" + r.nextInt(10) + "" + r.nextInt(10);
		return ret;
	}
}
