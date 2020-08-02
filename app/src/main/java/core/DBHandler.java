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
    private static final String CREATE_TABLE_RESOURCE = "CREATE TABLE resource (resourceid INTEGER PRIMARY KEY, title TEXT, content BLOB, filename TEXT, type TEXT, thumbnail BLOB)";

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

    public Lesson getLesson() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Lesson lesson = new Lesson();

        Cursor cursor = db.query("lesson", new String[]{"lessonid", "title"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("lessonid"));

        lesson.setTitle(title);
        lesson.setId(id);

        return lesson;
    }

    public long createSequence(Sequence sequence) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("sequenceid", sequence.getId());
        values.put("title", sequence.getTitle());

        // insert row
        long key_id = db.insert("sequence", null, values);
        return key_id;
    }

    public Sequence[] getSequence() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query("sequence", new String[]{"sequenceid", "title", "subject", "grade","comments","goal","preknowledge"}, null, null, null, null, null);
        int length = cursor.getCount();
        Sequence[] sequence = new Sequence[length];

        for (int i = 0; i < length; i++ ) {
            cursor.moveToNext();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("sequenceid"));

            sequence[i].setTitle(title);
            sequence[i].setId(id);
        }
        return sequence;
    }

    public long createPlanentry(PlanEntry planEntry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("planentryid", planEntry.getId());
        values.put("title", planEntry.getTitle());

        // insert row
        long key_id = db.insert("planentry", null, values);
        return key_id;
    }

    public PlanEntry getPlanentry() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        PlanEntry planEntry = new PlanEntry();

        Cursor cursor = db.query("planentry", new String[]{"planentryid", "title"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("planentryid"));

        planEntry.setTitle(title);
        planEntry.setId(id);

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
        values.put("title", resource.getFilename());

        // insert row
        long key_id = db.insert("resource", null, values);
        return key_id;
    }

    public Resource getResource() {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        Resource resource = new Resource();

        Cursor cursor = db.query("resource", new String[]{"resourceid", "filename"}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        String title = cursor.getString(cursor.getColumnIndexOrThrow("filename"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("resourceid"));

        resource.setTitle(title);
        resource.setId(id);

        return resource;
    }
}