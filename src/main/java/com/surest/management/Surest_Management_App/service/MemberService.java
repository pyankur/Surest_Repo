package com.surest.management.Surest_Management_App.service;

import com.surest.management.Surest_Management_App.dto.MemberCreateDto;
import com.surest.management.Surest_Management_App.entity.Member;
import com.surest.management.Surest_Management_App.repository.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<Member> search(String firstName, String lastName, Pageable
            pageable) {
        if (firstName == null) firstName = "";
        if (lastName == null) lastName = "";
        return memberRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName,lastName, pageable);

    }

    @Cacheable(value = "members", key = "#id")
    public Optional<Member> getById(UUID id) {
        return memberRepository.findById(id);
    }

    public Member create(MemberCreateDto dto) {
        Member m = new Member();
        m.setFirstName(dto.firstName);
        m.setLastName(dto.lastName);
        m.setDateOfBirth(dto.dateOfBirth);
        m.setEmail(dto.email);
        return memberRepository.save(m);
    }

    @CacheEvict(value = "members", key = "#id")
    public Optional<Member> update(UUID id, MemberCreateDto dto) {
        return memberRepository.findById(id).map(m -> {
            m.setFirstName(dto.firstName);
            m.setLastName(dto.lastName);
            m.setDateOfBirth(dto.dateOfBirth);
            m.setEmail(dto.email);
            return memberRepository.save(m);
        });
    }

    @CacheEvict(value = "members", key = "#id")
    public void delete(UUID id) {
        memberRepository.deleteById(id);
    }



}
