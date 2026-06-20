



const vehiculos = [
    { id: "VH-001", lat: 4.7110, lng: -74.0721, detenido: false, lastMove: Date.now() },
    { id: "VH-002", lat: 4.7300, lng: -74.0500, detenido: false, lastMove: Date.now() },
    { id: "VH-003", lat: 4.6900, lng: -74.1000, detenido: true } // detenido fijo
];

function moverVehiculo(v) {

    // VH-003 se queda quieto
    if (v.detenido) return v;

    return {
        ...v,
        lat: Math.max(4.60, Math.min(4.75, v.lat + (Math.random() - 0.5) * 0.002)),
        lng: Math.max(-74.20, Math.min(-73.95, v.lng + (Math.random() - 0.5) * 0.002)),
        lastMove: Date.now()
    };
}

// errores simulados
function generarPayload(v) {

    const error = Math.random() < 0.1;

    if (error) {
        return {
            vehiculoId: v.id,
            lat: null // dato inválido
        };
    }

    return {
        vehiculoId: v.id,
        lat: v.lat,
        lng: v.lng,
        timestamp: new Date().toISOString()
    };
}

async function enviarGPS(v) {
    const payload = generarPayload(v);

    const res = await fetch("http://localhost:8080/gps", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    const data = await res.text();
    console.log("Respuesta backend:", data);
}

setInterval(async () => {

    

    for (let v of vehiculos) {

        const nuevo = moverVehiculo(v);

        v.lat = nuevo.lat;
        v.lng = nuevo.lng;

        console.log("Enviando:", v.id, v.lat, v.lng);
        await enviarGPS(v);
        console.log("Enviado:", v.id);
        
    }

}, Math.random() * 2000 + 3000); // 3–5 segundos


