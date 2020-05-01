package com.example.covid_19visualizer.info;

//FOLLOWING IMPORTS ADDED BY FEDERICO FOR INFO TAB

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.covid_19visualizer.R;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//ArrayList<xmlinfo> xmlinfoList = new ArrayList<>();
/**
 * File created by: Harry, modified by: Federico to include all classes and methods for RSS feed
 *
 * This class contains everything needed for the info_fragment.xml page.
 */

public class InfoFragment extends ListFragment
{
    private ListView listView;
    private InfoAdapter mAdapter;
    public List links;
    ArrayList<xmlinfo> xmlinfoList = new ArrayList<>();

    @Override
    public View onCreateView (@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(android.R.id.list);
        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask<Object,Void, ArrayAdapter>
        {

            @Override
            protected ArrayAdapter doInBackground(Object[] params)
            {
                links = new ArrayList();
                try
                {
                    URL url = new URL("https://tools.cdc.gov/api/v2/resources/media/403372.rss");
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();

                    // We will get the XML from an input stream
                    xpp.setInput(getInputStream(url), "UTF_8");
                    boolean insideItem = false;

                    // Returns the type of current event: START_TAG, END_TAG, etc..
                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT)
                    {
                        if (eventType == XmlPullParser.START_TAG)
                        {
                            if (xpp.getName().equalsIgnoreCase("item"))
                            {
                                insideItem = true;
                            }
                            else if (xpp.getName().equalsIgnoreCase("title"))
                            {
                                if (insideItem)
                                    xmlinfoList.add(new xmlinfo(xpp.nextText(), null)); //extract the headline
                            }
                            else if (xpp.getName().equalsIgnoreCase("link"))
                            {
                                if (insideItem)
                                    links.add(xpp.nextText()); //extract the link of article
                            }
                            else if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                if (insideItem)
                                    xmlinfoList.add(new xmlinfo(null, xpp.nextText())); //extract the description of article
                            }

                        }
                        else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item"))
                        {
                            insideItem=false;
                        }
                        eventType = xpp.next(); //move to next element
                    }

                }
                catch (MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            protected void onPostExecute(ArrayAdapter adapter)
            {
                mAdapter = new InfoAdapter(getActivity().getApplicationContext(),xmlinfoList);
                listView.setAdapter(mAdapter);
            }
        }

        public void onListItemClick(ListView l, View v, int position, long id)
        {
            Uri uri = Uri.parse((links.get(position)).toString());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

        public InputStream getInputStream(URL url)
        {
            try
            {
                return url.openConnection().getInputStream();
            }
            catch (IOException e)
            {
                return null;
            }
        }
    }