package org.choongang.note.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class NoteNotFoundException extends CommonException {
    public NoteNotFoundException() {
        super("NotFound.note", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
