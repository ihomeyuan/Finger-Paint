package nt.finger.paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DrawView extends View implements OnTouchListener {
	private static final String TAG = "DrawView";
	
	List<Point> points = new ArrayList<Point>();
	Paint paint = new Paint();
	Random gen;
	int col_mode = 0;	// set default colour to white
	
	public DrawView(Context context) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		this.setOnTouchListener(this);
		
		paint.setAntiAlias(true);
	}
	
	// used to clear the screen
	public void clearPoints () {
		points.clear();
		// force View to redraw
		// without this, points aren't cleared until next action
		invalidate();
	}
	
	// used to set drawing colour
	public void changeColour (int col_in) {
		col_mode = col_in;
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		// for each point, draw on canvas
		for (Point point : points) {
			point.draw(canvas, paint);
			Log.d(TAG, "Painting: "+point);
		}
	}
	
	public boolean onTouch(View view, MotionEvent event) {
		int new_col = 0;
		if (col_mode >= 0) {
			switch (col_mode) {
				case 0 : {
					new_col = Color.WHITE;
					break;
				}
				case 1 : {
					new_col = Color.BLUE;
					break;
				}
				case 2 : {
					new_col = Color.CYAN;
					break;
				}
				case 3 : {
					new_col = Color.GREEN;
					break;
				}
				case 4 : {
					new_col = Color.MAGENTA;
					break;
				}
				case 5 : {
					new_col = Color.RED;
					break;
				}
				case 6 : {
					new_col = Color.YELLOW;
					break;
				}
				case 7 : {
					new_col = Color.BLACK;
					break;
				}
			}
		} else {
			gen = new Random();
			new_col = gen.nextInt( 8 );
		}
		Point point;
		if(event.getAction() == MotionEvent.ACTION_MOVE) {
			point = new FriendlyPoint(event.getX(), event.getY(), new_col, points.get(points.size() - 1));	
		} else if (event.getAction() == MotionEvent.ACTION_DOWN) {	
			point = new Point(event.getX(), event.getY(), new_col);
		} else {
			return false;
		}
		points.add(point);
		invalidate();
		Log.d(TAG, "point: " + point);
		return true;
	}
}