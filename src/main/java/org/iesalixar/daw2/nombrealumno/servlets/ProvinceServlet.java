package org.iesalixar.daw2.nombrealumno.servlets;

import org.iesalixar.daw2.nombrealumno.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.nombrealumno.dao.RegionDAO;
import org.iesalixar.daw2.nombrealumno.entity.Province;
import org.iesalixar.daw2.nombrealumno.entity.Region;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/provinces")
public class ProvinceServlet extends HttpServlet {

    private ProvinceDAO provinceDAO;
    private RegionDAO regionDAO;

    @Override
    public void init() throws ServletException{
        try {
            provinceDAO = new ProvinceDAOImpl();
            regionDAO = new RegionDAOImpl();
        } catch (Exception e) {
            throw new ServletException("Error al inicializar el RegionDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Manejo para evitar el valor null
            if (action == null) {
                action = "list"; // O cualquier acción predeterminada que desees manejar
            }

            switch (action) {
                case "new":
                    showNewForm(request, response);  // Mostrar formulario para nueva región
                    break;
                case "edit":
                    break;
                default:
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Maneja las solicitudes POST al servlet. Según el parámetro "action", decide qué método invocar.
     * @param request  Solicitud HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException en caso de errores en el servlet.
     * @throws IOException en caso de errores de E/S.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "insert":
                    insertProvince(request, response);  // Insertar nueva región
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listProvinces(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException{
        List<Province> listProvinces = provinceDAO.listAllProvinces(); // Obtener todas las regiones desde el DAO
        request.setAttribute("listProvinces", listProvinces);      // Pasar la lista de regiones a la vista
        request.getRequestDispatcher("province.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Region> listRegions  = regionDAO.listAllRegions();
        request.setAttribute("listRegions", listRegions);      // Pasar la lista de regiones a la vista
        request.getRequestDispatcher("province-form.jsp").forward(request, response); // Redirige a la vista para nueva región
    }

    private void insertProvince(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String code = request.getParameter("code").trim().toUpperCase(); // Convertir a mayúsculas
        String name = request.getParameter("name").trim();
        String id_region = request.getParameter("id_region");

        Province province = new Province();
        province.setCode(code);
        province.setName(name);

        Region region = new Region();
        region = regionDAO.getRegionById(Integer.parseInt(id_region));

        province.setRegion(region);

        try{

            provinceDAO.insertProvince(province);

        }catch (SQLException e) {
            if (e.getSQLState().equals("23505")) { // Código SQL para unique constraint violation
                request.setAttribute("errorMessage", "El código de la región debe ser único.");
                request.getRequestDispatcher("province-form.jsp").forward(request, response);
            } else {
                throw e;
            }
        }

        response.sendRedirect("provinces");
    }

}
