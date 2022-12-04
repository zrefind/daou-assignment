package org.kang.assignment.service;

import lombok.RequiredArgsConstructor;
import org.kang.assignment.common.exception.CustomException;
import org.kang.assignment.common.exception.ErrorCode;
import org.kang.assignment.domain.member.Member;
import org.kang.assignment.domain.member.MemberRepository;
import org.kang.assignment.domain.member.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new User(String.valueOf(member.getId()), member.getPassword(), getAuthorities(member));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Member member) {
        String[] roles = member.getRoles().getRolesReadOnly().stream()
                .map(Role::getKey)
                .toArray(String[]::new);

        return AuthorityUtils.createAuthorityList(roles);
    }

}
