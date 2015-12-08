package cs301.twodoer;


import android.graphics.RectF;

/*This code defines the brick object for the Breakout game*/
/*Code and comments came from http://gamecodeschool.com/android/coding-a-breakout-game-for-android/*/
public class Brick {

    private RectF rect;

    private boolean isVisible;

    public Brick(int row, int column, int width, int height){

        isVisible = true;

        int padding = 1;

        rect = new RectF(column * width + padding,
                row * height + padding,
                column * width + width - padding,
                row * height + height - padding);
    }

    public RectF getRect(){
        return this.rect;
    }

    public void setInvisible(){
        isVisible = false;
    }

    public boolean getVisibility(){
        return isVisible;
    }
}
