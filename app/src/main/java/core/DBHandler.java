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
    private static final String CREATE_TABLE_LESSON = "CREATE TABLE lesson (lessonid INTEGER PRIMARY KEY, sequenceid INTEGER, _order INTEGER, title TEXT, length INTEGER, goal TEXT, homeworks TEXT, comments TEXT, markunfinished TEXT)";

    // Tag table create statement
    private static final String CREATE_TABLE_SEQUENCE = "CREATE TABLE sequence (sequenceid INTEGER PRIMARY KEY, userid INTEGER, title TEXT, subject TEXT, grade INTEGER, preknowledge TEXT, goal TEXT, standard INTEGER, comments TEXT, markunfinished INTEGER)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_PLANENTRY = "CREATE TABLE planentry (planentryid INTEGER PRIMARY KEY, lessonid INTEGER, track INTEGER, start INTEGER, length INTEGER, title TEXT, steps TEXT, goal TEXT, socialform TEXT, parentplanentry INTEGER, comments TEXT, markunfinished INTEGER, color TEXT)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_CUSTOMFIELD = "CREATE TABLE customfield (customfieldid INTEGER PRIMARY KEY, name TEXT, binarycontent BLOB, sequenceid INTEGER, lessonid INTEGER, planentryid INTEGER)";

    // todo_tag table create statement
    private static final String CREATE_TABLE_RESOURCE = "CREATE TABLE resource (resourceid INTEGER PRIMARY KEY, title TEXT,content BLOB, filename TEXT, type TEXT, thumbnail BLOB)";

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
        db.execSQL(CREATE_TABLE_CUSTOMFIELD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS lesson");
        db.execSQL("DROP TABLE IF EXISTS sequence");
        db.execSQL("DROP TABLE IF EXISTS planentry");
        db.execSQL("DROP TABLE IF EXISTS resource");
        db.execSQL("DROP TABLE IF EXISTS customfield");

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

        // insert row
        long key_id = db.insert("lesson", null, values);
        return key_id;
    }

    public Lesson[] getLessons(int sequence) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("lesson", new String[]{"lessonid", "title","sequenceid","_order","length","goal","homeworks","comments"}, "sequenceid like ?",new String[]{sequence+"%"}, null, null, null);
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

            lesson[i] = new Lesson();
            lesson[i].setTitle(title);
            lesson[i].setId(id);
            lesson[i].setSequenceid(sequenceid);
            lesson[i].setLength(length);
            lesson[i].setOrder(_order);
            lesson[i].setHomeworks(homeworks);
            lesson[i].setGoal(goal);
            lesson[i].setComments(comments);
        }

        return lesson;
    }

    public Lesson getLesson(int lessonid) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("lesson", new String[]{"lessonid", "title","sequenceid","_order","length","goal","homeworks","comments"}, "lessonid like ?",new String[]{lessonid+"%"}, null, null, null);
        Lesson lesson;

        cursor.moveToFirst();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            int sequenceid = cursor.getInt(cursor.getColumnIndexOrThrow("sequenceid"));
            int _order = cursor.getInt(cursor.getColumnIndexOrThrow("_order"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String homeworks = cursor.getString(cursor.getColumnIndexOrThrow("homeworks"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));

            lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setId(id);
            lesson.setSequenceid(sequenceid);
            lesson.setLength(length);
            lesson.setOrder(_order);
            lesson.setHomeworks(homeworks);
            lesson.setGoal(goal);
            lesson.setComments(comments);

        return lesson;
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



        // insert row
        long key_id = db.insert("sequence", null, values);
        return key_id;
    }

    public Sequence[] getSequence() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query("sequence", new String[]{"sequenceid", "title", "subject", "grade","comments","goal","preknowledge"}, null, null, null, null, null);

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

            sequence[i] = new Sequence();
            sequence[i].setTitle(title);
            sequence[i].setId(id);
            sequence[i].setSubject(subject);
            sequence[i].setGrade(grade);
            sequence[i].setGoal(goal);
            sequence[i].setComments(comments);
            sequence[i].setPreknowledge(preknowledge);

        }

        return sequence;
    }

    public long createPlanentry(PlanEntry planEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("planentryid", planEntry.getId());
        values.put("color", planEntry.getColor());
        values.put("title", planEntry.getTitle());
        values.put("comments", planEntry.getComments());
        values.put("goal", planEntry.getGoal());
        values.put("length", planEntry.getLength());
        values.put("lessonid", planEntry.getLessonId());
        values.put("socialform", planEntry.getSocialForm());
        values.put("start", planEntry.getStart());
        values.put("steps", planEntry.getSteps());
        values.put("track", planEntry.getTrack());


        // insert row
        long key_id = db.insert("planentry", null, values);
        return key_id;
    }

    public PlanEntry[] getPlanentry(String lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("planentry", new String[]{"planentryid", "title","color","comments","goal","length","lessonid","socialform","start","steps","track"}, "lessonid like ?",new String[]{lesson+"%"},  null, null, null);
        PlanEntry planEntry[] = new PlanEntry[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("planentryid"));
            int length = cursor.getInt(cursor.getColumnIndexOrThrow("length"));
            int start = cursor.getInt(cursor.getColumnIndexOrThrow("start"));
            int track = cursor.getInt(cursor.getColumnIndexOrThrow("track"));
            int lessonid = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));
            String color = cursor.getString(cursor.getColumnIndexOrThrow("color"));
            String comments = cursor.getString(cursor.getColumnIndexOrThrow("comments"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String socialform = cursor.getString(cursor.getColumnIndexOrThrow("socialform"));
            String steps = cursor.getString(cursor.getColumnIndexOrThrow("steps"));

            planEntry[i] = new PlanEntry();
            planEntry[i].setTitle(title);
            planEntry[i].setId(id);
            planEntry[i].setSteps(steps);
            planEntry[i].setTrack(track);
            planEntry[i].setStart(start);
            planEntry[i].setSocialForm(socialform);
            planEntry[i].setLessonId(lessonid);
            planEntry[i].setGoal(goal);
            planEntry[i].setColor(color);
            planEntry[i].setComments(comments);
            planEntry[i].setLength(length);
        }

        return planEntry;
    }

    public long createCustomfields(CustomField customField) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("customfieldid", customField.getId());
        values.put("name", customField.getName());


        // insert row
        long key_id = db.insert("customfield", null, values);
        return key_id;
    }

    public CustomField getCustomfileds() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        CustomField customField = new CustomField();

        Cursor cursor = db.query("customfield", new String[]{"customfieldid", "name"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("customfieldid"));

        customField.setName(name);
        customField.setId(id);

        return customField;
    }

    public long createResource(Resource resource) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("resourceid", resource.getId());
        values.put("filename", resource.getFilename());
        values.put("content", resource.getTextContent());
        values.put("title", resource.getTitle());
        values.put("type", resource.getType().toString());

        // insert row
        long key_id = db.insert("resource", null, values);
        return key_id;
    }

    public Resource[] getResource() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();


        Cursor cursor = db.query("resource", new String[]{"resourceid", "filename","content","title","type"}, null, null, null, null, null);

        int length = cursor.getCount();
        Resource resources[] = new Resource[length];

        for (int i = 0; i < length; i++) {
            cursor.moveToNext();
            String filename = cursor.getString(cursor.getColumnIndexOrThrow("filename"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("resourceid"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));

            resources[i] = new Resource();
            resources[i].setTitle(title);
            resources[i].setId(id);
            //resources.setType(ResourceType.valueOf(type));
            resources[i].setTextContent(content);
            resources[i].setFilename(filename);

        }

        return resources;
    }
}