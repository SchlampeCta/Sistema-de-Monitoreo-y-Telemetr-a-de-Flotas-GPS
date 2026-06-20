//Conexion backEnd Y frontEnd
const API_URL = "http://localhost:8080";



//Consulta backend actualizacion vehiculo

async function cargarVehiculos() {

    try {

        const respuesta = await fetch(
            "http://localhost:8080/vehicles"
        );

        const vehiculos = await respuesta.json();

        console.log(vehiculos);

        actualizarHora();

    } catch (error) {

        console.error(
            "Error al consultar el vehículo",
            error
        );

    }

}


function actualizarHora() {

    const ahora = new Date();

    document.getElementById(
        "ultimaActualizacion"
    ).innerText =
        "Última actualización: " +
        ahora.toLocaleTimeString();

}


cargarVehiculos();

setInterval(
    cargarVehiculos,
    5000
);