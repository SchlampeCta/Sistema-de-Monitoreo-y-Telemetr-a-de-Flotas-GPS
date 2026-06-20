const map = L.map('map').setView(
    [4.7110, -74.0721],
    12
);

// Fondo del mapa
L.tileLayer(
    'https://tile.openstreetmap.org/{z}/{x}/{y}.png', //dibuja mapa
    {
        attribution: '&copy; OpenStreetMap'
    }
).addTo(map);

// guarda marcadores reales
let markers = {};




//crear marcadores

async function cargarVehiculos() {
    try {
        const res = await fetch("http://localhost:8080/vehicles");
        const vehiculos = await res.json();

        vehiculos.forEach(v => {

            if (markers[v.vehiculo_id]) {

                // actualizar posición
                markers[v.vehiculo_id].setLatLng([v.lat, v.lng]);

                markers[v.vehiculo_id].setPopupContent(
                    `<b>${v.vehiculo_id}</b><br>${v.estado}`
                );

            } else {

                // crear marcador nuevo
                const marker = L.marker([v.lat, v.lng])
                    .addTo(map)
                    .bindPopup(
                        `<b>${v.vehiculo_id}</b><br>${v.estado}`
                    );

                markers[v.vehiculo_id] = marker;
            }
        });

    } catch (error) {
        console.error("Error cargando vehículos:", error);
    }
}