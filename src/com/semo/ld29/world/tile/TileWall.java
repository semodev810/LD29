package com.semo.ld29.world.tile;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.semo.ld29.Game;
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
	public Vector2i getTextureSize(byte meta, int pass)
	{
		return new Vector2i(32, 64);
	}
	
	@Override
	public Vector2i getTextureOffset(byte meta, int pass)
	{
		return new Vector2i(0, 0);
	}
	
	@Override
	public Vector2f getOffset(byte meta, int pass)
	{
		return new Vector2f(0, -64);
	}
	
	@Override
	public boolean shouldRenderInPassAtLocation(int x, int y, int pass)
	{
		Vector2i player = Game.getInstance().getPlayer().getTilePosition();

		if (pass == 0)
			return (y <= player.y);
		else
			return (y > player.y); 
	}
	
	@Override
	public boolean rendersInPass(int pass)
	{
		return pass == 0 || pass == 1;
	}
}