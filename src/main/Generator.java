package main;

import java.util.Random;

public class Generator extends Random {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// This class is just a Random object with an easily retrievable seed.
	// That may be later useful for players wanting to play a specific seed.
	// In addition, we can manually change the seed from here for debug purposes.
	
	public long seed;
   public Generator() {
      super();
      seed = (long) this.nextInt();
      this.setSeed(seed);
      //System.out.println("Seed : " + seed);	// debug
   }
}