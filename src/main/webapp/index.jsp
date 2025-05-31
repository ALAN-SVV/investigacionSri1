<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31/5/2025
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema de Facturación Electrónica</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 30px;
        }
        .menu {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }
        .card {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            text-align: center;
            transition: transform 0.3s;
            text-decoration: none;
            color: #333;
            border: 1px solid #ddd;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            background: #e9ecef;
        }
        .card h2 {
            color: #3498db;
            margin-top: 0;
        }
        .footer {
            text-align: center;
            margin-top: 30px;
            color: #7f8c8d;
            font-size: 0.9em;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Sistema de Facturación Electrónica</h1>

    <div class="menu">
        <a href="GenerarFactura" class="card">
            <h2>Nueva Factura</h2>
            <p>Crear una nueva factura electrónica</p>
        </a>

        <a href="consultar-facturas.jsp" class="card">
            <h2>Consultar Facturas</h2>
            <p>Buscar y visualizar facturas emitidas</p>
        </a>

        <a href="configuracion.jsp" class="card">
            <h2>Configuración</h2>
            <p>Configurar parámetros del sistema</p>
        </a>

        <a href="acerca-de.jsp" class="card">
            <h2>Acerca De</h2>
            <p>Información sobre el sistema</p>
        </a>
    </div>

    <div class="footer">
        <p>Sistema desarrollado para el SRI | Versión 1.0.0</p>
        <p>Usuario: Elvis Pachacama | RUC: 1719284752001</p>
    </div>
</div>
</body>
</html>