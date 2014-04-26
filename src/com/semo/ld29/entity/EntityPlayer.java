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
		super.update(elapsed);
		
		if (InputState.keyDown(Key.RIGHT))
		{
			move(128 * elapsed, 0);
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.LEFT))
		{
			move(-128 * elapsed, 0);
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.UP))
		{
			move(0, -88 * elapsed);
			sprite.activateAnimation("Jump");
		}
		if (InputState.keyDown(Key.DOWN))
		{
			move(0, 88 * elapsed);
			sprite.activateAnimation("Jump");
		}
	}
}