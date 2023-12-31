package org.example;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
public class ConexionDB {

    private static Connection conX;
    private static Statement sT;


    public static Connection conexionDB() {

        try {
            conX = DriverManager.getConnection("jdbc:mysql://localhost:3306/db-soporte-incidente", "root", "");
            sT = conX.createStatement();
            return conX;
        } catch (Exception obj) {

            System.out.println("Error en la conexion de la base de datos" + obj);
            System.out.println(obj.fillInStackTrace());
        }
        return null;

    }


    //***********************ALTA CLIENTE
    public static void altaClienteDB(Cliente cli) {
        //validar que el cuit no exista -tambien se podria validar el contrato(codSoporte)
        String consulta = "insert into cliente(idCli,cuit,razonS,nom,ape,dire,cel,mail,contrato,altaCliente)"
                + " values (idCli,?,?,?,?,?,?,?,?,?)";


        try {
            PreparedStatement sqlUp = conX.prepareStatement(consulta);

            sqlUp.setString(1, cli.getCuitCliente());
            sqlUp.setString(2, cli.getRazonSocial());
            sqlUp.setString(3, cli.getNomCliente());
            sqlUp.setString(4, cli.getApeCliente());
            sqlUp.setString(5, cli.getDireCliente());
            sqlUp.setString(6, cli.getCelCliente());
            sqlUp.setString(7, cli.getMailCliente());
            sqlUp.setString(8, cli.getContratos());
            sqlUp.setString(9, LocalDate.now().toString());

            sqlUp.executeUpdate();

            System.out.println("La DB/TABLA CLIENTE se actualizo con exito");

        } catch (SQLException obj) {
            System.out.println("Error en el insert de la tabla Cliente" + obj);
            obj.fillInStackTrace();
        }

    }

    //*************ALTA EMPLEADO
    public static void altaEmpleadoDB(Empleado emp1) {
        //validar que el cuit no exista

        String consulta = "insert into Empleado(idEmpleado,cuitEmpleado,nomEmpleado,apeEmpleado,direEmpleado"
                + ",celEmpleado,mailEmpleado,altaEmpleado,areaEmpleado) values (idEmpleado,?,?,?,?,?,?,?,?)";


        try {
            PreparedStatement sqlUp = conX.prepareStatement(consulta);

            sqlUp.setString(1, emp1.getCuitEmpleado());
            sqlUp.setString(2, emp1.getNomEmpleado());
            sqlUp.setString(3, emp1.getApeEmpleado());
            sqlUp.setString(4, emp1.getDireEmpleado());
            sqlUp.setString(5, emp1.getCelEmpleado());
            sqlUp.setString(6, emp1.getMailEmpleado());
            sqlUp.setString(7, LocalDate.now().toString());
            sqlUp.setString(8, emp1.getAreaEmpleado());

            sqlUp.executeUpdate();

            System.out.println("La DB/TABLA EMPLEADO se actualizo con exito");
        } catch (SQLException obj) {
            System.out.println("Error en el insert de la tabla Empleado" + obj);
            obj.fillInStackTrace();
        }

    }

    //******************LISTAR SOPORTE
    public static void listarEmpleado() {


        String consulta = "select * from empleado";

        ResultSet sql;
        try {
            sql = sT.executeQuery(consulta);
            System.out.println("campos EMPELADO agregar y dejar bonito");
            while (sql.next()) {

                System.out.println(sql.getInt(1) + "\t" + sql.getString(2) + "\t" + sql.getString(3) +
                        "\t" + sql.getString(4) + sql.getString(5) + "\t" + sql.getString(6) + sql.getString(7) + "\t" + sql.getString(8));

            }

        } catch (SQLException e) {
            System.out.println("Error en el select de la tabla EMPLEADO" + e);
            e.printStackTrace();
        }
    }

    //*********************ALTA TECNICO
    public static void altaTecnicoDB(Tecnico tec1) {

        String consulta = "insert into Tecnico(idTecnico,cuitEmpleado,codSoporte,tituloTecnico,dispoTecnico,altaTecnico,estadoTecnico) values (idTecnico,?,?,?,?,?,?)";


        try {
            PreparedStatement sqlUp = conX.prepareStatement(consulta);

            sqlUp.setString(1, tec1.getCuitEmpleado());
            sqlUp.setString(2, tec1.getCodSoporte());
            sqlUp.setString(3, tec1.getTituloTecnico());
            sqlUp.setString(4, tec1.getDispoTecnico());
            sqlUp.setString(5, LocalDate.now().toString());
            sqlUp.setString(6, tec1.getEstadoTecnico());


            sqlUp.executeUpdate();

            System.out.println("La DB/TABLA TECNICO se actualizo con exito");

        } catch (SQLException obj) {
            System.out.println("Error en el insert de la tabla Tecnico" + obj);
            obj.fillInStackTrace();
        }

    }

