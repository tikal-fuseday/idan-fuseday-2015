package com.willinger.shmulik.dos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MyActivity extends Activity
{
    private AttackThreadsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void attack(View view)
    {
        // Create an adapter to bind the items with the view
        mAdapter = new AttackThreadsAdapter(this, R.layout.row_list_to_do);
        ListView listViewToDo = (ListView) findViewById(R.id.listViewToDo);
        listViewToDo.setAdapter(mAdapter);

        EditText ipToAttack = (EditText) findViewById(R.id.ipToAttack);
        for (int i = 0; i < 1000; i++) {
            DosThread thread = null;
            try {
                thread = new DosThread(ipToAttack);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (thread != null)
            {
                thread.start();
                refreshList(ipToAttack, thread);
            }
        }
    }

    private void refreshList(EditText ipToAttack, DosThread thread)
    {
        final AttackThread item = new AttackThread("100 messages sent From " + thread.getName());
        mAdapter.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class AttackThreadsAdapter extends ArrayAdapter<AttackThread> {

    /**
     * Adapter context
     */
    Context mContext;

    /**
     * Adapter View layout
     */
    int mLayoutResourceId;

    public AttackThreadsAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final AttackThread currentThread = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentThread);
        final TextView textView = (TextView) row.findViewById(R.id.attackInfo);
        textView.setText(currentThread.getText());
        return row;
    }
}

class AttackThread {
    private String mText;
    private String mId;
    private boolean mComplete;

    public AttackThread(String text) {
        mText = text;
    }

    public String toString() {
        return getText();
    }

    public String getText() {
        return mText;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof AttackThread && ((AttackThread) o).mText == mText;

    }
}
