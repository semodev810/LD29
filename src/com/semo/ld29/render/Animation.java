package com.semo.ld29.render;

public class Animation 
{
	public static enum AnimationType
	{
		SINGLE, BACK_SINGLE, LOOP, BACK_LOOP, BOUNCE, BACK_BOUNCE
	}
	
	public final int startFrame;
	public final int endFrame;
	public final String name;
	
	private float speed;
	private AnimationType type;
	private int currentFrame;
	
	private boolean forward; // If this is a bounce animation, we need to know the direction
	private boolean playing;
	private float lastUpdate;
	
	public Animation(int start, int end, float speed, String name)
	{
		this(start, end, speed, name, AnimationType.LOOP);
	}
	
	public Animation(int start, int end, float speed, String name, AnimationType type)
	{
		assert start <= end : "You cannot set an animations start point to be past its end point.";
		
		this.startFrame = start;
		this.endFrame = end;
		this.speed = speed;
		this.name = name;
		
		switch (type)
		{
		case SINGLE:
		case LOOP:
		case BOUNCE:
			this.currentFrame = start;
			this.forward = true;
			break;
		case BACK_SINGLE:
		case BACK_LOOP:
		case BACK_BOUNCE:
			this.currentFrame = end;
			this.forward = false;
			break;
		}
		
		this.lastUpdate = 0;
		this.playing = true;
		this.type = type;
	}
	
	public void update(float elapsed)
	{
		if (!playing)
			return;
		
		lastUpdate += elapsed;
		if (lastUpdate < speed)
			return;
		
		lastUpdate = 0;
		
		if (type == AnimationType.BACK_BOUNCE || type == AnimationType.BOUNCE) 
		{
			if (forward)
			{
				if (currentFrame < endFrame)
					++currentFrame;
				else
				{
					--currentFrame;
					forward = false;
				}
			}
			else
			{
				if (currentFrame > startFrame)
					--currentFrame;
				else
				{
					++currentFrame;
					forward = true;
				}
			}
		}
		else
		{
			String typename = type.toString().toLowerCase();

			if (typename.contains("back_")) // If this is a reverse animation
			{
				if (type == AnimationType.BACK_SINGLE) 
				{
					if (currentFrame > startFrame)
						--currentFrame;
				} 
				else if (type == AnimationType.BACK_LOOP)
				{
					if (currentFrame > startFrame)
						--currentFrame;
					else
						currentFrame = endFrame;
				}
			} 
			else 
			{
				if (type == AnimationType.SINGLE)
				{
					if (currentFrame < endFrame)
						++currentFrame;
				}
				else if (type == AnimationType.LOOP)
				{
					if (currentFrame < endFrame)
						++currentFrame;
					else
						currentFrame = startFrame;
				}
			}
		}
	}
	
	// =============================================================
	
	public void play()
	{
		playing = true;
	}
	
	public void pause()
	{
		playing = false;
	}
	
	public void stop()
	{
		playing = false;
		reset();
	}
	
	public void restart()
	{
		playing = true;
		reset();
	}
	
	public void reset()
	{
		switch (type)
		{
		case SINGLE:
		case LOOP:
		case BOUNCE:
			this.currentFrame = startFrame;
			this.forward = true;
			break;
		case BACK_SINGLE:
		case BACK_LOOP:
		case BACK_BOUNCE:
			this.currentFrame = endFrame;
			this.forward = false;
			break;
		}
	}
	
	// =============================================================
	
	public AnimationType getType()
	{
		return type;
	}
	
	public Animation setType(AnimationType type, boolean restart)
	{
		this.type = type;
		if (restart)
			restart();
		
		return this;
	}
	
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	public Animation setSpeed(float speed)
	{
		this.speed = speed;
		return this;
	}
	
	// ===================================
	
	@Override
	public Object clone()
	{
		Animation anim = new Animation(startFrame, endFrame, speed, name, type);
		anim.currentFrame = this.currentFrame;
		anim.forward = this.forward;
		return anim;
	}
}