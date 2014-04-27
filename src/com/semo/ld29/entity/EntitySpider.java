package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;

import com.semo.ld29.Resources;

public class EntitySpider extends EntityMonster
{
	public EntitySpider(Vector2f position)
	{
		super(position, 5);
		this.setTexture(Resources.SPIDER_SPRITE.copy(), true);
	}
}