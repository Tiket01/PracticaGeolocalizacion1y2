// config.js
const { Sequelize } = require('sequelize');

const sequelize = new Sequelize('geolocalizacion', 'postgres', 'utNay', {
    host: 'localhost',
    dialect: 'postgres',
    logging: console.log  // Activa el logging para ver consultas detalladas
});

module.exports = sequelize;
