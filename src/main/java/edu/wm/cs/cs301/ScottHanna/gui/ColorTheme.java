package edu.wm.cs.cs301.ScottHanna.gui;


import java.util.logging.Level;
import java.util.logging.Logger;
import android.graphics.Color;
import android.util.Log;

/**
 * This class encapsulates the selection of colors for the game.
 * The purpose is to support a specific theme such that colors are used consistently across the user interface.
 * The class internally works with a Singleton pattern. 
 * 
 * Client classes: Wall, CompassRose, FirstPersonView, Map, SimpleScreens
 * 
 * @author Peter Kemper
 *
 */
public class ColorTheme {
	/*
	 * Several themes are supported with the help of an internal Singleton
	 * that varies based on the instantiation of a different internal class.
	 */
	
	
	// General color settings across multiple screens
	private static final Color greenWM = Color.valueOf(Color.parseColor("#115740"));
	private static final Color goldWM = Color.valueOf(Color.parseColor("#916f41"));
	private static final Color blackWM = Color.valueOf(Color.parseColor("#222222"));
	private static final Color yellowWM = Color.valueOf(Color.parseColor("#FFFF99"));

	//specifically for CompassRose.java, corresponding enum values have prefix COMPASSROSE_
	// fixed configuration for arms
    private static final Color MAIN_COLOR = greenWM; //new Color(0.4f, 0.4f, 1.0f);
    // fixed configuration for circle surrounding arms
    private static final Color CIRCLE_HIGHLIGHT = Color.valueOf(1.0f, 1.0f, 1.0f, 0.8f);
    //Color.decode("#115740").darker();// = new Color(1.0f, 1.0f, 1.0f, 0.8f); 
    private static final Color CIRCLE_SHADE = Color.valueOf(1.0f, 1.0f, 1.0f, 0.3f);
    //Color.decode("#115740").brighter(); //new Color(0.0f, 0.0f, 0.0f, 0.2f); 
    // fixed configuration for letters used to indicate direction
    private static final Color MARKER_COLOR = Color.valueOf(Color.BLACK);
    
    // Logger to track execution
    private static final Logger LOGGER = Logger.getLogger(ColorTheme.class.getName());

    /** 
     * MazeColors enumerates color choices for specific purposes.
     * The prefix indicates which class or feature uses it. 
     */
	public enum MazeColors {BACKGROUND_TOP, BACKGROUND_BOTTOM, 
		MAP_DEFAULT, MAP_WALL_DEFAULT, MAP_WALL_SEENBEFORE, MAP_CURRENTLOCATION, MAP_SOLUTION, 
		COMPASSROSE_MAIN_COLOR, COMPASSROSE_CIRCLE_HIGHLIGHT, COMPASSROSE_CIRCLE_SHADE, 
		COMPASSROSE_MARKER_COLOR_DEFAULT, COMPASSROSE_MARKER_COLOR_CURRENTDIRECTION, COMPASSROSE_BACKGROUND,
		TITLE_LARGE, TITLE_SMALL, TITLE_DEFAULT, FRAME_OUTSIDE, FRAME_MIDDLE, FRAME_INSIDE,
		FIRSTPERSON_DEFAULT} ;
	
		// address needs from Map.java
		// requires predefined color for white (seen), grey for other walls, red for currentlocation, yellow for solution
		
