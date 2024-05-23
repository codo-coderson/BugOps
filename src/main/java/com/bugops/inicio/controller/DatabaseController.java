package com.bugops.inicio.controller;

import com.bugops.inicio.model.*;
import com.bugops.inicio.repository.UsuarioRepository;
import com.bugops.inicio.service.DatabaseService;
import com.bugops.inicio.service.FileStorageService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para manejar las operaciones relacionadas con la base de datos.
 */
@Controller
public class DatabaseController {

    /**
     * Servicio para interactuar con la base de datos.
     */
    @Autowired
    private DatabaseService databaseService;

    /**
     * Servicio para almacenar y cargar archivos.
     */
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Codificador de contraseñas.
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Repositorio de usuarios.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param error
     * @param model
     * @return la vista de inicio de sesión
     */
    @GetMapping("/login")
    public String showLoginForm(@RequestParam Optional<String> error, Model model) {
        error.ifPresent(s -> model.addAttribute("error", "Error: Credenciales no encontradas o incorrectas."));
        List<String> adminEmails = databaseService.getAdminEmails();
        model.addAttribute("adminEmails", adminEmails);
        return "login";
    }

    /**
     * Procesa la solicitud de inicio de sesión.
     *
     * @param nombreUsuario
     * @param password
     * @param model
     * @return la página de inicio si el inicio de sesión es exitoso, de lo
     *         contrario, la página de inicio de sesión con un mensaje de error.
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String nombreUsuario, @RequestParam String password, Model model) {
        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (user == null || !user.isEnabled()) {
            model.addAttribute("error", "Error: Usuario no encontrado o no habilitado.");
            return "login"; // Retorna directamente a la vista de inicio de sesión con el mensaje de error.
        }
        if (user.getNeedsPasswordChange()) {
            return "redirect:/changePassword?username=" + nombreUsuario;
        }
        return "redirect:/";
    }

    /**
     * Muestra el formulario para cambiar la contraseña.
     *
     * @param username
     * @param model
     * @return la vista de cambio de contraseña
     */

    @GetMapping("/changePassword")
    public String showChangePasswordForm(@RequestParam("username") String username, Model model) {
        Usuario user = usuarioRepository.findByNombreUsuario(username);
        if (user == null || !user.getNeedsPasswordChange()) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "changePassword";
    }

