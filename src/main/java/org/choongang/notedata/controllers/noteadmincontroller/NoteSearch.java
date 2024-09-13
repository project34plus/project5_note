package org.choongang.notedata.controllers.noteadmincontroller;

import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
public class NoteSearch extends CommonSearch {
    private String category;
    private List<String> nid;
}
