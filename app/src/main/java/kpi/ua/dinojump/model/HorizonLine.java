package kpi.ua.dinojump.model;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import kpi.ua.dinojump.view.GameView;

import static kpi.ua.dinojump.Runner.BaseBitmap;

public class HorizonLine extends BaseEntity {

    private Point spritePos;
    private int[] sourceXPos = new int[2];
    private double[] xPos = new double[2];
    private final int yPos = 127;

    private static final int WIDTH = 600;
    private static final int HEIGHT = 12;

    public HorizonLine(Point pos) {
        spritePos = pos;
        sourceXPos[0] = spritePos.x;
        sourceXPos[1] = spritePos.x + WIDTH;
        xPos[0] = 0;
        xPos[1] = WIDTH;
    }

    private void UpdateXPos(int pos, double increment) {
        int line1 = pos;
        int line2 = pos == 0 ? 1 : 0;
        xPos[line1] -= increment;
        xPos[line2] = xPos[line1] + WIDTH;
        if (xPos[line1] <= -WIDTH) {
            xPos[line1] += WIDTH * 2;
            xPos[line2] = xPos[line1] - WIDTH;
            sourceXPos[line1] = spritePos.x;
        }
    }

    public void update(long deltaTime, double speed) {
        int fps = GameView.FPS;
        double increment = speed * fps * deltaTime / 1000;
        if (xPos[0] <= 0) {
            UpdateXPos(0, increment);
        } else {
            UpdateXPos(1, increment);
        }
    }

    public void draw(Canvas canvas) {
        Rect sRect1 = getScaledSource(sourceXPos[0], spritePos.y, WIDTH, HEIGHT);
        Rect tRect1 = getScaledTarget((int) xPos[0], yPos, WIDTH, HEIGHT);
        canvas.drawBitmap(
                BaseBitmap,
                sRect1,
                tRect1,
                null);
        Rect sRect2 = getScaledSource(sourceXPos[1], spritePos.y, WIDTH, HEIGHT);
        Rect tRect2 = getScaledTarget((int) xPos[1], yPos, WIDTH, HEIGHT);
        canvas.drawBitmap(
                BaseBitmap,
                sRect2,
                tRect2,
                null);
    }

    @Override
    public double getXPos() {
        return xPos[0];
    }

    @Override
    public int getYPos() {
        return yPos;
    }

    public void reset() {
        this.xPos[0] = 0;
        this.xPos[1] = WIDTH;
    }
}