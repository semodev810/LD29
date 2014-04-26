package com.semo.ld29;

import java.io.IOException;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import com.semo.ld29.input.InputState;
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
	}
	
	public void run()
	{
		Clock clock = new Clock();
		
		World world = new World(10, 10);
		
		while (window.isOpen())
		{
			window.clear(Color.WHITE);
			
			float deltaTime = (clock.restart()).asSeconds();
			InputState.swapStates();
			
			for (Event event : window.pollEvents())
			{
				InputState.handleEvent(event);
				
				if (event.type == Event.Type.CLOSED)
					window.close();
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