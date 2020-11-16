package com.example.gamelab;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class CowSprite extends Sprite
{
    Bitmap bit;
    int rows, cols, mr, mc, single_width, single_height, count;
    int cow_row = 4, cow_col = 6, anim = 0;
    enum Direction {DOWN, LEFT, RIGHT, UP};
    int change = 10;
    float modifier = 500;
    public CowSprite(Bitmap b, int r, int c, int cow_r, int cow_c)
    {
        bit = b;
        rows = r;
        cols = c;
        mr = mc = 0;
        cow_row = cow_r;
        cow_col = cow_c;
        single_width = b.getWidth() / cols + 1;
        single_height = b.getHeight() / rows - 1;
        m_shape = ShapeType.BITMAP;
        right = single_width;
        bottom = single_height;
    }

    public float dist(CowSprite other)
    {
        return (float) Math.sqrt(Math.pow((other.getCenterX() - getCenterX()), 2f) + Math.pow(other.getCenterY() - getCenterY(), 2f));
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();

        switch (m_shape)
        {
            case BITMAP:
                int dir = 0;
                if (Math.abs(vx) > Math.abs(vy))
                {
                    if (vx > 0)
                        dir = Direction.RIGHT.ordinal();
                    else
                        dir = Direction.LEFT.ordinal();

                    if (vx != 0)
                        change = (int) Math.abs(modifier / vx) + 1;
                }
                else
                {
                    if (vy > 0)
                        dir = Direction.DOWN.ordinal();
                    else
                        dir = Direction.UP.ordinal();
                    if (vy != 0)
                        change = (int) Math.abs(modifier / vy) + 1;
                }
                mr = cow_row + dir;
                mc = cow_col + anim;

                Rect src = new Rect(single_width * mc, single_height * mr, single_width * (mc + 1), single_height * (mr + 1));

                canvas.drawBitmap(bit, src, this, paint);
                if (count % change == 0)
                {
                    anim++;
                    anim %= 3;
                }
                count++;

        }
    }

}
