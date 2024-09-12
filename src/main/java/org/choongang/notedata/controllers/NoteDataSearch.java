package org.choongang.notedata.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;

@Data
public class NoteDataSearch  extends CommonSearch {
    private String category;
    private String nid;
}
