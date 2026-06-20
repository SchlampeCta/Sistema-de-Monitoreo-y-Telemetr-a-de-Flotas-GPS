//Conexion backEnd Y frontEnd
const API_URL = "http://localhost:8080";

//CORS por bloqueo
const express = require("express");
const cors = require("cors");

const app = express();

app.use(cors());        
app.use(express.json()); 
app.options("*", cors());



//Consulta backend actualizacion vehiculo

async function cargarVehiculos() {

    try {

        const respuesta = await fetch(
            "http://localhost:8080/vehicles"
        );

        const vehiculos = await respuesta.json();
        const tabla =
            document.getElementById(
                "tablaVehiculos"
            );

        tabla.innerHTML = "";
        vehiculos.forEach(v => {
            //estados vehiculo

            let claseEstado = "";

            if (v.estado === "En movimiento") {
                claseEstado = "estado-movimiento";
            }

            if (v.estado === "Detenido") {
                claseEstado = "estado-detenido";
            }

            if (v.estado === "Sin señal") {
                claseEstado = "estado-senal";
            }

            tabla.innerHTML += `
        <tr>
            <td>${v.vehiculo_id}</td>
            <td>${v.estado}</td>
            <td>${v.last_seen}</td>
            <td>${v.lat}</td>
            <td>${v.lng}</td>
        </tr>
    `;

        });

        console.log(vehiculos);

        actualizarHora();

    } catch (error) {

        console.error(
            "Error al consultar el vehículo",
            error
        );

    }

}
 //Actualizacion tiempo real vehiculos en mapa

async function cargarVehiculosEnMapa() {
    try {
        const res = await fetch("http://localhost:8080/vehicles");
        const vehiculos = await res.json();

        vehiculos.forEach(v => {
            const id = v.vehiculo_id;

            // Si ya existe marcador, lo actualizamos
            if (markers[id]) {
                markers[id].setLatLng([v.lat, v.lng]);
                markers[id].setPopupContent(`
                    <b>${id}</b><br>
                    ${v.estado}
                `);
            }
            else {
                // Crear marcador nuevo
                const marker = L.marker([v.lat, v.lng])
                    .addTo(map)
                    .bindPopup(`
                        <b>${id}</b><br>
                        ${v.estado}
                    `);

                markers[id] = marker;
            }
        });

    } catch (error) {
        console.error("Error cargando vehículos:", error);
    }
}

function actualizarHora() {

    const ahora = new Date();

    const elemento = document.getElementById("ultimaActualizacion");

    if (!elemento) return;

    elemento.innerText =
        "Última actualización: " +
        ahora.toLocaleTimeString();
}





cargarVehiculos();

setInterval(
    cargarVehiculos,
    5000
);