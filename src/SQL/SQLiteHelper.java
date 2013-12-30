package SQL; /**
 * Created by dominicnunes on 2013-10-17.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS_STRENGTHS = "commentsS";
    public static final String TABLE_COMMENTS_WEAKNESSES = "commentsW";
    public static final String TABLE_COMMENTS_OPPORTUNITIES = "commentsO";
    public static final String TABLE_COMMENTS_THREATS = "commentsT";

    private static String nameId;
    public static final String COLUMN_ID = "_id";

    public static String getNameId() {
        return nameId;
    }

    public final void setNameId(String nameId) {
        this.nameId = nameId;
    }

    public static final String COLUMN_COMMENT = "comment";



    private static final String DATABASE_NAME = nameId + ".db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_STRENGTHS = "create table "
            + TABLE_COMMENTS_STRENGTHS + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_COMMENT
            + " text not null);";

    private static final String DATABASE_CREATE_WEAKNESSES = "create table "
            + TABLE_COMMENTS_WEAKNESSES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    private static final String DATABASE_CREATE_OPPORTUNITIES = "create table "
            + TABLE_COMMENTS_OPPORTUNITIES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    private static final String DATABASE_CREATE_THREATS = "create table "
            + TABLE_COMMENTS_THREATS + "(" + COLUMN_ID
            + " integer primary key, " + COLUMN_COMMENT
            + " text not null);";


    public SQLiteHelper(Context context) {
        super(context, nameId, null, DATABASE_VERSION);
        ;
    }





    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE_STRENGTHS);
        database.execSQL(DATABASE_CREATE_WEAKNESSES);
        database.execSQL(DATABASE_CREATE_OPPORTUNITIES);
        database.execSQL(DATABASE_CREATE_THREATS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS_STRENGTHS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS_WEAKNESSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS_OPPORTUNITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS_THREATS);

        onCreate(db);
    }

}

