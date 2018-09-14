package com.laisontech.infraredscanlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.util.SparseIntArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
	private static SoundPool mSoundPool;
	private static SparseIntArray soundMap;

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	static void initSoundPool(Context context) {
		int maxSounds = 1;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mSoundPool = new SoundPool.Builder().setMaxStreams(maxSounds)
					.setAudioAttributes(
							new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_SYSTEM).build())
					.build();
		} else {
			// This old constructor is deprecated, but we need it for compatibility.
			mSoundPool = new SoundPool(maxSounds, AudioManager.STREAM_SYSTEM, 0);
		}

		soundMap = new SparseIntArray();
		soundMap.put(1, mSoundPool.load(context, R.raw.msg, 1));
	}

	static void play() {
		mSoundPool.play(soundMap.get(1), 1.0f, 1.0f, 1, 0, 1);
	}

	static void close() {
		mSoundPool.release();
	}

	public static void logE(String tag, String msg) {
		Log.e(tag, msg);
	}

	static int count = 0;

	public static void writeLog(Object obj) {
		File file = new File(Environment.getExternalStorageDirectory().getPath() + "count.txt");
		if (!file.exists()) {
			count = 0;
		}
		count++;
		try {
			FileWriter fw = new FileWriter(file);
			fw.write("count = " + count);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
