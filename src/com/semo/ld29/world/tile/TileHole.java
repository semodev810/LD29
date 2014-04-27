package com.semo.ld29.world.tile;

import com.semo.ld29.entity.Entity;
import com.semo.ld29.entity.EntityMonster;

public class TileHole extends Tile
{
	public TileHole(int id, String name, int index)
	{
		super(id, name, index);
	}
	
	@Override
	public void onEntityEnter(Entity entity, int x, int y, byte meta)
	{
		if (entity instanceof EntityMonster)
			((EntityMonster) entity).kill();
	}
}