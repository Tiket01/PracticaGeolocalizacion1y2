const express = require('express');
const bodyParser = require('body-parser');
const sequelize = require('./config');
const Ubicacion = require('./models/ubicacion');

const app = express();
app.use(bodyParser.json());

// Conectar a la base de datos
sequelize.sync().then(() => {
    console.log('Conectado a la base de datos');
});

// Endpoint para agregar una ubicación
app.post('/ubicaciones', async (req, res) => {
    try {
        const { latitud, longitud, nombre_ubicacion } = req.body;
        const ubicacion = await Ubicacion.create({ latitud, longitud, nombre_ubicacion });
        res.status(201).json(ubicacion);
    } catch (error) {
        console.error("Error al guardar la ubicación:", error);
        res.status(500).json({ error: error.message });
    }
});

// Endpoint para obtener todas las ubicaciones (usa GET en lugar de POST)
app.get('/ubicaciones', async (req, res) => {
    try {
        const ubicaciones = await Ubicacion.findAll();
        res.json(ubicaciones);
    } catch (error) {
        console.error("Error al obtener ubicaciones:", error);
        res.status(500).json({ error: error.message });
    }
});

const PORT = 3000;
app.listen(PORT, '0.0.0.0', () => {
    console.log(`Servidor corriendo en http://0.0.0.0:${PORT}`);
});
