package modulos;

//import java.io.IOException;
import java.sql.Connection;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;


import db.mysql;
import middleware.errores;

public class controlador  {


    // Guardar ubicacion / POST

    public String guardarUbicacion(
            String vehiculoId,
            double lat,
            double lng,
            String timestamp) {

        try {

            // Validar vehiculo
            if (vehiculoId == null || vehiculoId.isEmpty()) {

                errores.badRequest(
                        "vehiculo_id es obligatorio");

                return "400";
            }

            // Validar timestamp
            if (timestamp == null || timestamp.isEmpty()) {

                errores.badRequest(
                        "timestamp es obligatorio");

                return "400";
            }

            // Validar latitud
            if (lat < -90 || lat > 90) {

                errores.badRequest(
                        "latitud inválida");

                return "400";
            }

            // Validar longitud
            if (lng < -180 || lng > 180) {

                errores.badRequest(
                        "longitud inválida");

                return "400";
            }

            // Validar formato fecha
            Instant.parse(timestamp);

            // Insertar
            Connection con = mysql.conectar();

            String sql = "INSERT INTO ubicaciones (vehiculo_id, lat, lng, timestamp) VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, vehiculoId);
            stmt.setDouble(2, lat);
            stmt.setDouble(3, lng);
            stmt.setString(4, timestamp);

            stmt.executeUpdate(); // Se guarda el registro

            con.close();

            System.out.println(
                    "✅ ubicación almacenada correctamente");

            return "200 OK";

        } catch (Exception e) {

            errores.internalError(
                    e.getMessage());

            return "500";
        }
    }

    // Get - Consulta estado vehiculo
    public String estadoVehiculo() {

        try {

            Connection con = mysql.conectar();

            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM ubicaciones ORDER BY vehiculo_id, timestamp DESC";

            ResultSet rs = stmt.executeQuery(sql);

            Map<String, double[]> ultimaPosicion = new HashMap<>();

            while (rs.next()) {

                String vehiculoId = rs.getString("vehiculo_id");

                double lat = rs.getDouble("lat");

                double lng = rs.getDouble("lng");

                String timestamp = rs.getString("timestamp");

                String estado;

                Instant ultimoDato = Instant.parse(timestamp);

                long minutos = Duration.between(
                        ultimoDato,
                        Instant.now())
                        .toMinutes();

                if (minutos >= 2) {

                    estado = "Sin señal";

                } else {

                    if (ultimaPosicion.containsKey(
                            vehiculoId)) {

                        double[] posicionAnterior = ultimaPosicion.get(
                                vehiculoId);

                        if (posicionAnterior[0] == lat &&
                                posicionAnterior[1] == lng) {

                            estado = "Detenido";

                        } else {

                            estado = "En movimiento";
                        }

                    } else {

                        estado = "En movimiento";
                    }
                }

                ultimaPosicion.put(
                        vehiculoId,
                        new double[] { lat, lng });

                System.out.println(
                        "Vehiculo: "
                                + vehiculoId
                                + " | Lat: "
                                + lat
                                + " | Lng: "
                                + lng
                                + " | Estado: "
                                + estado);
            }

            con.close();

        } catch (Exception e) {
            errores.internalError(
                    e.getMessage());

        }
        return "500";

    }

    // Eliminar vehiculo

    public String eliminar(String vehiculoId) {

        try {

            Connection con = mysql.conectar();

            String sql = "DELETE FROM ubicaciones WHERE vehiculo_id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, vehiculoId);

            int filasBorradas = stmt.executeUpdate();

            con.close();

            if (filasBorradas > 0) {

                return "200 OK: Vehículo eliminado";
            }

            return "404 Not Found: Vehículo no existe";

        } catch (Exception e) {

            errores.internalError(
                    e.getMessage());

            return "500";
        }
    }

    public String obtenerVehiculos() {

        try {

            Connection con = mysql.conectar();

            String sql = "SELECT vehiculo_id, lat, lng, timestamp " +
                    "FROM ubicaciones " +
                    "ORDER BY timestamp DESC";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            StringBuilder json = new StringBuilder();

            json.append("[");

            boolean primero = true;

            while (rs.next()) {

                if (!primero) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"vehiculo_id\":\"")
                        .append(rs.getString("vehiculo_id"))
                        .append("\",");

                json.append("\"lat\":")
                        .append(rs.getDouble("lat"))
                        .append(",");

                json.append("\"lng\":")
                        .append(rs.getDouble("lng"))
                        .append(",");

                json.append("\"estado\":\"En movimiento\",");

                json.append("\"last_seen\":\"")
                        .append(rs.getString("timestamp"))
                        .append("\"");

                json.append("}");

                primero = false;
            }

            json.append("]");

            con.close();

            return json.toString();

        } catch (Exception e) {

            errores.internalError(
                    e.getMessage());

            return "[]";
        }
    }

}

