package com.example.musicstrike;

import java.util.Random;

public class Behavior {

	public int behaviorType;
	public int backgroundImage;
	public int finalBackground;
	public int objectInitialSprite;
	public int objectFinalSprite;
	public int initialSound;
	public int finalSound;
	
	public boolean haveFinalBackground = false;
	public boolean haveObject = false;
	//Numero de acciones maximas
	private int randomLimit = 2;
	
	
	public Behavior(){
		
		int type = generateRandom();
		behaviorType = findBehaviorType(type);
		backgroundImage = findBackgroundImage(type);
		objectInitialSprite = findObjectInitialSprite(type);
		objectFinalSprite = findObjectFinalSprite(type);
		initialSound = findObjectInitialSound(type);
		finalSound = findObjectFinalSound(type);
		finalBackground = findFinalBackground(type);
		
		/*
		 * Behavior Definitions
		 * 0 -> Spider
		 * 1 -> Overtake Left
		*/
		
	}
	
	public int findBehaviorType(int t){
		
		/*
		 * Retornar:
		 * 0 = TAP
		 * 1 = SCROLL
		 * 2 = SHAKE
		 * 3 = LEFT
		 * 4 = RIGHT
		 */
		
		
		if(t == 0)
			return 0;
		else if (t == 1)
			return 3;
		else 
			return 0;
		
	}
	
	public int findBackgroundImage(int t){
		
		if(t == 0)			
			return R.drawable.spiderbackground;
			
		else if (t == 1)
			return R.drawable.overtakeleft;
		
		else
			return 0;
		
	}
	
	public int findObjectInitialSprite(int t){
				
		if(t == 0)
		{			
			haveObject = true;
			return R.drawable.spider;			
		}
		else if(t ==1)
		{
			haveObject = false;
			return 0;
		}
					
		else
			return 0;
		}
	
	public int findObjectFinalSprite(int t){
		
		if(t == 0)		
			return R.drawable.spot;
		else if(t ==1)
			return 0;
					
		else
			return 0;
		}
	
	public int findObjectInitialSound(int t){
		
		if(t == 0)
			return R.drawable.front2;

		else if (t == 1)
			return R.raw.compastempo120;
					
		else
			return 0;
		}
	
	public int findObjectFinalSound(int t){
		
		if(t == 0)		
			return R.drawable.front2;

		else if(t == 1)
			return R.raw.compastempo120;
					
		else
			return 0;
		}
	
	public int findFinalBackground(int t){
		if(t == 0)
		{
			haveFinalBackground = false;
			return 0;
		}
		else if(t == 1)
		{
			haveFinalBackground = true;
			return R.drawable.overtakeleftwin;
		}
		else
			return 0;
	}
	
	public int generateRandom(){
		Random r = new Random();
		int rnd = r.nextInt(randomLimit);
		return rnd;
	}
}
