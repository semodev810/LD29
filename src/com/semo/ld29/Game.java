package com.semo.ld29;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Vector2f;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.SizeEvent;

import com.semo.ld29.entity.Entity;
import com.semo.ld29.entity.EntityPlayer;
import com.semo.ld29.input.InputState;
import com.semo.ld29.render.AnimatedSprite;
import com.semo.ld29.render.EntityRenderer;
import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.World;

public class Game 
{
	public static Texture missingTexture;
	
	private RenderWindow window;
	private DebugConsole dConsole;
	
	public Game()
	{
		instance = this;
		
		window = new RenderWindow();
		window.create(new VideoMode(800, 600), "Insert Whitty Title Here Eventually");
		//window.setFramerateLimit(60);
		
		try 
		{
			Resources.init();
		} 
		catch (IOException e1) 
		{
			System.out.println("Failed while trying to load resources.");
			e1.printStackTrace();
			this.exit(EXIT_ERROR);
		}
		
		try
		{
			WorldRenderer.init(window);
		}
		catch (IOException e)
		{
			System.out.println("Could not load tileMap for world.");
			this.exit(EXIT_ERROR);
		}
		
		EntityRenderer.init(window);
		
		try 
		{
			dConsole = new DebugConsole(window);
		} 
		catch (IOException e) 
		{
			System.err.println("Could not load text file for the debug console.");
		}
		
		try
		{
			missingTexture = new Texture();
			missingTexture.loadFromFile(Paths.get("resources\\missing.png"));
		}
		catch(IOException e)
		{
			System.out.println("Could not load the missing texture.");
			this.exit(EXIT_ERROR);
		}
	}
	
	public void run()
	{
		Clock clock = new Clock();
		Clock fpsClock = new Clock();
		
		dConsole.updateInformation("FPS", "FPS: 0");
		
		World world = new World(10, 10);
		Entity.createEntityInWorld(new EntityPlayer(new Vector2f(50, 50)).setTexture(Resources.PLAYER_SPRITE, true), world);
		
		int frames = 0;
		while (window.isOpen())
		{
			window.clear(Color.BLACK);
			
			float deltaTime = (clock.restart()).asSeconds();
			InputState.swapStates();
			
			for (Event event : window.pollEvents())
			{
				InputState.handleEvent(event);
				
				if (event.type == Event.Type.CLOSED)
					window.close();
				
				if (event.type == Event.Type.RESIZED)
				{
					SizeEvent se = event.asSizeEvent();
					window.setView(new View(new FloatRect(0, 0, se.size.x, se.size.y)));
					// TODO: move the world renderer when the window resizes
				}
			}
			
			if (fpsClock.getElapsedTime().asSeconds() > 0.5f)
			{
				float totalTime = fpsClock.restart().asSeconds();
				dConsole.updateInformation("FPS", "FPS: " + (frames / totalTime));
				frames = 0;
			}
			
			world.update(deltaTime);
			world.render(deltaTime);
			
			dConsole.render();
			
			window.display();
			++frames;
		}
	}
	
	public RenderWindow getWindow()
	{
		return window;
	}
	
	public DebugConsole getDebugConsole()
	{
		return dConsole;
	}
	
	public static final int EXIT_OK = 0;
	public static final int EXIT_ERROR = -1;
	public void exit(int status)
	{
		window.close();
		
		if (status == EXIT_ERROR)
			System.err.println("The game exited with an error.");
		
		System.exit(status);
	}
	
	// ==========================================================
	
	private static Game instance;
	public static Game getInstance()
	{
		return instance;
	}
	
	public static void main(String... args)
	{
		(new Game()).run();
	}
}