package postgre_db;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

import clases.*;

public class PostgreDB {

	private static Connection getConexion(){
		Connection con = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost/postgres","postgres","java");
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static Vector<Estacion> recuperarEstaciones(Optional<Integer> id, Optional<String> nombre, Optional<LocalTime> apertura, Optional<LocalTime> cierre, Optional<Boolean> operativa){
		Vector<Estacion> salida = new Vector<Estacion>();
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * FROM tp_died.estacion";
		boolean faltaWhere = true;
		
		if(id.isPresent()) {
			consulta+=(" WHERE id = "+id.get());
			faltaWhere = false;
		}
		
		if(nombre.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=(" nombre LIKE '%"+nombre.get()+"%'");
		}
		
		if(apertura.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=(" apertura = '"+apertura.get().format(DateTimeFormatter.ofPattern("HH:mm"))+"'");
		}
		
		if(cierre.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=(" cierre = '"+cierre.get().format(DateTimeFormatter.ofPattern("HH:mm"))+"'");
		}
		
		if(operativa.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=(" operativa = "+operativa.get());
		}
		
		consulta+=(" ORDER BY 1");
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer idS = resultado.getInt(1);
				String nombreS = resultado.getString(2);
				LocalTime aperturaS = LocalTime.parse(resultado.getString(3),DateTimeFormatter.ofPattern("HH:mm:ss"));
				LocalTime cierreS = LocalTime.parse(resultado.getString(4),DateTimeFormatter.ofPattern("HH:mm:ss"));;
				Boolean operativaS = resultado.getBoolean(5);
				
				salida.add(new Estacion(idS,nombreS,aperturaS,cierreS,operativaS));
				
			}
			System.out.println("Consulta realizada: " +consulta);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public static void cargarEstacion(Integer id, String nombre, LocalTime apertura, LocalTime cierre, Boolean operativa) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement("INSERT INTO tp_died.estacion VALUES (?,?,?,?,?)");
			sentencia.setInt(1,id);
			sentencia.setString(2, nombre);
			sentencia.setObject(3, apertura);
			sentencia.setObject(4, cierre);
			sentencia.setBoolean(5, operativa);
			
			sentencia.executeUpdate();
			
			System.out.println("Inserción realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public static void eliminarEstacion(Integer id) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("DELETE FROM tp_died.estacion WHERE id = ?");
			sentencia.setInt(1,id);
			sentencia.executeUpdate();
			
			System.out.println("Eliminación realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	public static void modificarEstacion(Integer id, String nombre, LocalTime apertura, LocalTime cierre) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("UPDATE tp_died.estacion SET nombre = ?, apertura = ?, cierre = ? WHERE id = ?");
			sentencia.setString(1, nombre);
			sentencia.setObject(2, apertura);
			sentencia.setObject(3, cierre);
			sentencia.setInt(4, id);
			sentencia.executeUpdate();
			
			System.out.println("Actualización realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public static Vector<Mantenimiento> recuperarMantenimientos(Optional<Integer> id, Optional<Integer> estacion){
		Vector<Mantenimiento> salida = new Vector<Mantenimiento>();
		
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * FROM tp_died.mantenimiento";
		boolean faltaWhere = true;
		
		if(id.isPresent()) {
			consulta+= (" WHERE id = "+id.get());
			faltaWhere = false;
		}
		
		if(estacion.isPresent()){
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			
			consulta+=(" id_estacion = "+estacion.get());
		}
		
		consulta+=(" ORDER BY 1");
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			Vector<Estacion> estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
			
			while(resultado.next()) {
				Integer idS = resultado.getInt(1);
				Integer idEstacionS = resultado.getInt(2);
				LocalDateTime inicioS = LocalDateTime.parse(resultado.getString(3),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				LocalDateTime finS;
				if(resultado.getObject(4) != null) finS = LocalDateTime.parse(resultado.getString(4),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				else finS = null;
				String observacionesS = resultado.getString(5);
				
				Estacion estacionS = estaciones.stream().filter(est -> est.getId() == idEstacionS).findFirst().get();
				
				salida.add(new Mantenimiento(idS,estacionS,inicioS,finS,observacionesS));
			}
			
			System.out.println("Consulta realizada: " +consulta);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public static void cargarMantenimiento(Integer id, Integer estacion, String observaciones) {
		Connection conexion = getConexion();
		Statement sentencia = null;
		
		try {
			String insertarMantenimiento = "INSERT INTO tp_died.mantenimiento VALUES ("+id+","+estacion+",'"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+"',NULL,'"+observaciones+"')";
			String actualizarEstacion = ("UPDATE tp_died.estacion SET operativa = false WHERE id = "+estacion);
			
			conexion.setAutoCommit(false);	
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(insertarMantenimiento);
			sentencia.executeUpdate(actualizarEstacion);
			conexion.commit();
			
			System.out.println("Inserción realizada: "+insertarMantenimiento);
			System.out.println("Actualización realizada: "+actualizarEstacion);
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
	}
	
	public static void finalizarMantenimiento(Integer estacion) {
		Connection conexion = getConexion();
		Statement sentencia = null;
		
		try {
			String actualizarMantenimiento = ("UPDATE tp_died.mantenimiento SET fecha_fin = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+"' WHERE id_estacion = "+estacion+" AND fecha_fin IS NULL");
			String actualizarEstacion = ("UPDATE tp_died.estacion SET operativa = true WHERE id = "+estacion);
			
			conexion.setAutoCommit(false);
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(actualizarMantenimiento);
			sentencia.executeUpdate(actualizarEstacion);
			conexion.commit();
			
			System.out.println("Actualización realizada: "+actualizarMantenimiento);
			System.out.println("Actualización realizada: "+actualizarEstacion);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
	}

	public static Vector<Color> recuperarColores() {
		Vector<Color> salida = new Vector<Color>();
			
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT id, color_r, color_g, color_b FROM tp_died.linea ORDER BY id");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer r = resultado.getInt(2);
				Integer g = resultado.getInt(3);
				Integer b = resultado.getInt(4);
						
				salida.add(new Color(r,g,b));
			}
			
			System.out.println("Consulta realizada: " +sentencia.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}

	public static Vector<Linea> recuperarLineas(Optional<Integer> id, Optional<String> nombre, Optional<Color> color, Optional<Boolean> activa){
		Vector<Linea> salida = new Vector<Linea>();
		
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * FROM tp_died.linea";
		boolean faltaWhere = true;
		
		if(id.isPresent()) {
			consulta+=" WHERE id = "+id.get();
			faltaWhere = false;
		}
		if(nombre.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+= " nombre = "+nombre.get();
		}
		if(color.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=" color_r = "+color.get().getRed()+" AND color_g = "+color.get().getGreen()+" AND color_b = "+color.get().getBlue();
		}
		if(activa.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+=" activa = "+activa.get();
		}
		
		consulta+=" ORDER BY 1";
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer idS = resultado.getInt(1);
				String nombreS = resultado.getString(2);
				Color colorS = new Color(resultado.getInt(3),resultado.getInt(4),resultado.getInt(5));
				Boolean activaS = resultado.getBoolean(6);
				
				salida.add(new Linea(idS,nombreS,colorS,activaS));
			}
			
			System.out.println("Consulta realizada: " +consulta);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public static void cargarLinea(Integer id, String nombre, Color color, Boolean activa) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement("INSERT INTO tp_died.linea VALUES (?,?,?,?,?,?)");
			
			sentencia.setInt(1, id);
			sentencia.setString(2, nombre);
			sentencia.setInt(3, color.getRed());
			sentencia.setInt(4, color.getGreen());
			sentencia.setInt(5, color.getBlue());
			sentencia.setBoolean(6, activa);
			
			sentencia.executeUpdate();
			
			System.out.println("Inserción realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}

	public static void eliminarLinea(Integer id) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("DELETE FROM tp_died.linea WHERE id = ?");
			sentencia.setInt(1,id);
			sentencia.executeUpdate();
			
			System.out.println("Eliminación realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}

	public static void modificarLinea(Integer id, String nombre, Color color) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("UPDATE tp_died.linea SET nombre = ?, color_r = ?, color_g = ?, color_b = ? WHERE id = ?");
			sentencia.setString(1, nombre);
			sentencia.setInt(2, color.getRed());
			sentencia.setInt(3, color.getGreen());
			sentencia.setInt(4, color.getBlue());
			sentencia.setInt(5, id);
			sentencia.executeUpdate();
			
			System.out.println("Actualización realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	public static void cambiarEstadoLinea(Integer id, Boolean activa) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("UPDATE tp_died.linea SET activa = ? WHERE id = ?");
			sentencia.setBoolean(1, activa);
			sentencia.setInt(2,id);
			sentencia.executeUpdate();
			
			System.out.println("Actualización realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}

	public static Vector<Ruta> recuperarRutas(Optional<Integer> linea, Optional<Integer> origen, Optional<Integer> destino) {
		Vector<Ruta> salida = new Vector<Ruta>();
		
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		String consulta = "SELECT * FROM tp_died.ruta";
		boolean faltaWhere = true;
		
		if(linea.isPresent()) {
			consulta+=" WHERE id_linea = "+linea.get();
			faltaWhere = false;
		}
		if(origen.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+= " id_origen = "+origen.get();
		}
		if(destino.isPresent()) {
			if(faltaWhere) {
				consulta+=(" WHERE");
				faltaWhere = false;
			}
			else {
				consulta+=(" AND");
			}
			consulta+= "id_destino = "+destino.get();
		}
		
		consulta+=" ORDER BY 1";
		
		try {
			sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
			
			Vector<Estacion> estaciones = PostgreDB.recuperarEstaciones(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
			Vector<Linea> lineas = PostgreDB.recuperarLineas(Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null), Optional.ofNullable(null));
			
			while(resultado.next()) {
				Integer idS = resultado.getInt(1);
				Integer idOrigenS = resultado.getInt(2);
				Integer idDestinoS = resultado.getInt(3);
				Integer idLineaS = resultado.getInt(4);
				Double distanciaS = resultado.getDouble(5);
				Integer duracionS = resultado.getInt(6);
				Integer capacidadS = resultado.getInt(7);
				Boolean activaS = resultado.getBoolean(8);
				Double costoS = resultado.getDouble(9);
				
				Estacion destinoS = estaciones.stream().filter(est -> est.getId() == idDestinoS).findFirst().get();
				Estacion origenS = estaciones.stream().filter(est -> est.getId() == idOrigenS).findFirst().get();
				Linea lineaS = lineas.stream().filter(lin -> lin.getId() == idLineaS).findFirst().get();
				
				salida.add(new Ruta(idS,origenS,destinoS,lineaS,distanciaS,duracionS,capacidadS,activaS,costoS));
			}
			
			System.out.println("Consulta realizada: " +consulta);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}
	
	public static void cambiarEstadoRuta(Integer id, Boolean activa) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("UPDATE tp_died.ruta SET activa = ? WHERE id = ?");
			sentencia.setBoolean(1, activa);
			sentencia.setInt(2,id);
			sentencia.executeUpdate();
			
			System.out.println("Actualización realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conexion.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	public static void cargarRuta(Integer id, Integer id_origen, Integer id_destino, Integer id_linea, Double distancia, Integer duracion, Integer capacidad, Boolean activa, Double costo) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement("INSERT INTO tp_died.ruta VALUES (?,?,?,?,?,?,?,?,?)");
			
			sentencia.setInt(1, id);
			sentencia.setInt(2, id_origen);
			sentencia.setInt(3, id_destino);
			sentencia.setInt(4, id_linea);
			sentencia.setDouble(5, distancia);
			sentencia.setInt(6, duracion);
			sentencia.setInt(7, capacidad);
			sentencia.setBoolean(8, activa);
			sentencia.setDouble(9, costo);
			
			sentencia.executeUpdate();
			
			System.out.println("Inserción realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}

	public static void eliminarTrayecto(Integer idLinea) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("DELETE FROM tp_died.ruta WHERE id_linea = ?");
			sentencia.setInt(1,idLinea);
			sentencia.executeUpdate();
			
			System.out.println("Eliminación realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}

	public static void modificarRuta(Integer id, Double distancia, Integer duracion, Integer capacidad, Double costo) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		
		try {
			sentencia = conexion.prepareStatement("UPDATE tp_died.ruta SET distancia = ?, duracion = ?, capacidad = ?, costo = ? WHERE id = ?");
			sentencia.setDouble(1, distancia);
			sentencia.setInt(2,duracion);
			sentencia.setInt(3,capacidad);
			sentencia.setDouble(4, costo);
			sentencia.setInt(5,id);
			sentencia.executeUpdate();
			
			System.out.println("Actualización realizada: "+sentencia.toString());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
	public static Map<Estacion,LocalDateTime> recuperarUltimosMantenimientos(){
		Map<Estacion,LocalDateTime> salida = new LinkedHashMap<Estacion,LocalDateTime>();
		
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT es.id, es.nombre, MAX(ma.fecha_inicio) "
												+ "FROM tp_died.estacion es LEFT JOIN tp_died.mantenimiento ma ON es.id = ma.id_estacion "
												+ "GROUP BY es.id, es.nombre ORDER BY 1");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer id = resultado.getInt(1);
				String nombre = resultado.getString(2);
				LocalDateTime fecha;
				if(resultado.getObject(3) == null) fecha = LocalDateTime.MIN;
				else  fecha = LocalDateTime.parse(resultado.getString(3),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				
				LocalTime a = null, c = null;
				salida.put(new Estacion(id,nombre,a,c,true), fecha);
			}
			
			System.out.println("Consulta realizada: " +sentencia.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}

	public static Vector<Boleto> recuperarBoletos(){
		Vector<Boleto> salida = new Vector<Boleto>();
		
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		ResultSet resultado = null;
		
		try {
			sentencia = conexion.prepareStatement("SELECT bo.numero, e1.id, e1.nombre, e2.id, e2.nombre, bo.email_cliente, bo.nombre_cliente, bo.fechaventa, bo.costo, bo.camino "
					+ "FROM tp_died.boleto bo, tp_died.estacion e1, tp_died.estacion e2 "
					+ "WHERE bo.id_origen = e1.id AND bo.id_destino = e2.id");
			resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				Integer nro = resultado.getInt(1);
				Integer id_o = resultado.getInt(2);
				String nombre_o = resultado.getString(3);
				Integer id_d = resultado.getInt(4);
				String nombre_d = resultado.getString(5);
				String mail = resultado.getString(6);
				String nombre = resultado.getString(7);
				LocalDateTime fecha = LocalDateTime.parse(resultado.getString(8),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				Double costo = resultado.getDouble(9);
				String camino = resultado.getString(10);
				
				LocalTime a = null, c = null;
				Estacion origen = new Estacion(id_o,nombre_o,a,c,true);
				Estacion destino = new Estacion(id_d,nombre_d,a,c,true);
				
				salida.add(new Boleto(nro,origen,destino,camino,mail,nombre,fecha,costo));
			}
			
			System.out.println("Consulta realizada: " +sentencia.toString());
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(resultado!=null) try { resultado.close();} catch(SQLException e) {e.printStackTrace();}
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
		
		return salida;
	}

	public static void cargarBoleto(Integer nro, Integer origen, Integer destino, String mail, String nombre, LocalDateTime fecha, Double costo, String camino) {
		Connection conexion = getConexion();
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement("INSERT INTO tp_died.boleto VALUES (?,?,?,?,?,'"+fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+"',?,?)");
			
			sentencia.setInt(1, nro);
			sentencia.setInt(2, origen);
			sentencia.setInt(3, destino);
			sentencia.setString(4, mail);
			sentencia.setString(5, nombre);
			sentencia.setDouble(6, costo);
			sentencia.setString(7, camino);
			
			sentencia.executeUpdate();
			
			System.out.println("Inserción realizada: "+sentencia.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(sentencia!=null) try { sentencia.close();} catch(SQLException e) {e.printStackTrace();}
			if(conexion!=null) try { conexion.close();} catch(SQLException e) {e.printStackTrace();}
		}
	}
	
}