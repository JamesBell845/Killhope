package com.example.jamesbell.killhope;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    public static ArrayList<GlossaryTerm> terms;

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
        XMLParserMinerals parser = new XMLParserMinerals(stream);
        minerals = parser.getMinerals();
        names = new ArrayList<String>();
        for(MineralObject mo: minerals){
            names.add(mo.getName());
        }

        InputStream glossStream = null;
        try {
            glossStream = getApplicationContext().getAssets().open("glossary.xml");
        }catch(IOException e){
            e.printStackTrace();
        }
        XMLParserGlossary glossParser = new XMLParserGlossary(glossStream);
        terms = glossParser.getTerms();



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
                .replace(R.id.container, MineralFragment.newInstance(position + 1))
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
        ImageView imgFavorite = (ImageView) customHeader.findViewById(R.id.logo);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commit();
            }
        });
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
        if (id == R.id.glossaryButton) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, GlossaryFragment.newInstance())
                    .commit();
        }

        return super.onOptionsItemSelected(item);
    }

    public static class GlossaryFragment extends Fragment {

        public static GlossaryFragment newInstance() {
            GlossaryFragment fragment = new GlossaryFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_glossary, container, false);

            ExpandableListView glossaryList = (ExpandableListView) rootView.findViewById(R.id.glossaryList);

            List<String> termsList = new ArrayList<String>();
            HashMap<String, List<String>> termsDefinition = new HashMap<String, List<String>>();
            int i = 0;
            for (GlossaryTerm t : terms) {
                termsList.add(t.getWord());
                List<String> sub = new ArrayList<String>();
                sub.add(t.getDefinition());
                for (GlossaryTerm s : t.getSubterms()) {
                    sub.add("Subterm: " + s.getWord() + ": " + s.getDefinition());
                }
                termsDefinition.put(termsList.get(i), sub);
                i = i + 1;
            }
            ExpandableListAdapter adapter = new ExpandableListAdapter(rootView.getContext(), termsList, termsDefinition);
            glossaryList.setAdapter(adapter);
            return rootView;
        }

    }
    public static class HomeFragment extends Fragment {

        public static HomeFragment newInstance() {
            HomeFragment fragment = new HomeFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.home_layout, container, false);


            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MineralFragment extends Fragment {
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
        public static MineralFragment newInstance(int sectionNumber) {
            MineralFragment fragment = new MineralFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            number = sectionNumber;


            return fragment;
        }

        public MineralFragment() {


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_mineral, container, false);

            ExpandableListView expandable = (ExpandableListView) rootView.findViewById(R.id.lvExp);

            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            prepareListData();


            ExpandableListAdapter listAdapter = new ExpandableListAdapter(rootView.getContext(), listDataHeader, listDataChild);

            expandable.setAdapter(listAdapter);

            mineralName = (TextView) rootView.findViewById(R.id.mineralName);
            mineralName.setText(minerals.get(number-1).getName());

            ImageView mineralImage = (ImageView) rootView.findViewById(R.id.mineralImage);
            String url = "@drawable/"+minerals.get(number-1).getName().toLowerCase();
            int imageResource = getResources().getIdentifier(url, null, rootView.getContext().getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            mineralImage.setImageDrawable(res);

            return rootView;
        }

        private void prepareListData() {
            listDataHeader.add("Stage 1");
            listDataHeader.add("Stage 2");
            listDataHeader.add("Stage 3");
            listDataHeader.add("Stage 4");

            List<String> stage1 = new ArrayList<String>();
            stage1.add("Formula: " + minerals.get(number-1).getForumla());
            stage1.add("Colour: " + minerals.get(number-1).getColour());
            stage1.add("Abundance: " + minerals.get(number-1).getAbundance() );
            stage1.add("Hardness: " + minerals.get(number-1).getHardness());
            stage1.add("Lustre: " + minerals.get(number-1).getLustre());
            stage1.add("Ore: " + minerals.get(number-1).getOre());
            stage1.add("Fun Fact: " + minerals.get(number-1).getInterestingFact());

            List<String> stage2 = new ArrayList<String>();
            stage2.add("Uses: " + minerals.get(number-1).getUses());
            stage2.add("Main Countries: " + minerals.get(number-1).getMainCountries());
            stage2.add("Crystal Habit: " + minerals.get(number-1).getCrystalHabit());
            stage2.add("Crystal Structure: " + minerals.get(number-1).getCrystalStructure());
            stage2.add("Depositional Environment: " + minerals.get(number-1).getDepositionalEnviro());
            stage2.add("Transparency: " + minerals.get(number-1).getTransparency());
            stage2.add("Origin of Name: " + minerals.get(number-1).getOriginOfName());
            stage2.add("Colours at Killhope: " + minerals.get(number-1).getColoursAtKillhope());

            List<String> stage3 = new ArrayList<String>();
            stage3.add("Further Uses: " + minerals.get(number-1).getFurtherUses());
            stage3.add("Streak: " + minerals.get(number-1).getStreak());
            stage3.add("Cleavage: " + minerals.get(number-1).getCleavage());
            stage3.add("Fracture: " + minerals.get(number-1).getFracture());

            List<String> stage4 = new ArrayList<String>();
            stage4.add("Specific Gravity: " + minerals.get(number-1).getSpecificGravity());
            stage4.add("Further Properties: " + minerals.get(number-1).getFurtherProperties());
            stage4.add("Relevance at Killhope: " + minerals.get(number-1).getRelevanceAtKillhope());
            stage4.add("Specific Gravity: " + minerals.get(number-1).getSpecificGravity());
            stage4.add("Optical Properties: " + minerals.get(number-1).getOpticalProperties());
            stage4.add("Impurities: " + minerals.get(number-1).getImpurities());



            listDataChild.put(listDataHeader.get(0), stage1);
            listDataChild.put(listDataHeader.get(1),stage2);
            listDataChild.put(listDataHeader.get(2),stage3);
            listDataChild.put(listDataHeader.get(3),stage4);

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

