package com.semo.ld29;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class Game 
{
	private RenderWindow window;
	
	public Game()
	{
		instance = this;
		
		window = new RenderWindow();
		window.create(new VideoMode(800, 600), "Insert Whitty Title Here Eventually");
		window.setFramerateLimit(60);
	}
	
	public void run()
	{
		Clock clock = new Clock();
		
		while (window.isOpen())
		{
			window.clear(Color.BLACK);
			
			float deltaTime = (clock.restart()).asSeconds();
			
			for (Event event : window.pollEvents())
			{
				if (event.type == Event.Type.CLOSED)
					window.close();
			}
			
			window.display();
		}
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