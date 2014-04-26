package com.semo.ld29;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Text;

public class DebugConsole 
{
	private RenderTarget target;
	
	private TreeMap<String, String> infoMap;
	
	private Font font;
	private Text text;
	
	public DebugConsole(RenderTarget target) throws IOException
	{
		this.target = target;
		
		this.infoMap = new TreeMap<String, String>();
		
		this.font = new Font();
		font.loadFromFile(Paths.get("resources\\consola.ttf"));
		text = new Text();
		text.setFont(font);
		text.setColor(Color.YELLOW);
		text.setCharacterSize(14);
	}
	
	public void render()
	{
		float ty = 5;
		for (String str : infoMap.values())
		{
			text.setString(str);
			text.setPosition(5, ty);
			ty += text.getLocalBounds().height + 4;
			target.draw(text);
		}
	}
	
	public void updateInformation(String key, String info)
	{
		infoMap.put(key, info);
	}
	
	public RenderTarget getTarget()
	{
		return target;
	}
}