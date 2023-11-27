package com.example.team12.components.search;

import java.util.List;

public class ParentModelClass {
    String category_title;
    List<ChildModelClass> childModelClassList;

    public ParentModelClass(String category_title, List<ChildModelClass> childModelClassList) {
        this.category_title = category_title;
        this.childModelClassList = childModelClassList;
    }
}
