package com.semo.ld29.render;

import java.util.ArrayList;

import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import com.semo.ld29.Game;
import com.semo.ld29.entity.Entity;

public class EntityRenderer 
{
	private static RenderTarget target; // RenderTarget allows the target to be a window or a render to texture
	
	public static void init(RenderTarget target)
	{
		EntityRenderer.target = target;
	}
	
	public static void renderAllEntities(ArrayList<Entity> entities)
	{
		View oldView = (View) target.getView();
		Vector2f playerPos = Game.getInstance().getPlayer().getPosition();
		View newView = new View(new Vector2f(playerPos.x * 64, playerPos.y * 44), oldView.getSize());
		target.setView(newView);
		
		for (Entity e : entities)
			renderEntity(e);
		
		target.setView(oldView);
	}
	
	public static void renderEntity(Entity entity)
	{
		Vector2f pos = entity.getPosition();
		entity.getTexture().render(target, new Vector2f(pos.x * 64, pos.y * 44), new Vector2f(2, 2));
	}
}