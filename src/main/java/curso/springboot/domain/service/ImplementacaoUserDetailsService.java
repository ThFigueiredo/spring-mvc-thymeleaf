package curso.springboot.domain.service;

import curso.springboot.domain.model.UsuarioModel;
import curso.springboot.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioModel usuario = usuarioRepository.findUserByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}

		return (UserDetails) usuario;
		//return usuario;
	}

}
