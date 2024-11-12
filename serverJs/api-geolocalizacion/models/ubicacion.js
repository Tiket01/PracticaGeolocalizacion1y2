// models/ubicacion.js
const { DataTypes } = require('sequelize');
const sequelize = require('../config');

const Ubicacion = sequelize.define('Ubicacion', {
    pk_ubicacion: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    latitud: {
        type: DataTypes.NUMERIC(12, 8),
        allowNull: false
    },
    longitud: {
        type: DataTypes.NUMERIC(10, 8),
        allowNull: false
    },
    nombre_ubicacion: {
        type: DataTypes.STRING(50),
        allowNull: false
    },
    estado: {
        type: DataTypes.INTEGER,
        defaultValue: 1
    }
}, {
    tableName: 'ubicacion',  
    timestamps: true         // Agrega columnas createdAt y updatedAt automáticamente
});

module.exports = Ubicacion;
