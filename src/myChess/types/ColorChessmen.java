package myChess.types;

import java.io.Serializable;

public enum ColorChessmen implements Serializable {
	black,
	white;

	public static ColorChessmen parseColorChessmen(String string) {
		ColorChessmen colorChessmen = null;
		switch (string) {
		case "black":
			colorChessmen = ColorChessmen.black;
			break;
		case "white":
			colorChessmen = ColorChessmen.white;
			break;
		}
		return colorChessmen;
	}

	public static ColorChessmen switchColor(ColorChessmen color) {
		ColorChessmen colorChessmen = null;
		switch (color) {
		case black:
			colorChessmen = ColorChessmen.white;
			break;
		case white:
			colorChessmen = ColorChessmen.black;
			break;
		}
		return colorChessmen;
	}
}
