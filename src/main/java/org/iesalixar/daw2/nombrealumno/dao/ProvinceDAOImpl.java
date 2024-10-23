package org.iesalixar.daw2.nombrealumno.dao;

import org.iesalixar.daw2.nombrealumno.entity.Province;
import org.iesalixar.daw2.nombrealumno.entity.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAOImpl implements ProvinceDAO {

    public List<Province> listAllProvinces() throws SQLException {
        List<Province> provinces = new ArrayList<>();
        String query = "SELECT p.id as id_province, p.code as code_province, p.name as name_province, r.id as id_region, r.code as code_region, r.name as name_region from provinces p inner join regions r on p.id_region = r.id;";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id_province = resultSet.getInt("id_province");
                String code_province = resultSet.getString("code_province");
                String name_province = resultSet.getString("name_province");

                int id_region = resultSet.getInt("id_region");
                String code_region = resultSet.getString("code_region");
                String name_region = resultSet.getString("name_region");

                Region region = new Region(id_region, code_region, name_region);
                Province province = new Province(id_province, code_province, name_province, region);

                provinces.add(province);
            }
        }

        return provinces;
    }

    @Override
    public void insertProvince(Province province) throws SQLException {
        String query = "INSERT INTO provinces (code, name, id_region) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, province.getCode());
            preparedStatement.setString(2, province.getName());
            preparedStatement.setInt(3, province.getRegion().getId());
            preparedStatement.executeUpdate();
        }
    }

}
