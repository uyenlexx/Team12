package com.example.team12.components.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.team12.R;
import com.example.team12.components.FragmentSearch;

public class FragmentSearchNotFound extends Fragment {
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View searchNotFoundView = inflater.inflate(R.layout.fragment_search_not_found, container, false);
        return searchNotFoundView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to fragment search
                FragmentSearch fragment = new FragmentSearch();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_search, fragment)
                        .commit();
            }
        });
    }
}
