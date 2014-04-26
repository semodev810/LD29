package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard.Key;

import com.semo.ld29.input.InputState;

public class EntityPlayer extends Entity
{
	public EntityPlayer(Vector2f position)
	{
		super(position);
	}
	
	@Override
	public void update(float elapsed)
	{
		float mx = 0, my = 0;
		
		if (InputState.keyDown(Key.RIGHT))
		{
			mx += 128 * elapsed;
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.LEFT))
		{
			mx += -128 * elapsed;
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.UP))
		{
			my += -88 * elapsed;
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.DOWN))
		{
			my += 88 * elapsed;
			sprite.activateAnimation("Jump");
		}
		
		move(mx, 0);
		move(0, my);
		
		super.update(elapsed);
	}
}