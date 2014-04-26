package com.semo.ld29.render;

import org.jsfml.graphics.RenderTarget;

import com.semo.ld29.entity.Entity;

public class EntityRenderer 
{
	private static RenderTarget target; // RenderTarget allows the target to be a window or a render to texture
	
	public static void init(RenderTarget target)
	{
		EntityRenderer.target = target;
	}
	
	public static void renderEntity(Entity entity)
	{
		entity.getTexture().render(target, entity.getPosition());
	}
}