    /**
     * Procesa la solicitud de cambio de contraseña.
     *
     * @param username
     * @param password
     * @param confirmPassword
     * @param model
     * @param redirectAttributes
     * @return la página de inicio de sesión si el cambio de contraseña es exitoso,
     *         de lo contrario, la página de cambio de contraseña con un mensaje de
     *         error.
     */

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String username, @RequestParam String password,
            @RequestParam String confirmPassword, Model model, RedirectAttributes redirectAttributes) {
        Usuario user = usuarioRepository.findByNombreUsuario(username);
        if (user == null) {
            model.addAttribute("error", "Error: Usuario no encontrado.");
            return "login";
        }
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Error: Las contraseñas no coinciden.");
            return "redirect:/changePassword?username=" + username;
        }
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setNeedsPasswordChange(false);
        usuarioRepository.save(user);
        return "redirect:/login";
    }

    /**
     * Procesa la solicitud de restablecimiento de contraseña.
     *
     * @param username
     * @return la página de administración de usuarios
     */
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username) {
        Usuario user = usuarioRepository.findByNombreUsuario(username);
        if (user == null) {
            return "redirect:/userAdmin";
        }
        user.setNeedsPasswordChange(true);
        usuarioRepository.save(user);
        return "redirect:/userAdmin";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    /**
     * Muestra la página de inicio.
     *
     * @param showArchived
     * @param model
     * @return la vista de inicio
     */

    @GetMapping("/")
    public String showIndex(@RequestParam(value = "showArchived", required = false) Boolean showArchived, Model model) {
        List<List<String>> datosTabla;
        if (Boolean.TRUE.equals(showArchived)) {
            datosTabla = databaseService.getActiveBugTableData();
        } else {
            datosTabla = databaseService.getActiveBugFromActiveProjectsTableData();
        }

        model.addAttribute("datosTabla", datosTabla);
        return "index";
    }

    /**
     * Muestra la página de errores archivados.
     *
     * @param model
     * @return la vista de errores archivados
     */

    @GetMapping("/archivedBugs")
    public String showArchivedBugs(Model model) {
        List<List<String>> datosTabla = databaseService.getArchivedBugTableData();
        model.addAttribute("datosTabla", datosTabla);
        return "archivedBugs";
    }

    /**
     * Muestra los detalles de una tabla.
     *
     * @param nombreTabla
     * @param model
     * @return la vista de detalles de la tabla
     */

    @GetMapping("/tables/{nombreTabla}")
    public String showTableData(@PathVariable String nombreTabla, Model model) {
        List<String> nombresTabla = databaseService.getTableNames();
        List<List<String>> datosTabla = databaseService.getTableData(nombreTabla);
        model.addAttribute("nombresTabla", nombresTabla);
        model.addAttribute("datosTabla", datosTabla);
        model.addAttribute("nombreTabla", nombreTabla);
        return "tableDetails";
    }

    /**
     * Muestra la página de creación de un nuevo error.
     *
     * @param model
     * @return la vista de creación de un nuevo error
     */

    @GetMapping("/newBug")
    public String newBug(Model model) {

        List<String> prioridades = databaseService.getEnumValues("informe_error", "Prioridad")
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        List<String> estados = databaseService.getEnumValues("informe_error", "Estado")
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("estados", estados);

        List<String> nombresProyecto = databaseService.getActiveProjectNames();
        model.addAttribute("nombresProyecto", nombresProyecto);

        return "newBug";
    }

    /**
     * Crea un nuevo informe de error.
     *
     * @param titulo
     * @param proyecto
     * @param prioridad
     * @param estado
     * @param descripcion
     * @param adjunto
     * @param asignarA
     * @param programmerUsername
     * @param redirectAttributes
     * @return la página de inicio si la creación es exitosa, de lo contrario, la
     *         página de creación de un nuevo error con un mensaje de error.
     */

    @PostMapping("/createBugReport")
    public String createBugReport(@RequestParam String titulo, @RequestParam String proyecto,
            @RequestParam String prioridad, @RequestParam String estado, @RequestParam String descripcion,
            @RequestParam("adjunto") MultipartFile adjunto, @RequestParam(required = false) String asignarA,
            @RequestParam(required = false) String programmerUsername, RedirectAttributes redirectAttributes) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nombreUsuario;
        if (principal instanceof UserDetails) {
            nombreUsuario = ((UserDetails) principal).getUsername();
        } else {
            nombreUsuario = principal.toString();
        }

        if (!adjunto.isEmpty() && adjunto.getSize() > 5242880) { // 5 Megabytes
            return "error";
        }

        try {
            databaseService.createBugReport(titulo, proyecto, prioridad, nombreUsuario, estado,
                    descripcion, adjunto, programmerUsername);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/newBug";
        }
        return "redirect:/";
    }

    /*
     * Maneja la excepción de tamaño de archivo máximo excedido.
     *
     * @param e la excepción
     *
     * @return un mensaje de error
     */

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public ResponseEntity<?> handleMaxUploadSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("El archivo debe ser menor de 5 MB.");
    }

    /**
     * Muestra la página de registro de un nuevo usuario.
     *
     * @param nombreUsuario
     * @param password
     * @param nombre
     * @param email
     * @param model
     * @return la vista de registro de un nuevo usuario
     */

    @PostMapping("/register")
    public String registerUser(@RequestParam String nombreUsuario, @RequestParam String password,
            @RequestParam String nombre, @RequestParam String email, Model model) {
        Usuario existingUser = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (existingUser != null) {
            model.addAttribute("error", "Error: Ese nombre de usuario ya existe. Por favor, elige otro.");
            return "register";
        }

        Rol rol = Rol.valueOf("REPORTADOR");
        Boolean eliminado = false;
        Date fechaAlta = new Date();

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        databaseService.createUser(nombreUsuario, encodedPassword, rol, nombre, email, eliminado, fechaAlta);
        return "redirect:/";
    }

    /**
     * Muestra la página de administración de usuarios.
     *
     * @param model
     * @return la vista de administración de usuarios
     */

    @GetMapping("/userAdmin")
    public String showUserAdmin(Model model) {
        List<Usuario> usuarios = usuarioRepository.findByEliminadoFalse();
        List<String> roles = Arrays.stream(Rol.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", roles);
        return "userAdmin";
    }

    /**
     * Actualiza el email de un usuario.
     *
     * @param nombreUsuario
     * @param email
     * @return un mensaje de éxito o error
     */

    @PostMapping("/updateUserEmail")
    public ResponseEntity<?> updateUserEmail(@RequestParam String nombreUsuario, @RequestParam String email) {
        try {
            databaseService.updateUserEmail(nombreUsuario, email);
            return new ResponseEntity<>("Email actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el email", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un usuario.
     *
     * @param nombreUsuario
     * @return un mensaje de éxito o error
     */

    @PostMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String nombreUsuario) {
        try {
            databaseService.deleteUser(nombreUsuario);
            return new ResponseEntity<>("Usuario marcado como eliminado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al marcar el usuario como eliminado", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza el rol de un usuario.
     *
     * @param nombreUsuario
     * @param rol
     * @return un mensaje de éxito o error
     */

    @PostMapping("/updateUserRole")
    public ResponseEntity<?> updateUserRole(@RequestParam String nombreUsuario, @RequestParam String rol) {
        try {
            databaseService.updateUserRole(nombreUsuario, Rol.valueOf(rol));
            return new ResponseEntity<>("Rol actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el rol", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene los colaboradores de un proyecto.
     *
     * @param nombreProyecto
     * @return los colaboradores
     */

    @GetMapping("/getCollaborators")
    public ResponseEntity<List<String>> getCollaborators(@RequestParam String nombreProyecto) {
        List<String> collaborators = databaseService.getCollaboratorsByProjectName(nombreProyecto);
        return new ResponseEntity<>(collaborators, HttpStatus.OK);
    }

    /**
     * Obtiene los comentarios de un error.
     *
     * @param bugId
     * @return los comentarios
     */

    @GetMapping("/getComments")
    public ResponseEntity<List<Comentario>> getComments(@RequestParam int bugId) {
        List<Comentario> comments = databaseService.getCommentsByBugId(bugId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /**
     * Muestra los detalles de un error.
     *
     * @param bugId
     * @param model
     * @return la vista de detalles de un error
     */

    @GetMapping("/viewBug/{bugId}")
    public String viewBug(@PathVariable int bugId, Model model) {
        InformeError informeError = databaseService.getBugReportDetails(bugId);
        List<String> prioridades = databaseService.getEnumValues("informe_error", "Prioridad");
        List<String> estados = databaseService.getEnumValues("informe_error", "Estado");
        Usuario programadorAsignado = databaseService.getAssignedProgrammer(bugId);
        List<String> programadores = databaseService.getProjectCollaboratorsByBugId(bugId);
        List<Comentario> comentarios = databaseService.getCommentsByBugId(bugId);
        for (Comentario comentario : comentarios) {
            List<Adjunto> adjuntos = databaseService.getAttachmentsByCommentId(comentario.getId());
            comentario.setAdjuntos(adjuntos);
        }

        model.addAttribute("informeError", informeError);
        model.addAttribute("prioridades", prioridades);
        model.addAttribute("estados", estados);
        model.addAttribute("programadorAsignado", programadorAsignado);
        model.addAttribute("programadores", programadores);
        model.addAttribute("comentarios", comentarios);
        return "viewBug";
    }

    /**
     * Actualiza la prioridad de un error.
     *
     * @param bugId
     * @param prioridad
     * @return un mensaje de éxito o error
     */

    @PostMapping("/updateBugPriority")
    public ResponseEntity<?> updateBugPriority(@RequestParam("bugId") int bugId,
            @RequestParam("prioridad") String prioridad) {
        try {
            databaseService.updateBugPriority(bugId, prioridad);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza el estado de un error.
     *
     * @param bugId
     * @param estado
     * @return un mensaje de éxito o error
     */

    @PostMapping("/updateBugState")
    public ResponseEntity<?> updateBugState(@RequestParam int bugId, @RequestParam String estado) {
        try {
            databaseService.updateBugState(bugId, estado);
            return new ResponseEntity<>("Estado actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el estado", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza el programador asignado a un error.
     *
     * @param bugId
     * @param programadorNombreUsuario
     * @return un mensaje de éxito o error
     */

    @PostMapping("/updateBugProgrammer")
    public ResponseEntity<?> updateBugProgrammer(@RequestParam int bugId,
            @RequestParam String programadorNombreUsuario) {
        try {
            databaseService.updateBugProgrammer(bugId, programadorNombreUsuario);
            return new ResponseEntity<>("Programador actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el programador", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina la asignación de un programador a un error.
     *
     * @param bugId
     * @return un mensaje de éxito o error
     */

    @PostMapping("/removeBugProgrammer")
    public ResponseEntity<?> removeBugProgrammer(@RequestParam int bugId) {
        try {
            databaseService.removeBugProgrammer(bugId);
            return ResponseEntity.ok("Asignación anulada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al anular la asignación");
        }
    }

    /**
     * Descarga un archivo.
     *
     * @param fileName
     * @param request
     * @return el archivo
     */

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource;
        try {
            resource = fileStorageService.loadFileAsResource(fileName);
        } catch (FileNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + fileName);
        }

        // Intenta determinar el tipo de archivo
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // Si no se puede determinar el tipo de archivo, se establece el tipo de
            // contenido por defecto
            contentType = "application/octet-stream";
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        // Retorna el recurso para ser descargado
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    /**
     * Crea un nuevo comentario.
     *
     * @param bugId
     * @param contenido
     * @param adjunto
     * @param redirectAttributes
     * @param request
     * @param model
     * @return la página de detalles de un error
     */

    @PostMapping("/createComment")
    public String createComment(@RequestParam int bugId, @RequestParam String contenido,
            @RequestParam("adjunto") MultipartFile adjunto, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nombreUsuario;
        if (principal instanceof UserDetails) {
            nombreUsuario = ((UserDetails) principal).getUsername();
        } else {
            nombreUsuario = principal.toString();
        }

        if (!adjunto.isEmpty() && adjunto.getSize() > 5242880) { // 5 Megabytes
            return "error";
        }

        try {
            databaseService.createComment(bugId, nombreUsuario, contenido, adjunto);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/viewBug/" + bugId;
        }
        return "redirect:/viewBug/" + bugId;
    }

    /**
     * Elimina un comentario.
     *
     * @param commentId
     * @param bugId
     * @return un mensaje de éxito o error
     */

    @GetMapping("/projectAdmin")
    public String showProjects(Model model) {
        List<Proyecto> proyectos = databaseService.getActiveProjects();
        Map<Integer, String> colaboradores = new HashMap<>();
        for (Proyecto proyecto : proyectos) {
            List<String> collaborators = databaseService.getCollaboratorsByProjectId(proyecto.getId());
            colaboradores.put(proyecto.getId(), String.join(", ", collaborators));
        }
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("colaboradores", colaboradores);
        return "projectAdmin";
    }

    /**
     * Muestra la página de administración de proyectos.
     *
     * @param model
     * @return
     */

    @GetMapping("/archivedProjects")
    public String showArchivedProjects(Model model) {
        List<Proyecto> proyectos = databaseService.getArchivedProjects();
        Map<Integer, String> colaboradores = new HashMap<>();
        for (Proyecto proyecto : proyectos) {
            List<String> collaborators = databaseService.getCollaboratorsByProjectId(proyecto.getId());
            colaboradores.put(proyecto.getId(), String.join(", ", collaborators));
        }
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("colaboradores", colaboradores);
        return "archivedProjects";
    }

    /**
     * Muestra la página de proyectos archivados.
     *
     * @param model
     * @return la vista de proyectos archivados
     */

    @PostMapping("/updateProjectName")
    public ResponseEntity<?> updateProjectName(@RequestParam int projectId, @RequestParam String newName) {
        try {
            databaseService.updateProjectName(projectId, newName);
            return new ResponseEntity<>("Nombre del proyecto actualizado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el nombre del proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza el nombre de un proyecto.
     *
     * @param projectId
     * @return un mensaje de éxito o error
     */

    @PostMapping("/archiveProject")
    public ResponseEntity<?> archiveProject(@RequestParam int projectId) {
        try {
            databaseService.archiveProject(projectId);
            return new ResponseEntity<>("Proyecto archivado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al archivar el proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Archiva un proyecto.
     *
     * @param projectId
     * @return un mensaje de éxito o error
     */

    @PostMapping("/unarchiveProject")
    public ResponseEntity<?> unarchiveProject(@RequestParam int projectId) {
        try {
            databaseService.unarchiveProject(projectId);
            return new ResponseEntity<>("Proyecto desarchivado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al desarchivar el proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Desarchiva un proyecto.
     *
     * @param projectId
     * @return un mensaje de éxito o error
     */

    @GetMapping("/getAllProgrammers")
    public ResponseEntity<?> getAllProgrammers() {
        try {
            List<Usuario> programmers = databaseService.getAllProgrammers();
            return new ResponseEntity<>(programmers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los programadores", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Asigna programadores a un proyecto.
     *
     * @param payload el payload de la solicitud, que contiene el ID del proyecto y
     *                una lista de nombres de usuario
     * @return un mensaje de éxito o error
     */

    @PostMapping("/assignCollaborators")
    public ResponseEntity<?> assignCollaborators(@RequestBody Map<String, Object> payload) {
        try {
            int projectId = Integer.parseInt(payload.get("projectId").toString());
            List<String> usernames = null;
            if (payload.get("usernames") instanceof List<?>) {
                usernames = ((List<?>) payload.get("usernames")).stream()
                        .filter(object -> object instanceof String)
                        .map(object -> (String) object)
                        .collect(Collectors.toList());
            }
            if (usernames == null) {
                throw new Exception("usernames no es una lista de strings válida");
            }
            databaseService.assignCollaboratorsToProject(projectId, usernames);
            return new ResponseEntity<>("Programadores asignados con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al asignar los programadores", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene los colaboradores asignados a un proyecto.
     *
     * @param projectId
     * @return los colaboradores asignados
     */

    @GetMapping("/getAssignedCollaborators")
    public ResponseEntity<?> getAssignedCollaborators(@RequestParam int projectId) {
        try {
            List<String> assignedCollaborators = databaseService.getCollaboratorsByProjectId(projectId);
            return new ResponseEntity<>(assignedCollaborators, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los colaboradores asignados", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene los colaboradores asignados a un proyecto.
     *
     * @param projectId
     * @return los colaboradores asignados
     */

    @PostMapping("/createProject")
    public ResponseEntity<String> createProject(@RequestBody Map<String, String> body) {
        try {
            String nombre = body.get("nombre");
            databaseService.createProject(nombre);
            return new ResponseEntity<>("Proyecto creado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el proyecto", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene los colaboradores asignados a un proyecto.
     *
     * @return un mapeo de todos los errores y a quién están asignados
     */

    @GetMapping("/getAssignedToAll")
    public ResponseEntity<Map<Integer, String>> getAssignedToAll() {
        Map<Integer, String> assignedToAll = databaseService.getAssignedToAll();
        return new ResponseEntity<>(assignedToAll, HttpStatus.OK);
    }

}
