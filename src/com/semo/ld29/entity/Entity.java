package com.semo.ld29.entity;

import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import com.semo.ld29.render.AnimatedSprite;
import com.semo.ld29.world.World;
import com.semo.ld29.world.tile.Tile;
import com.semo.ld29.world.tile.TileWall;

// Rendering is done with the EntityRenderer class and called by the containing world
public abstract class Entity 
{
	protected World world;
	protected FloatRect hitbox;
	
	private Vector2i tilePos;
	private Vector2i lastTilePos;
	protected Vector2f position;
	protected Vector2f lastPosition;
	protected Vector2f velocity;
	
	protected AnimatedSprite sprite;
	
	public Entity(Vector2f position)
	{
		this.position = position;
		this.lastPosition = position;
		this.tilePos = new Vector2i((int)position.x, (int)position.y);
		this.lastTilePos = new Vector2i((int)position.x, (int)position.y);
		this.hitbox = new FloatRect(position.x - 0.25f, position.x - 0.5f, 0.5f, 0.5f);
	}
	
	public Entity setSize(Vector2f sz)
	{
		this.hitbox = new FloatRect(position.x - (sz.x / 2), position.y - sz.y, sz.x, sz.y);
		return this;
	}
	
	public Vector2f getSize()
	{
		return new Vector2f(hitbox.width, hitbox.height);
	}
	
	public Vector2f getPosition()
	{
		return new Vector2f(position.x, position.y);
	}
	
//	public Entity setPosition(float x, float y)
//	{
//		return setPosition(new Vector2f(x, y));
//	}
//	
//	public Entity setPosition(Vector2f newPos)
//	{
//		if (this.canMoveTo(newPos))
//		{
//			this.position = newPos;
//			updateHitbox();
//		}
//		// TODO: check is a position changed event is necessary
//		return this;
//	}
//	
//	public Entity move(float x, float y)
//	{
//		return move(new Vector2f(x, y));
//	}
//	
//	public Entity move(Vector2f amt)
//	{
//		return setPosition(position.x + amt.x, position.y + amt.y);
//	}
	
	public Vector2f getVelocity()
	{
		return velocity;
	}
	
	public float getAbsoluteVelocity()
	{
		return (float) Math.sqrt(Math.pow(velocity.x, 2) + Math.pow(velocity.y, 2));
	}
	
	public Entity setVelocity(Vector2f vel)
	{
		velocity = vel;
		return this;
	}
	
	public Entity addToVelocity(Vector2f vel)
	{
		velocity = Vector2f.add(velocity, vel);
		return this;
	}
	
	public boolean canMoveTo(Vector2f loc)
	{
		float dx = loc.x - position.x;
		float dy = loc.y - position.y;
		
		int tilex = 0, tiley = 0;
		
		if (dx < 0)
			tilex = (int)hitbox.left;
		else if (dx > 0)
			tilex = (int)(hitbox.left + hitbox.width);
		else
			tilex = (int)position.x;
		
		if (dy < 0)
			tiley = (int)((position.y - (hitbox.height / 4))); // This kind of emulates a cylindrical hitbox
		else if (dy > 0)
			tiley = (int)((position.y + (hitbox.height / 4)));
		else
			tiley = (int)position.y;
		
		if (world.getTile(tilex, tiley) instanceof TileWall)
			this.onCollideWithWall();
		
		return world.getTile(tilex, tiley).canEntityEnterTile(this, tilex, tiley, world.getMetadata(tilex, tiley));
	}
	
	public Vector2f distanceTo(Vector2f point)
	{
		float dx = point.x - position.x;
		float dy = point.y - position.y;
		
		float deltax = 0, deltay = 0;
		if (dx < 0)
			deltax = point.x - hitbox.left;
		else if (dx > 0)
			deltax = point.x - (hitbox.left + hitbox.width);
		
		if (dy < 0)
			deltay = point.y - (position.y - (hitbox.height / 4));
		else if (dy > 0)
			deltay = point.y - (position.y + (hitbox.height / 4));
		
		return new Vector2f(deltax, deltay);
	}
	
	// DO NOT FORGET TO CALL super.update IN CHILD CLASSES, or sadness
	public void update(float elapsed)
	{	
		this.tilePos = new Vector2i((int)this.position.x, (int)this.position.y);
		
		Tile curr = world.getTile(tilePos.x, tilePos.y);
		if (lastTilePos.x != tilePos.x || lastTilePos.y != tilePos.y)
		{
			Tile last = world.getTile(lastTilePos.x, lastTilePos.y);
			last.onEntityExit(this, lastTilePos.x, lastTilePos.y, world.getMetadata(lastTilePos.x, lastTilePos.y));
			curr.onEntityEnter(this, tilePos.x, tilePos.y, world.getMetadata(tilePos.x, tilePos.y));
		}
		curr.onEntityStep(this, tilePos.x, tilePos.y, world.getMetadata(tilePos.x, tilePos.y));
		
		this.lastTilePos = new Vector2i(this.tilePos.x, this.tilePos.y);
		this.lastPosition = new Vector2f(this.position.x, this.position.y);
		sprite.update(elapsed);
	}
	
	// ==================================================
	
	public void onInitialize() { }
	public void onSpawn() { }
	public void onCollideWithWall() { }
	public void onCollideWithEntity(Entity other) { }
	
	// ==================================================
	
	public final Entity setTexture(AnimatedSprite sprite, boolean useDims)
	{
		this.sprite = sprite;
		this.sprite.setOrigin(new Vector2f(sprite.parameters.frameWidth / 2.0f, sprite.parameters.frameHeight));
		
		if (useDims)
			this.setSize(new Vector2f(sprite.parameters.frameWidth / 64.0f, sprite.parameters.frameHeight / 44.0f));
		
		return this;
	}
	
	public final AnimatedSprite getTexture()
	{
		return sprite;
	}
	
	public final FloatRect getHitbox()
	{
		return hitbox;
	}
	
	public void updateHitbox()
	{
		this.hitbox = new FloatRect(position.x - (hitbox.width / 2), position.y - hitbox.height, hitbox.width, hitbox.height);
	}
	
	public final World getWorld()
	{
		return world;
	}
	
	// =================================================
	public static void createEntityInWorld(Entity entity, World world)
	{
		entity.world = world;
		entity.onInitialize();
		world.addEntity(entity);
	}
}