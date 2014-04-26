package com.semo.ld29.world;

import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.tile.Tile;

public class World 
{
	private int[][] tiles;
	
	public final int width;
	public final int height;
	
	public World(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.tiles = new int[width][height];
		
		for (int x = 0; x < width; ++x)
		{
			for (int y = 0; y < height; ++y)
			{
				if (y == 0)
					tiles[x][y] = 0;
				else
					tiles[x][y] = 1;
			}
		}
	}
	
	public void update(float elapsed)
	{
		
	}
	
	public void render(float elapsed)
	{
		WorldRenderer.renderWorld(this);
	}
	
	// ==============================================
	
	public Tile getTile(int x, int y)
	{
		if (!inBounds(x, y))
			return Tile.HOLE_EDGE;
		
		return Tile.getTile(tiles[x][y]);
	}
	
	public void setTile(int x, int y, int t)
	{
		setTile(x, y, Tile.getTile(t));
	}
	
	public void setTile(int x, int y, Tile t)
	{
		if (!inBounds(x, y))
			return;
		
		tiles[x][y] = t.id;
	}
	
	public boolean isHole(int x, int y)
	{
		if (!inBounds(x, y))
			return true;
		
		return getTile(x, y) == Tile.HOLE_EDGE || getTile(x, y) == Tile.HOLE_CENTER;
	}
	
	public boolean inBounds(int x, int y)
	{
		return (x >= 0) && (y >= 0) && (x < width) && (y < height);
	}
}