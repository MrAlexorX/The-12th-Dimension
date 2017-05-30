package com.bdinc.t12d.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.ImageIcon;

import com.bdinc.t12d.main.Game;
import com.bdinc.t12d.main.IReferences;
import com.bdinc.t12d.main.LevelManager;
import com.bdinc.t12d.maths.IntVector2;
import com.bdinc.t12d.maths.Map;
import com.bdinc.t12d.maths.Physics;
import com.bdinc.t12d.objects.Block;
import com.bdinc.t12d.objects.Entity;
import com.bdinc.t12d.objects.Flame;
import com.bdinc.t12d.objects.Level;

public class DisplayManager implements IReferences {
	
	private Canvas game = Game.canvas;
	Level lvl1 = new Level();
	private boolean collisionBottom;
	private Entity player;
	private IntVector2 plCell, flameCell;
	private Map map = new Map();
	
	public void init()
	{
		map.init();
	}
	
	public void update(long delta)
	{
		if(LevelManager.levelNumber > 0) {
			player = Game.player;
			collisionBottom = Physics.collidesBottom(player.posX(), player.posY());
			plCell = Game.player.getCell();
			
			if(!collisionBottom && !player.jump) {
				player.incY(0.5f);
				player.setCell(map.checkCell(player.posX(), player.posY()));
			}
			if(Game.player.jump) {
				player.jump();
			}
			if(Game.player.left) {
				player.moveLeft();
			}
			if(Game.player.right) {
				player.moveRight();
			}
			for(Flame f : LevelManager.currentLevel.flames) {
				flameCell = f.getCell();
				if(plCell.x == flameCell.x) {
					if(plCell.y == flameCell.y) {
						f.activate();
						//System.err.println("PlayerCell:["+plCell.x+","+plCell.y+"];");
					}
				}
				System.err.println("PlayerCell:["+plCell.x+","+plCell.y+"];");
			}
//			if(Physics.collidesEntity(player.posX(), player.posY())) {
//				player.decreaseHealth(10);
//			}
		}
		
		
	}
	
	public void initRender(Graphics g)
	{
		BufferStrategy bs = game.getBufferStrategy();
		if(bs == null)
		{
			game.createBufferStrategy(2);
			game.requestFocus();
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, game.getWidth(), game.getHeight());
	}
	
	public void dispose(BufferStrategy bs, Graphics g)
	{
		g.dispose();
		bs.show();
	}
	
	public void render(Graphics g)
	{
		BufferStrategy bs = new Game().getBufferStrategy();
		if(bs == null)
		{
			new Game().createBufferStrategy(2);
			new Game().requestFocus();
			return;
		}
		g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, game.getWidth(), game.getHeight());
//		try
//		{
//			for(Block b : LevelManager.currentLevel.blocks) {
//				if(g == null)
//				{
//					System.err.println("Graphics lost!");
//				}
//				if(b.getSprite() == null)
//				{
//					System.err.println("No sprite in block<"+b.toString()+">!");
//				}
//				g.drawImage(new ImageIcon("assets/sprites/blocks/brick6.png").getImage(), 0, 0, null);
//				//b.draw(g);
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		dispose(bs, g);
	}
	
}
