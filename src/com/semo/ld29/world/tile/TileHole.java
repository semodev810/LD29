package com.semo.ld29.world.tile;

public class TileHole extends Tile
{
	public TileHole(int id, String name, int index)
	{
		super(id, name, index);
	}
	
	@Override
	public int getIndex(byte meta)
	{
		return super.getIndex(meta) + meta;
	}
}