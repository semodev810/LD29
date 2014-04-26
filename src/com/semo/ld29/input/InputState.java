package com.semo.ld29.input;

import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;
import org.jsfml.window.event.MouseWheelEvent;

public class InputState 
{
	private static KeyboardState lastKeyState = new KeyboardState();
	private static KeyboardState currentKeyState = new KeyboardState();
	private static MouseState lastMouseState = new MouseState();
	private static MouseState currentMouseState = new MouseState();
	
	public static void handleEvent(Event event)
	{
		switch (event.type)
		{
		case KEY_PRESSED:
		case KEY_RELEASED:
			KeyEvent ke = event.asKeyEvent();
			currentKeyState.keys[ke.key.ordinal()] = (event.type == Event.Type.KEY_PRESSED) ? true : false;
			break;
		case MOUSE_BUTTON_PRESSED:
		case MOUSE_BUTTON_RELEASED:
			MouseButtonEvent mbe = event.asMouseButtonEvent();
			currentMouseState.buttons[mbe.button.ordinal()] = (event.type == Event.Type.MOUSE_BUTTON_PRESSED) ? true : false;
			break;
		case MOUSE_MOVED:
			MouseEvent me = event.asMouseEvent();
			currentMouseState.position = new Vector2i(me.position.x, me.position.y);
			break;
		case MOUSE_WHEEL_MOVED:
			MouseWheelEvent mwe = event.asMouseWheelEvent();
			currentMouseState.wheelPos = lastMouseState.wheelPos + mwe.delta;
			break;
		default:
			return;
		}
	}
	
	public static void swapStates()
	{
		lastKeyState = new KeyboardState(currentKeyState);
		lastMouseState = new MouseState(currentMouseState);
	}
	
	// Keyboard methods ==============================================
	public static boolean keyDown(Key key)
	{
		return currentKeyState.getKey(key);
	}
	
	public static boolean keyUp(Key key)
	{
		return !keyDown(key);
	}
	
	public static boolean keyPressed(Key key)
	{
		return keyDown(key) && !lastKeyState.getKey(key);
	}
	
	public static boolean keyReleased(Key key)
	{
		return keyUp(key) && lastKeyState.getKey(key);
	}
	
	// Mouse methods ===============================================
	public static boolean buttonDown(Button button)
	{
		return currentMouseState.getButton(button);
	}
	
	public static boolean buttonUp(Button button)
	{
		return !buttonDown(button);
	}
	
	public static boolean buttonPressed(Button button)
	{
		return buttonDown(button) && !lastMouseState.getButton(button);
	}
	
	public static boolean buttonReleased(Button button)
	{
		return buttonUp(button) && lastMouseState.getButton(button);
	}
	
	public static Vector2i getMousePosition()
	{
		return new Vector2i(currentMouseState.position.x, currentMouseState.position.y);
	}
	
	public static Vector2i getMouseDelta()
	{
		return new Vector2i(currentMouseState.position.x - lastMouseState.position.x, currentMouseState.position.y - lastMouseState.position.y);
	}
	
	public static int getMouseWheel()
	{
		return currentMouseState.wheelPos;
	}
	
	public static int getMouseWheelDelta()
	{
		return currentMouseState.wheelPos - lastMouseState.wheelPos;
	}
}