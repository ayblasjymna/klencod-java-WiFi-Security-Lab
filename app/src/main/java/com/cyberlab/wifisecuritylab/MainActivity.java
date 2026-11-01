
package com.cyberlab.wifisecuritylab;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import android.text.InputType;

public class MainActivity extends Activity {

    LinearLayout main;
    EditText password;
    TextView result;

    int bg = Color.rgb(13, 17, 28);
    int blue = Color.rgb(35, 110, 230);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(25, 35, 25, 35);
        main.setBackgroundColor(bg);

        scroll.addView(main);
        setContentView(scroll);

        TextView title = new TextView(this);
        title.setText("WIFI SECURITY LAB");
        title.setTextSize(27);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 20, 0, 15);
        main.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Educational Cybersecurity Toolkit");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        main.addView(subtitle);

        addSpace();

        addLabel("PASSWORD SECURITY ANALYZER");

        password = new EditText(this);
        password.setSingleLine(true);
        password.setHint("Enter a test password");
        password.setHintTextColor(Color.GRAY);
        password.setTextColor(Color.WHITE);
        password.setInputType(
            InputType.TYPE_CLASS_TEXT |
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        );
        password.setPadding(15, 12, 15, 12);
        main.addView(password);

        addButton("ANALYZE PASSWORD", v -> analyze());

        addSpace();

        addLabel("LOCAL GUESSING SIMULATOR");

        addButton("START DEMO SIMULATION", v -> simulate());

        addSpace();

        result = new TextView(this);
        result.setTextColor(Color.WHITE);
        result.setTextSize(16);
        result.setPadding(15, 20, 15, 20);
        result.setBackgroundColor(Color.rgb(28, 35, 48));
        main.addView(result);

        addSpace();

        TextView footer = new TextView(this);
        footer.setText(
            "EDUCATIONAL LAB\n" +
            "Local simulation only\n" +
            "No real WiFi attacks"
        );
        footer.setTextColor(Color.LTGRAY);
        footer.setGravity(Gravity.CENTER);
        main.addView(footer);
    }

    void addLabel(String text) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextColor(Color.rgb(70, 160, 255));
        label.setTextSize(17);
        label.setPadding(0, 20, 0, 12);
        main.addView(label);
    }

    void addSpace() {
        TextView space = new TextView(this);
        space.setHeight(25);
        main.addView(space);
    }

    void addButton(String text,
                   android.view.View.OnClickListener listener) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(blue)
        );

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                -1, -2
            );

        params.setMargins(0, 12, 0, 12);
        main.addView(button, params);
        button.setOnClickListener(listener);
    }

    void analyze() {

        String p = password.getText().toString();

        if (p.isEmpty()) {
            password.setError("Enter a test password");
            return;
        }

        int score = 0;

        if (p.length() >= 12) score += 2;
        if (p.matches(".*[A-Z].*")) score++;
        if (p.matches(".*[a-z].*")) score++;
        if (p.matches(".*[0-9].*")) score++;
        if (p.matches(".*[^a-zA-Z0-9].*")) score++;

        String level;

        if (score <= 2) {
            level = "WEAK";
        } else if (score <= 4) {
            level = "MEDIUM";
        } else {
            level = "STRONG";
        }

        result.setText(
            "PASSWORD ANALYSIS\n\n" +
            "Length: " + p.length() + "\n" +
            "Security Score: " + score + "/6\n" +
            "Security Level: " + level
        );
    }

    void simulate() {

        String target = password.getText().toString();

        if (target.isEmpty()) {
            password.setError("Enter a demo password first");
            return;
        }

        String[] words = {
            "12345678",
            "password",
            "admin123",
            "welcome123",
            "Lab2026!"
        };

        int attempts = 0;
        boolean found = false;

        for (String word : words) {
            attempts++;

            if (word.equals(target)) {
                found = true;
                break;
            }
        }

        result.setText(
            "LOCAL SIMULATION REPORT\n\n" +
            "Attempts: " + attempts + "\n" +
            "Result: " +
            (found ? "Demo match found" : "No match found") +
            "\n\nThis was an offline demonstration."
        );
    }
}
