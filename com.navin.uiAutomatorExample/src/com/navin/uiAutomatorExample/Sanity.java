package com.navin.uiAutomatorExample;

import com.android.uiautomator.core.UiObjectNotFoundException;


public class Sanity extends BaseUiAutomatorTestCase {
	
	public void testHomeScreen() throws UiObjectNotFoundException{
		logTestCase("Check_Home_screen_buttons");
		
		assertViewTrue("Home", "Notifications", "Messages", "Find people", "Search",
				"Selected. Home Tab", "Discover Tab", "Activity Tab");
		assertTextViewTrue("What's happening?");
		assertImageButtonTrue("Photo", "Camera");
		testFailed = false;
	}

}
