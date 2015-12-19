# Profiler_project
##About

Profiler allows a user to customize user's phone’s settings that will be activated automatically based on the battery charging left, time of the day and the user's current location.

##Wiki

The Journal is in the Wiki section of this repository.

# Pre-Requisites:

Before you launch the app, there are a few things you have to do to ensure everything runs smoothly:

1.	Log in to google developers console and make a new project , link : https://console.developers.google.com
2.	Enable the following APIs with package name: com.example.ishwari.profiler

                                           ♣	Google Maps Android API
                                           ♣	Google + API
3.	Authenticate the APIs and place the keys in the strings.xml.

##Walkthrough of the app
* The home screen with a FAB and list of profiles that have been/will be created.
* ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912047/64ffcd9c-a5e0-11e5-8637-bbc4fd77377d.png)
* On clicking the FAB three mini FABs with the three triggers- Time of Day, Location and Battery Remaining respectively open up
* ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912044/64ff75d6-a5e0-11e5-9379-2bbd783d088f.png)
* Selecting the Time Trigger will take you to the Time picker activity where the user can select what time of the day the settings should be activated
   - Select the time from the time picker and click the 'Set' button
   - Click on the 'Set Night Mode' button to activate settings
     * Turn Wifi Off
     * Turn Ringer to Silent Mode
     * Turn Bluetooth on
     * Set screen brightness to 50/255
  -![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912045/64ff7662-a5e0-11e5-94cd-d5cfee5e95b5.png)
* Select Location by clicking on the second Trigger from the Main Activity to get to the Select Place Activity
  - Select Home to open map and select Home location
  - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912043/64fef30e-a5e0-11e5-9ebd-35726b6a356d.png)
  
  - Click on select location button to get the location from map
  - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912046/64ffe868-a5e0-11e5-9516-b6ba71877160.png)
  - Click on Save Location button to save location and create geofence at that location of radius 500 metres
  - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912042/64fcc23c-a5e0-11e5-8504-75a887fb18fc.png)
  - Use any mock location creator app to fake locations to check the entry and exit to and from geofences
  - We have used https://play.google.com/store/apps/details?id=com.evezzon.fakegps&hl=en app to fake locations
  - On entering the location/geofence the following settings get activated
    - Set Brightness to 100/255
    - Set Phone Ringer to Vibrate mode
    - Set Screen Time out ot 25 seconds
    - Turn Wifi on
    - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912049/65128234-a5e0-11e5-8c60-b58f2bb6d11f.png)
  - On exiting the location/geofence the following settings get activated
    - Set Brightness to 200/255
    - Set Phone Ringer to Normal mode
    - Set Screen Time out ot 15 seconds
    - Turn Wifi Off
    - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912050/65134674-a5e0-11e5-8daa-c0fb2bb1bc91.png)
 
     
* Select the battery percentage at which the triggers should activate low power mode
  - Sets the brightness to 30/255
  - Turn Wifi off
  - Set ringer mode to silent
  - Set screen timeout to 15 seconds
  - ![alt tag](https://cloud.githubusercontent.com/assets/10985717/11912051/65146c2a-a5e0-11e5-8762-663ef54b85a0.png)
  



##Collaborators

* Siddhartha Sankasala
* Ishwari Shevade
