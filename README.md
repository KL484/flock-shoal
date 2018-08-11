# flock-shoal
This is a simple fish/flock/swarm simulation in Java using LibGDX.  

Individuals in a flock make movement decisions based on three rules:
- Don't overcrowd neighbors
- Align to the nearby average direction
- Head towards the nearby average position

With these three rules a convincing flock simulation can be made. 

### Building
Use the included build.gradle

The flock logic can be found in core/src/, however to run on desktop you should use ```desktop/src/com.fonduegames.flockshoal.desktop/DesktopLauncher``` as the main class.  
