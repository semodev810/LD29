package com.semo.ld29.world;

import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.tile.Tile;

public class World 
{
	private int[][] tileData;
	private byte[][] metaData;
	
	public final int width;
	public final int height;
	
	public World(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.tileData = new int[width][height];
		this.metaData = new byte[width][height];
		
		for (int x = 0; x < width; ++x)
		{
			for (int y = 0; y < height; ++y)
			{
				if (y == 0)
					setTile(x, y, 0);
				else
					setTile(x, y, 0, (byte)1);
			}
		}
		
		for (int x = 0; x < 4; ++x)
		{
			for (int y = 0; y < 3; ++y)
			{
				setTile(x + 3, y + 3, 1, (byte)0);
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
			return Tile.HOLE;
		
		return Tile.getTile(tileData[x][y]);
	}
	
	public void setTile(int x, int y, int t, byte meta)
	{
		setTile(x, y, Tile.getTile(t), meta);
	}
	
	public void setTile(int x, int y, Tile t, byte meta)
	{
		if (!inBounds(x, y))
			return;
		
		tileData[x][y] = t.id;
		setMetadata(x, y, meta);
	}
	
	public void setTile(int x, int y, int t)
	{
		setTile(x, y, Tile.getTile(t));
	}
	
	public void setTile(int x, int y, Tile t)
	{
		if (!inBounds(x, y))
			return;
		
		tileData[x][y] = t.id;
		setMetadata(x, y, (byte)0);
	}
	
	// TODO: Make this update neighboors
	public void setMetadata(int x, int y, byte meta)
	{
		if (!inBounds(x, y))
			return;
		
		metaData[x][y] = meta;
	}
	
	public byte getMetadata(int x, int y)
	{
		if (!inBounds(x, y))
			return 0;
		
		return metaData[x][y];
	}
	
	// ================================================
	
	public boolean isHole(int x, int y)
	{
		if (!inBounds(x, y))
			return true;
		
		return getTile(x, y) == Tile.HOLE;
	}
	
	public boolean inBounds(int x, int y)
	{
		return (x >= 0) && (y >= 0) && (x < width) && (y < height);
	}
}