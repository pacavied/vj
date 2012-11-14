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
	private int randomLimit = 5;
	
	
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
		 * Behavior Definitions		Theme		Action
		 * 
		 * 0 -> Spider 				Normal		TAP
		 * 1 -> Overtake Left		Normal		LEFT
		 * 2 -> Ring the Bell		Normal		SHAKE
		 * 3 -> Move the Box		Normal		SCROLL
		 * 4 -> Thrown In			Football	SHAKE
		 * 
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
		else if (t == 2)
			return 2;
		else if (t == 3)
			return 1;
		else if (t == 4)
			return 2;
		else 
			return 0;
		
	}
	
	public int findBackgroundImage(int t){
		
		if(t == 0)			
			return R.drawable.spiderbackground;
		else if (t == 1)
			return R.drawable.overtakeleft;
		else if (t == 2)
			return R.drawable.bell;
		else if(t == 3)
			return R.drawable.scrollbox;
		else if (t == 4)
			return R.drawable.backgroundfootballthrowin;
		
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
		else if(t == 2)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 3)
		{
			haveObject = false;
			return 0;		
		}
		else if(t == 4)
		{
			haveObject = true;
			return R.drawable.throwininitial;
		}
		
		else
			return 0;
		}
	
	public int findObjectFinalSprite(int t){
		
		if(t == 0)		
			return R.drawable.spot;
		else if(t ==1)
			return 0;
		else if (t == 2)
			return 0;
		else if (t == 3)
			return 0;
		else if(t == 4)
			return R.drawable.throwinfinal;
					
		else
			return 0;
		}
	
	public int findObjectInitialSound(int t){
		
		if(t == 0)
			return R.drawable.front2;

		else if (t == 1)
			return R.raw.compastempo120;
		else if (t == 2)
			return R.raw.compastempo120;
		else if (t == 3)
			return R.raw.compastempo120;
		else if (t == 4)
			return R.raw.compastempo120;
					
		else
			return 0;
		}
	
	public int findObjectFinalSound(int t){
		
		if(t == 0)		
			return R.raw.winsound;
		else if(t == 1)
			return R.raw.winsound;
		else if(t == 2)
			return R.raw.bellsound;
		else if (t == 3)
			return R.raw.winsound;
		else if (t == 4)
			return R.raw.winsound;
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
		else if(t == 2)
		{
			haveFinalBackground = true;
			return R.drawable.bellwin2;
		}
		else if(t == 3)
		{
			haveFinalBackground = true;
			return R.drawable.scrollboxwin2;
		}
		else if(t == 4)
		{
			haveFinalBackground = false;
			return 0;
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
