package org.choongang.notedata.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
public class NoteDataSearch  extends CommonSearch {
    private String category;
    private List<String> nid;
}
