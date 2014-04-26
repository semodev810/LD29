package com.semo.ld29.render;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import com.semo.ld29.world.World;

public class WorldRenderer 
{
	public static Texture tileMap;
	public static Sprite currentTile;
	public static RenderWindow target;
	
	public static void init(RenderWindow window) throws IOException
	{
		target = window;
		
		tileMap = new Texture();
		tileMap.loadFromFile(Paths.get("resources\\tileMap.png"));
		
		currentTile = new Sprite(tileMap);
		currentTile.setOrigin(new Vector2f(0, 0));
		currentTile.setTextureRect(getTextureLocation(0));
		currentTile.setPosition(new Vector2f(0, 0));
	}
	
	public static void renderWorld(World world)
	{
		currentTile.setTextureRect(getTextureLocation(0));
		
		// TODO: make this more efficient, not rendering EVERYTHING only what needs to be rendered (ie what is on screen)
		for (int x = 0; x < world.width; ++x)
		{
			for (int y = 0; y < world.height; ++y)
			{
				currentTile.setTextureRect(getTextureLocation(world.getTile(x, y).getIndex()));
				currentTile.setPosition(new Vector2f(x * 32, y * 32));
				target.draw(currentTile);
			}
		}
	}
	
	public static IntRect getTextureLocation(int index)
	{
		if (index < 0 || index >= 64)
			return new IntRect(0, 0, 32, 32);
		
		int x = index % 8;
		int y = index / 8;
		
		return new IntRect(x * 32, y * 32, 32, 32);
	}
}