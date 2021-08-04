# Observaciones
-Para conectarse a la base de datos se debe cambiar la url, usuario y contraseña en el método getConexion() de la clase PostgreDB (en el paquete postgre_db).  
-En la carpeta "database" se encuentran los archivos .sql para generar las tablas e insertar los datos.  
-En la carpeta "extra" se encuentran unas imágenes que muestran el mapa de las estaciones, lineas y rutas cargadas indicando la duración/costo/distancia/capacidad de cada ruta.  
-En la carpeta "resources" se encuentra el JAR de las clases de PostgreSQL y la imágen del logo del programa al ejecutarse.  
-Para el cálculo del flujo máximo, el subgrafo considerado fue el resultante de eliminar las rutas entrantes a la estación de origen (hacerla fuente) y las rutas salientes de cada estación destino (hacerlas sumideros).
