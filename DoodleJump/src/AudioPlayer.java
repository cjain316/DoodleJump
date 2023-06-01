import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer implements LineListener, Runnable{
	boolean playCompleted;
	boolean loop;
	boolean stop;
	String path;
	
	public AudioPlayer(String path, boolean loop) {
		//playCompleted = true;
		this.loop = loop;
		this.path = path;
		stop = false;
	}
	
	public void run() {
		stop = false;
		do {
			play(path);
		} while(loop && !stop);
	}
	
	public void stopMusic() {
		stop = true;
	}
	
	void play(String audioFilePath) {
        File audioFile = new File(audioFilePath);
 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
 
            audioClip.open(audioStream);
             
            audioClip.start();
            playCompleted = false;
            
            while (!playCompleted && !stop) {
                // wait for the playback completes
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    break;
                }
            }
             
            audioClip.close();
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
         
    }
	
	@Override
	public void update(LineEvent event) {
		if (LineEvent.Type.START == event.getType()) {
            //System.out.println("Playback started.");
            playCompleted = false;
        } else if (LineEvent.Type.STOP == event.getType()) {
            playCompleted = true;
            //System.out.println("Playback completed.");
        }
	}
	
	
}
