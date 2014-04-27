package com.semo.ld29.entity;

import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;

import com.semo.ld29.render.AnimatedSprite;

public class EntityBullet extends Entity
{
	private static final float SPEED = (float) (15.0f / Math.sqrt(2));
	private static final float LIFETIME = 3;
	
	private int direction;
	private float lifetime;
	
	public EntityBullet(Vector2f position, int direction, AnimatedSprite sprite)
	{
		super(position);
		super.setTexture(sprite, true);
		
		setDirection(direction);
	}
	
	public void setDirection(int dir)
	{
		this.direction = dir;
		
		switch (direction)
		{
		case 0:
			this.sprite.activateAnimation("Up");
			break;
		case 1:
			this.sprite.activateAnimation("UpRight");
			break;
		case 2:
			this.sprite.activateAnimation("Right");
			break;
		case 3:
			this.sprite.activateAnimation("DownRight");
			break;
		case 4:
			this.sprite.activateAnimation("Down");
			break;
		case 5:
			this.sprite.activateAnimation("DownLeft");
			break;
		case 6:
			this.sprite.activateAnimation("Left");
			break;
		case 7:
			this.sprite.activateAnimation("UpLeft");
			break;
		default:
			this.sprite.activateAnimation("Up");
			break;
		}
	}
	
	@Override
	public void update(float elapsed)
	{
		lifetime += elapsed;
		if (lifetime > LIFETIME)
		{
			world.removeEntity(this);
			return;
		}
		
		switch (direction)
		{
		case 0:
			this.move(0, -15 * elapsed);
			break;
		case 1:
			this.move(SPEED * elapsed, -SPEED * elapsed);
			break;
		case 2:
			this.move(15 * elapsed, 0);
			break;
		case 3:
			this.move(SPEED * elapsed, SPEED * elapsed);
			break;
		case 4:
			this.move(0, 15 * elapsed);
			break;
		case 5:
			this.move(-SPEED * elapsed, SPEED * elapsed);
			break;
		case 6:
			this.move(-15 * elapsed, 0);
			break;
		case 7:
			this.move(-SPEED * elapsed, -SPEED * elapsed);
			break;
		default:
			this.move(0, -15 * elapsed);
			break;
		}
		
		super.update(elapsed);
	}
	
	@Override
	public Vector2f getRenderOffset()
	{
		return new Vector2f(0, -64);
	}
	
	@Override
	public FloatRect getHitbox()
	{
		return new FloatRect(hitbox.left, hitbox.top - 1.25f, hitbox.width, hitbox.height);
	}
	
	@Override
	public void onCollideWithWall()
	{
		world.removeEntity(this);
		return;
	}
}
