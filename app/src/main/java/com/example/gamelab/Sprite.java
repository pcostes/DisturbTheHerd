package com.example.gamelab;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;

import androidx.annotation.Nullable;

public class Sprite extends RectF
{
    enum ShapeType {RECT, OVAL, BITMAP};

    protected ShapeType m_shape;
    protected float centerX, centerY;
    protected float vx, vy;
    protected float ax, ay;

    protected void init_physics()
    {
        vx = 0;
        vy = 0;
        ax = 0;
        ay = 0;
    }

    public void init_physics(float pvx, float pvy, float pax, float pay)
    {
        vx = pvx;
        vy = pvy;
        ax = pax;
        ay = pay;
    }

    public Sprite(float width, float height, ShapeType shape)
    {
        super(0, 0, width, height);
        m_shape = shape;
        updateCenters();
        init_physics();
    }

    public Sprite()
    {
        this(0, 0, ShapeType.RECT);
    }

    public void updateVeloc(float dt)
    {
        vx += ax * dt;
        vy += ay * dt;
    }

    public void updatePosition(float dt)
    {
        left += vx * dt;
        right += vx * dt;
        top += vy * dt;
        bottom += vy * dt;
        updateCenters();
    }

    public void bounce(RectF screen)
    {
        if (centerX < screen.left || centerX > screen.right)
            vx *= -1f;
        if (centerY < screen.top || centerY > screen.bottom)
            vy *= -1f;
        if (centerX < screen.left - 10)
            setCenter(screen.left + 100, centerY);
        if (centerX > screen.right + 10)
            setCenter(screen.right - 100, centerY);
        if (centerY < screen.top - 10)
            setCenter(centerX, screen.top + 100);
        if (centerY > screen.bottom + 10)
            setCenter(centerX , screen.bottom - 100);
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();

            switch (m_shape)
            {
                case OVAL:
                    canvas.drawOval(this, paint);
                    break;
                case RECT:
                    canvas.drawRect(this, paint);
                    break;
            }
    }

    public void updateAll(RectF screen, float dt, Canvas canvas)
    {
        draw(canvas);
        updatePosition(dt);
        updateVeloc(dt);
        bounce(screen);
    }

    protected void updateCenters()
    {
        centerX = (left + right) / 2f;
        centerY = (top + bottom) / 2f;
    }

    public void setCenter(float x, float y)
    {
        float dx = x - centerX;
        float dy = y - centerY;
        left += dx;
        right += dx;
        top += dy;
        bottom += dy;
        updateCenters();
    }

    public float getCenterX()
    {
        return centerX;
    }

    public float getCenterY()
    {
        return centerY;
    }

    public void setVx(float vx)
    {
        this.vx = vx;
    }

    public void setVy(float vy)
    {
        this.vy = vy;
    }

    public void setAx(float ax)
    {
        this.ax = ax;
    }

    public void setAy(float ay)
    {
        this.ay = ay;
    }

    public float getVx() { return vx; }

    public float getVy() { return vy; }

    public float getAx() { return ax; }

    public float getAy() { return ay; }
}















