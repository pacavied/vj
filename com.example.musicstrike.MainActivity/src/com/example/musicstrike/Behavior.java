package com.example.musicstrike;

public class Behavior {

	public int behaviorType;
	public int backgroundImage;
	public int objectInitialSprite;
	public int objectFinalSprite;
	public int initialSound;
	public int finalSound;
	public boolean haveObject = false;
	
	
	public Behavior(int type){
		
		behaviorType = findBehaviorType(type);
		backgroundImage = findBackgroundImage(type);
		objectInitialSprite = findObjectInitialSprite(type);
		objectFinalSprite = findObjectFinalSprite(type);
		initialSound = findObjectInitialSound(type);
		finalSound = findObjectFinalSound(type);
		
	}
	
	public int findBehaviorType(int t){
		
		/*
		 * Retornar:
		 * 0 = TAP
		 * 1 = SCROLL
		 * 2 = SHAKE
		 * 3 = RIGHT
		 * 4 = LEFT
		 */
		
		
		if(t == 0)
			return 0;
		else 
			return 0;
		
	}
	
	public int findBackgroundImage(int t){
		
		if(t == 0){
			
			return R.drawable.spiderbackground;
			
		}
		
		else
			return 0;
		
	}
	
	public int findObjectInitialSprite(int t){
				
		if(t == 0){
			
			haveObject = true;
			return R.drawable.spider;
			
		}
					
		else
			return 0;
		}
	
	public int findObjectFinalSprite(int t){
		
		if(t == 0){
			
			return R.drawable.spot;
			
		}
					
		else
			return 0;
		}
	
	public int findObjectInitialSound(int t){
		
		if(t == 0){
			
			return R.drawable.front2;
			
		}
					
		else
			return 0;
		}
	
	public int findObjectFinalSound(int t){
		
		if(t == 0){
			
			return R.drawable.front2;
			
		}
					
		else
			return 0;
		}
}
