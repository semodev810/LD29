package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard.Key;

import com.semo.ld29.Game;
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
		
		{
			Vector2i mous = InputState.getMousePosition();
			Vector2i scr = Vector2i.div(Game.getInstance().getWindow().getSize(), 2);
			Vector2i dmouse = Vector2i.sub(mous, scr);
			dmouse = new Vector2i(dmouse.x, -dmouse.y);
			float angle = (float) Math.atan2(dmouse.y, dmouse.x);
			angle += (Math.PI / 8.0);
			if (angle < 0)
				angle += 2 * Math.PI;
			int direction = (int)(angle / (Math.PI / 4.0));
			int frame = sprite.getActiveAnimation().getCurrentFrame() % 8;
			switch (direction)
			{
			case 0:
				direction = 2;
				sprite.activateAnimation("Right");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 2:
				direction = 0;
				sprite.activateAnimation("Up");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 3:
				direction = 7;
				sprite.activateAnimation("UpLeft");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 4:
				direction = 6;
				sprite.activateAnimation("Left");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 6:
				direction = 4;
				sprite.activateAnimation("Down");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 7:
				direction = 3;
				sprite.activateAnimation("DownRight");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 1:
				sprite.activateAnimation("UpRight");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			case 5:
				sprite.activateAnimation("DownLeft");
				sprite.getActiveAnimation().setCurrentFrame(sprite.getActiveAnimation().startFrame + frame);
				break;
			}
			
			if (lastShot > shotSpeed && InputState.keyDown(Key.SPACE))
			{
				Entity.createEntityInWorld(new EntityBullet(Vector2f.add(position, new Vector2f(0, -1.25f)), direction, Resources.BULLET_SPRITE.copy()), world);
				lastShot = 0;
			}
		}
		
		move(mx, 0);
		move(0, my);
		
		super.update(elapsed);
	}
}