package com.semo.ld29.entity;

import org.jsfml.system.Vector2f;

public class EntityMonster extends Entity
{
	protected int health;
	protected int maxHealth;
	
	public EntityMonster(Vector2f position, int maxHealth)
	{
		super(position);
		this.health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void damage(int amt)
	{
		health -= amt;
		
		if (health <= 0)
			kill();
	}
	
	public void heal(int amt)
	{
		health += amt;
		
		if (health > maxHealth)
			health = maxHealth;
	}
	
	public void kill()
	{
		this.world.removeEntity(this);
	}
	
	@Override
	public void onCollideWithEntity(Entity other)
	{
		if (other instanceof EntityBullet && !(this instanceof EntityPlayer))
		{
			this.damage(1);
			System.out.println("IM HIT!");
		}
	}
}