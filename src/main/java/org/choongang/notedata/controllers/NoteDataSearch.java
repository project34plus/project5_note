package org.choongang.notedata.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.choongang.global.CommonSearch;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteDataSearch  extends CommonSearch {
    private String category;
    private List<String> nid;
}
