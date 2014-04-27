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
	protected Vector2f position;
	protected Vector2f lastPosition;
	protected World world;
	protected FloatRect hitbox;
	
	protected int lastDirection;
	
	private Vector2i tilePos;
	private Vector2i lastTilePos;
	
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
	
	public Entity setPosition(float x, float y)
	{
		return setPosition(new Vector2f(x, y));
	}
	
	public Entity setPosition(Vector2f newPos)
	{
		if (this.canMoveTo(newPos))
		{
			this.position = newPos;
			updateHitbox();
		}
		// TODO: check is a position changed event is necessary
		return this;
	}
	
	public Entity move(float x, float y)
	{
		return move(new Vector2f(x, y));
	}
	
	public Entity move(Vector2f amt)
	{
		return setPosition(position.x + amt.x, position.y + amt.y);
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
	
	public void updateHitbox()
	{
		this.hitbox = new FloatRect(position.x - (hitbox.width / 2), position.y - hitbox.height, hitbox.width, hitbox.height);
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
		float dx = position.x - lastPosition.x;
		float dy = position.y - lastPosition.y;
		
		int lr = (dx < 0) ? 0 : (dx == 0) ? 1 : 2;
		int ud = (dy < 0) ? 0 : (dy == 0) ? 1 : 2;
		
		if (ud == 0 && lr == 0)
			lastDirection = 7;
		else if (ud == 0 && lr == 1)
			lastDirection = 0;
		else if (ud == 0 && lr == 2)
			lastDirection = 1;
		else if (ud == 1 && lr == 0)
			lastDirection = 6;
		else if (ud == 1 && lr == 2)
			lastDirection = 2;
		else if (ud == 2 && lr == 0)
			lastDirection = 5;
		else if (ud == 2 && lr == 1)
			lastDirection = 4;
		else if (ud == 2 && lr == 2)
			lastDirection = 3;
		
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
	
	// =================================================
	public static void createEntityInWorld(Entity entity, World world)
	{
		entity.world = world;
		entity.onInitialize();
		world.addEntity(entity);
	}
}