    //*********validar cuit empleado
    public static boolean validarCuitEmpleado(String cuitEmp) {
        String consulta = String.format("select * from empleado where cuitEmpleado = %s", cuitEmp);
        ResultSet sql;
        boolean resultado = false;

        try {
            sql = sT.executeQuery(consulta);
            int id = 0;
            String nom = " ", ape = " ";

            while (sql.next()) {

                id = sql.getInt(1);
                nom = sql.getString(3);
                ape = sql.getString(4);
                System.out.println(sql.getInt(1) + "\t" + sql.getString(2) + "\t" + sql.getString(3) + "\t" + sql.getString(4));
                if (sql.getRow() == 0) {
                    System.out.println("El empleado no existe, INGRESE OTRO CUIT");
                    return resultado = false;
                } else return resultado = true;

            }

        } catch (SQLException e) {
            System.out.println("Error en la busqueda de Empleado" + e);
            e.printStackTrace();
        }
        return false;
    }


    //****************ALTA SOPORTE
    public static void altaSoporteDB(SoporteServicio sop1) {
//validad que el cuit no exista
        String consulta = "insert into soporte(idSoporte,codSoporte,tipoSoporte,desSoporte,altaSoporte,tmpRespSoporte,complejidadSoporte,estadoSoporte) values (idSoporte,?,?,?,?,?,?,?)";


        try {
            PreparedStatement sqlUp = conX.prepareStatement(consulta);

            sqlUp.setString(1, sop1.getCodSoporte());
            sqlUp.setString(2, sop1.getTipoSoporte());
            sqlUp.setString(3, sop1.getDesSoporte());
            sqlUp.setString(4, LocalDate.now().toString());
            sqlUp.setString(5, sop1.getTmpRespSoporte());
            sqlUp.setString(6, sop1.getComplejidadSoporte());
            sqlUp.setString(7, sop1.getEstadoSoporte());


            sqlUp.executeUpdate();


            System.out.println("La DB/TABLA SOPORTE se actualizo con exito");

        } catch (SQLException obj) {
            System.out.println("Error en el insert de la tabla SOPORTE" + obj);
            obj.fillInStackTrace();
        }

    }

    //******************LISTAR SOPORTE
    public static void listarSoporte() {


        String consulta = "select * from SOPORTE";

        ResultSet sql;
        try {
            sql = sT.executeQuery(consulta);
            System.out.println("campos soporte agregar y dejar bonito");
            while (sql.next()) {

                System.out.println(sql.getInt(1) + "\t" + sql.getString(2) + "\t" + sql.getString(3) + "\t" + sql.getString(4) + sql.getString(5) + "\t" + sql.getString(6) + sql.getString(7) + "\t" + sql.getString(8));

            }

        } catch (SQLException e) {
            System.out.println("Error en el INSERT de la tabla SOPORTE" + e);
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    //**********************ALTA INCIDENTE
    public static void altaIncidenteDB(Incidente inc1) {


        String consulta = "insert into incidente(idIncidente,idEmpleado,idCliente,idSoporte,idTecnico,altaIncidente,fechaResolucion,horaColchon,estadoIncidente)"
                + " values (idIncidente,?,?,?,?,?,?,?,?)";


        try {
            PreparedStatement sqlUp = conX.prepareStatement(consulta);

            sqlUp.setInt(1, inc1.getIdEmpleado());
            sqlUp.setInt(2, inc1.getIdCliente());
            sqlUp.setInt(3, inc1.getIdSoporte());
            sqlUp.setInt(4, inc1.getIdTecnico());
            sqlUp.setString(5, LocalDate.now().toString());
            sqlUp.setString(6, inc1.getFechaResolucion());
            sqlUp.setString(7, inc1.getHorasColchon());
            sqlUp.setString(8, inc1.getEstadoIncidente());


            sqlUp.executeUpdate();

            System.out.println("La DB/TABLA INCIDENTE se actualizo con exito");

        } catch (SQLException obj) {
            System.out.println("Error en el insert de la tabla Incidente" + obj);
            obj.fillInStackTrace();
        }

    }
}








