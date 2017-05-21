/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mightyfrog.android.watchfaceanimationsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

/**
 * @author Shigehiro Soejima
 */
public class MyWatchFace extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {
        float mCenterX;
        float mCenterY;
        Bitmap mBackground;
        Paint mBackgroundPaint = new Paint();
        boolean mAmbient;
        float mDegree;
        Matrix mMatrix = new Matrix();

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            Point p = new Point();
            display.getSize(p);
            mCenterX = p.x / 2f;
            mCenterY = p.y / 2f;

            mBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            mBackground = Bitmap.createScaledBitmap(mBackground, p.x, p.y, false);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                invalidate();
            }
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;
                invalidate();
            }
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            if (mDegree > 360) {
                mDegree = 0;
            } else {
                mDegree += 0.5;
            }
            mMatrix.setRotate(mDegree, mCenterX, mCenterY);
            canvas.setMatrix(mMatrix);
            canvas.drawBitmap(mBackground, mMatrix, mBackgroundPaint);

            if (isVisible() && !isInAmbientMode()) {
                invalidate();
            }
        }
    }
}
