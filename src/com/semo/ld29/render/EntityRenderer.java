package com.semo.ld29.render;

import java.util.ArrayList;

import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.View;

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
		View newView = new View(Game.getInstance().getPlayer().getPosition(), oldView.getSize());
		target.setView(newView);
		
		for (Entity e : entities)
			renderEntity(e);
		
		target.setView(oldView);
	}
	
	public static void renderEntity(Entity entity)
	{
		entity.getTexture().render(target, entity.getPosition());
	}
}