	public static Color getColor(MazeColors color) {
		switch (color) {
		// unused color settings for FirstPersonView, background
		// original color choices for background black on top, dark gray at bottom
		case BACKGROUND_TOP: 
			return Color.valueOf(Color.BLACK); // unused, just for completeness
		case BACKGROUND_BOTTOM:
			return Color.valueOf(Color.DKGRAY); // unused, just for completeness
		// color settings for Map
		case MAP_DEFAULT:
			return Color.valueOf(Color.WHITE);
		case MAP_WALL_DEFAULT:
			return Color.valueOf(Color.GRAY);
		case MAP_WALL_SEENBEFORE:
			return Color.valueOf(Color.WHITE);
		case MAP_CURRENTLOCATION:
			return Color.valueOf(Color.RED);
		case MAP_SOLUTION:
			return Color.valueOf(Color.YELLOW);
		// color settings for CompassRose
		case COMPASSROSE_MAIN_COLOR:
			return MAIN_COLOR;
		case COMPASSROSE_CIRCLE_HIGHLIGHT:
			return CIRCLE_HIGHLIGHT;
		case COMPASSROSE_CIRCLE_SHADE:
			return CIRCLE_SHADE;
		case COMPASSROSE_MARKER_COLOR_DEFAULT:
			return goldWM;
		case COMPASSROSE_MARKER_COLOR_CURRENTDIRECTION:
			return MARKER_COLOR;
		case COMPASSROSE_BACKGROUND:
			return Color.valueOf(Color.WHITE);
		// color settings for SimpleScreens
		case TITLE_DEFAULT:
			return blackWM;
		case TITLE_LARGE:
			return goldWM;
		case TITLE_SMALL:
			return greenWM;
		case FRAME_OUTSIDE:
			return greenWM;
		case FRAME_MIDDLE:
			return goldWM;
		case FRAME_INSIDE:
			return Color.valueOf(Color.WHITE);
		// color settings for FirstPersonView
		case FIRSTPERSON_DEFAULT:
			return Color.valueOf(Color.WHITE);
		default:
			break;
		}
		return Color.valueOf(Color.WHITE); // this is a mistake if you get here!!!
	}
	
