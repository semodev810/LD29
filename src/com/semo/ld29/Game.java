package com.semo.ld29;

import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.SizeEvent;

import com.semo.ld29.input.InputState;
import com.semo.ld29.render.EntityRenderer;
import com.semo.ld29.render.WorldRenderer;
import com.semo.ld29.world.World;

public class Game 
{
	private RenderWindow window;
	
	public Game()
	{
		instance = this;
		
		window = new RenderWindow();
		window.create(new VideoMode(800, 600), "Insert Whitty Title Here Eventually");
		window.setFramerateLimit(60);
		
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
	}
	
	public void run()
	{
		Clock clock = new Clock();
		
		World world = new World(10, 10);
		
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
			
			world.update(deltaTime);
			world.render(deltaTime);
			
			window.display();
		}
	}
	
	public RenderWindow getWindow()
	{
		return window;
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