package middleware;

public class errores {

    //Respuesta errores
    public static void badRequest(String mensaje) {
        System.out.println("400 Bad Request: " + mensaje);
    }

    public static void notFound(String mensaje) {
        System.out.println("404 Not Found: " + mensaje);
    }

    public static void internalError(String mensaje) {
        System.out.println("500 Internal Server Error: " + mensaje);
    }
    
}
