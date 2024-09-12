package org.choongang.notedata.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class NoteDataNotFoundException extends CommonException {
    public NoteDataNotFoundException() { super("NotFound.notedata", HttpStatus.BAD_REQUEST);
    setErrorCode(true);
    }
}
