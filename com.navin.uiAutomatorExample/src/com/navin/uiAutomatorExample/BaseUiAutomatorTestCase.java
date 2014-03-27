package com.navin.uiAutomatorExample;

import java.io.File;
import java.util.Iterator;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BaseUiAutomatorTestCase extends UiAutomatorTestCase{
	
	protected String currentTestCase;
	
	protected Boolean testFailed = true;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		trace("In setup, Launching the application");
		/*
		 * Over here, I am trying to launch the application
		 * Now there are two ways to do that
		 * 1. Getting runtime environment and starting the application using "am" and call the invoking path  for the 
		 * 		activity from the AndroidManifest.xml
		 * 		Runtime.getRuntime().exec("am start -n com.package.name/com.package.name.ActivityName")
		 * 		Now, since I do not have the activity name to launch the application, I cannot use the "am" command to 
		 * 		launch the application (Unless i reverse engineer the apk to get the AndroidManifest.xml to get the activity name
		 * 
		 * 2. Conventional way, Launch the application via the apps page from the device, which I am going to use
		 */
		
		launchApp("Twitter");
		sleep(3000);
		testFailed = true;
	}
	
	protected void checkForFailures() {
		if(testFailed) takeScreenShot(currentTestCase);
			
	}
	
	protected Boolean takeScreenShot(String fileName) {
		// TODO Auto-generated method stub
		trace("Taking Screentshot");
		File screenShot = new File("/data/" + fileName + ".png");
		return getUiDevice().takeScreenshot(screenShot);
	}

	protected void logTestCase(String testCase) {
		//This trace helps in debugging
		currentTestCase = testCase;
		System.out.println("***********************************************************");
		System.out.println("TestCase Start: " + testCase);
		System.out.println("***********************************************************");
	}
	
	@Override
	protected void tearDown() throws Exception {
		checkForFailures();
		super.tearDown();
		trace("In tear down, closing the application");
		/*
		 * Over here, I am trying to stop the application
		 * Now there are two ways to do that
		 * 1. Getting runtime environment and force stopping the application using "am" and call the invoking path  for the 
		 * 		activity from the AndroidManifest.xml
		 * 		Runtime.getRuntime().exec("am force-stop -n com.package.name/com.package.name.ActivityName")
		 * 
		 * 2. Conventional way, stop the application via the settings page from the device, which I am going to use
		 */
		getUiDevice().pressHome();
		launchApp("Settings");
		clickUiObject(Boolean.TRUE, "android:id/title", "Apps");
		clickUiObject(Boolean.TRUE, "com.android.settings:id/app_name", "Twitter");
		clickUiObject(Boolean.TRUE,  "Force stop");
		clickUiObject(Boolean.FALSE, "OK");
		getUiDevice().pressHome();
		
	}
	
	protected void trace(String message){
		
		System.out.println("### " + message + " ###");
	}
	
	protected void clickUiObject(Boolean waitForNewWindow, String resourceId, String application) throws UiObjectNotFoundException{
		UiScrollable list = new UiScrollable(new UiSelector().className(ListView.class.getName())
        .scrollable(true));
		UiObject target = list.getChildByText(new UiSelector().resourceId(resourceId), application);
		if(waitForNewWindow)target.clickAndWaitForNewWindow();
		else target.click();
	}
	
	protected void clickUiObject(Boolean waitForNewWindow, String text) throws UiObjectNotFoundException{
		
		UiObject target = new UiObject(new UiSelector().text(text));
		if(waitForNewWindow)target.clickAndWaitForNewWindow();
		else target.click();
	}
	/**
	 * This method takes in the name of the application and launches it.
	 * @param application the name of the application to be launched.
	 * @throws UiObjectNotFoundException If the application is not available in the apps tab.
	 */
	protected void launchApp(String application) throws UiObjectNotFoundException{
		
		  //Simulate a short press on the HOME button.
	      getUiDevice().pressHome();
	      
	      //Now we get the allApps button to launch the allapps screen
	      UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
	      
	      allAppsButton.clickAndWaitForNewWindow();
	      
	      //this is to make sure we are in the apps page, So even if the page was in widgets tab, it will come to the apps tab
	      UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
	      appsTab.click();
	      
	      // Next, in the apps tabs, we can simulate a user swiping until
	      // they come to the Settings app icon.  Since the container view 
	      // is scrollable, we can use a UiScrollable object.
	      UiScrollable appViews = new UiScrollable(new UiSelector()
	         .scrollable(true));
	      
	      // Set the swiping mode to horizontal (the default is vertical)
	      appViews.setAsHorizontalList();
	      
	      //Getting hold of our app to be opened
	      UiObject appToBeLaunched = appViews.getChildByText(new UiSelector()
	         .className(android.widget.TextView.class.getName()), 
	         application);
	      
	      appToBeLaunched.clickAndWaitForNewWindow();
		
	}
	
	protected void assertViewTrue(String... descriptions) throws UiObjectNotFoundException{
		
		for(String description: descriptions){
			trace("Check if View with description: " + description + " is available?");
			UiObject view = new UiObject(new UiSelector().className(View.class.getName()).description(description));
			assertTrue("View with description: " + description + " is not available", view.exists());
		}
	}
	
	protected void assertTextViewTrue(String... texts) throws UiObjectNotFoundException{
		
		for(String text: texts){
			trace("Check if TextView with text: " + text + " is available?");
			UiObject textView = new UiObject(new UiSelector().className(TextView.class.getName()).text(text));
			assertTrue("TextView with text: " + text + " is not available", textView.exists());
		}
	}
	
	protected void assertImageButtonTrue(String... descriptions) throws UiObjectNotFoundException{
		
		for(String description: descriptions){
			trace("Check if ImageButton with description: " + description + " is available?");
			UiObject view = new UiObject(new UiSelector().className(ImageButton.class.getName()).description(description));
			assertTrue("ImageButton with description: " + description + " is not available", view.exists());
		}
	}

}
