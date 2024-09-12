package org.choongang.note.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class NoteDeleteException extends CommonException {
    public NoteDeleteException() {
        super("Select.note", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }

    public NoteDeleteException(String s, HttpStatus httpStatus) {
        super(s,httpStatus);
    }
}
