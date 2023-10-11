package com.example.team12.onboarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.team12.R;

import org.w3c.dom.Text;

public class ViewPageAdapter extends PagerAdapter {
    Context context;
    int sliderAllImages[] = {R.drawable.splash1, R.drawable.splash2, R.drawable.splash3};
    int sliderAllTitle[] = {R.string.heading_1, R.string.heading_2, R.string.heading_3};
    int sliderAllDesc[] = {R.string.description_1, R.string.description_2, R.string.description_3};

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return sliderAllTitle.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object; // return true if the view is the same as the object
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // inflate the layout
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_screen, container, false);

        // get the reference of the view objects
        ImageView sliderImage = (ImageView) view.findViewById(R.id.sliderImage);
        TextView sliderTitle = (TextView) view.findViewById(R.id.sliderTitle);
        TextView sliderDesc = (TextView) view.findViewById(R.id.sliderDesc);

        // set the values
        sliderImage.setImageResource(sliderAllImages[position]);
        sliderTitle.setText(sliderAllTitle[position]);
        sliderDesc.setText(sliderAllDesc[position]);

        // add the view to the parent
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // remove the view from the parent
            container.removeView((LinearLayout) object);
    }
}
