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
			move(64 * elapsed, 0);
		}
	}
}