
import java.io.*;
import java.net.URL;

import javax.sound.sampled.*;
import javax.swing.*;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class Get_Sound {
	protected final String SOUND_BUTTON = "SOUND/s_button.wav";
	protected final String SOUND_BACKGROUND = "SOUND/s_background.wav";

	protected Clip sound(String title) {
		Clip clip = null;
		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource(title);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		return clip;
	}
	
}