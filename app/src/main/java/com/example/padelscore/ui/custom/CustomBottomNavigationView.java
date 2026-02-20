package com.example.padelscore.ui.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.padelscore.R;

public class CustomBottomNavigationView extends ConstraintLayout {
    private LinearLayout tabsContainer;
    private View selectionBubble;
    private int activeIndex = 0;
    private int tabCount = 4;
    private OnTabSelectedListener listener;

    public CustomBottomNavigationView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomBottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_bottom_navigation_view, this, true);
        tabsContainer = findViewById(R.id.tabsContainer);
        selectionBubble = findViewById(R.id.selectionBubble);
        setupSafeAreaInsets();
        setupTabs();
    }

    private void setupSafeAreaInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(this, (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
            if (params != null) {
                params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.bottom_nav_margin) + bottomInset;
                setLayoutParams(params);
            }
            return insets;
        });
    }

    private void setupTabs() {
        tabsContainer.removeAllViews();
        int[] icons = {R.drawable.ic_trophy, R.drawable.ic_calendar, R.drawable.ic_star, R.drawable.ic_profile};
        String[] labels = {"Torneos", "Partidos", "Favoritos", "Perfil"};
        for (int i = 0; i < tabCount; i++) {
            LinearLayout tab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_custom_bottom_tab, tabsContainer, false);
            ImageView icon = tab.findViewById(R.id.tabIcon);
            TextView label = tab.findViewById(R.id.tabLabel);
            icon.setImageResource(icons[i]);
            label.setText(labels[i]);
            int finalI = i;
            tab.setOnClickListener(v -> selectTab(finalI));
            tabsContainer.addView(tab);
        }
        post(() -> selectTab(0));
    }

    private void selectTab(int index) {
        if (index == activeIndex) return;
        animateBubbleTo(index);
        updateTabStates(index);
        activeIndex = index;
        if (listener != null) listener.onTabSelected(index);
    }

    private void animateBubbleTo(int index) {
        int tabWidth = tabsContainer.getWidth() / tabCount;
        // Mantén el ancho de la burbuja igual al tab, pero añade un padding horizontal para que no se "coma" los bordes
        int horizontalPadding = (int) (tabWidth * 0.15f);
        int bubbleWidth = tabWidth - 2 * horizontalPadding;
        int bubbleLeft = tabWidth * index + horizontalPadding;
        selectionBubble.getLayoutParams().width = bubbleWidth;
        selectionBubble.requestLayout();
        ValueAnimator animator = ValueAnimator.ofFloat(selectionBubble.getTranslationX(), bubbleLeft);
        animator.setDuration(300);
        animator.addUpdateListener(animation -> selectionBubble.setTranslationX((float) animation.getAnimatedValue()));
        animator.start();
    }

    private void updateTabStates(int selectedIndex) {
        for (int i = 0; i < tabCount; i++) {
            LinearLayout tab = (LinearLayout) tabsContainer.getChildAt(i);
            ImageView icon = tab.findViewById(R.id.tabIcon);
            TextView label = tab.findViewById(R.id.tabLabel);
            if (i == selectedIndex) {
                icon.setColorFilter(getResources().getColor(R.color.white, null));
                icon.animate().scaleX(1.1f).scaleY(1.1f).setDuration(150).start();
                label.setTypeface(Typeface.DEFAULT_BOLD);
                label.setAlpha(1f);
            } else {
                icon.setColorFilter(getResources().getColor(R.color.colorOnSurface, null));
                icon.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                label.setTypeface(Typeface.DEFAULT);
                label.setAlpha(0.6f);
            }
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }
}
