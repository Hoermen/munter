package com.example.munter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.pdfview.PDFView;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;

public class LessonActivity extends AppCompatActivity {
    SpannableStringBuilder spanTxt;
    DBHandler db;
    Lesson lesson;
    String lessonID = "";
    String comments = "";
    EditText checkliste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lesson);
        Intent i = getIntent();
        lessonID  = i.getStringExtra("lessonID");

        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        db = new DBHandler(getApplicationContext());
        lesson = db.getLesson(Integer.parseInt(lessonID));
        final Resource[] resource = db.getResource(Integer.parseInt(lessonID));
        final PlanEntry[] planEntries = db.getPlanentry(lessonID);

        spanTxt = new SpannableStringBuilder("Materialien: \n");
        spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int j = 0; j < resource.length; j++) {
            spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
            final int finalJ = j;
            spanTxt.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    if (resource[finalJ].getType().contains("image/*")) {
                        Intent i = new Intent(LessonActivity.this, materialActivity.class);
                        i.putExtra("material", resource[finalJ].getTextContent());
                        startActivity(i);
                    }
                    if (resource[finalJ].getType().contains("application/pdf")) {
                        Intent i = new Intent(LessonActivity.this, pdfActivity.class);
                        i.putExtra("material", resource[finalJ].getTextContent());
                        startActivity(i);
                    }
                }
            }, spanTxt.length() - resource[j].getFilename().length()-1, spanTxt.length(), 0);
        }

        final TextView lessonText = (TextView) findViewById(R.id.LessonInfo);
        String html = "<h2><u>"+lesson.getTitle()+"</u></h2><p>Beschreibung: "+lesson.getBeschreibung()+"</p>";
        lessonText.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView HAText = (TextView) findViewById(R.id.HAInfo);
        String html2 = "<h4>Hausaufgaben:</h4><p>"+lesson.getHomeworks()+"</p>";
        HAText.setText(HtmlCompat.fromHtml(html2, HtmlCompat.FROM_HTML_MODE_LEGACY));

        final TextView materialien = findViewById(R.id.LessonMaterialien);
        materialien.setMovementMethod(LinkMovementMethod.getInstance());
        materialien.setText(spanTxt, TextView.BufferType.SPANNABLE);

        final TextView notesText = findViewById(R.id.textNotizen);
        String html3 = "<b>Notizen:<b>";
        notesText.setText(HtmlCompat.fromHtml(html3, HtmlCompat.FROM_HTML_MODE_LEGACY));

        checkliste = (EditText) findViewById(R.id.Checkliste);

        checkliste.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        final LinearLayout ausblick = findViewById(R.id.Ausblick);
        checkliste.setText(lesson.getComments());

        Button draw = findViewById(R.id.buttonDraw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LessonActivity.this, drawActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
            }
        });

        LinearLayout tableLayout = findViewById(R.id.Zeitplan);

        TextView tv = findViewById(R.id.textView3);
        String html4 = "<b>Zeitplan</b>";
        tv.setTextSize(20);
        tv.setText(HtmlCompat.fromHtml(html4, HtmlCompat.FROM_HTML_MODE_LEGACY));

        for (int x=0; x < planEntries.length; x++){
            TextView tv2 = new TextView(this);

            String plan = "<h4><u>"+planEntries[x].getTitle() + "</u></h4><p>"+planEntries[x].getStart()+". Minute - "+(planEntries[x].getLength()+planEntries[x].getStart())+". Minute (Phase gesamt: "+planEntries[x].getLength()+"min)</p><p><b>Ziele:</b> "+
                    planEntries[x].getGoal()+"</p><p><b>Sozialform:</b> "+planEntries[x].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntries[x].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntries[x].getReserve()+"</p><p><b>Kommentare:</b> "+planEntries[x].getComments()+"</p>";
            tv2.setText(HtmlCompat.fromHtml(plan, HtmlCompat.FROM_HTML_MODE_LEGACY));
            tv2.setBackground(getDrawable(R.drawable.border));
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            tv2.setWidth(width);
            tv2.setMinHeight(height / 4);
            int padding_in_dp = 10;
            final float scale = getResources().getDisplayMetrics().density;
            int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
            tv2.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);
            tv2.setLineSpacing(-10,1);
            tableLayout.addView(tv2);
        }

        //buttonStart
        Button ButtonStart = (Button) findViewById(R.id.ButtonStart);
        ButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(LessonActivity.this, Durchfuehrung.class);
                i.putExtra("lessonID", lessonID);
                comments = checkliste.getText().toString();
                lesson.setComments(comments);
                db.updateLesson(lesson, lesson.getId());
                finish();
                startActivity(i);
            }
        });



        Button ButtonFeedback = (Button) findViewById(R.id.buttonFeedback);
        ButtonFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(LessonActivity.this, FeedbackActivity.class);
                i.putExtra("lessonID", lessonID);
                comments = checkliste.getText().toString();
                lesson.setComments(comments);
                db.updateLesson(lesson, lesson.getId());
                startActivity(i);
            }
        });
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        db = new DBHandler(getApplicationContext());
        lesson = db.getLesson(Integer.parseInt(lessonID));
        lesson.setComments(checkliste.getText().toString());
        db.updateLesson(lesson, lesson.getId());
        super.onBackPressed();
    }
}