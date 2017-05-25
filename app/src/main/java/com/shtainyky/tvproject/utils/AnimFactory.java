package com.shtainyky.tvproject.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by Bell on 23.05.2017.
 */
public abstract class AnimFactory {

    public static class AnimatorBuilder {

        ValueAnimator animator;

        public AnimatorBuilder(ValueAnimator animator) {
            this.animator = animator;
        }

        public AnimatorBuilder setValues(float... values) {
            animator.setFloatValues(values);
            return this;
        }

        public AnimatorBuilder setValues(int... values) {
            animator.setIntValues(values);
            return this;
        }

        public AnimatorBuilder setInterpolator(TimeInterpolator interpolator) {
            animator.setInterpolator(interpolator);
            return this;
        }

        public AnimatorBuilder addUpdateListener(ValueAnimator.AnimatorUpdateListener listener) {
            animator.addUpdateListener(listener);
            return this;
        }

        public AnimatorBuilder addListener(Animator.AnimatorListener listener) {
            animator.addListener(listener);
            return this;
        }

        public AnimatorBuilder setStartDelay(long delay) {
            animator.setStartDelay(delay);
            return this;
        }

        public AnimatorBuilder setDuration(long time) {
            animator.setDuration(time);
            return this;
        }

        public ValueAnimator get() {
            return animator;
        }
    }

    public static AnimatorBuilder alpha(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.ALPHA, 0f, 1f));
    }

    public static AnimatorBuilder transX(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.TRANSLATION_X, 0f, 1f));
    }

    public static AnimatorBuilder transY(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, 0f, 1f));
    }

    public static AnimatorBuilder scaleX(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.SCALE_X, 0f, 1f));
    }

    public static AnimatorBuilder scaleY(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.SCALE_Y, 0f, 1f));
    }

    public static AnimatorBuilder rotate(View target) {
        return new AnimatorBuilder(ObjectAnimator.ofFloat(target, View.ROTATION, 0f, 180f));
    }

    public static AnimatorBuilder values(int... values) {
        return new AnimatorBuilder(ValueAnimator.ofInt(values));
    }


}
