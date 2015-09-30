package com.example.ishwari.profiler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        ItemAdapter.ListItemClickListener {

    private List<String>         itemList;
    private int                  nextItemNumber;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<>();
        nextItemNumber = 0;

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /**
         * If it is known in advance that the physical size of all the items in
         * the RecyclerView are the same, setting this flag will improve
         * performance.
         */
        recyclerView.setHasFixedSize(true);

        /**
         * A LinearLayoutManager arranges the items of the RecyclerView
         * vertically.
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * The ItemAdpater manages the data model (i.e., list of strings) for
         * the RecyclerView. See comments in class ItemAdapter for further
         * explanations.
         */
        adapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Whenever the user presses the floating action button, this
                 * callback will be invoked. With each click one more item will
                 * be added to the list. First the underlying data model will be
                 * changed and then the adapter is told what has changed. In
                 * this example items are always added at the top of the list to
                 * visualize the "insertion animation" (no animation happens
                 * when items are appended at the end).
                 */
                itemList.add(0, "Item #" + nextItemNumber++);
                adapter.notifyItemInserted(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {
        /**
         * The ItemAdapter will call this method whenever the user clicks an
         * item. The item that was clicked will get deleted (in this example
         * without further user confirmation). Removing is analog to insertion:
         * first the item is removed from the underlying data model, then the
         * adapter is told how the data has changed.
         */
        itemList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}

