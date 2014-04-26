package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.semo.ld29.world.World;
import com.semo.ld29.world.tile.Tile;

// Rendering is done with the EntityRenderer class and called by the containing world
public abstract class Entity 
{
	protected Vector2f position;
	protected World world;
	
	private Vector2i tilePos;
	
	public Entity(Vector2f position)
	{
		this.position = position;
		this.tilePos = new Vector2i((int)position.x / 64, (int)position.y / 44);
	}
	
	public Vector2f getPosition()
	{
		return new Vector2f(position.x, position.y);
	}
	
	public void setPosition(Vector2f newPos)
	{
		this.position = newPos;
		// TODO: check is a position changed event is necessary
	}
	
	public final World getWorld()
	{
		return world;
	}
	
	public void onInitialize() { }
	public void onSpawn() { }
	
	// DO NOT FORGET TO CALL super.update IN CHILD CLASSES, or sadness
	public void update(float elapsed)
	{
		int tx = (int)position.x / 64;
		int ty = (int)position.y / 44;
		
		Tile curr = world.getTile(tilePos.x, tilePos.y);
		curr.onEntityStep(this, tilePos.x, tilePos.y, world.getMetadata(tilePos.x, tilePos.y));
		if (tx != tilePos.x || ty != tilePos.y)
		{
			curr.onEntityExit(this, tilePos.x, tilePos.y, world.getMetadata(tilePos.x, tilePos.y));
			world.getTile(tx, ty).onEntityEnter(this, tilePos.x, tilePos.y, world.getMetadata(tilePos.x, tilePos.y));
			tilePos = new Vector2i(tx, ty);
		}
	}
	
	// =================================================
	public static void createEntityInWorld(Entity entity, World world)
	{
		entity.world = world;
		entity.onInitialize();
		world.addEntity(entity);
	}
}