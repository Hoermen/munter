/** munter - das mobile unterrichtsbegleitende Unterstützungssystem für angehende Lehrpersonen
© 2020 Herrmann Elfreich

This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, on
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

package com.example.munter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import core.DBHandler;
import core.Gesten;
import core.Lesson;
import core.PlanEntry;
import core.Resource;

public class Durchfuehrung extends AppCompatActivity {
    DBHandler db;
    CountDownTimer mCountDownTimer;
    ProgressBar pb;
    int progress = -1;
    int length;
    int id = 0;
    String lessonID;
    long[] time;
    long[] startTime;
    long[] endTime;
    LinearLayout ll;
    SpannableStringBuilder spanTxt;
    int textsize = 18;
    Lesson lesson;
    EditText notes;
    int planid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_durchfuehrung);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        db = new DBHandler(getApplicationContext());
        notes = (EditText) findViewById(R.id.editTextNotes);

        notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Intent i = getIntent();

        lessonID = i.getStringExtra("lessonID");
        final TextView current = (TextView) findViewById(R.id.current);
        final TextView previous = (TextView) findViewById(R.id.previous);
        final TextView next = (TextView) findViewById(R.id.next);
       lesson = db.getLesson(Integer.parseInt(lessonID));
        final PlanEntry[] planEntry = db.getPlanentry(lessonID);
       ll = (LinearLayout) findViewById(R.id.PlanEntry);
       final TextView timeStunde = findViewById(R.id.timeStunde);

       planid = planEntry[0].getId();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int defaultValue = 22;
        textsize = sharedPref.getInt("textsize", defaultValue);

        current.setTextSize(textsize);
        previous.setTextSize(textsize);
        next.setTextSize(textsize);

        notes.setText(lesson.getComments());

        final TextView titleStunde = findViewById(R.id.titleStunde);
        final String titleText = "<h1><u>"+lesson.getTitle()+"</u></h1>";
        titleStunde.setText(HtmlCompat.fromHtml(titleText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        LinearLayout gridLayout = findViewById(R.id.duringLesson);

        final Resource[] resource = db.getResource(Integer.parseInt(lessonID));

        spanTxt = new SpannableStringBuilder();
        String Materialien = "Materialien: \n";
        spanTxt.append(Materialien);
        spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (int j = 0; j < resource.length; j++) {
            if (resource[j].getPlanentryid() == planid){
                spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                final int finalJ = j;
                spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanTxt.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (resource[finalJ].getType().contains("image/*")) {
                            Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                            i.putExtra("material", resource[finalJ].getTextContent());
                            startActivity(i);
                        }
                        if (resource[finalJ].getType().contains("application/pdf")) {
                            Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                            i.putExtra("material", resource[finalJ].getTextContent());
                            startActivity(i);

                        }


                    }
                }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
            } else {
                spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                final int finalJ = j;
                spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanTxt.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (resource[finalJ].getType().contains("image/*")) {
                            Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                            i.putExtra("material", resource[finalJ].getTextContent());
                            startActivity(i);
                        }
                        if (resource[finalJ].getType().contains("application/pdf")) {
                            Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                            i.putExtra("material", resource[finalJ].getTextContent());
                            startActivity(i);

                        }


                    }
                }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
            }


        }

        TextView materials = findViewById(R.id.materials);
        materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
        materials.setMovementMethod(LinkMovementMethod.getInstance());

        gridLayout.setOnTouchListener(new Gesten(this) {
            public boolean onSwipeTop() {
                //es wurde nach oben gewischt, hier den Code einfügen
                return false;
            }
            public boolean onSwipeLeft() {
                if (id < planEntry.length) {
                    for (int i = 0; i < planEntry.length; i++) {
                        if (startTime[i] != 0) {
                            endTime[i] = System.currentTimeMillis();
                            time[i] = (time[i] + (endTime[i] - startTime[i]));
                            endTime[i] = 0;
                            startTime[i] = 0;
                        }
                    }
                    startTime[id+1] = System.currentTimeMillis();
                    changeColor(lessonID, planEntry[id+1].getId());
                    TextView tv = findViewById(planEntry[id+1].getId());
                    tv.setBackgroundColor(Color.parseColor("#54e0ff"));
                    tv.setClickable(false);

                    String currentText = "<h5><u>" + planEntry[id + 1].getTitle() + "</u></h5><p>" + planEntry[id + 1].getStart() + ". Minute - " + (planEntry[id + 1].getLength() + planEntry[id + 1].getStart()) + ". Minute (" + planEntry[id + 1].getLength() + "min)</p><p><b>Ziele:</b> " +
                            planEntry[id + 1].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id + 1].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id + 1].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id + 1].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id + 1].getComments() + "</p>";

                    current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    if (id >= 0) {
                        String previousText = "<h5><u>" + planEntry[id].getTitle() + "</u></h5><p>" + planEntry[id].getStart() + ". Minute - " + (planEntry[id].getLength() + planEntry[id].getStart()) + ". Minute (" + planEntry[id].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id].getComments() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(previousText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (Integer.parseInt(lessonID) - 1 >= 1) {
                        String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p>Hausaufgaben: " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else previous.setText("letzte Stunde nicht verfügbar");
                    if (id + 2 <= planEntry.length - 1) {
                        String nextText = "<h5><u>" + planEntry[id + 2].getTitle() + "</u></h5><p>" + planEntry[id + 2].getStart() + ". Minute - " + (planEntry[id + 2].getLength() + planEntry[id + 2].getStart()) + ". Minute (" + planEntry[id + 2].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id + 2].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id + 2].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id + 2].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id + 2].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id + 2].getComments() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null) {
                        String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h2><p>Beschreibung: " + db.getLesson(Integer.parseInt(lessonID + 1)).getBeschreibung() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else next.setText("letzte Phase erreicht");
                    id = id + 1;
                    planid = planEntry[id].getId();
                }
                spanTxt.clear();
                spanTxt.append("Materialien: \n");
                spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                for (int j = 0; j < resource.length; j++) {
                    if (resource[j].getPlanentryid() == planid){
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    } else {
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    }


                }

                TextView materials = findViewById(R.id.materials);
                materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
                materials.setMovementMethod(LinkMovementMethod.getInstance());

                return false;
            }

            public boolean onSwipeRight() {
                if (id > 0) {
                    for (int i = 0; i < planEntry.length; i++) {
                        if (startTime[i] != 0) {
                            endTime[i] = System.currentTimeMillis();
                            time[i] = (time[i] + (endTime[i] - startTime[i]));
                            endTime[i] = 0;
                            startTime[i] = 0;
                        }
                    }
                    startTime[id-1] = System.currentTimeMillis();
                    changeColor(lessonID, planEntry[id-1].getId());
                    TextView tv = findViewById(planEntry[id-1].getId());
                    tv.setBackgroundColor(Color.parseColor("#54e0ff"));
                    tv.setClickable(false);
                    String currentText = "<h5><u>" + planEntry[id - 1].getTitle() + "</u></h5><p>" + planEntry[id - 1].getStart() + ". Minute - " + (planEntry[id - 1].getLength() + planEntry[id - 1].getStart()) + ". Minute (" + planEntry[id - 1].getLength() + "min)</p><p><b>Ziele:</b> " +
                            planEntry[id - 1].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id - 1].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id - 1].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id - 1].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id - 1].getComments() + "</p>";

                    current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    if (id - 2 >= 0) {
                        String previousText = "<h5><u>" + planEntry[id - 2].getTitle() + "</u></h2><p>" + planEntry[id - 2].getStart() + ". Minute - " + (planEntry[id - 2].getLength() + planEntry[id - 2].getStart()) + ". Minute (" + planEntry[id - 2].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id - 2].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id - 2].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id - 2].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id - 2].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id - 2].getComments() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(previousText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (Integer.parseInt(lessonID) - 1 >= 1) {
                        String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p><b>Hausaufgaben:</b> " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else previous.setText("letzte Stunde nicht verfügbar");
                    if (id <= planEntry.length - 1) {
                        String nextText = "<h5><u>" + planEntry[id].getTitle() + "</u></h2><p>" + planEntry[id].getStart() + ". Minute - " + (planEntry[id].getLength() + planEntry[id].getStart()) + ". Minute (" + planEntry[id].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id].getComments() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null) {
                        String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h2><p><b>Beschreibung:</b> " + db.getLesson(Integer.parseInt(lessonID + 1)).getBeschreibung() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else next.setText("letzte Phase erreicht");
                    id = id - 1;
                    planid = planEntry[id].getId();
                }

                spanTxt.clear();
                spanTxt.append("Materialien: \n");
                spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                for (int j = 0; j < resource.length; j++) {
                    if (resource[j].getPlanentryid() == planid){
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    } else {
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    }


                }

                TextView materials = findViewById(R.id.materials);
                materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
                materials.setMovementMethod(LinkMovementMethod.getInstance());
                return false;

            }
            public boolean onSwipeBottom() {
                //es wurde nach unten gewischt, hier den Code einfügen
                return false;
            }
            public boolean nichts(){
                //es wurde keine wischrichtung erkannt, hier den Code einfügen
                return false;
            }
        });

        startTime = new long[planEntry.length];
        endTime = new long[planEntry.length];
        time = new long[planEntry.length];
        for (int j = 0; j < planEntry.length; j++) {
            endTime[j] = 0;
            startTime[j] = 0;
        }
        startTime[0] = System.currentTimeMillis();

        String currentText = "<h5><u>" + planEntry[0].getTitle() + "</u></h5><p>"+planEntry[0].getStart()+". Minute - "+(planEntry[0].getLength()+planEntry[0].getStart())+". Minute ("+planEntry[0].getLength()+"min)</p><p><b>Ziele:</b> "+
                planEntry[0].getGoal()+"</p><p><b>Sozialform:</b> "+planEntry[0].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntry[0].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntry[0].getReserve()+"</p><p><b>Kommentare:</b> "+planEntry[0].getComments()+"</p>";

        current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        current.setLineSpacing(-5,1);
        if (Integer.parseInt(lessonID) - 1 >= 1 && db.getLesson(Integer.parseInt(lessonID) - 1).getSequenceid() == lesson.getSequenceid()) {
            String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p><b>Hausaufgaben:</b> " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks()+"</p>";
            previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else previous.setText("letzte Stunde nicht verfügbar");
        previous.setLineSpacing(-5,1);
        if (1 <= planEntry.length - 1) {
            String nextText = "<h5><u>" + planEntry[1].getTitle() + "</u></h5><p>"+planEntry[1].getStart()+". Minute - "+(planEntry[1].getLength()+planEntry[1].getStart())+". Minute ("+planEntry[1].getLength()+"min)</p><p><b>Ziele:</b> "+
                    planEntry[1].getGoal()+"</p><p><b>Sozialform:</b> "+planEntry[1].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntry[1].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntry[1].getReserve()+"</p><p><b>Kommentare:</b> "+planEntry[1].getComments()+"</p>";
            next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null && db.getLesson(Integer.parseInt(lessonID) + 1).getSequenceid() == lesson.getSequenceid()) {
            String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h5><p><b>Beschreibung:</b> " + db.getLesson(Integer.parseInt(lessonID+1)).getBeschreibung() + "</p>";
            next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
        }
        else next.setText("letzte Phase erreicht");
        next.setLineSpacing(-5,1);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id < planEntry.length) {
                    for (int i = 0; i < planEntry.length; i++) {
                        if (startTime[i] != 0) {
                            endTime[i] = System.currentTimeMillis();
                            time[i] = (time[i] + (endTime[i] - startTime[i]));
                            endTime[i] = 0;
                            startTime[i] = 0;
                        }
                    }
                    startTime[id + 1] = System.currentTimeMillis();
                    changeColor(lessonID, planEntry[id + 1].getId());
                    TextView tv = findViewById(planEntry[id + 1].getId());
                    tv.setBackgroundColor(Color.parseColor("#54e0ff"));
                    tv.setClickable(false);

                    String currentText = "<h5><u>" + planEntry[id + 1].getTitle() + "</u></h5><p>" + planEntry[id + 1].getStart() + ". Minute - " + (planEntry[id + 1].getLength() + planEntry[id + 1].getStart()) + ". Minute (" + planEntry[id + 1].getLength() + "min)</p><p><b>Ziele:</b> " +
                            planEntry[id + 1].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id + 1].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id + 1].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id + 1].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id + 1].getComments() + "</p>";

                    current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    if (id >= 0) {
                        String previousText = "<h5><u>" + planEntry[id].getTitle() + "</u></h5><p>" + planEntry[id].getStart() + ". Minute - " + (planEntry[id].getLength() + planEntry[id].getStart()) + ". Minute (" + planEntry[id].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id].getComments() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(previousText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (Integer.parseInt(lessonID) - 1 >= 1) {
                        String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p>Hausaufgaben: " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else previous.setText("letzte Stunde nicht verfügbar");
                    if (id + 2 <= planEntry.length - 1) {
                        String nextText = "<h5><u>" + planEntry[id + 2].getTitle() + "</u></h5><p>" + planEntry[id + 2].getStart() + ". Minute - " + (planEntry[id + 2].getLength() + planEntry[id + 2].getStart()) + ". Minute (" + planEntry[id + 2].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id + 2].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id + 2].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id + 2].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id + 2].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id + 2].getComments() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null) {
                        String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h2><p>Beschreibung: " + db.getLesson(Integer.parseInt(lessonID + 1)).getBeschreibung() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else next.setText("letzte Phase erreicht");
                    id = id + 1;
                    planid = planEntry[id].getId();
                }
                spanTxt.clear();
                spanTxt.append("Materialien: \n");
                spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                for (int j = 0; j < resource.length; j++) {
                    if (resource[j].getPlanentryid() == planid) {
                        spanTxt.append(resource[j].getTitle() + " (" + resource[j].getFilename() + ")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length() - resource[j].getFilename().length() - 2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length() - 2, spanTxt.length(), 0);
                    } else {
                        spanTxt.append(resource[j].getTitle() + " (" + resource[j].getFilename() + ")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length() - resource[j].getFilename().length() - 2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length() - 2, spanTxt.length(), 0);
                    }


                }

                TextView materials = findViewById(R.id.materials);
                materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
                materials.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id > 0) {
                    for (int i = 0; i < planEntry.length; i++) {
                        if (startTime[i] != 0) {
                            endTime[i] = System.currentTimeMillis();
                            time[i] = (time[i] + (endTime[i] - startTime[i]));
                            endTime[i] = 0;
                            startTime[i] = 0;
                        }
                    }
                    startTime[id-1] = System.currentTimeMillis();
                    changeColor(lessonID, planEntry[id-1].getId());
                    TextView tv = findViewById(planEntry[id-1].getId());
                    tv.setBackgroundColor(Color.parseColor("#54e0ff"));
                    tv.setClickable(false);
                    String currentText = "<h5><u>" + planEntry[id - 1].getTitle() + "</u></h5><p>" + planEntry[id - 1].getStart() + ". Minute - " + (planEntry[id - 1].getLength() + planEntry[id - 1].getStart()) + ". Minute (" + planEntry[id - 1].getLength() + "min)</p><p><b>Ziele:</b> " +
                            planEntry[id - 1].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id - 1].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id - 1].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id - 1].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id - 1].getComments() + "</p>";

                    current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    if (id - 2 >= 0) {
                        String previousText = "<h5><u>" + planEntry[id - 2].getTitle() + "</u></h2><p>" + planEntry[id - 2].getStart() + ". Minute - " + (planEntry[id - 2].getLength() + planEntry[id - 2].getStart()) + ". Minute (" + planEntry[id - 2].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id - 2].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id - 2].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id - 2].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id - 2].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id - 2].getComments() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(previousText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (Integer.parseInt(lessonID) - 1 >= 1) {
                        String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p><b>Hausaufgaben:</b> " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks() + "</p>";
                        previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else previous.setText("letzte Stunde nicht verfügbar");
                    if (id <= planEntry.length - 1) {
                        String nextText = "<h5><u>" + planEntry[id].getTitle() + "</u></h2><p>" + planEntry[id].getStart() + ". Minute - " + (planEntry[id].getLength() + planEntry[id].getStart()) + ". Minute (" + planEntry[id].getLength() + "min)</p><p><b>Ziele:</b> " +
                                planEntry[id].getGoal() + "</p><p><b>Sozialform:</b> " + planEntry[id].getSocialForm() + "</p><p><b>Beschreibung:</b> " + planEntry[id].getBeschreibung() + "</p><p><b>didaktische Reserve:</b> " + planEntry[id].getReserve() + "</p><p><b>Kommentare:</b> " + planEntry[id].getComments() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null) {
                        String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h2><p><b>Beschreibung:</b> " + db.getLesson(Integer.parseInt(lessonID + 1)).getBeschreibung() + "</p>";
                        next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    } else next.setText("letzte Phase erreicht");
                    id = id - 1;
                    planid = planEntry[id].getId();
                }

                spanTxt.clear();
                spanTxt.append("Materialien: \n");
                spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                for (int j = 0; j < resource.length; j++) {
                    if (resource[j].getPlanentryid() == planid){
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    } else {
                        spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                        final int finalJ = j;
                        spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (resource[finalJ].getType().contains("image/*")) {
                                    Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);
                                }
                                if (resource[finalJ].getType().contains("application/pdf")) {
                                    Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                    i.putExtra("material", resource[finalJ].getTextContent());
                                    startActivity(i);

                                }


                            }
                        }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                    }


                }

                TextView materials = findViewById(R.id.materials);
                materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
                materials.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        length = lesson.getLength();
        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(length*60);
        pb.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        pb.setScaleY(2f);

        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progress < length*60) {
                    progress += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    long minutes = progress / 60;
                    long seconds = progress % 60;

                    final String timeString = String.format("%02d:%02d", minutes, seconds);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(progress);
                            String text = "<h4>vergangene Zeit: "+timeString+"</h4>";
                            timeStunde.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        }
                    });
                    try {
                        // Sleep for 60 seconds.
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        for (int j = 0; j < planEntry.length; j++) {

                final TextView value2TV = new TextView(Durchfuehrung.this);

                final int start = planEntry[j].getStart();
                int length = planEntry[j].getLength();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                Double width = Double.parseDouble(""+displayMetrics.widthPixels);
                Double setWidth = (width) / lesson.getLength() * length;
                value2TV.setWidth((int) Math.round(setWidth));

                String lessonText = "<b>" + planEntry[j].getTitle() + "</b>";
                value2TV.setText(HtmlCompat.fromHtml(lessonText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                value2TV.setId(planEntry[j].getId());
                value2TV.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                value2TV.setBackground(getDrawable(R.drawable.border));
                value2TV.setTextColor(getColor(R.color.colorText));
                value2TV.setGravity(Gravity.CENTER);
                value2TV.setTextSize(16);

            ImageButton zoomin = findViewById(R.id.ZoomIn);
            zoomin.setBackgroundResource(R.drawable.zoomin);
            zoomin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    current.setTextSize(textsize+1);
                    previous.setTextSize(textsize+1);
                    next.setTextSize(textsize+1);
                    textsize=textsize+1;
                }
            });
            ImageButton zoomout = findViewById(R.id.ZoomOut);
            zoomout.setBackgroundResource(R.drawable.zoomout);
            zoomout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    current.setTextSize(textsize-1);
                    previous.setTextSize(textsize-1);
                    next.setTextSize(textsize-1);
                    textsize=textsize-1;
                }
            });

                final int finalJ = j;
                value2TV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < planEntry.length; i++) {
                            if (startTime[i] != 0) {
                                endTime[i] = System.currentTimeMillis();
                                time[i] = (time[i] + (endTime[i] - startTime[i]));
                                endTime[i] = 0;
                                startTime[i] = 0;
                            }
                        }
                        id = finalJ;
                        planid = planEntry[finalJ].getId();

                        startTime[finalJ] = System.currentTimeMillis();
                        changeColor(lessonID, planEntry[finalJ].getId());
                        value2TV.setBackgroundColor(Color.parseColor("#54e0ff"));
                        value2TV.setClickable(false);
                        String currentText = "<h5><u>" + planEntry[finalJ].getTitle() + "</u></h5><p>"+planEntry[finalJ].getStart()+". Minute - "+(planEntry[finalJ].getLength()+planEntry[finalJ].getStart())+". Minute ("+planEntry[finalJ].getLength()+"min)</p><p><b>Ziele:</b> "+
                                planEntry[finalJ].getGoal()+"</p><p><b>Sozialform:</b> "+planEntry[finalJ].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntry[finalJ].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntry[finalJ].getReserve()+"</p><p><b>Kommentare:</b> "+planEntry[finalJ].getComments()+"</p>";

                        current.setText(HtmlCompat.fromHtml(currentText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        if (finalJ - 1 >= 0) {
                            String previousText = "<h5><u>" + planEntry[finalJ-1].getTitle() + "</u></h5><p>"+planEntry[finalJ-1].getStart()+". Minute - "+(planEntry[finalJ-1].getLength()+planEntry[finalJ-1].getStart())+". Minute ("+planEntry[finalJ-1].getLength()+"min)</p><p><b>Ziele:</b> "+
                                    planEntry[finalJ-1].getGoal()+"</p><p><b>Sozialform:</b> "+planEntry[finalJ-1].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntry[finalJ-1].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntry[finalJ-1].getReserve()+"</p><p><b>Kommentare:</b> "+planEntry[finalJ-1].getComments()+"</p>";
                            previous.setText(HtmlCompat.fromHtml(previousText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        } else if (Integer.parseInt(lessonID) - 1 >= 1 && db.getLesson(Integer.parseInt(lessonID) - 1).getSequenceid() == lesson.getSequenceid()) {
                            String letzteStunde = "<h5><u>letzte Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) - 1).getTitle() + "</h2><p><b>Hausaufgaben:</b> " + db.getLesson(Integer.parseInt(lessonID)).getHomeworks()+"</p>";
                            previous.setText(HtmlCompat.fromHtml(letzteStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        } else previous.setText("letzte Stunde nicht verfügbar");
                        if (finalJ + 1 <= planEntry.length - 1) {
                            String nextText = "<h5><u>" + planEntry[finalJ+1].getTitle() + "</u></h5><p>"+planEntry[finalJ+1].getStart()+". Minute - "+(planEntry[finalJ+1].getLength()+planEntry[finalJ+1].getStart())+". Minute ("+planEntry[finalJ+1].getLength()+"min)</p><p><b>Ziele:</b> "+
                                    planEntry[finalJ+1].getGoal()+"</p><p><b>Sozialform:</b> "+planEntry[finalJ+1].getSocialForm()+"</p><p><b>Beschreibung:</b> "+planEntry[finalJ+1].getBeschreibung()+"</p><p><b>didaktische Reserve:</b> "+planEntry[finalJ+1].getReserve()+"</p><p><b>Kommentare:</b> "+planEntry[finalJ+1].getComments()+"</p>";
                            next.setText(HtmlCompat.fromHtml(nextText, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        } else if (db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() != null && db.getLesson(Integer.parseInt(lessonID)+1).getSequenceid() == lesson.getSequenceid()) {
                            String nextStunde = "<h5><u>nächste Stunde:</u> " + db.getLesson(Integer.parseInt(lessonID) + 1).getTitle() + "</h2><p><b>Beschreibung:</b> " + db.getLesson(Integer.parseInt(lessonID+1)).getBeschreibung() + "</p>";
                            next.setText(HtmlCompat.fromHtml(nextStunde, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        }
                        else next.setText("letzte Phase erreicht");

                        spanTxt.clear();
                        spanTxt.append("Materialien: \n");
                        spanTxt.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, 12, spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                        for (int j = 0; j < resource.length; j++) {
                            if (resource[j].getPlanentryid() == planid){
                                spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                                final int finalJ = j;
                                spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.BOLD), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                                spanTxt.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (resource[finalJ].getType().contains("image/*")) {
                                            Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                            i.putExtra("material", resource[finalJ].getTextContent());
                                            startActivity(i);
                                        }
                                        if (resource[finalJ].getType().contains("application/pdf")) {
                                            Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                            i.putExtra("material", resource[finalJ].getTextContent());
                                            startActivity(i);

                                        }


                                    }
                                }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                            } else {
                                spanTxt.append(resource[j].getTitle() + " ("+ resource[j].getFilename()+")\n");
                                final int finalJ = j;
                                spanTxt.setSpan(new android.text.style.StyleSpan(Typeface.ITALIC), spanTxt.length()-resource[j].getFilename().length()-2, spanTxt.length(), spanTxt.SPAN_EXCLUSIVE_EXCLUSIVE);
                                spanTxt.setSpan(new ClickableSpan() {
                                    @Override
                                    public void onClick(View widget) {
                                        if (resource[finalJ].getType().contains("image/*")) {
                                            Intent i = new Intent(Durchfuehrung.this, materialActivity.class);
                                            i.putExtra("material", resource[finalJ].getTextContent());
                                            startActivity(i);
                                        }
                                        if (resource[finalJ].getType().contains("application/pdf")) {
                                            Intent i = new Intent(Durchfuehrung.this, pdfActivity.class);
                                            i.putExtra("material", resource[finalJ].getTextContent());
                                            startActivity(i);

                                        }


                                    }
                                }, spanTxt.length() - resource[j].getFilename().length()-2, spanTxt.length(), 0);
                            }


                        }

                        TextView materials = findViewById(R.id.materials);
                        materials.setText(spanTxt, TextView.BufferType.SPANNABLE);
                        materials.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                });
                ll.addView(value2TV);

        }

        TextView entry = (TextView) findViewById(planEntry[0].getId());
        entry.setBackgroundColor(Color.parseColor("#54e0ff"));
        entry.setClickable(false);


        Button draw = findViewById(R.id.buttonDraw);
        draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Durchfuehrung.this, drawActivity.class);
                i.putExtra("lessonID", lessonID);
                startActivity(i);
            }
        });

        //buttonStart
        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                new AlertDialog.Builder(Durchfuehrung.this)
                        .setTitle("Stunde beenden")
                        .setMessage("Wollen Sie die Stunde wirklich beenden?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < planEntry.length; i++) {
                                    if (startTime[i] != 0) {
                                        endTime[i] = System.currentTimeMillis();
                                        time[i] = (time[i] + (endTime[i] - startTime[i]));
                                        endTime[i] = 0;
                                        startTime[i] = 0;
                                    }
                                }


                                for (int k = 0; k < planEntry.length; k++) {
                                    int zeit = (int) time[k];
                                    planEntry[k].setReallength(zeit/1000);
                                    db.updatePlanentry(planEntry[k], planEntry[k].getId());
                                }
                                lesson.setComments(notes.getText().toString());
                                db.updateLesson(lesson, Integer.parseInt(lessonID));
                                Intent i = new Intent(Durchfuehrung.this, FeedbackActivity.class);
                                i.putExtra("lessonID", lessonID);
                                SharedPreferences sharedPref = Durchfuehrung.this.getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt("textsize", textsize);
                                editor.commit();

                                finish();
                                Durchfuehrung.this.startActivity(i);
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton("nein", null)
                        .show();


            }
        });
        final TextView time = (TextView) findViewById(R.id.Chronometer);
        Button timer = (Button) findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                final EditText input = new EditText(Durchfuehrung.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                final NumberPicker np = new NumberPicker(Durchfuehrung.this);
                np.setMinValue(1);
                np.setMaxValue(90);
                np.setValue(5);
                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                new AlertDialog.Builder(Durchfuehrung.this)
                        .setTitle("Timer starten")
                        .setMessage("Minuten wählen")
                        .setView(np)


                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Timer starten (bei Ablauf akustisch)", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final long zeit = np.getValue();

                                //cancel the old countDownTimer
                                if(mCountDownTimer!=null){
                                    mCountDownTimer.cancel();
                                }
                                time.setTextColor(Color.BLACK);
                                mCountDownTimer = new CountDownTimer(zeit*1000*60,1000){
                                    public void onTick(long millisUntilFinished) {
                                        long minutes = (millisUntilFinished / 1000) / 60;
                                        long seconds = (millisUntilFinished / 1000) % 60;

                                        String timeString = String.format("%02d:%02d", minutes, seconds);
                                        time.setText(timeString);
                                    }

                                    public void onFinish() {
                                        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION,100);
                                        tg.startTone(ToneGenerator.TONE_CDMA_ABBR_REORDER);
                                        time.setText("Ende!");
                                        time.setTextColor(Color.RED);
                                    }
                                };
                                   mCountDownTimer.start();
                                }
                        })

                        .setNegativeButton("Timer starten (bei Ablauf visuell)", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final long zeit = np.getValue();

                                //cancel the old countDownTimer
                                if(mCountDownTimer!=null){
                                    mCountDownTimer.cancel();
                                }
                                time.setTextColor(Color.BLACK);
                                mCountDownTimer = new CountDownTimer(zeit*1000*60,1000){
                                    public void onTick(long millisUntilFinished) {
                                        long minutes = (millisUntilFinished / 1000) / 60;
                                        long seconds = (millisUntilFinished / 1000) % 60;

                                        String timeString = String.format("%02d:%02d", minutes, seconds);
                                        time.setText(timeString);
                                    }

                                    public void onFinish() {
                                       LinearLayout ll = findViewById(R.id.duringLesson);
                                        ObjectAnimator anim = ObjectAnimator.ofInt(ll, "backgroundColor", Color.WHITE, Color.RED,
                                                Color.WHITE);
                                        anim.setDuration(1500);
                                        anim.setEvaluator(new ArgbEvaluator());
                                        anim.setRepeatMode(ValueAnimator.REVERSE);
                                        anim.setRepeatCount(5);
                                        anim.start();
                                        time.setText("Ende!");
                                        time.setTextColor(Color.RED);
                                    }
                                };
                                mCountDownTimer.start();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNeutralButton("Abbrechen", null)
                        .show();
            }
        });
    }

    private void changeColor(String lessonID, int id) {
        PlanEntry[] pe = db.getPlanentry(lessonID);
        TextView[] tv = new TextView[pe.length];
        for (int j = 0; j < pe.length; j++) {
            tv[j] = findViewById(pe[j].getId());
            tv[j].setBackground(getDrawable(R.drawable.border));
            tv[j].setClickable(true);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        db = new DBHandler(getApplicationContext());
        lesson = db.getLesson(Integer.parseInt(lessonID));
        lesson.setComments(notes.getText().toString());
        db.updateLesson(lesson, lesson.getId());

        new AlertDialog.Builder(Durchfuehrung.this)
                .setTitle("Stunde beenden")
                .setMessage("Wollen Sie die Stunde wirklich beenden?")

                .setPositiveButton("ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Durchfuehrung.this, SequenceActivity.class);
                        finish();
                        Durchfuehrung.this.startActivity(i);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("nein", null)
                .show();

    }
}
