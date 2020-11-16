package com.example.gamelab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrawView extends View
{
    ArrayList<CowSprite> basicRects = new ArrayList<CowSprite>();
    RectF screen = null;
    float dt = 0.1f;
    PointF center;
    int num_cows = 0;
    boolean[][] in_range;
    public DrawView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        for (int i = 0; i < 1; i++)
        {
            for (int r = 0; r < 8; r += 4)
            {
                for (int c = 0; c < 12; c += 3)
                {
                    basicRects.add(new CowSprite(BitmapFactory.decodeResource(getResources(), R.drawable.cows_new), 8, 12, r, c));
                    num_cows++;
                }
            }
        }
        in_range = new boolean[num_cows][num_cows];
        for (int i = 0; i < num_cows; i++)
        {
            for (int j = 0; j < num_cows; j++)
            {
                in_range[i][j] = false;
            }
        }
    }

    private void draw_init()
    {
        screen = new RectF(0, 0, getWidth(), getHeight());
        center = new PointF(getWidth() / 2f, getHeight() / 2f);
        for (int i = 0; i < num_cows; i++)
        {
            CowSprite cow = basicRects.get(i);
            cow.setCenter( getWidth() * (float) Math.random(),  getHeight() * (float) Math.random());
            cow.init_physics(100f, -100f, 0f, 0f);
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if (screen == null)
        {
            draw_init();
        }

        super.onDraw(canvas);
        for (int i = 0; i < num_cows; i++)
        {
            CowSprite cow = basicRects.get(i);
            for (int j = 0; j < num_cows; j++)
            {
                if (j != i)
                {
                    CowSprite other = basicRects.get(j);

                    float d = cow.dist(other);
                    float c_ax = cow.getVx(), c_ay = cow.getVy(), o_ax = other.getVx(), o_ay = other.getVy();
                    if (d < 300 && !in_range[i][j])
                    {
                        float max = 200;
                        float mod_x = (float) ((max * Math.random()) - (max / 2f));
                        float mod_y = (float) ((max * Math.random()) - (max / 2f));

                        cow.setVx(mod_x);// + ((o_ax - c_ax) / (d * mod_x)));
                        cow.setVy(mod_y); //+ ((o_ay - c_ay) / (d * mod_y)));

                        in_range[i][j] = true;
                    }

                    else if (d < 1200 && !in_range[i][j])
                    {
                        float modifier = 1f;

                        cow.setVx(c_ax + ((o_ax - c_ax) / ( (d - 500) * modifier)));
                        cow.setVy(c_ay + ((o_ay - c_ay) / ( (d - 500) * modifier)));
                    }

                    else if (d > 1200)
                    {
                        in_range[i][j] = false;
                    }

                }
            }
            cow.updateAll(screen, dt, canvas);

        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        for (int i = 0; i < num_cows; i++)
        {
            CowSprite cow = basicRects.get(i);
            if (cow.contains(event.getX(), event.getY()))
            {
                float max = 800;
                float mod_x = (float) ((max * Math.random()) - (max / 2f));
                float mod_y = (float) ((max * Math.random()) - (max / 2f));

                cow.setVx(mod_x);// + ((o_ax - c_ax) / (d * mod_x)));
                cow.setVy(mod_y); //+ ((o_ay - c_ay) / (d * mod_y)));
            }
        }
        return super.onTouchEvent(event);
    }
}
