package com.tedu.element;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

public class BgmMusic {

	public static void playMusicCycle(String music_scr) {// 背景音乐播放
		music_scr = "image/FishCatcher/bgm/" + music_scr + ".wav";// 只需要输入需要的bgm名字就可以了
		// music_scr="image/FishCatcher/bgm/coinanimate.wav";//这是测试用的后期直接注释掉
		File music_path = new File(music_scr);

		try {
			//
			if (music_path.exists()) {
				AudioInputStream ais = AudioSystem.getAudioInputStream(music_path);
				AudioFormat aif = ais.getFormat();
				// wav的设置，这里是不能动的，不进行这一步会出现报错
				if (aif.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					aif = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, aif.getSampleRate(), 16, aif.getChannels(),
							aif.getChannels() * 2, aif.getSampleRate(), false);
					ais = AudioSystem.getAudioInputStream(aif, ais);
				}
				//
				Clip clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 
	 * @param music_scr 会占用线程和报错最好不使用先，用上面的
	 */
	public static void playMusic(String music_scrname) {
		new Thread() {
			public void run() {
				try {
					//
					final String music_scr = "image/FishCatcher/bgm/" + music_scrname + ".wav";// 这是测试用的后期直接注释掉
					File music_path = new File(music_scr);
					try {
						AudioInputStream ais = AudioSystem.getAudioInputStream(music_path); // 文件路径，从image开始写
						AudioFormat aif = ais.getFormat();
						if (aif.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
							aif = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, aif.getSampleRate(), 16,
									aif.getChannels(), aif.getChannels() * 2, aif.getSampleRate(), false);
							ais = AudioSystem.getAudioInputStream(aif, ais);
						}
						final SourceDataLine sdl;
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
						sdl = (SourceDataLine) AudioSystem.getLine(info);
						sdl.open(aif);
						sdl.start();
						FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
						// value可以用来设置音量，从0-2.0
						double value = 2;
						float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
						fc.setValue(dB);
						int nByte = 0;
						final int SIZE = 1024 * 64;
						byte[] buffer = new byte[SIZE];
						while (nByte != -1) {
							nByte = ais.read(buffer, 0, SIZE);
							if (nByte == -1) {

							} else {
								sdl.write(buffer, 0, nByte);
							}

						}

						sdl.stop();

					} catch (Exception e) {
						e.printStackTrace();
					}
					//
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();

	}
}