	/**
	 * Creates an opaque sRGB color with the specified combined RGB value
	 * consisting of the red component in bits 16-23,
	 * the green component in bits 8-15,
	 * and the blue component in bits 0-7. 
	 * The actual color used in rendering depends on finding the best match
	 * given the color space available for a particular output device.
	 * Alpha is defaulted to 255.
	 * @param rgb the rgb value
	 * @return the matching instance of a color
	 */
	public static Color getColor(int rgb) {
		return Color.valueOf(rgb);
	}
	/**
	 * Class encapsulates a color setting for the background and walls.
	 * The background is black on top, darkgray on the bottom. 
	 * All walls are lightgrey.
	 * 
	 * @author Peter Kemper
	 *
	 */
	private static class ColorSettings {
		Color getColor(MazeColors color, float percentToExit) {
			Color result = (MazeColors.BACKGROUND_TOP == color)? Color.valueOf(Color.BLACK) : Color.valueOf(Color.DKGRAY);;
			Log.v("Color settings", "given:" + color + ", returns color: " + result);
			return result;
		}
		Color getWallColor(final int distance, final int cc, final int extensionX) {
			Log.v("Color settings", "regardless of input, returns color: " + Color.valueOf(Color.LTGRAY));
			return Color.valueOf(Color.LTGRAY);
	    }
		//////// shared code for subclasses, not used in this class ////////
	    /**
	     * Computes an RGB value based on the given numerical value.
	     *
	     * @param distance
	     *            value to select color
	     * @return the calculated RGB value
	     */
	    static int calculateRGBValue(final int distance, final int extensionX) {
	        // compute rgb value, depends on distance and x direction
	        // 7 in binary is 0...0111
	        // use AND to get last 3 digits of distance
	        final int part1 = distance & 7;
	        final int add = (extensionX != 0) ? 1 : 0;
	        return ((part1 + 2 + add) * 70) / 8 + 80;
	    }
	    /**
	     * Default minimum value for RGB values.
	     */
	    static final int RGB_DEF = 20;
	   
	}
	/**
	 * Class encapsulates a color setting for the background and walls.
	 * The background is black on top, darkgray on the bottom. 
	 * A wall has a color that is selected from 6 broad categories
	 * and some variation within that depends on the distance to the exit.
	 * The effect is that there are areas within the maze where walls
	 * have the same coloring inside an area and different colorings
	 * across areas. The color itself provides no guidance to the 
	 * user about the distance to the exit. 
	 * This setting follows the original design of the game.
	 * 
	 * @author Peter Kemper
	 *
	 */
	private static class ColorSettingsBasic extends ColorSettings {
	     /**
	     * Determine and set the color for this segment.
	     *
	     * @param distance
	     *            to exit
	     * @param cc
	     *            obscure
	     */
	    @Override
	    Color getWallColor(final int distance, final int cc, final int extensionX) {
	    	Color result; 
	    	final int d = distance / 4;
	        // mod used to limit the number of colors to 6
	        final int rgbValue = calculateRGBValue(d, extensionX);
	        switch (((d >> 3) ^ cc) % 6) {
	        case 0:
	            result = Color.valueOf((float) rgbValue,(float) RGB_DEF,(float) RGB_DEF);

	            break;
	        case 1:
	        	result = Color.valueOf((float) RGB_DEF,(float) rgbValue,(float) RGB_DEF);

	        	break;
	        case 2:
	        	result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF,(float) rgbValue);
	        	break;
	        case 3:
	        	result = Color.valueOf((float) rgbValue,(float) rgbValue,(float) RGB_DEF);
	        	break;
	        case 4:
	        	result = Color.valueOf((float) RGB_DEF,(float) rgbValue,(float) rgbValue);
	        	break;
	        case 5:
	        	result = Color.valueOf((float) rgbValue,(float) RGB_DEF,(float) rgbValue);
	        	break;
	        default:
				result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF,(float) RGB_DEF);
	        	break;
	        }
	        Log.v("Wall color", "given distance:" + distance + ", returns color: " + result);
	        return result;
	    }
	}
	/**
	 * Class encapsulates a color setting for the background and walls.
	 * The background colors for the top and bottom rectangle are a 
	 * blend of two colors and the ratios vary with the distance to the
	 * exit. The top rectangle is a mix of yellow and gold, the bottom
	 * is a mix of light gray and green. The blend starts with yellow and
	 * light gray and then turns into gold and green towards the exit.
	 * The background coloring provides guidances to the user.
	 *  
	 * A wall has a color that is selected from 6 broad categories
	 * and some variation within that depends on the distance to the exit.
	 * The effect is that there are areas within the maze where walls
	 * have the same coloring inside an area and different colorings
	 * across areas. The color itself provides no guidance to the 
	 * user about the distance to the exit. 
	 * This setting derives from basic setting but carries a 
	 * different setting for the green part in the RGB value
	 * for some categories.
	 * 
	 * @author Peter Kemper
	 *
	 */
	private static class ColorSettingsAdvanced extends ColorSettings {
		/**
		 * Determine the background color for the top and bottom
		 * rectangle as a blend between starting color settings
		 * of yellowWM and lightGray towards goldWM and greenWM as final
		 * color settings close to the exit
		 * @param percentToExit describes how far it is to the exit as a percentage value
		 * @param color is true for the top rectangle, false for the bottom
		 * @return the color to use for the background rectangle
		 */
		@Override
		Color getColor(MazeColors color, float percentToExit) {
			Color result = (MazeColors.BACKGROUND_TOP == color)? 
					blend(yellowWM, goldWM, percentToExit) : 
						blend(Color.valueOf(Color.LTGRAY), greenWM, percentToExit);
			Log.v("Color of background", "given:" + color + ", returns color: " + result);
	        return result;
		}
		/**
		 * Calculates the weighted average of the two given colors.
		 * The weight for the first color is expected to be between
		 * 0 and 1. The weight for the other color is then 1-weight0.
		 * The result is the weighted average of the red, green, and
		 * blue components of the colors. The resulting alpha value
		 * for transparency is the max of the alpha values of both colors.
		 * @param fstColor is the first color
		 * @param sndColor is the second color
		 * @param weightFstColor is the weight of fstColor, {@code 0.0 <= weightFstColor <= 1.0}
		 * @return blend of both colors as weighted average of their rgb values
		 */
		private Color blend(Color fstColor, Color sndColor, double weightFstColor) {
			if (weightFstColor < 0.1)
				return sndColor;
			if (weightFstColor > 0.95)
				return fstColor;
		    double r = weightFstColor * fstColor.red() + (1-weightFstColor) * sndColor.red();
		    double g = weightFstColor * fstColor.green() + (1-weightFstColor) * sndColor.green();
		    double b = weightFstColor * fstColor.blue() + (1-weightFstColor) * sndColor.blue();
		    double a = Math.max(fstColor.alpha(), sndColor.alpha());

		    return Color.valueOf((float) r, (float) g, (float) b, (float) a);
		  }

		/**
	     * Default minimum value for RGB values.
	     */
	    private static final int RGB_DEF_GREEN = 10;
	    /**
	     * Determine the color for this wall.
	     *
	     * @param distance
	     *            to exit
	     * @param cc
	     *            obscure
	     */
	    @Override
	    Color getWallColor(final int distance, final int cc, final int extensionX) {
	    	Color result;
	    	final int d = distance / 4;
	        // mod used to limit the number of colors to 6
	        final int rgbValue = calculateRGBValue(d, extensionX);
	        //System.out.println("Initcolor rgb: " + rgbValue);
	        switch (((d >> 3) ^ cc) % 6) {
	        case 0:
	            result = Color.valueOf((float) rgbValue,(float) RGB_DEF,(float) RGB_DEF);
	            break;
	        case 1:
	        	result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF_GREEN,(float) RGB_DEF);

	        	break;
	        case 2:
	        	result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF,(float) rgbValue);
	        	break;
	        case 3:
	        	result =Color.valueOf((float) rgbValue,(float) RGB_DEF_GREEN,(float) RGB_DEF);
	        	break;
	        case 4:
	        	result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF_GREEN,(float) rgbValue);
	        	break;
	        case 5:
	        	result = Color.valueOf((float) rgbValue,(float) RGB_DEF,(float) rgbValue);
	        	break;
	        default:
	        	result = Color.valueOf((float) RGB_DEF,(float) RGB_DEF,(float) RGB_DEF);
	        	break;
	        }
	        Log.v("Distance color", "given distance:" + distance + ", returns color: " + result);
	        return result;
	    }
	}
	
	/////// set up for Singleton pattern //
	private static ColorSettings instance;
	private static ColorThemeSelection theme = ColorThemeSelection.ADVANCED;
	private static ColorSettings getColorSettings() {
		if (instance == null) {
			Log.v("Color Theme", "Using Color Theme: " + theme);
			switch (theme) {
			case BASIC:
				instance = new ColorSettingsBasic();
				break;
			case ADVANCED:
				instance = new ColorSettingsAdvanced();
				break;
			case DEFAULT:
			default:
				instance = new ColorSettings();
				break;
			}
		}
		return instance;
	}
	
	public enum ColorThemeSelection {DEFAULT, BASIC, ADVANCED};
	
	public static void setColorTheme(ColorThemeSelection selection) {
		theme = selection;
	}
	
	
	// address needs from FirstPersonView.java
	/**
	 * Determine the background color for the top and bottom
	 * rectangle. The resulting color choices depend on the 
	 * configuration of the ColorTheme either being basic or advanced.
	 * In the basic settings the top is black, the bottom is dark gray
	 * regardless of the percentToExit. 
	 * In the advanced setting the color is a blend between the 
	 * starting color settings of yellowWM and lightGray 
	 * towards goldWM and greenWM as final
	 * color settings close to the exit
	 * @param percentToExit describes how far it is to the exit as a percentage value
	 * @param color is BACKGROUND_TOP for the upper rectangle or BACKGROUND_BOTTTOM
	 * for the lower one
	 * @return the color to use for the background rectangle
	 */
	public static Color getColor(MazeColors color, float percentToExit) {
		return getColorSettings().getColor(color, percentToExit);
	}

	/**
     * Determines the color for a wall.
     * Supports color determination for the Wall constructor method.
     * See also https://www.geeksforgeeks.org/static-method-in-interface-in-java/ 
     * @param distance is the distance to the exit
     * @param cc is an obscure parameter used in Wall for color determination, just passed in here
     * @param extensionX is the wall's length and direction (sign), horizontal dimension
     * @return the rgb value for the color of the wall
     */
    public static int getWallColor(int distance, int cc, int extensionX) {
    	return getColorSettings().getWallColor(distance,cc,extensionX).toArgb();
    };
    
 

}
