package com.semo.ld29.input;

import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;

public class KeyboardState 
{
	public static final int NUM_KEYS = Keyboard.Key.PAUSE.ordinal();
	
	boolean[] keys;
	
	public KeyboardState()
	{
		keys = new boolean[NUM_KEYS];
		
		for (int i = 0; i < NUM_KEYS; ++i)
			keys[i] = false;
	}
	
	public KeyboardState(final KeyboardState other)
	{
		keys = new boolean[NUM_KEYS];
		
		for (int i = 0; i < NUM_KEYS; ++i)
			keys[i] = other.keys[i];
	}
	
	public boolean getKey(Key key)
	{
		return keys[key.ordinal()];
	}
}