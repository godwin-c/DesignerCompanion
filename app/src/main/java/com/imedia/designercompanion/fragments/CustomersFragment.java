package com.imedia.designercompanion.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imedia.designercompanion.R;


public class CustomersFragment extends Fragment {

    SearchView searchView;
    TextView emptyTV;
    RecyclerView recyclerView;

    public CustomersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        searchView = view.findViewById(R.id.frag_customer_searchview);
        emptyTV = view.findViewById(R.id.empty_data_view);
        recyclerView = view.findViewById(R.id.frag_customers_recycler_view);
        
        return view;
    }
}