package com.vision.smarthomeapi.util;

import android.content.Context;

/**
 * px与dp互相转换
 * Created by yangle on 2015/5/6.
 */
public class DensityUtils {

	public static int dp2px(Context ctx, float dp) {
        //获取设备密度
		float density = ctx.getResources().getDisplayMetrics().density;
        //4.3, 4.9, 加0.5是为了四舍五入
		int px = (int) (dp * density + 0.5f);
		return px;
	}

	public static float px2dp(Context ctx, int px) {
        //获取设备密度
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp = px / density;
		return dp;
	}
}
