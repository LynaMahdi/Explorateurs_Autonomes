package com.prjt.explorateursautonomes;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.useVsync(false);
		config.setWindowIcon("Images/letter-e.png");
		config.setTitle("Explorateurs intelligents");
		new Lwjgl3Application(MyGame.getInstance(), config);
	}
}
