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

package core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = DBHandler.class.getName();
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "munter";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_LESSON = "CREATE TABLE lesson (lessonid INTEGER PRIMARY KEY, sequenceid INTEGER, _order INTEGER, title TEXT, length INTEGER, goal TEXT, homeworks TEXT, comments TEXT, markunfinished TEXT, feedback TEXT, beschreibung Text)";

    // Tag table create statement
    private static final String CREATE_TABLE_SEQUENCE = "CREATE TABLE sequence (sequenceid INTEGER PRIMARY KEY, userid INTEGER, title TEXT, subject TEXT, grade INTEGER, preknowledge TEXT, goal TEXT, standard INTEGER, comments TEXT, markunfinished INTEGER, beschreibung Text)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_PLANENTRY = "CREATE TABLE planentry (planentryid INTEGER PRIMARY KEY, lessonid INTEGER, start INTEGER, length INTEGER, title TEXT, goal TEXT, socialform TEXT, comments TEXT, reserve TEXT, beschreibung TEXT, reallength INTEGER)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_RESOURCE = "CREATE TABLE resource (resourceid INTEGER PRIMARY KEY, title TEXT,content BLOB, filename TEXT, type TEXT, thumbnail BLOB, lessonid INTEGER, planentryid INTEGER)";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_LESSON);
        db.execSQL(CREATE_TABLE_SEQUENCE);
        db.execSQL(CREATE_TABLE_PLANENTRY);
        db.execSQL(CREATE_TABLE_RESOURCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS lesson");
        db.execSQL("DROP TABLE IF EXISTS sequence");
        db.execSQL("DROP TABLE IF EXISTS planentry");
        db.execSQL("DROP TABLE IF EXISTS resource");

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public long createLesson(Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("lessonid", lesson.getId());
        values.put("title", lesson.getTitle());
        values.put("sequenceid", lesson.getSequenceid());
        values.put("_order", lesson.getOrder());
        values.put("length", lesson.getLength());
        values.put("goal", lesson.getGoal());
        values.put("homeworks", lesson.getHomeworks());
        values.put("comments", lesson.getComments());
        values.put("beschreibung",lesson.getBeschreibung());
        values.put("feedback", lesson.getFeedback());

        // insert row
        long key_id = db.insert("lesson", null, values);
        return key_id;
    }

    public Lesson[] getLessons(int sequence) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("lesson", new String[]{"lessonid", "title","sequenceid","_order","length","goal","homeworks","comments","feedback","beschreibung"}, "sequenceid like ?",new String[]{sequence+"%"}, null, null, null);
        Lesson lesson[] = new Lesson[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            int sequenceid = cursor.getInt(cursor.getColumnIndexOrThrow("sequenceid"));
            int _order = cursor.getInt(cursor.getColumnIndexOrThrow("_order"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String homeworks = cursor.getString(cursor.getColumnIndexOrThrow("homeworks"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            String feedback = cursor.getString(cursor.getColumnIndexOrThrow("feedback"));
            String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"));

            lesson[i] = new Lesson();
            lesson[i].setTitle(title);
            lesson[i].setId(id);
            lesson[i].setSequenceid(sequenceid);
            lesson[i].setLength(length);
            lesson[i].setOrder(_order);
            lesson[i].setHomeworks(homeworks);
            lesson[i].setGoal(goal);
            lesson[i].setComments(comments);
            lesson[i].setFeedback(feedback);
            lesson[i].setBeschreibung(beschreibung);
        }

        return lesson;
    }

    public Lesson getLesson(int lessonid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("lesson", new String[]{"lessonid", "title","sequenceid","_order","length","goal","homeworks","comments","feedback","beschreibung"}, "lessonid like ?",new String[]{lessonid+"%"}, null, null, null);
        Lesson lesson;
        lesson = new Lesson();
        int id;
        int sequenceid;
        int _order;
        int length;
        String goal,homeworks,comments,feedback,beschreibung,title;

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            id = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            sequenceid = cursor.getInt(cursor.getColumnIndexOrThrow("sequenceid"));
            _order = cursor.getInt(cursor.getColumnIndexOrThrow("_order"));
            length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            homeworks = cursor.getString(cursor.getColumnIndexOrThrow("homeworks"));
            comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            feedback = cursor.getString(cursor.getColumnIndexOrThrow("feedback"));
            beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"));
            lesson.setTitle(title);
            lesson.setId(id);
            lesson.setSequenceid(sequenceid);
            lesson.setLength(length);
            lesson.setOrder(_order);
            lesson.setHomeworks(homeworks);
            lesson.setGoal(goal);
            lesson.setComments(comments);
            lesson.setFeedback(feedback);
            lesson.setBeschreibung(beschreibung);
        }
        return lesson;
    }

    public long updateLesson(Lesson lesson, int lessonID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("lessonid", lesson.getId());
        values.put("title", lesson.getTitle());
        values.put("sequenceid", lesson.getSequenceid());
        values.put("_order", lesson.getOrder());
        values.put("length", lesson.getLength());
        values.put("goal", lesson.getGoal());
        values.put("homeworks", lesson.getHomeworks());
        values.put("comments", lesson.getComments());
        values.put("feedback", lesson.getFeedback());
        values.put("beschreibung", lesson.getBeschreibung());

        // update row
        long key_id = db.update("lesson",values, "lessonid="+lessonID,null);
        return key_id;
    }

    public long createSequence(Sequence sequence) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sequenceid", sequence.getId());
        values.put("title", sequence.getTitle());
        values.put("subject",sequence.getSubject());
        values.put("comments",sequence.getComments());
        values.put("goal",sequence.getGoal());
        values.put("grade",sequence.getGrade());
        values.put("preknowledge",sequence.getPreknowledge());
        values.put("beschreibung",sequence.getBeschreibung());


        // insert row
        long key_id = db.insert("sequence", null, values);
        return key_id;
    }

    public Sequence[] getSequence() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query("sequence", new String[]{"sequenceid", "title", "subject", "grade","comments","goal","preknowledge","beschreibung"}, null, null, null, null, null);

        Sequence[] sequence = new Sequence[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("sequenceid"));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow("subject"));
            int grade = cursor.getInt(cursor.getColumnIndexOrThrow("grade"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String preknowledge = cursor.getString(cursor.getColumnIndexOrThrow("preknowledge"));
            String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"));

            sequence[i] = new Sequence();
            sequence[i].setTitle(title);
            sequence[i].setId(id);
            sequence[i].setSubject(subject);
            sequence[i].setGrade(grade);
            sequence[i].setGoal(goal);
            sequence[i].setComments(comments);
            sequence[i].setPreknowledge(preknowledge);
            sequence[i].setBeschreibung(beschreibung);

        }

        return sequence;
    }

    public long createPlanentry(PlanEntry planEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("planentryid", planEntry.getId());
        values.put("title", planEntry.getTitle());
        values.put("comments", planEntry.getComments());
        values.put("goal", planEntry.getGoal());
        values.put("length", planEntry.getLength());
        values.put("lessonid", planEntry.getLessonId());
        values.put("socialform", planEntry.getSocialForm());
        values.put("start", planEntry.getStart());
        values.put("reserve", planEntry.getReserve());
        values.put("beschreibung", planEntry.getBeschreibung());
        values.put("reallength", planEntry.getReallength());



        // insert row
        long key_id = db.insert("planentry", null, values);
        return key_id;
    }

    public long updatePlanentry(PlanEntry planEntry, int planentryID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("planentryid", planEntry.getId());
        values.put("title", planEntry.getTitle());
        values.put("comments", planEntry.getComments());
        values.put("goal", planEntry.getGoal());
        values.put("length", planEntry.getLength());
        values.put("lessonid", planEntry.getLessonId());
        values.put("socialform", planEntry.getSocialForm());
        values.put("start", planEntry.getStart());
        values.put("reserve", planEntry.getReserve());
        values.put("beschreibung", planEntry.getBeschreibung());
        values.put("reallength", planEntry.getReallength());

        // update row
        long key_id = db.update("planentry",values, "planentryid="+planentryID,null);
        return key_id;
    }

    public PlanEntry[] getPlanentry(String lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("planentry", new String[]{"planentryid", "title","comments","goal","length","lessonid","socialform","start","reserve","beschreibung","reallength"}, "lessonid like ?",new String[]{lesson+"%"},  null, null, null);
        PlanEntry planEntry[] = new PlanEntry[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("planentryid"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            int start = cursor.getInt(cursor.getColumnIndexOrThrow("start"));
            int lessonid = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String socialform = cursor.getString(cursor.getColumnIndexOrThrow("socialform"));
            String reserve = cursor.getString(cursor.getColumnIndexOrThrow("reserve"));
            String beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"));
            int reallength = cursor.getInt(cursor.getColumnIndexOrThrow("reallength"));

            planEntry[i] = new PlanEntry();
            planEntry[i].setTitle(title);
            planEntry[i].setId(id);
            planEntry[i].setStart(start);
            planEntry[i].setSocialForm(socialform);
            planEntry[i].setLessonId(lessonid);
            planEntry[i].setGoal(goal);
            planEntry[i].setComments(comments);
            planEntry[i].setLength(length);
            planEntry[i].setReserve(reserve);
            planEntry[i].setBeschreibung(beschreibung);
            planEntry[i].setReallength(reallength);
        }

        return planEntry;
    }

    public long createResource(Resource resource) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("resourceid", resource.getId());
        values.put("filename", resource.getFilename());
        values.put("content", resource.getTextContent());
        values.put("title", resource.getTitle());
        values.put("type", resource.getType());
        values.put("lessonid", resource.getLessonid());
        values.put("planentryid", resource.getPlanentryid());

        // insert row
        long key_id = db.insert("resource", null, values);
        return key_id;
    }

    public Resource[] getResource(int lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = db.query("resource", new String[]{"resourceid", "filename","content","title","type","lessonid","planentryid"}, "lessonid like ?",new String[]{lesson+"%"}, null, null, null);

        int length = cursor.getCount();
        Resource resources[] = new Resource[length];

        for (int i = 0; i < length; i++) {
            cursor.moveToNext();
            String filename = cursor.getString(cursor.getColumnIndexOrThrow("filename"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("resourceid"));
            int lessonid = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            int planentryid = cursor.getInt(cursor.getColumnIndexOrThrow("planentryid"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

            resources[i] = new Resource();
            resources[i].setTitle(title);
            resources[i].setId(id);
            resources[i].setType(type);
            resources[i].setTextContent(content);
            resources[i].setFilename(filename);
            resources[i].setLessonid(lessonid);
            resources[i].setPlanentryid(planentryid);
        }
        return resources;
    }
}
