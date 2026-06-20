const map = L.map('map').setView(
    [4.7110, -74.0721],
    12
);


//Dibuja el mapa

L.tileLayer(
    'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
    {
        attribution:
            '&copy; OpenStreetMap'
    }
).addTo(map);


//prueba
const vehiculos = [

    {
        id: "VH001",
        lat: 4.7110,
        lng: -74.0721,
        estado: "En movimiento"
    },

    {
        id: "VH002",
        lat: 4.7300,
        lng: -74.0500,
        estado: "Detenido"
    },

    {
        id: "VH003",
        lat: 4.6900,
        lng: -74.1000,
        estado: "Sin señal"
    }

];

//crear marcadores

vehiculos.forEach(v => {

    L.marker([
        v.lat,
        v.lng
    ])
        .addTo(map)
        .bindPopup(

            `<b>${v.id}</b><br>Estado: ${v.estado}`

        );

});