package com.semo.ld29.render;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2f;

import com.semo.ld29.world.World;

public class WorldRenderer 
{
	public static Texture tileMap;
	public static Sprite currentTile;
	private static RenderTarget target; // RenderTarget allows the target to be a window or a render to texture
	
	public static void init(RenderTarget target) throws IOException
	{
		WorldRenderer.target = target;
		
		tileMap = new Texture();
		tileMap.loadFromFile(Paths.get("resources\\tileMap.png"));
		
		currentTile = new Sprite(tileMap);
		currentTile.setOrigin(new Vector2f(0, 0));
		currentTile.setTextureRect(getTextureLocation(0));
		currentTile.setPosition(new Vector2f(0, 0));
		currentTile.setScale(new Vector2f(2, 2));
	}
	
	public static void renderWorld(World world)
	{
		currentTile.setTextureRect(getTextureLocation(0));
		
		// TODO: make this more efficient, not rendering EVERYTHING only what needs to be rendered (ie what is on screen)
		for (int y = 0; y < world.width; ++y)
		{
			for (int x = 0; x < world.height; ++x)
			{
				if (!world.isHole(x, y)) 
				{
					currentTile.setTextureRect(getTextureLocation(world.getTile(x, y).getIndex(world.getMetadata(x, y))));
					currentTile.setPosition(new Vector2f(x * 64, y * 44)); // This is because the front texture is 10 px tall and x2 scale
					target.draw(currentTile);
				}
			}
		}
	}
	
	public static IntRect getTextureLocation(int index)
	{
		if (index < 0 || index >= 64)
			return new IntRect(0, 0, 32, 32);
		
		int x = index % 16;
		int y = index / 16;
		
		return new IntRect(x * 32, y * 32, 32, 32);
	}
}