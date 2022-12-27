package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip; //audio file
	URL soundURL[] = new URL[30];
	long clipTime;
	
	public Sound() {
		soundURL[0] = getClass().getResource("/sound/BlueBoyAdventure.wav");
		soundURL[1] = getClass().getResource("/sound/Test.wav");
		soundURL[2] = getClass().getResource("/sound/unlock.wav");
		soundURL[3] = getClass().getResource("/sound/MushRoom.wav");

	}
	
	public void setFile(int i) {
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			FloatControl gainControl = 
				    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-15.0f); // Reduces volume by 15 decibels.
			
		}catch (Exception e) {
		}
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);	
	}
	
	public void stop() {
		clip.stop();
	}
	public void pauseMusic() {

		clipTime= clip.getMicrosecondPosition();

		clip.stop();
	}

	public void unpauseMusic() {
		clip.setMicrosecondPosition(clipTime);

		clip.start();
	}
}
