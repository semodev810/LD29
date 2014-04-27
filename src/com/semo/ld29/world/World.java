package com.semo.ld29.world;

import java.util.ArrayList;

import com.semo.ld29.entity.Entity;
import com.semo.ld29.render.EntityRenderer;
import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.tile.Tile;

public class World 
{
//	private static final int[][] TILEDATA = 
//		{
//		{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
//		{ 1, 1, 2, 1, 1, 2, 1, 2, 1, 2 },
//		{ 1, 1, 2, 1, 1, 2, 1, 2, 1, 2 },
//		{ 1, 1, 2, 1, 1, 2, 1, 2, 1, 2 },
//		{ 1, 1, 2, 2, 1, 2, 1, 2, 1, 2 },
//		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 2 },
//		{ 1, 1, 0, 0, 1, 1, 1, 1, 1, 2 },
//		{ 1, 1, 0, 0, 1, 1, 1, 1, 1, 2 },
//		{ 1, 1, 1, 0, 1, 1, 1, 1, 1, 2 },
//		{ 1, 1, 1, 1, 1, 1, 2, 2, 2, 2 }
//		};
	
	
	private int[][] tileData;
	private byte[][] metaData;
	
	public final int width;
	public final int height;
	
	private ArrayList<Entity> entityList;
	private ArrayList<Entity> toRemoveList;
	
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
				if (x == 0 || y == 0 || x == (width - 1) || y == (height - 1))
					setTile(x, y, 2);
				else
					setTile(x, y, 1);
			}
		}
		
		setTile(4, 4, 0);

		entityList = new ArrayList<Entity>();
		toRemoveList = new ArrayList<Entity>();
	}
	
	public void update(float elapsed)
	{
		for (int i = 0; i < entityList.size(); ++i)
			entityList.get(i).update(elapsed);
		
		// Brute force approach to collision checking
		for (int i = 0; i < entityList.size(); ++i)
		{
			for (int j = i + 1; j < entityList.size(); ++j)
			{
				Entity e1 = entityList.get(i);
				Entity e2 = entityList.get(j);
				if (e1.getHitbox().intersection(e2.getHitbox()) != null)
				{
					System.out.println("e1: " + e1 + "    e2: " + e2);
					e1.onCollideWithEntity(e2);
					e2.onCollideWithEntity(e1);
				}
			}
		}
		
		if (!toRemoveList.isEmpty())
			for (Entity ent : toRemoveList)
				if (entityList.contains(ent))
					entityList.remove(ent);
		
		toRemoveList.clear();
	}
	
	public void render(float elapsed)
	{
		WorldRenderer.renderWorld(this, 0);
		
		EntityRenderer.renderAllEntities(entityList);
		
		WorldRenderer.renderWorld(this, 1);
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
		toRemoveList.add(entity);
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