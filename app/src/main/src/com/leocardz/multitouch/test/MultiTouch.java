package com.leocardz.multitouch.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouch extends View {
    static int r = 0, g = 0, b = 0, radius = 40, outerRadius = radius + 10,
            outerOuterRadius = radius + 20, currentTotal = 0;
    static int colorPoolR[] = {000, 000, 000, 255, 255, 206, 255, 000, 126, 126, 
							   000, 000, 000, 255, 255, 206, 255, 000, 126, 126};
    static int colorPoolG[] = {246, 000, 255, 000, 162, 206, 240, 255, 255, 000, 
							   246, 000, 255, 000, 102, 206, 240, 255, 255, 000};
    static int colorPoolB[] = {255, 255, 000, 000, 000, 206, 000, 216, 000, 255, 
							   255, 255, 000, 000, 000, 206, 000, 216, 000, 255};
    private final int TOUCHS = 30;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint messagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    boolean start_draw = false;
    float[] x0 = new float[TOUCHS];
    float[] y0 = new float[TOUCHS];
    float[] x = new float[TOUCHS];
    float[] y = new float[TOUCHS];
    float[] startX1 = new float[TOUCHS], startY1 = new float[TOUCHS],
            stopX1 = new float[TOUCHS], stopY1 = new float[TOUCHS];
    float[] startX2 = new float[TOUCHS], startY2 = new float[TOUCHS],
            stopX2 = new float[TOUCHS], stopY2 = new float[TOUCHS];
    boolean[] isTouch = new boolean[TOUCHS];
    CharSequence additional = "";
    int add = 20;
    private Context context;

    public MultiTouch(Context context) {
        super(context);
        this.context = context;
        numberPaint.setStrokeWidth(1);
        numberPaint.setARGB(255, 255, 255, 255);
        numberPaint.setStyle(Paint.Style.FILL);
        numberPaint.setTextSize(MultitouchTest.screenDensity / 10);

        messagePaint.setStrokeWidth(1);
        messagePaint.setARGB(255, 255, 255, 255);
        messagePaint.setStyle(Paint.Style.FILL);
        messagePaint.setTextSize(MultitouchTest.screenDensity / 10);

        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setStrokeWidth(5);
    }

    public MultiTouch(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MultiTouch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float messageDensity;
        int counting = 0;

        for (int i = 0; i < isTouch.length; i++) {
            if (isTouch[i]) {
                counting++;
                if (MultitouchTest.colorChanging == 1) {
                    randColor();
                } else {
                	r = colorPoolR[i];
                	g = colorPoolG[i];
                	b = colorPoolB[i];
                }
                paint.setStrokeWidth(1);
                paint.setStyle(Paint.Style.FILL);

                if (MultitouchTest.rings == 1) {
                    paint.setARGB(255, r, g, b);
                    canvas.drawCircle(x[i], y[i], outerOuterRadius, paint);

                    paint.setARGB(255, 0, 0, 0);
                    canvas.drawCircle(x[i], y[i], outerRadius, paint);
                }

                paint.setARGB(255, r, g, b);
                canvas.drawCircle(x[i], y[i], radius, paint);

                if (MultitouchTest.lines == 1) {
                    canvas.drawLine(startX1[i], startY1[i], stopX1[i],
                            stopY1[i], paint);
                    canvas.drawLine(startX2[i], startY2[i], stopX2[i],
                            stopY2[i], paint);
                }

                if (MultitouchTest.coordinates == 1) {
                    additional = "[x: " + String.valueOf((int) x[i]) + ", y: "
                            + String.valueOf((int) y[i]) + "]";
                    add = 20;

                    if (MultitouchTest.numberShowing == 1) 
                        canvas.drawText(String.valueOf(i + 1) + ": " + additional, 
                        				x[i] - 50, y[i] - 50 - add, numberPaint);
                    else 
                        canvas.drawText((String) additional, 
                        				x[i] - 50, y[i] - 50 - add, numberPaint);
                } else {
                    additional = "";
                    add = 0;

                    if (MultitouchTest.numberShowing == 1) 
                        canvas.drawText(String.valueOf(i + 1),
                        				x[i] - 50, y[i] - 50 - add, numberPaint);
                }
                
                if (MultitouchTest.draw == 1 && start_draw) {
                	linePaint.setARGB(255, r, g, b);
                	canvas.drawLine(x0[i], y0[i], x[i], y[i], linePaint);
                }
                
            }
        }

        if (currentTotal == 0) {
            MultitouchTest.ab.show();

            float messageWidth = messagePaint
                    .measureText(MultitouchTest.centerMessage);
            canvas.drawText(MultitouchTest.centerMessage,
                    (MultitouchTest.screenWidth - messageWidth) / 2,
                    MultitouchTest.screenHeight / 2, messagePaint);

            if (MultitouchTest.density == 1) {
                messageDensity = messagePaint
                        .measureText(MultitouchTest.densityText + " "
                                + MultitouchTest.screenDensity + "dpi");
                canvas.drawText(MultitouchTest.densityText + " "
                                + MultitouchTest.screenDensity + "dpi",
                        (MultitouchTest.screenWidth - messageDensity) / 2,
                        (float) (MultitouchTest.screenHeight * 0.70),
                        messagePaint
                );
            }
        } else if (MultitouchTest.ab.isShowing()) {
            MultitouchTest.ab.hide();
        }

        String currentTouches = context.getString(R.string.current_touches)
                + ": " + counting;
        messageDensity = messagePaint.measureText(currentTouches);
        canvas.drawText(currentTouches,
                (MultitouchTest.screenWidth - messageDensity) / 2, 40f, messagePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        @SuppressWarnings("deprecation")
        int pointerIndex = ((motionEvent.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT);
        int pointerId = motionEvent.getPointerId(pointerIndex);
        int action = (motionEvent.getAction() & MotionEvent.ACTION_MASK);
        int pointCnt = motionEvent.getPointerCount();

        if (pointCnt <= TOUCHS) {
            if (pointerIndex <= TOUCHS - 1) {
                for (int i = 0; i < pointCnt; i++) {
                    int id = motionEvent.getPointerId(i);
                    if (x0[id] == x[id] && y0[id] == y[id]) 
                    	start_draw = false;
                    else
                    	start_draw = true;
                    if (start_draw) {
                    	x0[id] = x[id];
                    	y0[id] = y[id];
                    }
                    x[id] = (int) motionEvent.getX(i);
                    y[id] = (int) motionEvent.getY(i);
                    
                    startX1[id] = motionEvent.getX(i);
                    startY1[id] = 0;
                    stopX1[id] = motionEvent.getX(i);
                    stopY1[id] = MultitouchTest.screenHeight * 1.5f;

                    startX2[id] = 0;
                    startY2[id] = motionEvent.getY(i);
                    stopX2[id] = MultitouchTest.screenWidth;
                    stopY2[id] = motionEvent.getY(i);
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    currentTotal++;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                    currentTotal--;

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (MultitouchTest.vibration == 1) {
                            Vibrator v = (Vibrator) context
                                    .getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(25);
                        }

                        if (MultitouchTest.colorChanging == 0)
                            randColor();
                    case MotionEvent.ACTION_MOVE:
                        isTouch[pointerId] = true;
                        break;
                    default:
                        isTouch[pointerId] = false;
                }
            }
        }

        invalidate();
        return true;
    }

    public void randColor() {
        do {
            r = (int) (Math.random() * 255);
            g = (int) (Math.random() * 255);
            b = (int) (Math.random() * 255);
        } while (r == 0 && g == 0 && b == 0);
    }

}
