package com.kierek.hold.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kierek.hold.HoldGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Hold";
		config.width = 360;
		config.height = 640;
		new LwjglApplication(new HoldGame(), config);
	}
}
