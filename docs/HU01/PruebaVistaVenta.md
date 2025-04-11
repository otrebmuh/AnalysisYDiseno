| No. | Objetivo de prueba | Precondiciones | Valores de entrada | Valores esperados de salida |
|-----|-------------------|----------------|-------------------|---------------------------|
| 1 | Probar que el método nuevaVenta() se comporta correctamente con datos válidos | - Empleado con ID 1 existe<br>- Sucursal con ID 1 existe | - idEmpleado: 1L<br>- idSucursal: 1L | Venta creada con ID 1L |
| 2 | Probar que el método nuevaVenta() arroja excepción con empleado inexistente | Empleado con ID 999 no existe | - idEmpleado: 999L<br>- idSucursal: 1L | IllegalArgumentException |
| 3 | Probar que agregarProductoAVenta() funciona con stock suficiente | - Venta con ID 1 existe<br>- Producto "123" existe<br>- Stock suficiente | - idVenta: 1L<br>- codigo: "123"<br>- cantidad: 5 | Cantidad agregada: 5 |
| 4 | Probar que agregarProductoAVenta() valida stock insuficiente | - Venta con ID 1 existe<br>- Producto "123" existe<br>- Stock: 10 unidades | - idVenta: 1L<br>- codigo: "123"<br>- cantidad: 20 | IllegalArgumentException |
| 5 | Probar que agregarProductoAVenta() valida venta inexistente | Venta con ID 999 no existe | - idVenta: 999L<br>- codigo: "123"<br>- cantidad: 5 | IllegalArgumentException |
| 6 | Probar que finalizarVenta() funciona correctamente | - Venta con ID 1 existe<br>- Venta tiene productos | idVenta: 1L | true |
| 7 | Probar que finalizarVenta() valida venta inexistente | Venta con ID 999 no existe | idVenta: 999L | IllegalArgumentException |
| 8 | Probar que eliminarDetalleVenta() funciona correctamente | - Venta existe<br>- Detalle con ID 1 existe | idDetalle: 1L | void (sin excepción) |
| 9 | Probar que eliminarDetalleVenta() valida detalle inexistente | Detalle con ID 999 no existe | idDetalle: 999L | IllegalArgumentException |
| 10 | Probar que obtenerUltimoIdDetalleVenta() funciona correctamente | Existen detalles de venta | No aplica | Último ID de detalle |