package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard.Key;

import com.semo.ld29.Resources;
import com.semo.ld29.input.InputState;

public class EntityPlayer extends EntityMonster
{
	private float lastShot = 0;
	private float shotSpeed = 0.1f;
	
	public EntityPlayer(Vector2f position)
	{
		super(position, 15);
	}
	
	@Override
	public void update(float elapsed)
	{
		float mx = 0, my = 0;
		lastShot += elapsed;
		
		if (InputState.keyDown(Key.RIGHT) || InputState.keyDown(Key.D))
		{
			mx += 3 * elapsed;
			//sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.LEFT) || InputState.keyDown(Key.A))
		{
			mx += -3 * elapsed;
			//sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.UP) || InputState.keyDown(Key.W))
		{
			my += -3 * elapsed;
			//sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.DOWN) || InputState.keyDown(Key.S))
		{
			my += 3 * elapsed;
			//sprite.activateAnimation("Jump");
		}
		
		if (lastShot > shotSpeed && InputState.keyDown(Key.SPACE))
		{
			lastShot = 0;
			System.out.println(lastDirection);
			Entity.createEntityInWorld(new EntityBullet(this.getPosition(), this.lastDirection, Resources.BULLET_SPRITE.copy()), world);
		}
		
		move(mx, 0);
		move(0, my);
		
		super.update(elapsed);
	}
}