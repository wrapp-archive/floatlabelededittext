package com.wrapp.floatlabelededittext;

import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

public class Utils {
	
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	/**
	 * Generate a value suitable for use in {@link #setId(int)}.
	 * This value will not collide with ID values generated at build time by aapt for R.id.
	 *
	 * @return a generated ID value
	 */
	private static int generateViewId() {
	    for (;;) {
	        final int result = sNextGeneratedId.get();
	        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	        int newValue = result + 1;
	        if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	        if (sNextGeneratedId.compareAndSet(result, newValue)) {
	            return result;
	        }
	    }
	}
	
	@SuppressLint("NewApi")
	public static int generateId() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

	        return generateViewId();
	    } else {

	        return View.generateViewId();
	    }
	}

}
