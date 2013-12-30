package com.discourse;

import SQL.*;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


/**
 * Created by dominicnunes on 2013-10-23.
 */
public class Outline extends Activity {

    Comment comment = null;


    // Parent view for all rows and the add button.
    private String passedName;
    private LinearLayout mContainerViewStrengths;
    private LinearLayout mContainerViewWeaknesses;
    private LinearLayout mContainerViewOpportunities;
    private LinearLayout mContainerViewThreats;

    // The "Add new" button
    private ImageButton mAddButtonStrengths;
    private ImageButton mAddButtonWeaknesses;
    private ImageButton mAddButtonOpportunities;
    private ImageButton mAddButtonThreats;

    // There always should be only one empty row, other empty rows will
    // be removed.
    private View mExclusiveEmptyView;

    private CommentsDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainerViewStrengths = (LinearLayout) findViewById(R.id.parentViewStrengths);
        mContainerViewWeaknesses = (LinearLayout) findViewById(R.id.parentViewWeaknessess);
        mContainerViewOpportunities = (LinearLayout) findViewById(R.id.parentViewOpportunities);
        mContainerViewThreats = (LinearLayout) findViewById(R.id.parentViewThreats);

        mAddButtonStrengths = (ImageButton) findViewById(R.id.btnAddNewItemStrengths);
        mAddButtonWeaknesses = (ImageButton) findViewById(R.id.btnAddNewItemWeaknesses);
        mAddButtonOpportunities = (ImageButton) findViewById(R.id.btnAddNewItemOpportunities);
        mAddButtonThreats = (ImageButton) findViewById(R.id.btnAddNewItemThreats);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.text_entry, null);
