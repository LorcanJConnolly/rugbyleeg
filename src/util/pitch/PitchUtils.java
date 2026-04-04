package util.pitch;

import components.pitch.PitchDimensions;
import util.vectors.Vector2;

/**
* A class consisting of static utility methods for preforming pitch related calculations..
*/
public class PitchUtils {


    public static double relativeToPitchX(PitchDimensions dimensions, double x_factor){
        return dimensions.leftTouch + dimensions.aabb.width*x_factor;
    }


    public static double relativeToPlayingFieldY(PitchDimensions dimensions, double y_factor){
        return dimensions.topTryLine + dimensions.aabb.height*y_factor;
    }


    public static double relativeToPitchY(PitchDimensions dimensions, double y_factor){
        return dimensions.aabb.origin.y + dimensions.aabb.height*y_factor;
    }


    public static Vector2 relativeToPlayingField(PitchDimensions dimensions, double x_factor, double y_factor){
        return new Vector2(
                relativeToPitchX(dimensions, x_factor),
                relativeToPlayingFieldY(dimensions, y_factor)
        );
    }


    public static Vector2 relativeToPitch(PitchDimensions dimensions, double x_factor, double y_factor){
        return new Vector2(
                relativeToPitchX(dimensions, x_factor),
                relativeToPitchY(dimensions, y_factor)
        );
    }
}
