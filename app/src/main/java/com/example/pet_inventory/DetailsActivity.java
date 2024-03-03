package com.example.pet_inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.Random;

public class DetailsActivity extends AppCompatActivity {
    private String imgUrl, name, price, pDate, sName, cName, schedule;
    TextView tv;
    ImageView pImage;
    LinearLayout bg;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        bg = findViewById(R.id.mainBG);
        pImage = (ImageView) findViewById(R.id.petImage);
        tv = (TextView) findViewById(R.id.tv);


        int colorCode = getLightRandomColorCode();
        tv.setBackgroundColor(colorCode);
        bg.setBackgroundColor(colorCode);
        loadIntent();
        showdata();

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadIntent();
    }

    public  void loadIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imgUrl = bundle.getString("Image");
            name = bundle.getString("name");
            price = bundle.getString("price");
            pDate = bundle.getString("date");
            sName = bundle.getString("supplierName");
            cName = bundle.getString("cageName");
            schedule = bundle.getString("schedule");
            Glide.with(getApplicationContext())
                    .load(imgUrl)
                    .into(pImage);
            //Toast.makeText(this, imgUrl+name+price+pDate+sName+cName+schedule, Toast.LENGTH_SHORT).show();
        }
    }

    public void showdata() {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        // Append Name
        appendColoredText(builder, "Name: ", Color.BLACK, Typeface.BOLD);
        appendText(builder, name, Color.BLUE, Typeface.NORMAL);

        // Append Description
        appendColoredText(builder, "\n\nDescription: ", Color.BLACK, Typeface.BOLD);
        String description = "This pet was purchased on " + pDate + " from the seller named '" + sName + "'.\n" +
                "It resides in the cage named '" + cName + "' and follows the schedule: " + schedule;
        appendText(builder, description, Color.BLACK, Typeface.NORMAL);

        // Append Price
        //appendColoredText(builder, "\n\nPrice: ", Color.BLACK, Typeface.BOLD);
        appendColoredPrice(builder, "\n\nPrice: ", price);
        //appendText(builder, price, Color.RED, Typeface.NORMAL);

        tv.setText(builder);
    }

    private void appendColoredText(SpannableStringBuilder builder, String text, int color, int style) {
        int start = builder.length();
        builder.append(text);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(style), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void appendText(SpannableStringBuilder builder, String text, int color, int style) {
        int start = builder.length();
        builder.append(text);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(style), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    private void appendColoredPrice(SpannableStringBuilder builder, String text, String price) {
        appendColoredText(builder, text, Color.BLACK, Typeface.BOLD);

        if ("Sold Out".equals(price)) {
            // Show in red if "Sold out"
            appendText(builder, price, Color.RED, Typeface.NORMAL);
        } else {
            // Show in green for other cases
            appendText(builder, price, Color.GREEN, Typeface.NORMAL);
        }
    }
    private int getLightRandomColorCode() {
        Random random = new Random();

        // Use a base value to bias towards lighter colors
        int baseValue = 150;

        int red = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105
        int green = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105
        int blue = baseValue + random.nextInt(106);  // Ranges from baseValue to baseValue + 105

        return Color.argb(255, red, green, blue);
    }
}