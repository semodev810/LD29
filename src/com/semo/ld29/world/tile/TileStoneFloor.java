package com.semo.ld29.world.tile;

public class TileStoneFloor extends Tile
{
	
	/*
	 * Metadata guide:
	 * 0 - Empty stone
	 * 1 - Poorly drawn rocks
	 */
	
	public TileStoneFloor(int id, int index)
	{
		super(id, "StoneFloor", index);
	}
	
	@Override
	public int getIndex(byte meta)
	{
		return super.getIndex(meta) + meta;
	}
}