package com.example.ishwari.profiler;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity {

    private List<String>         itemList;
    private int                  nextItemNumber;
    private RecyclerView.Adapter adapter;
    private ImageButton fab;


    private boolean expanded = false;

    private View fabaction1;
    private View fabaction2;
    private View fabaction3;

    private float offset1;
    private float offset2;
    private float offset3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemList = new ArrayList<>();
        nextItemNumber = 0;


        startService(new Intent(this,MyService.class));


        setContentView(R.layout.activity_main);
        final ViewGroup fabContainer = (ViewGroup) findViewById(R.id.fab_container);
        fab = (ImageButton) findViewById(R.id.fab);
        fabaction1 = findViewById(R.id.fab_action_1);
        fabaction2 = findViewById(R.id.fab_action_2);
        fabaction3 = findViewById(R.id.fab_action_3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expanded = !expanded;
                if (expanded) {
                    expandFab();
                } else {
                    collapseFab();
                }
            }


        });

        findViewById(R.id.fab_action_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BatteryRem.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Battery", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        findViewById(R.id.fab_action_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLocation.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Select Place", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        findViewById(R.id.fab_action_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimeOfDay.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Time Of Day", Toast.LENGTH_SHORT);
                toast.show();
            }
        });




        fabContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fabContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                offset1 = fab.getY() - fabaction1.getY();
                fabaction1.setTranslationY(offset1);
                offset2 = fab.getY() - fabaction2.getY();
                fabaction2.setTranslationY(offset2);
                offset3 = fab.getY() - fabaction3.getY();
                fabaction3.setTranslationY(offset3);
                return true;
            }
        });}

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /**
         * If it is known in advance that the physical size of all the items in
         * the RecyclerView are the same, setting this flag will improve
         * performance.
         */
        //recyclerView.setHasFixedSize(true);

        /**
         * A LinearLayoutManager arranges the items of the RecyclerView
         * vertically.
         */
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /**
         * The ItemAdpater manages the data model (i.e., list of strings) for
         * the RecyclerView. See comments in class ItemAdapter for further
         * explanations.
         */
       // adapter = new ItemAdapter(itemList, this);
        //recyclerView.setAdapter(adapter);

       // findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
                /**
                 * Whenever the user presses the floating action button, this
                 * callback will be invoked. With each click one more item will
                 * be added to the list. First the underlying data model will be
                 * changed and then the adapter is told what has changed. In
                 * this example items are always added at the top of the list to
                 * visualize the "insertion animation" (no animation happens
                 * when items are appended at the end).
                 */
                //itemList.add(0, "Profile #" + nextItemNumber++);
                //adapter.notifyItemInserted(0);
                //Intent intent = new Intent(MainActivity.this, Triggers.class);
               // startActivity(intent);
          //  }
       // });
   // }


   /* @Override
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
        //itemList.remove(position);
        //adapter.notifyItemRemoved(position);
    //}
    private void collapseFab() {
        fab.setImageResource(R.drawable.animated_minus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createCollapseAnimator(fabaction1, offset1),
                createCollapseAnimator(fabaction2, offset2),
                createCollapseAnimator(fabaction3, offset3));
        animatorSet.start();
        animateFab();

        fab.setImageResource(R.drawable.animated_minus);
        fab.setImageResource(R.drawable.animated_plus);

    }

    private void expandFab() {
        fab.setImageResource(R.drawable.animated_plus);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createExpandAnimator(fabaction1, offset1),
                createExpandAnimator(fabaction2, offset2),
                createExpandAnimator(fabaction3, offset3));
        animatorSet.start();
        animateFab();
    }

    private static final String TRANSLATION_Y = "translationY";

    private Animator createCollapseAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, 0, offset)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private Animator createExpandAnimator(View view, float offset) {
        return ObjectAnimator.ofFloat(view, TRANSLATION_Y, offset, 0)
                .setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime));
    }

    private void animateFab() {
        Drawable drawable = fab.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}

