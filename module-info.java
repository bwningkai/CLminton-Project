module BADprojectfinal {
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.sql;
	requires jfxtras.labs;
	opens main;
	opens Model to javafx.base;
}