package org.choongang.global.tests;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockNoteData {

        String gid() default "groupId";
        String Category() default "분류1";
        String Subject() default "제목";
        String Content() default "내용";
        String Email() default "test@example.com";
        String Username() default "testusername";



}
