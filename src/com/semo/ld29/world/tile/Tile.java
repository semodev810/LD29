package com.semo.ld29.world.tile;

import com.semo.ld29.Game;
import com.semo.ld29.entity.Entity;

public class Tile 
{
	public final int id;
	public final String name;
	
	protected int index; // The index of the tile on the spritesheet
	
	public Tile(int id, String name)
	{
		this(id, name, 0);
	}
	
	public Tile(int id, String name, int index)
	{
		this.id = id;
		this.name = name;
		this.index = index;
		
		if (Tile.tileExists(id))
		{
			System.err.println("Cannot register the same tile id [" + id + "] twice.");
			Game.getInstance().exit(Game.EXIT_ERROR);
		}
		
		Tile.tiles[id] = this;
	}
	
	// ======================================================
	
	// Checks if an entity can walk on a tile
	public boolean canEntityEnterTile(Entity entity, int x, int y, byte meta)
	{
		return true;
	}
	
	public void onEntityEnter(Entity entity, int x, int y, byte meta) { }
	
	public void onEntityStep(Entity entity, int x, int y, byte meta) { }
	
	public void onEntityExit(Entity entity, int x, int y, byte meta) { }
	
	// ======================================================
	
	public Tile setIndex(int index)
	{
		if (index < 0 || index > 64)
			return this;
		
		this.index = index;
		return this;
	}
	
	public int getIndex(byte meta)
	{
		return index;
	}
	
	// ==========================================================================
	public static Tile[] tiles;
	public static final Tile HOLE;
	public static final Tile STONE_FLOOR;
	
	static
	{
		tiles = new Tile[64];
		HOLE = new Tile(0, "Hole", 0);
		STONE_FLOOR = new TileStoneFloor(1, "StoneFloor", 1);
	}
	
	public static boolean tileExists(int id)
	{
		if (id < 0 || id >= tiles.length)
			return false;
		
		return tiles[id] != null;
	}
	
	public static Tile getTile(int id)
	{
		if (id < 0 || id >= tiles.length)
			return Tile.HOLE;
		
		return Tile.tiles[id];
	}
}