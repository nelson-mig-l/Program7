/*
Replace "<Program Name>" with the name of your program.
Replace "<Window Name>" with the name of the window.
*/

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Font;

public class EngineFrame extends JFrame
{
	//double buffering
	private Image dbImage;
	private Graphics dbg;

	public EngineFrame()
	{
		super("lmao");

		setSize(1000, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paint(Graphics window)
	{
		//don't edit this
		dbImage = createImage( getWidth(), getHeight() );
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		window.drawImage(dbImage, 0, 0, this);
	}

	public void paintComponent(Graphics window)
	{
		//actually painting goes here
	}

}
