package com.bugops.inicio.service;

import com.bugops.inicio.model.*;
import com.bugops.inicio.repository.UsuarioRepository;

import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * Esta clase proporciona métodos para interactuar con la base de datos de la
 * aplicación.
 *
 */
@Service
public class DatabaseService {

    /**
     * El objeto JdbcTemplate se utiliza para ejecutar consultas SQL en la base de
     * datos.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * El repositorio de usuarios se utiliza para acceder a los datos de los
     * usuarios en la base de datos.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene los nombres de las tablas de la base de datos.
     *
     * @return una lista de nombres de tablas
     */
    public List<String> getTableNames() {
        return jdbcTemplate.queryForList("SHOW TABLES", String.class);
    }

    /**
     * Obtiene los nombres de los proyectos.
     *
     * @return una lista de nombres de proyectos
     */

    public List<String> getProjectNames() {
        String query = "SELECT Nombre FROM Proyecto";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("Nombre"));
    }

    /**
     * Obtiene los nombres de los proyectos activos.
     *
     * @return una lista de nombres de proyectos activos
     */

    public List<String> getActiveProjectNames() {
        String query = "SELECT Nombre FROM Proyecto WHERE Archivado = false";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("Nombre"));
    }

    /**
     * Obtiene los datos de una tabla específica.
     *
     * @param tableName el nombre de la tabla de la cual se obtendrán los datos
     * @return una lista de listas de cadenas que representa los datos de la tabla
     */
    public List<List<String>> getTableData(String tableName) {
        if (tableName.equals("informe_error")) {
            return jdbcTemplate.query(
                    "SELECT informe_error.ID, Proyecto.Nombre, informe_error.Reportador, informe_error.Titulo, informe_error.Prioridad, informe_error.Estado, informe_error.FechaCreacion FROM "
                            + tableName + " JOIN Proyecto ON informe_error.ID_Proyecto = Proyecto.ID",
                    rs -> {
                        List<List<String>> tableData = new ArrayList<>();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        int columnCount = rsmd.getColumnCount();

                        // Agregar la fila superior con los nombres de los campos
                        List<String> columnNames = new ArrayList<>();
                        for (int i = 1; i <= columnCount; i++) {
                            columnNames.add(rsmd.getColumnName(i));
                        }
                        tableData.add(columnNames);

                        // Agregar las filas de datos
                        while (rs.next()) {
                            List<String> rowData = new ArrayList<>();
                            for (int i = 1; i <= columnCount; i++) {
                                if (tableName.equals("informe_error") && rsmd.getColumnName(i).equals("Programador")
                                        && rs.getString(i) == null) {
                                    rowData.add("[Ninguno]");
                                } else {
                                    rowData.add(rs.getString(i));
                                }
                            }
                            tableData.add(rowData);
                        }
                        return tableData;
                    });
        } else {
            return jdbcTemplate.query("SELECT * FROM " + tableName, rs -> {
                List<List<String>> tableData = new ArrayList<>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();

                List<String> columnNames = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    columnNames.add(rsmd.getColumnName(i));
                }
                tableData.add(columnNames);

                while (rs.next()) {
                    List<String> rowData = new ArrayList<>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.add(rs.getString(i));
                    }
                    tableData.add(rowData);
                }
                return tableData;
            });
        }
    }

    /**
     * Obtiene los valores de un campo ENUM de una tabla.
     *
     * @param tableName
     * @param columnName
     * @return una lista de nombres de columnas
     */

    public List<String> getEnumValues(String tableName, String columnName) {
        String query = "SHOW COLUMNS FROM " + tableName + " WHERE Field = '" + columnName + "'";
        List<List<String>> result = jdbcTemplate.query(query, (rs, rowNum) -> {
            String type = rs.getString("Type");
            if (type.startsWith("enum(")) {
                return Arrays.stream(type.substring(5, type.length() - 1).split(","))
                        .map(s -> s.substring(1, s.length() - 1))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        });
        return result.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    /**
     * Crea un informe de error en la base de datos.
     *
     * @param titulo
     * @param proyecto
     * @param prioridad
     * @param reportador
     * @param estado
     * @param descripcion
     * @param adjunto
     * @param programmerUsername
     * @return el ID del informe de error creado
     */

    public int createBugReport(String titulo, String proyecto, String prioridad, String reportador, String estado,
            String descripcion, MultipartFile adjunto, String programmerUsername) {
        String query = "INSERT INTO informe_error (ID_Proyecto, Reportador, Titulo, Prioridad, Estado, FechaCreacion) VALUES ((SELECT ID FROM Proyecto WHERE Nombre = ?), ?, ?, ?, ?, NOW())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, new String[] { "ID" });
            ps.setString(1, proyecto);
            ps.setString(2, reportador);
            ps.setString(3, titulo);
            ps.setString(4, prioridad);
            ps.setString(5, estado);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("No pudo obtenerse el identificador del informe de error");
        }
        int informeErrorId;
        try {
            informeErrorId = key.intValue();
        } catch (InvalidDataAccessApiUsageException e) {
            throw new RuntimeException("La inserción del informe de error no generó ninguna clave", e);
        }

        String commentQuery = "INSERT INTO Comentario (ID_informe_error, NombreUsuario, Contenido, FechaCreacion) VALUES (?, ?, ?, NOW())";

        KeyHolder commentKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(commentQuery, new String[] { "ID" });
            ps.setInt(1, informeErrorId);
            ps.setString(2, reportador); // nombre de usuario del usuario autenticado
            ps.setString(3, descripcion);
            return ps;
        }, commentKeyHolder);

        Number commentKey = commentKeyHolder.getKey();
        if (commentKey == null) {
            throw new RuntimeException("No pudo obtenerse el identificador del comentario");
        }
        int commentId;
        try {
            commentId = commentKey.intValue();
        } catch (InvalidDataAccessApiUsageException e) {
            throw new RuntimeException("La inserción del comentario no generó ninguna clave", e);
        }

        if (!adjunto.isEmpty()) {
            if (adjunto.getSize() > 5242880) { // 5 Megabytes
                throw new RuntimeException(
                        "El archivo adjunto es demasiado grande. El tamaño máximo permitido es de 5 MB.");
            }

            String uploadsDir = "adjuntos/";
            String currentDirectory = "";
            try {
                currentDirectory = new File(".").getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException("Error al obtener el directorio actual", e);
            }

            String realPathtoUploads = currentDirectory + File.separator + uploadsDir;
            File uploadDir = new File(realPathtoUploads);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String orgName = adjunto.getOriginalFilename();
            String filePath = uploadsDir + orgName; // Aquí cambiamos la ruta absoluta a una ruta relativa
            File dest = new File(realPathtoUploads + orgName);
            try {
                adjunto.transferTo(dest);

            } catch (IllegalStateException | IOException e) {
                throw new RuntimeException("Error al guardar el archivo adjunto", e);
            }

            String attachmentQuery = "INSERT INTO Adjunto (ID_Comentario, Ruta, Nombre) VALUES (?, ?, ?)";
            jdbcTemplate.update(attachmentQuery, commentId, filePath, orgName);
        }

        assignBugToProgrammer(informeErrorId, programmerUsername);

        return informeErrorId;
    }

    /**
     * Crea un usuario en la base de datos.
     *
     * @param nombreUsuario
     * @param password
     * @param rol
     * @param nombre
     * @param email
     * @param eliminado
     * @param fechaAlta
     */
    public void createUser(String nombreUsuario, String password, Rol rol, String nombre, String email,
            Boolean eliminado, Date fechaAlta) {
        Usuario newUser = new Usuario(nombreUsuario, password, rol, nombre, email, eliminado, fechaAlta);
        usuarioRepository.save(newUser);
    }

    /**
     * Actualiza el email de un usuario en la base de datos.
     *
     * @param nombreUsuario
     * @param newEmail
     */

    public void updateUserEmail(String nombreUsuario, String newEmail) {
        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (user != null) {
            user.setEmail(newEmail);
            usuarioRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    /**
     * Borra un usuario de la base de datos.
     *
     * @param nombreUsuario
     */
    public void deleteUser(String nombreUsuario) {
        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (user != null) {
            // Desasociar de proyectos
            jdbcTemplate.update("DELETE FROM colabora WHERE Colaborador = ?", nombreUsuario);

            // Desasociar de informes de error
            jdbcTemplate.update("UPDATE seasigna SET Programador = NULL WHERE Programador = ?", nombreUsuario);

            // Marcar como eliminado
            user.setEliminado(true);
            usuarioRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado para eliminar");
        }
    }

    /**
     * Actualiza el rol de un usuario en la base de datos.
     *
     * @param nombreUsuario
     * @param newRole
     */
    public void updateUserRole(String nombreUsuario, Rol newRole) {
        Usuario user = usuarioRepository.findByNombreUsuario(nombreUsuario);
        if (user != null) {
            // Solo desasociamos de proyectos e informes de error si el usuario era un
            // programador y se cambia a otro rol
            if (user.getRol() == Rol.PROGRAMADOR && newRole != Rol.PROGRAMADOR) {
                jdbcTemplate.update("DELETE FROM colabora WHERE Colaborador = ?", nombreUsuario);
                jdbcTemplate.update("UPDATE seasigna SET Programador = NULL WHERE Programador = ?", nombreUsuario);
            }
            user.setRol(newRole);
            usuarioRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado para actualizar el rol");
        }
    }

    /**
     * Obtiene los nombres de usuario de los colaboradores de un proyecto.
     *
     * @param nombreProyecto
     * @return una lista de nombres de usuario
     */
    public List<String> getCollaboratorsByProjectName(String nombreProyecto) {
        String query = "SELECT u.NombreUsuario FROM usuario u INNER JOIN colabora c ON u.NombreUsuario = c.Colaborador INNER JOIN proyecto p ON p.ID = c.ID_Proyecto WHERE p.Nombre = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("NombreUsuario"), nombreProyecto);
    }

    /**
     * Asigna un informe de error a un programador.
     *
     * @param nombreProyecto
     */

    public void assignBugToProgrammer(int bugReportId, String programmerUsername) {
        String query = "INSERT INTO seasigna (ID_Informe_Error, Programador) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, bugReportId);
            if (programmerUsername == null || programmerUsername.isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, programmerUsername);
            }
            return ps;
        });
    }

    /**
     * Obtiene los detalles de un informe de error.
     *
     * @param bugId
     * @return un objeto InformeError
     */

    public InformeError getBugReportDetails(int bugId) {
        String query = "SELECT ie.ID, ie.Titulo, ie.Prioridad, ie.Estado, ie.FechaCreacion, ie.ID_Proyecto, p.Nombre AS Proyecto, p.Archivado AS Archivado, ie.Reportador, c.Contenido "
                +
                "FROM informe_error AS ie " +
                "LEFT JOIN Comentario AS c ON ie.ID = c.ID_informe_error " +
                "LEFT JOIN Proyecto AS p ON ie.ID_Proyecto = p.ID " +
                "WHERE ie.ID = ?";
        return jdbcTemplate.query(
                con -> {
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, bugId);
                    return ps;
                },
                new InformeErrorRowMapper()).stream().findFirst().orElse(null);
    }

    /**
     * Actualiza la prioridad de un informe de error.
     *
     * @param bugId
     * @param prioridad
     *
     */
    public void updateBugPriority(int bugId, String prioridad) {
        String query = "UPDATE informe_error SET Prioridad = ? WHERE ID = ?";
        jdbcTemplate.update(query, prioridad, bugId);
    }

    /**
     * Actualiza el estado de un informe de error.
     *
     * @param bugId
     * @param estado
     */
    public void updateBugState(int bugId, String estado) {
        String query = "UPDATE informe_error SET Estado = ? WHERE ID = ?";
        jdbcTemplate.update(query, estado, bugId);
    }

    /**
     * Obtiene los proyectos activos.
     *
     * @return una lista de objetos Proyecto
     */
    public List<Proyecto> getActiveProjects() {
        String query = "SELECT * FROM Proyecto WHERE Archivado = false";
        return jdbcTemplate.query(query, new ProyectoRowMapper());
    }

    /**
     * Obtiene los proyectos archivados.
     *
     * @return una lista de objetos Proyecto
     */
    public List<Proyecto> getArchivedProjects() {
        String query = "SELECT * FROM Proyecto WHERE Archivado = true";
        return jdbcTemplate.query(query, new ProyectoRowMapper());
    }

    /**
     * Obtiene el programador asignado a un proyecto
     *
     * @param informeErrorId
     * @return un objeto Usuario
     */
    public Usuario getAssignedProgrammer(int informeErrorId) {
        String query = "SELECT * FROM Usuario JOIN seasigna ON Usuario.NombreUsuario = seasigna.Programador WHERE seasigna.ID_Informe_Error = ?";
        return jdbcTemplate.query(query, new UsuarioRowMapper(), informeErrorId).stream().findFirst().orElse(null);
    }

    /**
     * Obtiene los programadores en activo.
     *
     * @return una lista de objetos Usuario
     */
    public List<Usuario> getAllProgrammers() {
        String query = "SELECT * FROM Usuario WHERE rol = 'PROGRAMADOR' AND Eliminado = false";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Usuario.class));
    }

    /**
     * Actualiza el programador asignado a un informe de error.
     *
     * @param bugId
     * @param programadorNombreUsuario
     */
    public void updateBugProgrammer(int bugId, String programadorNombreUsuario) {
        String query = "UPDATE seasigna SET Programador = ? WHERE ID_Informe_Error = ?";
        jdbcTemplate.update(query, programadorNombreUsuario, bugId);
    }

    /**
     * Elimina el programador asignado a un informe de error.
     *
     * @param bugId
     */
    public void removeBugProgrammer(int bugId) {
        String query = "UPDATE seasigna SET Programador = NULL WHERE seasigna.ID_Informe_Error = ?";
        jdbcTemplate.update(query, bugId);
    }

    /**
     * Obtiene los comentarios de un informe de error.
     *
     * @param bugId
     * @return una lista de objetos Comentario
     */
    public List<Comentario> getCommentsByBugId(int bugId) {
        String query = "SELECT * FROM Comentario WHERE ID_Informe_Error = ? ORDER BY FechaCreacion ASC";
        List<Comentario> comentarios = jdbcTemplate.query(query, new ComentarioRowMapper(), bugId);
        for (Comentario comentario : comentarios) {
            List<Adjunto> adjuntos = getAttachmentsByCommentId(comentario.getId());
            comentario.setAdjuntos(adjuntos);
        }
        return comentarios;
    }

    /**
     * Obtiene los adjuntos de un comentario.
     *
     * @param commentId
     * @return una lista de objetos Adjunto
     */
    public List<Adjunto> getAttachmentsByCommentId(int commentId) {
        String query = "SELECT * FROM Adjunto WHERE ID_Comentario = ?";
        return jdbcTemplate.query(query, new AdjuntoRowMapper(), commentId);
    }

    /**
     * Crea un comentario en un informe de error.
     *
     * @param bugId
     * @param nombreUsuario
     * @param contenido
     * @param adjunto
     */
    public void createComment(int bugId, String nombreUsuario, String contenido, MultipartFile adjunto) {
        String commentQuery = "INSERT INTO Comentario (ID_informe_error, NombreUsuario, Contenido, FechaCreacion) VALUES (?, ?, ?, NOW())";
        KeyHolder commentKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(commentQuery, new String[] { "ID" });
            ps.setInt(1, bugId);
            ps.setString(2, nombreUsuario); // nombre de usuario del usuario autenticado
            ps.setString(3, contenido);
            return ps;
        }, commentKeyHolder);
        Number commentKey = commentKeyHolder.getKey();
        if (commentKey == null) {
            throw new RuntimeException("No pudo obtenerse el identificador del comentario");
        }
        int commentId;
        try {
            commentId = commentKey.intValue();
        } catch (InvalidDataAccessApiUsageException e) {
            throw new RuntimeException("La inserción del comentario no generó ninguna clave", e);
        }
        if (!adjunto.isEmpty()) {
            // Aquí reutilizamos el código de manejo de archivos adjuntos de createBugReport
            if (adjunto.getSize() > 5242880) { // 5 Megabytes
                throw new RuntimeException(
                        "El archivo adjunto es demasiado grande. El tamaño máximo permitido es de 5 MB.");
            }
            String uploadsDir = "adjuntos/";
            String currentDirectory = "";
            try {
                currentDirectory = new File(".").getCanonicalPath();
            } catch (IOException e) {
                throw new RuntimeException("Error al obtener el directorio actual", e);
            }
            String realPathtoUploads = currentDirectory + File.separator + uploadsDir;
            File uploadDir = new File(realPathtoUploads);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String orgName = adjunto.getOriginalFilename();
            // Solo guarda la parte relativa de la ruta
            String filePath = uploadsDir + orgName;
            File dest = new File(realPathtoUploads + orgName);
            try {
                adjunto.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                throw new RuntimeException("Error al guardar el archivo adjunto", e);
            }
            String attachmentQuery = "INSERT INTO Adjunto (ID_Comentario, Ruta, Nombre) VALUES (?, ?, ?)";
            jdbcTemplate.update(attachmentQuery, commentId, filePath, orgName);

        }
    }

    /**
     * Obtiene los datos de los informes de error activos, independientemente de si
     * pertenecen a proyectos archivados o no.
     *
     * @return una lista de listas de cadenas que representa los datos de la tabla
     */
    public List<List<String>> getActiveBugTableData() {
        String query = "SELECT informe_error.ID, Proyecto.Nombre, informe_error.Reportador, informe_error.Titulo, informe_error.Prioridad, informe_error.Estado, informe_error.FechaCreacion, Proyecto.Archivado FROM informe_error JOIN Proyecto ON informe_error.ID_Proyecto = Proyecto.ID WHERE informe_error.Estado != 'SOLUCIONADO' AND informe_error.Estado != 'DESCARTADO'";
        return jdbcTemplate.query(query, rs -> {
            List<List<String>> tableData = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // Agregar la fila superior con los nombres de los campos
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            tableData.add(columnNames);
            // Agregar las filas de datos
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 7) { // Si es la columna de la fecha
                        String fecha = rs.getString(i);
                        try {
                            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                            LocalDateTime dateTime = LocalDateTime.parse(fecha, inputFormatter);
                            String fechaFormateada = dateTime.format(outputFormatter);
                            rowData.add(fechaFormateada);
                        } catch (DateTimeParseException e) {
                            rowData.add(fecha); // Si la fecha no se puede analizar, simplemente agregamos la cadena
                                                // original
                        }
                    } else {
                        rowData.add(rs.getString(i));
                    }
                }
                boolean isArchived = rs.getBoolean("Archivado");
                rowData.add(isArchived ? "Yes" : "No");
                tableData.add(rowData);
            }
            return tableData;
        });
    }

    /**
     * Obtiene los datos de los informes de error activos que pertenecen a proyectos
     * activos.
     *
     * @return una lista de listas de cadenas que representa los datos de la tabla
     */
    public List<List<String>> getActiveBugFromActiveProjectsTableData() {
        String query = "SELECT informe_error.ID, Proyecto.Nombre, informe_error.Reportador, informe_error.Titulo, informe_error.Prioridad, informe_error.Estado, informe_error.FechaCreacion FROM informe_error JOIN Proyecto ON informe_error.ID_Proyecto = Proyecto.ID WHERE informe_error.Estado != 'SOLUCIONADO' AND informe_error.Estado != 'DESCARTADO' AND Proyecto.Archivado = false";
        return jdbcTemplate.query(query, rs -> {
            List<List<String>> tableData = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // Agregar la fila superior con los nombres de los campos
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            tableData.add(columnNames);
            // Agregar las filas de datos
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 7) { // Si es la columna de la fecha
                        String fecha = rs.getString(i);
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(fecha, inputFormatter);
                        String fechaFormateada = dateTime.format(outputFormatter);
                        rowData.add(fechaFormateada);
                    } else {
                        rowData.add(rs.getString(i));
                    }
                }
                tableData.add(rowData);
            }
            return tableData;
        });
    }

    /**
     * Obtiene los datos de los informes de error archivados.
     *
     * @return una lista de listas de cadenas que representa los datos de la tabla
     */
    public List<List<String>> getArchivedBugTableData() {
        String query = "SELECT informe_error.ID, Proyecto.Nombre, informe_error.Reportador, informe_error.Titulo, informe_error.Prioridad, informe_error.Estado, informe_error.FechaCreacion FROM informe_error JOIN Proyecto ON informe_error.ID_Proyecto = Proyecto.ID WHERE informe_error.Estado = 'SOLUCIONADO' OR informe_error.Estado = 'DESCARTADO'";
        return jdbcTemplate.query(query, rs -> {
            List<List<String>> tableData = new ArrayList<>();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // Agregar la fila superior con los nombres de los campos
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }
            tableData.add(columnNames);
            // Agregar las filas de datos
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 7) { // Si es la columna de la fecha
                        String fecha = rs.getString(i);
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        LocalDateTime dateTime = LocalDateTime.parse(fecha, inputFormatter);
                        String fechaFormateada = dateTime.format(outputFormatter);
                        rowData.add(fechaFormateada);
                    } else {
                        rowData.add(rs.getString(i));
                    }
                }
                tableData.add(rowData);
            }
            return tableData;
        });
    }

    /**
     * Obtiene los cloaboradores de un proyecto.
     *
     * @param projectId
     * @return una lista de nombres de usuario
     */
    public List<String> getCollaboratorsByProjectId(int projectId) {
        String query = "SELECT u.NombreUsuario FROM Usuario u INNER JOIN Colabora c ON u.NombreUsuario = c.Colaborador INNER JOIN Proyecto p ON p.ID = c.ID_Proyecto WHERE p.ID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("NombreUsuario"), projectId);
    }

    /**
     * Obtiene los colaboradores de un proyecto a través de un informe de error.
     *
     * @param bugId
     * @return una lista de nombres de usuario
     */
    public List<String> getProjectCollaboratorsByBugId(int bugId) {
        String query = "SELECT u.NombreUsuario FROM Usuario u INNER JOIN Colabora c ON u.NombreUsuario = c.Colaborador INNER JOIN Proyecto p ON p.ID = c.ID_Proyecto INNER JOIN informe_error i ON p.ID = i.ID_Proyecto WHERE i.ID = ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("NombreUsuario"), bugId);
    }

    /**
     * Actualiza el nombre de un proyecto.
     *
     * @param projectId
     * @param newName
     */
    public void updateProjectName(int projectId, String newName) {
        String query = "UPDATE Proyecto SET Nombre = ? WHERE ID = ?";
        jdbcTemplate.update(query, newName, projectId);
    }

    /**
     * Archiva un proyecto.
     *
     * @param projectId
     */
    public void archiveProject(int projectId) {
        String query = "UPDATE Proyecto SET Archivado = true WHERE ID = ?";
        jdbcTemplate.update(query, projectId);
    }

    /**
     * Desarchiva un proyecto.
     *
     * @param projectId
     */
    public void unarchiveProject(int projectId) {
        String query = "UPDATE Proyecto SET Archivado = false WHERE ID = ?";
        jdbcTemplate.update(query, projectId);
    }

    /**
     * Asigna colaboradores a un proyecto.
     *
     * @param projectId
     * @param usernames
     */
    public void assignCollaboratorsToProject(int projectId, List<String> usernames) {
        // Primero, obtenemos la lista de colaboradores actuales en el proyecto
        String currentCollaboratorsQuery = "SELECT colaborador FROM colabora WHERE id_proyecto = ?";
        List<String> currentCollaborators = jdbcTemplate.queryForList(currentCollaboratorsQuery, String.class,
                projectId);

        // Luego, eliminamos todas las asociaciones de colaboradores con el proyecto
        String deleteQuery = "DELETE FROM colabora WHERE id_proyecto = ?";
        jdbcTemplate.update(deleteQuery, projectId);

        // Después, para cada usuario en la lista de nombres de usuario proporcionada,
        // si no están actualmente asociados con el proyecto, los asociamos
        String checkQuery = "SELECT COUNT(*) FROM colabora WHERE id_proyecto = ? AND colaborador = ?";
        String insertQuery = "INSERT INTO colabora (id_proyecto, colaborador) VALUES (?, ?)";
        for (String username : usernames) {
            Integer count = jdbcTemplate.queryForObject(checkQuery, Integer.class, projectId, username);
            if (count == null || count == 0) {
                jdbcTemplate.update(insertQuery, projectId, username);
            }
        }

        // Finalmente, para cada colaborador que estaba en la lista original pero que no
        // está en la nueva lista, desasignamos sus informes de error
        String updateBugReportsQuery = "UPDATE seasigna SET Programador = NULL WHERE Programador = ? AND ID_Informe_Error IN (SELECT ID FROM informe_error WHERE ID_Proyecto = ?)";
        for (String collaborator : currentCollaborators) {
            if (!usernames.contains(collaborator)) {
                jdbcTemplate.update(updateBugReportsQuery, collaborator, projectId);
            }
        }
    }

    /**
     * Crea un proyecto en la base de datos.
     *
     * @param nombre
     */
    public void createProject(String nombre) {
        String query = "INSERT INTO proyecto (Nombre, FechaCreacion, Archivado) VALUES (?, NOW(), false)";
        jdbcTemplate.update(query, nombre);
    }

    /**
     * Obtiene los emails de los administradores.
     *
     * @return una lista de emails
     */
    public List<String> getAdminEmails() {
        String query = "SELECT Email FROM Usuario WHERE Rol = 'ADMINISTRADOR' AND Eliminado = false";
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("Email"));
    }

    /**
     * Obtiene un mapa de todos los informes de error y sus programadores asignados.
     *
     * @return un mapa de ID de informe de error a nombre de usuario del programador
     *         asignado
     */
    public Map<Integer, String> getAssignedToAll() {
        Map<Integer, String> assignedToAll = new HashMap<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT informe_error.id, seasigna.Programador " +
                        "FROM informe_error " +
                        "LEFT JOIN seasigna ON informe_error.id = seasigna.ID_informe_error");
        for (Map<String, Object> row : rows) {
            Integer id = (Integer) row.get("id");
            String assignedTo = (String) row.get("Programador");
            if (assignedTo == null) {
                assignedTo = "---";
            }
            assignedToAll.put(id, assignedTo);
        }
        return assignedToAll;
    }

}