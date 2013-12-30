package SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comment createCommentStrengths(String comment) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(SQLiteHelper.TABLE_COMMENTS_STRENGTHS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_STRENGTHS,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public Comment createCommentWeaknesses(String comment) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(SQLiteHelper.TABLE_COMMENTS_WEAKNESSES, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_WEAKNESSES,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }


    public Comment createCommentOpportunities(String comment) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(SQLiteHelper.TABLE_COMMENTS_OPPORTUNITIES, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_OPPORTUNITIES,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }


    public Comment createCommentThreats(String comment) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_COMMENT, comment);
        long insertId = database.insert(SQLiteHelper.TABLE_COMMENTS_THREATS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_THREATS,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }





    public void deleteCommentStrengths(String comment) {
        String where = SQLiteHelper.COLUMN_COMMENT + " = ? ";
        String[] whereArgs = new String[] {comment};
        database.delete(SQLiteHelper.TABLE_COMMENTS_STRENGTHS, where, whereArgs);
    }

    public void deleteCommentWeaknesses(String comment) {
        String where = SQLiteHelper.COLUMN_COMMENT + " = ? ";
        String[] whereArgs = new String[] {comment};
        database.delete(SQLiteHelper.TABLE_COMMENTS_WEAKNESSES, where, whereArgs);

    }  public void deleteCommentOpportunities(String comment) {
        String where = SQLiteHelper.COLUMN_COMMENT + " = ? ";
        String[] whereArgs = new String[] {comment};
        database.delete(SQLiteHelper.TABLE_COMMENTS_OPPORTUNITIES, where, whereArgs);

    }  public void deleteCommentThreats(String comment) {
        String where = SQLiteHelper.COLUMN_COMMENT + " = ? ";
        String[] whereArgs = new String[] {comment};
        database.delete(SQLiteHelper.TABLE_COMMENTS_THREATS, where, whereArgs);
    }



    public void deleteAllCommentsStrengths() {
        database.delete(SQLiteHelper.TABLE_COMMENTS_STRENGTHS, null, null);
        database.delete(SQLiteHelper.TABLE_COMMENTS_WEAKNESSES, null, null);
        database.delete(SQLiteHelper.TABLE_COMMENTS_OPPORTUNITIES, null, null);
        database.delete(SQLiteHelper.TABLE_COMMENTS_THREATS, null, null);


    }

    public List<Comment> getAllCommentsStrengths() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_STRENGTHS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Comment> getAllCommentsWeaknesses() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_WEAKNESSES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Comment> getAllCommentsOpportunities() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_OPPORTUNITIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    public List<Comment> getAllCommentsThreats() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMMENTS_THREATS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
} 