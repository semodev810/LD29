package com.semo.ld29.input;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;

public class MouseState 
{
	public static final int NUM_BUTTONS = Mouse.Button.XBUTTON2.ordinal();
	
	boolean[] buttons;
	Vector2i position;
	int wheelPos;
	
	public MouseState()
	{
		buttons = new boolean[NUM_BUTTONS];
		
		for (int i = 0; i < NUM_BUTTONS; ++i)
			buttons[i] = false;
		
		position = new Vector2i(0, 0);
		wheelPos = 0;
	}
	
	public MouseState(final MouseState other)
	{
		buttons = new boolean[NUM_BUTTONS];
		
		for (int i = 0; i < NUM_BUTTONS; ++i)
			buttons[i] = other.buttons[i];
		
		position = new Vector2i(other.position.x, other.position.y);
		wheelPos = other.wheelPos;
	}
	
	public boolean getButton(Button button)
	{
		return buttons[button.ordinal()];
	}
	
	// Changing the returned position could derp things up.
	public Vector2i getPosition()
	{
		return position;
	}
	
	public int getWheelPos()
	{
		return wheelPos;
	}
}