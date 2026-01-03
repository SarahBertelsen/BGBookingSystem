package gui;

public interface FrameIF {
	
	/**
	 * Called when a frame should open.
	 * Should make the frame visible.
	 * 
	 */
	public void enter();
	
	/**
	 * Called when a frame should minimize.
	 * Should make the frame invisible.
	 * 
	 */
	public void exit();
	
	/**
	 * Called when a frame should close.
	 * Should dispose of itself.
	 * 
	 */
	public void close();
	
}
