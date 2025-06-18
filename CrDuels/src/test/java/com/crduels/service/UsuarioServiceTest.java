@DataJpaTest
@Import(UsuarioService.class)
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario crearUsuario(String email, String telefono) {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setEmail(email);
        usuario.setTelefono(telefono);
        String tag = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        usuario.setTagClash("#" + tag);
        return usuario;
    }

    @Test
    void registrarUsuario_guardaUsuario() {
        Usuario usuario = crearUsuario("test1@example.com", "+111111111");
        Usuario guardado = usuarioService.registrarUsuario(usuario);
        usuarioRepository.flush();
        assertTrue(usuarioRepository.findById(guardado.getId()).isPresent());
    }

    @Test
    void registrarUsuario_emailDuplicado_rechazado() {
        Usuario primero = crearUsuario("dup@example.com", "+222222222");
        usuarioService.registrarUsuario(primero);
        usuarioRepository.flush();

        Usuario duplicado = crearUsuario("dup@example.com", "+333333333");
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.registrarUsuario(duplicado);
            usuarioRepository.flush();
        });
    }

    @Test
    void registrarUsuario_telefonoDuplicado_rechazado() {
        Usuario primero = crearUsuario("phone@example.com", "+444444444");
        usuarioService.registrarUsuario(primero);
        usuarioRepository.flush();

        Usuario duplicado = crearUsuario("other@example.com", "+444444444");
        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioService.registrarUsuario(duplicado);
            usuarioRepository.flush();
        });
    }
}
