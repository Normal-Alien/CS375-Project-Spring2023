# CS375-Project-Spring2023
Requirements:
	Java:
		javafx modules
		PATH_TO_FX environment variable initialized:
			Linux: export PATH_TO_FX=/your/pathto/javafx/lib
	In order to run using java:
		- Be sure that everything is in the proper folder
			- controllers and main java file
			- if packages are used:
				- mimic package directories
		- Linux Compile with:
			javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Main.java
		- Run with:
			java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Main
