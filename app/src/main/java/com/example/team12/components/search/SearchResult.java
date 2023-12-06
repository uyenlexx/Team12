package com.example.team12.components.search;

import android.view.View;

import com.example.team12.components.menu.FragmentRecipeDetailed;

public class SearchResult implements View.OnClickListener {
    public String url;
    public String description;
    public String name;
    SearchResultRedirect searchResultRedirect;
    public FragmentRecipeDetailed recipeDetailed;
    public SearchResult(String name, String description, String url) {
        this.description = description;
        this.name = name;
        this.url = url;
    }


    @Override
    public void onClick(View view) {
        searchResultRedirect.onClick(view);
    }

    public void setSearchResultRedirect(SearchResultRedirect searchResultRedirect) {
        this.searchResultRedirect = searchResultRedirect;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public interface SearchResultRedirect {
        public void onClick(View view);
    }
}