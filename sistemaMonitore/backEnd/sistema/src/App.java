import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
//import java.io.IOException;
import java.io.OutputStream;
//import java.net.InetSocketAddress;

import db.mysql;
import modulos.controlador;

public class App {
    public static void main(String[] args) throws Exception {
       mysql.conectar();

       //Instancia clase controlador
       /* 
        controlador c = new controlador();

        System.out.println(
            c.guardarUbicacion(
                "VH-001",
                4.7110,
                -74.0721,
                "2025-06-01T10:00:00Z"
            )
        );*/

        // servidor en el puerto 8080 
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Creamos la ruta /vehicles que busca api.js
        server.createContext("/vehicles", new VehiclesHandler());

        server.setExecutor(null); 
        System.out.println("🚀 Servidor HTTP Backend corriendo en http://localhost:8080/vehicles");
        server.start();
        
    }

        static class VehiclesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // CONFIGURACIÓN DE CABECERAS CORS (Para que Live Server no se bloquee)
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type,Authorization");

            
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            // 2. PROCESAR PETICIÓN GET
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                controlador ctrl = new controlador();
                
                
                ctrl.estadoVehiculo(); 
                
                // Construimos un JSON temporal de prueba mientras automatizas la respuesta del ResultSet
                String jsonResponse = "[{\"vehiculo_id\":\"V1\",\"lat\":4.6097,\"lng\":-74.0817,\"estado\":\"En movimiento\"}]";
                
                // Enviamos la respuesta HTTP al Frontend
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            } else {
                
                exchange.sendResponseHeaders(405, -1);
            }
        }

    }

}
