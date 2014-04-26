package com.semo.ld29.world;

import java.util.ArrayList;

import com.semo.ld29.entity.Entity;
import com.semo.ld29.render.EntityRenderer;
import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.tile.Tile;

public class World 
{
	private int[][] tileData;
	private byte[][] metaData;
	
	public final int width;
	public final int height;
	
	private ArrayList<Entity> entityList;
	
	public World(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		this.tileData = new int[width][height];
		this.metaData = new byte[width][height];
		
		for (int x = 0; x < width; ++x)
			for (int y = 0; y < height; ++y)
				setTile(x, y, 0);
		
		// Keep for testing purposes with new tiles
		for (int x = 0; x < 4; ++x)
		{
			for (int y = 0; y < 3; ++y)
			{
				setTile(x + 3, y + 3, 1, (byte)0);
			}
		}
		
		setTile(7, 3, 2);
		
		for (int x = 0; x < 4; ++x)
			setTile(x + 4, 2, 2);
		
		setTile(7, 4, 2);
		setTile(7, 5, 2);
		
		entityList = new ArrayList<Entity>();
	}
	
	public void update(float elapsed)
	{
		for (Entity ent : entityList)
			ent.update(elapsed);
	}
	
	public void render(float elapsed)
	{
		WorldRenderer.renderWorld(this, 0);
		WorldRenderer.renderWorld(this, 1);
		
		for (Entity ent : entityList)
			EntityRenderer.renderEntity(ent);
	}
	
	// ==============================================
	
	public void addEntity(Entity entity)
	{
		if (!entityList.contains(entity))
		{
			entityList.add(entity);
			entity.onSpawn();
			// TODO: fire event for the entity spawn?
		}
	}
	
	public ArrayList<Entity> getEntities()
	{
		return entityList;
	}
	
	public void removeEntity(Entity entity)
	{
		if (entityList.contains(entity))
			entityList.remove(entityList.indexOf(entity));
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