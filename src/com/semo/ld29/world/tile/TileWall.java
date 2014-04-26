package com.semo.ld29.world.tile;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.semo.ld29.entity.Entity;

public class TileWall extends Tile
{
	public TileWall(int id, String name, int index)
	{
		super(id, name, index);
	}
	
	@Override
	public boolean canEntityEnterTile(Entity entity, int x, int y, byte meta)
	{
		return false;
	}
	
	@Override
	public Vector2i getTextureSize(byte meta)
	{
		return new Vector2i(32, 74);
	}
	
	@Override
	public Vector2f getOffset(byte meta)
	{
		return new Vector2f(0, -84);
	}
	
	@Override
	public int getRenderPass()
	{
		return 0;
	}
}