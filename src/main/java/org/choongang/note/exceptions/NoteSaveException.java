package org.choongang.note.exceptions;

import org.choongang.global.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class NoteSaveException extends CommonException {
    public NoteSaveException(String s, HttpStatus badRequest) {
        super("Select.note", HttpStatus.BAD_REQUEST);
        setErrorCode(true);
    }
}