//text_entry is an Layout XML file containing two text field to display in alert dialog
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.EditText1);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditText2);
        input1.setText("DefaultValue", TextView.BufferType.EDITABLE);
        input2.setText("DefaultValue", TextView.BufferType.EDITABLE);



        datasource = new CommentsDataSource(this);

        datasource.open();

        datasource.deleteAllCommentsStrengths();
        datasource.deleteAllCommentsStrengths();
        datasource.deleteAllCommentsStrengths();
        datasource.deleteAllCommentsStrengths();

        List<Comment> valuesStrengths = datasource.getAllCommentsStrengths();
        List<Comment> valuesWeaknesses = datasource.getAllCommentsWeaknesses();
        List<Comment> valuesOpportunities = datasource.getAllCommentsOpportunities();
        List<Comment> valuesThreats = datasource.getAllCommentsThreats();



        for (int i = 0; i < valuesStrengths.size(); i++){
            inflateEditRowStrengths(valuesStrengths.get(i).toString());

        }

        for (int i = 0; i < valuesWeaknesses.size(); i++){
            inflateEditRowWeaknesses(valuesWeaknesses.get(i).toString());

        }

        for (int i = 0; i < valuesOpportunities.size(); i++){
            inflateEditRowOpportunities(valuesOpportunities.get(i).toString());

        }

        for (int i = 0; i < valuesThreats.size(); i++){
            inflateEditRowThreats(valuesThreats.get(i).toString());

        }

        alert.setTitle("Enter the Text:")
                .setView(textEntryView)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i("AlertDialog", "TextEntry 1 Entered " + input1.getText().toString());
                                Log.i("AlertDialog", "TextEntry 2 Entered " + input2.getText().toString());
                    /* User clicked OK so do some stuff */
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        });
        alert.show();

    }

    public String getPassedName() {
        return passedName;
    }

    public void setPassedName(String passedName) {
        this.passedName = passedName;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////


    // onClick handler for the "Add new" button;
    public void onAddNewClickedStrengths(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRowStrengths(null);
        v.setVisibility(View.GONE);
    }





    // Helper for inflating a row
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void inflateEditRowStrengths(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.button);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);


        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && !editText.getText().toString().isEmpty()) {
                    // Perform action on key press


                    inflateEditRowStrengths(null);

                    return true;
                }
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toFind = editText.getText().toString();

                datasource.deleteCommentStrengths(toFind);
                mContainerViewStrengths.removeView(rowView);



            }
        });




        if (name != null && !name.isEmpty()) {
            editText.setText(name);
        } else {
            mExclusiveEmptyView = rowView;
            deleteButton.setVisibility(View.INVISIBLE);
        }



        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    comment = datasource.createCommentStrengths(editText.getText().toString());

                }

            }
        });
        // A TextWatcher to control the visibility of the "Add new" button and
        // handle the exclusive empty view.
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    mAddButtonStrengths.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.INVISIBLE);

                    if (mExclusiveEmptyView != null
                            && mExclusiveEmptyView != rowView) {
                        mContainerViewStrengths.removeView(mExclusiveEmptyView);
                    }
                    mExclusiveEmptyView = rowView;
                } else {

                    if (mExclusiveEmptyView == rowView) {
                        mExclusiveEmptyView = null;
                    }

                    mAddButtonStrengths.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        // Inflate at the end of all rows but before the "Add new" button

        mContainerViewStrengths.addView(rowView, mContainerViewStrengths.getChildCount() - 1);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////


    // onClick handler for the "Add new" button;
    public void onAddNewClickedWeaknesses(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRowWeaknesses(null);
        v.setVisibility(View.GONE);
    }





    // Helper for inflating a row
    private void inflateEditRowWeaknesses(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.button);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);


        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && !editText.getText().toString().isEmpty()) {
                    // Perform action on key press


                    inflateEditRowWeaknesses(null);

                    return true;
                }
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toFind = editText.getText().toString();

                datasource.deleteCommentWeaknesses(toFind);
                mContainerViewWeaknesses.removeView(rowView);



            }
        });




        if (name != null && !name.isEmpty()) {
            editText.setText(name);
        } else {
            mExclusiveEmptyView = rowView;
            deleteButton.setVisibility(View.INVISIBLE);
        }



        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    comment = datasource.createCommentWeaknesses(editText.getText().toString());

                }

            }
        });
        // A TextWatcher to control the visibility of the "Add new" button and
        // handle the exclusive empty view.
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    mAddButtonWeaknesses.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.INVISIBLE);

                    if (mExclusiveEmptyView != null
                            && mExclusiveEmptyView != rowView) {
                        mContainerViewWeaknesses.removeView(mExclusiveEmptyView);
                    }
                    mExclusiveEmptyView = rowView;
                } else {

                    if (mExclusiveEmptyView == rowView) {
                        mExclusiveEmptyView = null;
                    }

                    mAddButtonWeaknesses.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        // Inflate at the end of all rows but before the "Add new" button

        mContainerViewWeaknesses.addView(rowView, mContainerViewWeaknesses.getChildCount() - 1);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    // onClick handler for the "Add new" button;
    public void onAddNewClickedOpportunities(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRowOpportunities(null);
        v.setVisibility(View.GONE);
    }





    // Helper for inflating a row
    private void inflateEditRowOpportunities(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.button);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);


        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && !editText.getText().toString().isEmpty()) {
                    // Perform action on key press


                    inflateEditRowOpportunities(null);

                    return true;
                }
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toFind = editText.getText().toString();

                datasource.deleteCommentOpportunities(toFind);
                mContainerViewOpportunities.removeView(rowView);



            }
        });




        if (name != null && !name.isEmpty()) {
            editText.setText(name);
        } else {
            mExclusiveEmptyView = rowView;
            deleteButton.setVisibility(View.INVISIBLE);
        }



        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    comment = datasource.createCommentOpportunities(editText.getText().toString());

                }

            }
        });
        // A TextWatcher to control the visibility of the "Add new" button and
        // handle the exclusive empty view.
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    mAddButtonOpportunities.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.INVISIBLE);

                    if (mExclusiveEmptyView != null
                            && mExclusiveEmptyView != rowView) {
                        mContainerViewOpportunities.removeView(mExclusiveEmptyView);
                    }
                    mExclusiveEmptyView = rowView;
                } else {

                    if (mExclusiveEmptyView == rowView) {
                        mExclusiveEmptyView = null;
                    }

                    mAddButtonOpportunities.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        // Inflate at the end of all rows but before the "Add new" button

        mContainerViewOpportunities.addView(rowView, mContainerViewOpportunities.getChildCount() - 1);
    }



    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    // onClick handler for the "Add new" button;
    public void onAddNewClickedThreats(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRowThreats(null);
        v.setVisibility(View.GONE);
    }





    // Helper for inflating a row
    private void inflateEditRowThreats(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_row, null);
        final ImageButton deleteButton = (ImageButton) rowView
                .findViewById(R.id.button);
        final EditText editText = (EditText) rowView
                .findViewById(R.id.editText);


        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER) && !editText.getText().toString().isEmpty()) {
                    // Perform action on key press


                    inflateEditRowThreats(null);

                    return true;
                }
                return false;
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toFind = editText.getText().toString();

                datasource.deleteCommentThreats(toFind);
                mContainerViewThreats.removeView(rowView);



            }
        });




        if (name != null && !name.isEmpty()) {
            editText.setText(name);
        } else {
            mExclusiveEmptyView = rowView;
            deleteButton.setVisibility(View.INVISIBLE);
        }



        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                    comment = datasource.createCommentThreats(editText.getText().toString());

                }

            }
        });
        // A TextWatcher to control the visibility of the "Add new" button and
        // handle the exclusive empty view.
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    mAddButtonThreats.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.INVISIBLE);

                    if (mExclusiveEmptyView != null
                            && mExclusiveEmptyView != rowView) {
                        mContainerViewThreats.removeView(mExclusiveEmptyView);
                    }
                    mExclusiveEmptyView = rowView;
                } else {

                    if (mExclusiveEmptyView == rowView) {
                        mExclusiveEmptyView = null;
                    }

                    mAddButtonThreats.setVisibility(View.VISIBLE);
                    deleteButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        // Inflate at the end of all rows but before the "Add new" button

        mContainerViewThreats.addView(rowView, mContainerViewThreats.getChildCount() - 1);
    }

}



///////////////////////////////////////////////////////////////////////////////////////////////


