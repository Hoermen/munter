package com.example.munter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import core.DBHandler;
import core.Lesson;
import core.PlanEntry;
import core.Resource;
import core.ResourceType;
import core.Sequence;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();

        TextView munter = findViewById(R.id.munter);
        String munterText = "<h1>munter</h1>";
        munter.setText(HtmlCompat.fromHtml(munterText, HtmlCompat.FROM_HTML_MODE_LEGACY));

        ImageView icon = findViewById(R.id.icon);
        icon.setImageResource(R.mipmap.munter);

        TextView munter2 = findViewById(R.id.munter2);
        String munter2Text = "<h3>- DIE Unterstützung für guten Unterricht -</h3>";
        munter2.setText(HtmlCompat.fromHtml(munter2Text, HtmlCompat.FROM_HTML_MODE_LEGACY));


        //buttonLogin
        Button ButtonSkip = (Button) findViewById(R.id.buttonSkip);
        ButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, SequenceActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //buttonLogin
        Button ButtonNew = (Button) findViewById(R.id.buttonNew);
        ButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, PlatonLoginActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        db = new DBHandler(getApplicationContext());
        SQLiteDatabase sql = db.getWritableDatabase();
        db.onUpgrade(sql, 2,3);
      createModel();
    }

    @Override
    protected void onDestroy() {
        db.closeDB();
        super.onDestroy();
    }
    public void createModel() {
        Sequence sequence1 = new Sequence();
        sequence1.setTitle("anders");
        sequence1.setComments("viel Sprechen");
        sequence1.setGoal("interessanter");
        sequence1.setGrade(8);
        sequence1.setSubject("Deutsch");
        sequence1.setId(8);

        Sequence sequence2 = new Sequence();
        sequence2.setTitle("Gleichungen");
        sequence2.setComments("viel Sprechen");
        sequence2.setGoal("interessant");
        sequence2.setGrade(9);
        sequence2.setSubject("Mathe");
        sequence2.setId(1);

        Lesson lesson1 = new Lesson();
        lesson1.setTitle("erste super neue Stunde");
        lesson1.setId(1);
        lesson1.setComments("aufpassen");
        lesson1.setGoal("vertiefen");
        lesson1.setHomeworks("Aufsatz");
        lesson1.setOrder(1);
        lesson1.setLength(90);
        lesson1.setSequenceid(1);
        lesson1.setBeschreibung("Beschreibung hier einfügen");
        lesson1.setFeedback("1. Phase ändern");

        Lesson lesson2 = new Lesson();
        lesson2.setTitle("Test");
        lesson2.setId(2);
        lesson2.setComments("test ist wichtig");
        lesson2.setGoal("neues Lernen");
        lesson2.setHomeworks("Übungsaufgaben");
        lesson2.setOrder(1);
        lesson2.setLength(90);
        lesson2.setSequenceid(8);
        lesson2.setFeedback("alles super");

        PlanEntry planEntry1 = new PlanEntry();
        planEntry1.setColor("blue");
        planEntry1.setComments("1. Kommentafdsggffffffgggggggggggggggggdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggr");
        planEntry1.setGoal("Einführung");
        planEntry1.setId(1);
        planEntry1.setLength(10);
        planEntry1.setLessonId(1);
        planEntry1.setSocialForm("Gruppenarbeit");
        planEntry1.setStart(0);
        planEntry1.setTitle("Erste richtig wichtige Phase");
        planEntry1.setReserve("Aufgaben für schnelle");
        planEntry1.setBeschreibung("so sollte der Unterricht laufen");
        planEntry1.setReallength(660);

        PlanEntry planEntry2 = new PlanEntry();
        planEntry2.setColor("blue");
        planEntry2.setComments("2. Kommentar");
        planEntry2.setGoal("Aufgaben");
        planEntry2.setId(2);
        planEntry2.setLength(20);
        planEntry2.setLessonId(1);
        planEntry2.setSocialForm("Einzelarbeit");
        planEntry2.setStart(10);
        planEntry2.setTitle("zweite");
        planEntry2.setReallength(1500);

        PlanEntry planEntry5 = new PlanEntry();
        planEntry5.setColor("blue");
        planEntry5.setComments("1. Kommentar");
        planEntry5.setGoal("Einführung");
        planEntry5.setId(5);
        planEntry5.setLength(30);
        planEntry5.setLessonId(1);
        planEntry5.setSocialForm("Gruppenarbeit");
        planEntry5.setStart(30);
        planEntry5.setTitle("dritte");
        planEntry5.setReallength(2000);

        PlanEntry planEntry6 = new PlanEntry();
        planEntry6.setColor("blue");
        planEntry6.setComments("2. Kommentar");
        planEntry6.setGoal("Aufgaben");
        planEntry6.setId(6);
        planEntry6.setLength(30);
        planEntry6.setLessonId(1);
        planEntry6.setSocialForm("Einzelarbeit");
        planEntry6.setStart(60);
        planEntry6.setTitle("vierte");
        planEntry6.setReallength(2000);

        Resource resource1 = new Resource();
        resource1.setId(1);
        resource1.setFilename("Unbenannt.PNG");
        resource1.setTextContent("file:/storage/emulated/0/Download/Unbenannt.PNG");
        //resource1.setTextContent("file:/sdcard/Download/Unbenannt.PNG");
        resource1.setTitle("Bild");
        resource1.setType("image/*");
        resource1.setLessonid(1);
        resource1.setPlanentryid(1);

        Resource resource2 = new Resource();
        resource2.setId(2);
        resource2.setFilename("test.pdf");
        resource2.setTextContent("file:/storage/emulated/0/Download/test.pdf");
        //resource2.setTextContent("file:/sdcard/Download/test.pdf");
        resource2.setTitle("Plan");
        resource2.setType("application/pdf");
        resource2.setLessonid(1);
        resource2.setPlanentryid(2);

        Resource resource3 = new Resource();
        resource3.setId(3);
        resource3.setFilename("Turnelemente.pdf");
        resource3.setTextContent("file:/storage/emulated/0/Download/Turnelemente.pdf");
        //resource2.setTextContent("file:/sdcard/Download/test.pdf");
        resource3.setTitle("Turnelemente");
        resource3.setType("application/pdf");
        resource3.setLessonid(5);
        resource3.setPlanentryid(29);

        PlanEntry planEntry8 = new PlanEntry();
        planEntry8.setColor("blue");
        planEntry8.setComments("1. Kommentar");
        planEntry8.setGoal("Einführung");
        planEntry8.setId(3);
        planEntry8.setLength(10);
        planEntry8.setLessonId(2);
        planEntry8.setSocialForm("Gruppenarbeit");
        planEntry8.setStart(0);
        planEntry8.setTitle("neu");

        PlanEntry planEntry9 = new PlanEntry();
        planEntry9.setColor("blue");
        planEntry9.setComments("4Kommentar");
        planEntry9.setGoal("Einführung");
        planEntry9.setId(4);
        planEntry9.setLength(10);
        planEntry9.setLessonId(2);
        planEntry9.setSocialForm("Gruppenarbeit");
        planEntry9.setStart(10);
        planEntry9.setTitle("schluss");

        Sequence mathe = new Sequence();
        mathe.setTitle("Vektoren");
        mathe.setId(3);
        mathe.setBeschreibung("Wissen über Vektoren");
        mathe.setGrade(12);
        mathe.setSubject("Mathematik");
        mathe.setGoal("Fähigkeit mit Vektoren rechnen zu können");

        Lesson mathe1 = new Lesson();
        mathe1.setTitle("Vektorbegriff");
        mathe1.setBeschreibung("");
        mathe1.setId(3);
        mathe1.setComments("");
        mathe1.setGoal("Einführung Vektoren");
        mathe1.setHomeworks("");
        mathe1.setOrder(1);
        mathe1.setLength(95);
        mathe1.setSequenceid(3);
        mathe1.setFeedback("");

        PlanEntry mathe11 = new PlanEntry();
        mathe11.setGoal("Schülerinnen und Schüler wissen, ob sie ihre Hausaufgaben richtig gelöst haben und können eventuell aufgetretene Fragen klären");
        mathe11.setId(7);
        mathe11.setLength(5);
        mathe11.setLessonId(3);
        mathe11.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe11.setStart(0);
        mathe11.setTitle("Begrüßung und HA-Vergleich");
        mathe11.setBeschreibung("nach Begrüßung: Kontrolle der Hausaufgaben");

        PlanEntry mathe12 = new PlanEntry();
        mathe12.setGoal("Vorstellung zum mathematischen Begriff entwickeln");
        mathe12.setId(8);
        mathe12.setLength(5);
        mathe12.setLessonId(3);
        mathe12.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe12.setStart(5);
        mathe12.setTitle("Motivierung und Zielorientierung");
        mathe12.setBeschreibung("Hinführung zum Vektorbegriff mit Hilfe einer Fabel");

        PlanEntry mathe13 = new PlanEntry();
        mathe13.setGoal("Definition des Vektorbegriffs");
        mathe13.setId(9);
        mathe13.setLength(10);
        mathe13.setLessonId(3);
        mathe13.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe13.setStart(10);
        mathe13.setTitle("Erarbeitung des Begriffs");
        mathe13.setBeschreibung("Beschreibung des Problems aus der Fabel mit Hilfe von Vektoren");

        PlanEntry mathe14 = new PlanEntry();
        mathe14.setGoal("");
        mathe14.setId(10);
        mathe14.setLength(10);
        mathe14.setLessonId(3);
        mathe14.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch, Einzelarbeit");
        mathe14.setStart(20);
        mathe14.setTitle("Sicherung der Ergebnisse");
        mathe14.setBeschreibung("Erarbeitung eines Tafelbildes\n" +
                "Tafelbild wird von den Schülerinnen und Schülern in ihre Hefter übernommen");

        PlanEntry mathe15 = new PlanEntry();
        mathe15.setGoal("erstes Anwenden des neuen Stoffes, Verinnerlichung der Eigenschaften von Vektoren");
        mathe15.setId(11);
        mathe15.setLength(10);
        mathe15.setLessonId(3);
        mathe15.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe15.setStart(30);
        mathe15.setTitle("Übungsphase");
        mathe15.setBeschreibung("Bearbeitung von Übungsaufgabe an der Tafel");

        PlanEntry mathe16 = new PlanEntry();
        mathe16.setGoal("");
        mathe16.setId(12);
        mathe16.setLength(5);
        mathe16.setLessonId(3);
        mathe16.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch, Einzelarbeit");
        mathe16.setStart(40);
        mathe16.setTitle("Sicherung der Ergebnisse");
        mathe16.setBeschreibung("Lösung der Übung übernehmen\n" +
                "Erarbeitung eines Tafelbildes\n" +
                "Tafelbild wird von den Schülerinnen und Schülern in ihre Hefter übernommen");

        PlanEntry mathe17 = new PlanEntry();
        mathe17.setGoal("");
        mathe17.setId(13);
        mathe17.setLength(5);
        mathe17.setLessonId(3);
        mathe17.setSocialForm("");
        mathe17.setStart(45);
        mathe17.setTitle("Pause");
        mathe17.setBeschreibung("Raumwechsel");

        PlanEntry mathe18 = new PlanEntry();
        mathe18.setGoal("Kennenlernen der Addition von Vektoren");
        mathe18.setId(14);
        mathe18.setLength(10);
        mathe18.setLessonId(3);
        mathe18.setSocialForm("");
        mathe18.setStart(50);
        mathe18.setTitle("Erarbeitung der Operation/Finden des Zusammenhangs");
        mathe18.setBeschreibung("Lehrperson erarbeitet gemeinsam mit den Schülerinnen und Schülern die Regeln für das Rechnen mit Vektoren\n" +
                "Erarbeitung erfolgt anhand des Beispiels aus der Fabel");

        PlanEntry mathe19 = new PlanEntry();
        mathe19.setGoal("Kennenlernen der Addition von Vektoren");
        mathe19.setId(15);
        mathe19.setLength(5);
        mathe19.setLessonId(3);
        mathe19.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch, Einzelarbeit");
        mathe19.setStart(60);
        mathe19.setTitle("Sicherung der Ergebnisse");
        mathe19.setBeschreibung("Erarbeitung eines Tafelbildes\n" +
                "Tafelbild wird von den Schülerinnen und Schülern in ihre Hefter übernommen");

        PlanEntry mathe20 = new PlanEntry();
        mathe20.setGoal("erstes Anwenden des neuen Stoffes, Verinnerlichung der Regeln");
        mathe20.setId(16);
        mathe20.setLength(10);
        mathe20.setLessonId(3);
        mathe20.setSocialForm("Einzelarbeit");
        mathe20.setStart(65);
        mathe20.setTitle("Übungsphase");
        mathe20.setBeschreibung("Bearbeitung von Übungsaufgaben aus dem Lehrbuch");

        PlanEntry mathe21 = new PlanEntry();
        mathe21.setGoal("Absicherung des richtigen Anwendens der Regeln");
        mathe21.setId(17);
        mathe21.setLength(5);
        mathe21.setLessonId(3);
        mathe21.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe21.setStart(75);
        mathe21.setTitle("Kontrolle");
        mathe21.setBeschreibung("Vergleichen der Lösungen von den Aufgaben");

        PlanEntry mathe22 = new PlanEntry();
        mathe22.setGoal("Kennenlernen der Vervielfachung von Vektoren");
        mathe22.setId(18);
        mathe22.setLength(5);
        mathe22.setLessonId(3);
        mathe22.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch");
        mathe22.setStart(80);
        mathe22.setTitle("Erarbeitung der Operation/Finden des Zusammenhangs");
        mathe22.setBeschreibung("Lehrperson erarbeitet gemeinsam mit den Schülerinnen und Schülern die Regeln für das Rechnen mit Vektoren\n" +
                "Erarbeitung erfolgt anhand eines Beispiels");

        PlanEntry mathe23 = new PlanEntry();
        mathe23.setGoal("Kennenlernen der Vervielfachung von Vektoren");
        mathe23.setId(19);
        mathe23.setLength(3);
        mathe23.setLessonId(3);
        mathe23.setSocialForm("Frontalunterricht, Lehrperson leitet Unterrichtsgespräch, Einzelarbeit");
        mathe23.setStart(85);
        mathe23.setTitle("Sicherung der Ergebnisse");
        mathe23.setBeschreibung("Erarbeitung eines Tafelbildes\n" +
                "Tafelbild wird von den Schülerinnen und Schülern in ihre Hefter übernommen");

        PlanEntry mathe24 = new PlanEntry();
        mathe24.setGoal("erstes Anwenden des neuen Stoffes, Verinnerlichung der Regeln");
        mathe24.setId(20);
        mathe24.setLength(5);
        mathe24.setLessonId(3);
        mathe24.setSocialForm("Einzelphase");
        mathe24.setStart(88);
        mathe24.setTitle("Übungsphase");
        mathe24.setBeschreibung("Bearbeitung von Übungsaufgaben aus dem Lehrbuch");

        PlanEntry mathe25 = new PlanEntry();
        mathe25.setGoal("Nachbereitung und Wiederholung des neuen Stoffes");
        mathe25.setId(21);
        mathe25.setLength(2);
        mathe25.setLessonId(3);
        mathe25.setSocialForm("Frontalunterricht, Lehrervortrag");
        mathe25.setStart(93);
        mathe25.setTitle("Hausaufgabe");
        mathe25.setBeschreibung("beenden der begonnenen Übung, kurze Erklärung geben,\n" +
                "Erwartungshorizont klären");

        Sequence sport = new Sequence();
        sport.setGoal("Sport");
        sport.setSubject("Sport");
        sport.setGrade(8);
        sport.setBeschreibung("Laut Lehrplan");
        sport.setId(4);
        sport.setTitle("Turnen & Leichtathletik");

        Lesson sport1 = new Lesson();
        sport1.setTitle("Schulung der konditionellen Fähigkeit Ausdauer");
        sport1.setBeschreibung("");
        sport1.setId(4);
        sport1.setComments("");
        sport1.setGoal("Verbesserung der Ausdauer");
        sport1.setHomeworks("");
        sport1.setOrder(1);
        sport1.setLength(90);
        sport1.setSequenceid(4);
        sport1.setFeedback("");

        PlanEntry sport11 = new PlanEntry();
        sport11.setGoal("Vorbereitung auf die Sportstunde und Motivierung");
        sport11.setId(22);
        sport11.setLength(7);
        sport11.setLessonId(4);
        sport11.setSocialForm("SuS gehen zusammen mit LP zum Sportplatz\n" +
                "geleitetes Unterrichtsgespräch: SuS sitzen auf Bank auf dem Sportplatz\n" +
                "LP steht vor SuS");
        sport11.setStart(0);
        sport11.setTitle("Einstimmung");
        sport11.setBeschreibung("1. Begrüßung\n" +
                "2. Ausblick auf die Stundeninhalte");

        PlanEntry sport12 = new PlanEntry();
        sport12.setGoal("Erwärmung des Bewegungsapparates, Aktivierung des Herz- Kreislauf-Systems, Vorbereitung des aktiven Bewegungsapparates auf die folgende Belastung");
        sport12.setId(23);
        sport12.setLength(15);
        sport12.setLessonId(4);
        sport12.setSocialForm("1. SuS finden sich in 3 Gruppen zusammen LP gibt Laufwege (Muster) vor bei jedem neuen Muster wechselt der führende Läufer (hinterster Läufer läuft nach vorn) 2. SuS stehen in Doppelreihe LP gibt Übung vor gestartet wird auf Pfiff reihenweise");
        sport12.setStart(7);
        sport12.setTitle("Erwärmung");
        sport12.setBeschreibung("1. „Lauf auf vorgegebenen Wegen“ SuS laufen in Gruppen; LP zeigt Karten auf denen Muster zu sehen sind, die die SuS in Gruppen „Nachlaufen“ 2. Lauf-ABC");
        sport12.setComments("1. Karten mit Muster, 2. Pfeife");

        PlanEntry sport13 = new PlanEntry();
        sport13.setGoal("Schulung der konditionellen Fähigkeit Ausdauer Förderung der Sozialkompetenz insbesondere der Teamfähigkeit");
        sport13.setId(24);
        sport13.setLength(45);
        sport13.setLessonId(4);
        sport13.setSocialForm("LP teilt Klasse in 4 Mannschaften sobald der erste Spieler wieder bei der Mannschaft angekommen ist, läuft der nächste los");
        sport13.setStart(22);
        sport13.setTitle("Haupteil");
        sport13.setBeschreibung("„Mensch ärgere dich nicht!“ je ein Spieler aus jedem Team würfelt, setzt die eigene Spielfigur  auf dem Spielbrett entsprechend weiter und läuft dann entsprechend viele Runden");
        sport13.setComments("Spielbrett, 16 Spielfiguren, 1 Würfel, 8 Hütchen");

        PlanEntry sport14 = new PlanEntry();
        sport14.setGoal("Cool-Down des Herz- Kreislauf-Systems, SuS kommen zur Ruhe, Entspannung");
        sport14.setId(25);
        sport14.setLength(11);
        sport14.setLessonId(4);
        sport14.setSocialForm("1. SuS laufen sich eine Runde aus (einzeln) 2. SuS und LP stellen sich im Innenstirnkreis auf LP gibt Übungen vor");
        sport14.setStart(67);
        sport14.setTitle("Abschluss");
        sport14.setBeschreibung("1. Auslaufen, 2. Dehnen");
        sport14.setComments("");

        PlanEntry sport15 = new PlanEntry();
        sport15.setGoal("Feedback, Verabschiedung (Zeit zum Umziehen geben)");
        sport15.setId(26);
        sport15.setLength(12);
        sport15.setLessonId(4);
        sport15.setSocialForm("geleitetes Unterrichtsgespräch: SuS sitzen auf Bank auf dem Sportplatz LP steht vor SuS SuS gehen zusammen mit LP zurück zur Sporthalle");
        sport15.setStart(78);
        sport15.setTitle("Stundenende");
        sport15.setBeschreibung("Kurze Auswertung/Feedback, Verabschiedung");
        sport15.setComments("");

        Lesson sport2 = new Lesson();
        sport2.setTitle("Turnen");
        sport2.setBeschreibung("Anfang Turnen");
        sport2.setId(5);
        sport2.setComments("");
        sport2.setGoal("Weiterentwicklung der turnerischen Fähigkeiten/Fertigkeiten");
        sport2.setHomeworks("");
        sport2.setOrder(1);
        sport2.setLength(90);
        sport2.setSequenceid(4);
        sport2.setFeedback("");

        PlanEntry sport22 = new PlanEntry();
        sport22.setGoal("Vorbereitung auf die Sportstunde und Motivierung");
        sport22.setId(27);
        sport22.setLength(7);
        sport22.setLessonId(5);
        sport22.setSocialForm("geleitetes Unterrichtsgespräch: SuS sitzen auf Bank LP steht vor SuS");
        sport22.setStart(0);
        sport22.setTitle("Einstimmung");
        sport22.setBeschreibung("1. Begrüßung\n" +
                "2. Ausblick auf die Stundeninhalte und kommenden Einheiten");

        PlanEntry sport23 = new PlanEntry();
        sport23.setGoal("Erwärmung des Bewegungsapparates, Aktivierung des Herz- Kreislauf-Systems, Vorbereitung des aktiven Bewegungsapparates auf die folgende Belastung, Stabilisationsübungen zur Kräftigung der Muskulatur für das Turnen");
        sport23.setId(28);
        sport23.setLength(15);
        sport23.setLessonId(5);
        sport23.setSocialForm("1. SuS laufen sich im Kreis ein, LP gibt Bewegungsaufgaben vor\n" +
                "2. SuS stehen im Kreis, LP gibt Übungen vor\n" +
                "3. SuS machen Planks zum Lied „Bring Sally ab“");
        sport23.setStart(7);
        sport23.setTitle("Erwärmung");
        sport23.setBeschreibung("1. Einlaufen\n" +
                "2. Gymnastik\n" +
                "3. Stabilisationstraining");

        PlanEntry sport24 = new PlanEntry();
        sport24.setGoal("Weiterentwicklung der turnerischen Fähigkeiten und Fertigkeiten, Schülerinnen und Schüler können selbstständig Hilfestellungen geben");
        sport24.setId(29);
        sport24.setLength(53);
        sport24.setLessonId(5);
        sport24.setSocialForm("LP erklärt Hilfestellung am Boden und Sprung während ein S vor turnt,\n" +
                "LP erklärt Hilfestellung am Reck und Parallelbarren während ein Schüler vor turnt,\n" +
                "LP erklärt Hilfestellung am Balken und Stufenbarren während eine Schülerin vor turnt\n" +
                "SuS helfen sich gegenseitig, LP geht zu einzelnen S und gibt Tipps, auf Karten an Geräten stehen Zielelemente");
        sport24.setStart(22);
        sport24.setTitle("Haupteil");
        sport24.setBeschreibung("Zielelemente an den einzelnen Geräten besprechen/zeigen und Hilfestellung erklären");
        sport24.setComments("Karten mit Zielelementen");

        PlanEntry sport25 = new PlanEntry();
        sport25.setGoal("Schulung der Kooperation, Cool-Down des Herz- Kreislauf-Systems");
        sport25.setId(30);
        sport25.setLength(8);
        sport25.setLessonId(5);
        sport25.setSocialForm("SuS bewegen sich frei in der Halle, auf Pfiff schließen sich je eine vorgegebene Anzahl an SuS zusammen (dabei gilt es verschiedene Anforderungen zu erfüllen)");
        sport25.setStart(75);
        sport25.setTitle("Abschluss");
        sport25.setBeschreibung("Atomspiel");
        sport25.setComments("Pfeife");

        PlanEntry sport26 = new PlanEntry();
        sport26.setGoal("Feedback, Verabschiedung (Zeit zum Umziehen geben)");
        sport26.setId(31);
        sport26.setLength(7);
        sport26.setLessonId(5);
        sport26.setSocialForm("geleitetes Unterrichtsgespräch: SuS sitzen auf Bank LP steht vor SuS");
        sport26.setStart(83);
        sport26.setTitle("Stundenende");
        sport26.setBeschreibung("Kurze Auswertung/Feedback, Verabschiedung");
        sport26.setComments("");

        DBHandler db = new DBHandler(getApplicationContext());
        db.createSequence(sport);
        db.createLesson(sport1);
        db.createLesson(sport2);
        db.createPlanentry(sport11);
        db.createPlanentry(sport12);
        db.createPlanentry(sport13);
        db.createPlanentry(sport14);
        db.createPlanentry(sport15);
        db.createPlanentry(sport22);
        db.createPlanentry(sport24);
        db.createPlanentry(sport23);
        db.createPlanentry(sport25);
        db.createPlanentry(sport26);

        db.createSequence(mathe);
        db.createLesson(mathe1);
        db.createPlanentry(mathe11);
        db.createPlanentry(mathe12);
        db.createPlanentry(mathe13);
        db.createPlanentry(mathe14);
        db.createPlanentry(mathe15);
        db.createPlanentry(mathe16);
        db.createPlanentry(mathe17);
        db.createPlanentry(mathe18);
        db.createPlanentry(mathe19);
        db.createPlanentry(mathe20);
        db.createPlanentry(mathe21);
        db.createPlanentry(mathe22);
        db.createPlanentry(mathe23);
        db.createPlanentry(mathe24);
        db.createPlanentry(mathe25);

        db.createLesson(lesson1);
        db.createLesson(lesson2);
        db.createSequence(sequence1);
        db.createSequence(sequence2);
        db.createPlanentry(planEntry1);
        db.createPlanentry(planEntry2);
        db.createPlanentry(planEntry8);
        db.createPlanentry(planEntry9);
        db.createResource(resource1);
        db.createResource(resource2);
        db.createResource(resource3);
        db.createPlanentry(planEntry5);
        db.createPlanentry(planEntry6);
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
}