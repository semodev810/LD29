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
	public static AnimatedSprite BULLET_SPRITE;
	
	public static void init() throws IOException
	{
		MISSING_TEXTURE = new Texture();
		MISSING_TEXTURE.loadFromFile(Paths.get("resources\\missing.png"));
		
		MISSING_SPRITE = new AnimatedSprite("missing.png", 1, 1, 32, 32);
		
		PLAYER_SPRITE = new AnimatedSprite("player.png", 8, 8, 20, 50);
		PLAYER_SPRITE.addAnimation(new Animation(0, 7, 0.2f, "Up"));
		
		BULLET_SPRITE = new AnimatedSprite("bullet.png", 8, 1, 9, 9);
		BULLET_SPRITE.addAnimation(new Animation(0, 0, 0, "Up"));
		BULLET_SPRITE.addAnimation(new Animation(1, 1, 0, "UpRight"));
		BULLET_SPRITE.addAnimation(new Animation(2, 2, 0, "Right"));
		BULLET_SPRITE.addAnimation(new Animation(3, 3, 0, "DownRight"));
		BULLET_SPRITE.addAnimation(new Animation(4, 4, 0, "Down"));
		BULLET_SPRITE.addAnimation(new Animation(5, 5, 0, "DownLeft"));
		BULLET_SPRITE.addAnimation(new Animation(6, 6, 0, "Left"));
		BULLET_SPRITE.addAnimation(new Animation(7, 7, 0, "UpLeft"));
	}
}