package com.semo.ld29.world.tile;

import com.semo.ld29.entity.Entity;

public class TileHole extends Tile
{
	public TileHole(int id, String name, int index)
	{
		super(id, name, index);
	}
	
	@Override
	public void onEntityEnter(Entity entity, int x, int y, byte meta)
	{
		entity.setPosition(1, 1);
	}
}