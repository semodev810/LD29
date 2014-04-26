package com.semo.ld29.render;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.jsfml.graphics.IntRect;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;

import com.semo.ld29.Game;

public class AnimatedSprite 
{
	public class AnimationParameters
	{
		public final int framesWidth, framesHeight; // Number of frames in the texture
		public final int frameWidth, frameHeight; // Pixel dimension of each frame
		public final int numFrames;
		
		public AnimationParameters(int fx, int fy, int fwidth, int fheight)
		{
			framesWidth = fx;
			framesHeight = fy;
			frameWidth = fwidth;
			frameHeight = fheight;
			numFrames = framesWidth * framesHeight;
		}
		
		public boolean checkAgainst(Texture texture)
		{
			int fw = framesWidth * frameWidth, fh = framesHeight * frameHeight;
			return (fw == texture.getSize().x && fh == texture.getSize().y);
		}
	}
	
	private Texture texture;
	private Sprite frame;
	
	public final AnimationParameters parameters;
	
	private HashMap<String, Animation> animations;
	private Animation activeAnimation;
	
	public AnimatedSprite(String name, int fx, int fy, int fwidth, int fheight)
	{
		this.texture = new Texture();
		try 
		{
			texture.loadFromFile(Paths.get("resources\\" + name));
		} 
		catch (IOException e) 
		{
			System.out.println("Could not load entity texture \"" + name + "\". Using missing texture.");
			texture = Game.missingTexture;
		}
		
		if (texture == Game.missingTexture)
		{
			fx = 1;
			fy = 1;
			fwidth = texture.getSize().x;
			fheight = texture.getSize().y;
		}
		
		parameters = new AnimationParameters(fx, fy, fwidth, fheight);
		if (!parameters.checkAgainst(texture))
		{
			System.err.println("Incorrect animation dimensions provided for texture: \"" + name + "\".");
			System.err.println("Requested dimensions: (" + (fx * fwidth) + ", " + (fy * fheight) + "). Actual dimstions: (" + 
								texture.getSize().x + ", " + texture.getSize().y + ").");
			Game.getInstance().exit(Game.EXIT_ERROR);
		}
		
		this.frame = new Sprite(texture);
		this.animations = new HashMap<String, Animation>();
		addAnimation(new Animation(0, 0, 0, "None"));
	}
	
	public void render(RenderTarget target)
	{
		frame.setTextureRect(getSubset());
	}
	
	public IntRect getSubset()
	{
		int frame = activeAnimation.getCurrentFrame();
		int fx = frame % parameters.framesWidth, fy = frame / parameters.framesHeight;
		
		return new IntRect(fx * parameters.frameWidth, fy * parameters.frameHeight, parameters.frameWidth, parameters.frameHeight);
	}
	
	// ========================================================
	
	public boolean addAnimation(Animation animation)
	{
		if (hasAnimation(animation))
		{
			System.err.println("Cannot add the same animation \"" + animation.name + "\" twice.");
			return false;
		}
		
		this.animations.put(animation.name, animation);
		return true;
	}
	
	public boolean hasAnimation(Animation animation)
	{
		return hasAnimation(animation.name);
	}
	
	public boolean hasAnimation(String string)
	{
		return animations.containsKey(string);
	}
	
	public boolean activateAnimation(Animation animation)
	{
		if (!hasAnimation(animation) && !addAnimation(animation))
			return false;
		
		activateAnimation(animation.name);
		return true;
	}
	
	public boolean activateAnimation(String name)
	{
		if (!hasAnimation(name))
		{
			System.out.println("Tried to request the animation \"" + name + "\" on a AnimatedSprite that doesnt not have this animation.");
			activeAnimation = animations.get("None");
			return false;
		}
		
		activeAnimation = animations.get(name);
		return true;
	}
	
	// ================================================================
	
	public void play()
	{
		activeAnimation.play();
	}
	
	public void pause()
	{
		activeAnimation.pause();
	}
	
	public void stop()
	{
		activeAnimation.stop();
	}
	
	public void restart()
	{
		activeAnimation.restart();
	}
	
	public void reset()
	{
		activeAnimation.reset();
	}
}