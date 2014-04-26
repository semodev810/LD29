package com.semo.ld29;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Texture;

import com.semo.ld29.render.AnimatedSprite;
import com.semo.ld29.render.Animation;

public class Resources 
{
	public static Texture MISSING_TEXTURE;
	public static AnimatedSprite MISSING_SPRITE;
	public static AnimatedSprite PLAYER_SPRITE;
	
	public static void init() throws IOException
	{
		MISSING_TEXTURE = new Texture();
		MISSING_TEXTURE.loadFromFile(Paths.get("resources\\missing.png"));
		
		MISSING_SPRITE = new AnimatedSprite("missing.png", 1, 1, 32, 32);
		
		PLAYER_SPRITE = new AnimatedSprite("player.png", 6, 1, 25, 50);
		PLAYER_SPRITE.addAnimation(new Animation(0, 5, 0.25f, "Jump"));
	}
}