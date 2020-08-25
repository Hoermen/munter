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

            String text = "<h2>Titel: "+sequence[i].getTitle()+"</h2><p>Unterrichtsfach: "+sequence[i].getSubject()+"</p><p>Klassenstufe: "+sequence[i].getGrade()+"</p><p>Ziele: "+sequence[i].getGoal()+"</p><p>Beschreibung: Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                    "\n" +
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                    "\n" +
                    "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                    "\n" +
                    "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                    "\n" +
                    "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                    "\n" +
                    "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur</p>";
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