package model;

/**
 * Give the rate at which a Pokemon gain levels depending on its experience.<br>
 * Each Curvetype corresponds to a specific function.<br>
 */
public enum CurveType {
	Slow,	// 1.25 * level^3
	Average,	// level^3
	Parabolic,	//1.2 * level^3 - 15 * level^2 + 100 * level - 40
	Fast	//0.8 * level^3
}
