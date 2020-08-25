package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import core.DBHandler;
import core.Lesson;
import core.Sequence;

public class SequenceActivity extends AppCompatActivity {
    boolean momentanesFenster;

    @Override
    public void onBackPressed() {

        if(momentanesFenster == true){
            //Der Nutzer befindet sich gerade im Unterfenster, da momentanesFenster = 1.

            //Nun öffnen wir wieder das Layout des Hauptfensters.
            Intent i = new Intent(SequenceActivity.this, SequenceActivity.class);
            startActivity(i);
            finish();


            //anschließend ändern wir die Variable momentanesFenster wieder auf 0, da dies das Hauptfenster beschreibt.
            momentanesFenster = false;

            //Mit diesem Befehl verhindern wir die weitere Ausführung der Funktion und damit überspringen wir alle weiteren Befehle.
            return;
        }

        // Standardaktion (App schließen)
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        final LinearLayout linearLayout = findViewById(R.id.list);

        final DBHandler db = new DBHandler(getApplicationContext());
        final Sequence[] sequence = db.getSequence();

        final TextView sequenceuebersicht = (TextView) findViewById(R.id.Sequenceübersicht);
        String html = "<h2>Übersicht der geladenen Sequenzen</h2>";
        sequenceuebersicht.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));

        ImageButton sync = findViewById(R.id.sync);
        sync.setBackgroundResource(R.mipmap.sync);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SequenceActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        for (int i = 0; i < sequence.length; i++) {
            final TextView valueTV = new TextView(this);
            String text = "<h2>Titel: "+sequence[i].getTitle()+"</h2><p>Unterrichtsfach: "+sequence[i].getSubject()+"</p><p>Klassenstufe: "+sequence[i].getGrade()+"</p><p>Ziele: "+sequence[i].getGoal()+"</p><p>Kommentare: "+sequence[i].getComments()+"</p>";
            valueTV.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
            valueTV.setId(sequence[i].getId());
            valueTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            valueTV.setBackground(getDrawable(R.drawable.border));

            int padding_in_dp = 5;
            final float scale = getResources().getDisplayMetrics().density;
            int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

            valueTV.setPadding(padding_in_px,padding_in_px,padding_in_px,padding_in_px);

            linearLayout.addView(valueTV);

            valueTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    momentanesFenster = true;
                    final Lesson[] lesson = db.getLessons(valueTV.getId());
                    Context context = getApplicationContext();
                    linearLayout.removeAllViewsInLayout();
                        for (int i = 0; i < sequence.length; i++) {
                            if (sequence[i].getId() == valueTV.getId()) {
                                String html = "<h2>Stunden zur Sequenz: " + sequence[i].getTitle() + " ("+sequence[i].getSubject()+", Klasse "+sequence[i].getGrade()+")</h2>";
                                sequenceuebersicht.setText(HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY));
                            }
                        }

                    for (int i = 0; i < lesson.length; i++) {
                        final TextView value2TV = new TextView(context);
                        String lessonText = "<h3>Titel: "+lesson[i].getTitle()+"</h3><p>Länge: " + lesson[i].getLength()+"</p><p>Ziele: "+
                                lesson[i].getGoal()+"</p><p>Hausaufgaben: "+lesson[i].getHomeworks()+"</p><p>Kommentare: "+lesson[i].getComments()+"</p>";
                        value2TV.setText(HtmlCompat.fromHtml(lessonText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        value2TV.setId(lesson[i].getId());
                        value2TV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                        value2TV.setBackground(getDrawable(R.drawable.border));
                        value2TV.setTextColor(getColor(R.color.colorText));
                        value2TV.setTextSize(16);

                        int padding_in_dp = 5;
                        final float scale = getResources().getDisplayMetrics().density;
                        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);

                        value2TV.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);

                        linearLayout.addView(value2TV);
                        value2TV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String lessonID = ""+value2TV.getId();
                                Intent i = new Intent(SequenceActivity.this, LessonActivity.class);
                                i.putExtra("lessonID", lessonID);
                                startActivity(i);
                            }
                        });
                    }
                }
            });
        }
    }
}