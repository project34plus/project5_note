package org.choongang.global.tests;

import org.choongang.member.MemberInfo;
import org.choongang.member.entities.Member;
import org.choongang.notedata.entities.NoteData;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.stereotype.Component;

@Component
public class MockSecurityContextFactory implements WithSecurityContextFactory<MockNoteData> {

    @Override
    public SecurityContext createSecurityContext(MockNoteData mockNoteData) {

        Member member = new Member();
        NoteData noteData = new NoteData();
        noteData.setGid(mockNoteData.gid());
        noteData.setCategory(mockNoteData.Category());
        noteData.setSubject(mockNoteData.Subject());
        noteData.setContent(mockNoteData.Content());
        noteData.setEmail(mockNoteData.Email());
        noteData.setUsername(mockNoteData.Username());


        MemberInfo memberInfo = MemberInfo.builder()

                .build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(memberInfo, null);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);

        return context;
    }
}
