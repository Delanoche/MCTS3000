package com.connorhenke.mcts3000;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.connorhenke.mcts.R;

import java.util.ArrayList;
import java.util.List;

public class RoutesActivity extends Activity {

    protected List<Route> routes = new ArrayList<Route>();
    protected RoutesAdapter adapter;
    ListView listView;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes);

        listView = (ListView) this.findViewById(android.R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView number = (TextView) view.findViewById(R.id.number);
                TextView name = (TextView) view.findViewById(R.id.name);
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.putExtra("NUMBER", number.getText());
                intent.putExtra("NAME", name.getText());
                startActivity(intent);
                overridePendingTransition(R.anim.lefttoright, R.anim.righttoleft);

            }
        });

        swipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RoutesRequester().execute();
            }
        });

        if(adapter == null) {
            adapter = new RoutesAdapter(this, R.layout.route_item, routes);
        }
        listView.setAdapter(adapter);

        new RoutesRequester().execute();
    }

    class RoutesRequester extends AsyncTask<String, String, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject routes = Constants.getRoutes();
            Log.i(WIFI_SERVICE, "Request completed");
            return routes;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                JSONArray routeList = result.getJSONObject("bustime-response").getJSONArray("routes");
                routes.clear();
                for(int i = 0; i < routeList.length(); i++) {
                    JSONObject route = (JSONObject)routeList.get(i);
                    String rt = route.getString("rt");
                    String rtnm = route.getString("rtnm");
                    String rtclr = route.getString("rtclr");
                    routes.add(new Route(rt, rtnm, rtclr));
                }
                swipeLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }catch(Exception e) {

            }
        }

    }
}