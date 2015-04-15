package com.example.jamesbell.killhope;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private ArrayList<String> names;
    public static ArrayList<MineralObject> minerals;

    //Data



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        InputStream stream = null;
        try {
            stream = getApplicationContext().getAssets().open("mineralDatabase.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        XMLParser parser = new XMLParser(stream);
        minerals = parser.getMinerals();
        names = new ArrayList<String>();
        for(MineralObject mo: minerals){
            names.add(mo.getName());
        }



        ListView drawerList = (ListView) findViewById(R.id.drawerList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        drawerList.setAdapter(arrayAdapter);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        mTitle = names.get(number-1);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setTitle(mTitle);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        final ViewGroup customHeader = (ViewGroup) getLayoutInflater().inflate(R.layout.customer_header,null);
        actionBar.setCustomView(customHeader);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private TextView mineralName, mineralFormula, mineralColour, mineralAbundance, mineralHardness, mineralLustre, mineralOre, mineralFunFact;
        private static int number;
        private List<String> listDataHeader;
        private HashMap<String, List<String>> listDataChild;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            number = sectionNumber;

            return fragment;
        }

        public PlaceholderFragment() {


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            ExpandableListView expandable = (ExpandableListView) rootView.findViewById(R.id.lvExp);

            prepareListData();

            ExpandableListAdapter listAdapter = new ExpandableListAdapter(rootView.getContext(), listDataHeader, listDataChild);

            expandable.setAdapter(listAdapter);


//            mineralName = (TextView) rootView.findViewById(R.id.mineralName);
//            mineralFormula = (TextView) rootView.findViewById(R.id.mineralFormula);
//            mineralColour = (TextView) rootView.findViewById(R.id.mineralColour);
//            mineralAbundance = (TextView) rootView.findViewById(R.id.mineralAbundance);
//            mineralHardness = (TextView) rootView.findViewById(R.id.mineralHardness);
//            mineralLustre = (TextView) rootView.findViewById(R.id.mineralLustre);
//            mineralOre = (TextView) rootView.findViewById(R.id.mineralOre);
//            mineralFunFact = (TextView) rootView.findViewById(R.id.mineralFunFact);
//
//            mineralName.setText(minerals.get(number-1).getName());
//            mineralFormula.setText(minerals.get(number-1).getForumla());
//            mineralFormula.setText(minerals.get(number-1).getForumla());
//            mineralColour.setText(minerals.get(number-1).getColour());
//            mineralAbundance.setText(minerals.get(number-1).getAbundance());
//            mineralHardness.setText(minerals.get(number-1).getHardness());
//            mineralLustre.setText(minerals.get(number-1).getLustre());
//            mineralOre.setText(minerals.get(number-1).getOre());
//            mineralFunFact.setText(minerals.get(number-1).getInterestingFact());

            return rootView;
        }

        private void prepareListData() {
            listDataHeader.add("Stage 1");
            listDataHeader.add("Stage 2");
            listDataHeader.add("Stage 3");
            listDataHeader.add("Stage 4");

            List<String> top250 = new ArrayList<String>();
            top250.add("The Shawshank Redemption");
            top250.add("The Godfather");
            top250.add("The Godfather: Part II");
            top250.add("Pulp Fiction");
            top250.add("The Good, the Bad and the Ugly");
            top250.add("The Dark Knight");
            top250.add("12 Angry Men");

            List<String> nowShowing = new ArrayList<String>();
            nowShowing.add("The Conjuring");
            nowShowing.add("Despicable Me 2");
            nowShowing.add("Turbo");
            nowShowing.add("Grown Ups 2");
            nowShowing.add("Red 2");
            nowShowing.add("The Wolverine");

            List<String> comingSoon = new ArrayList<String>();
            comingSoon.add("2 Guns");
            comingSoon.add("The Smurfs 2");
            comingSoon.add("The Spectacular Now");
            comingSoon.add("The Canyons");
            comingSoon.add("Europa Report");

            listDataChild.put(listDataHeader.get(0), top250);
            listDataChild.put(listDataHeader.get(1), nowShowing);
            listDataChild.put(listDataHeader.get(2), comingSoon);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
              super.onCreate(savedInstanceState);
        }
    }

}
