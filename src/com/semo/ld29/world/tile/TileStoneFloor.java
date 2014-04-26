package com.semo.ld29.world.tile;

public class TileStoneFloor extends Tile
{
	
	/*
	 * Metadata guide:
	 * 0 - Empty stone
	 * 1 - Poorly drawn rocks
	 * 2 - crack
	 */
	
	public TileStoneFloor(int id, String name, int index)
	{
		super(id, name, index);
	}
	
	@Override
	public int getIndex(byte meta)
	{
		return super.getIndex(meta) + meta;
	}
}