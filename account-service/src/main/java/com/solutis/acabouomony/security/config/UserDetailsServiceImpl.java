package com.solutis.acabouomony.security.config;

import com.solutis.acabouomony.account.model.User;
import com.solutis.acabouomony.account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        // Aqui você pode adicionar as permissões do usuário (roles), se houver.
        Set<GrantedAuthority> authorities = new HashSet<>();

        // Se o seu modelo User tiver roles ou permissões, adicione-as a "authorities".
        // Exemplo:
        // authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)  // As authorities podem ser adicionadas aqui.
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
