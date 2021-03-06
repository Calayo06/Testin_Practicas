package Controlador;

//Controlador.java

import Modelo.Empleado;
import Modelo.EmpleadoDAO;
import Modelo.Venta;
import Modelo.VentaDAO;
import config.GenerarSerie;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Calayo13
 * Guillermo alosilla edito clase controlador 
 */
public class Controlador extends HttpServlet {

    Empleado em = new Empleado();
    EmpleadoDAO edao=new EmpleadoDAO();
    Cliente c = new Cliente();
    ClienteDAO cdao = new ClienteDAO();
    Producto p = new Producto();
    ProductoDAO pdao = new ProductoDAO(); 
     
    int ide;
    int idc;
    int idp;
     
    Venta v = new Venta();
    List<Venta>lista = new ArrayList<>();
    int item;
    int cod;
    String descripcion;
    double precio;
    int cant;
    double subtotal;
    double totalPagar;
    
    String numeroserie;
    VentaDAO vdao=new VentaDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu=request.getParameter("menu");
        String accion=request.getParameter("accion");
        if(menu.equals("Principal")){
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }
        if(menu.equals("Empleado")){
            switch (accion){
                case "Listar":
                    List lista=edao.listar();
                    request.setAttribute("empleados", lista);
                    break;
                    
                case "Agregar":
                    String dni=request.getParameter("txtDni");
                    String nom=request.getParameter("txtNombre");
                    String tel=request.getParameter("txtTel");
                    String est=request.getParameter("txtEstado");
                    String user=request.getParameter("txtUser");
                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);
                    edao.agregar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                    
                case "Editar":
                    ide= Integer.parseInt(request.getParameter("id"));
                    Empleado e = edao.listarId(ide);
                    request.setAttribute("empleado",e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                    
                case "Actualizar":
                    String dni1=request.getParameter("txtDni");
                    String nom1=request.getParameter("txtNombre");
                    String tel1=request.getParameter("txtTel");
                    String est1=request.getParameter("txtEstado");
                    String user1=request.getParameter("txtUser");
                    em.setDni(dni1);
                    em.setNom(nom1);
                    em.setTel(tel1);
                    em.setEstado(est1);
                    em.setUser(user1);
                    em.setId(ide);
                    edao.actualizar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                    
                case "Delete":
                    ide=Integer.parseInt(request.getParameter("id"));
                    edao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);
                    break;
                
                default:
                    throw new AssertionError();
            }
            
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }
        if(menu.equals("Cliente")){
            request.getRequestDispatcher("Clientes.jsp").forward(request, response);
        }
        if(menu.equals("Producto")){
            request.getRequestDispatcher("Producto.jsp").forward(request, response);
        }
        if(menu.equals("NuevaVenta")){
            switch(accion){
                case "BuscarCliente":
                    String dni= request.getParameter("Codigocliente");
                    c.setDni(dni);
                    c = cdao.buscar(dni);
                    request.setAttribute("c", c);
                    break;
                case "BuscarProducto":
                    int id = Integer.parseInt(request.getParameter("codigoproducto"));
                    p=pdao.listarId(id);
                    request.setAttribute("c", c);
                    request.setAttribute("producto", p);
                    request.setAttribute("lista", lista);
                    request.setAttribute("totalpagar", totalPagar);
                    break;
                case "Agregar":
                    request.setAttribute("c", c);
                    totalPagar =0.0;
                    item = item + 1;
                    cod=p.getId();
                    descripcion=request.getParameter("nomproducto");
                    precio=Double.parseDouble(request.getParameter("precio"));
                    cant=Integer.parseInt(request.getParameter("cant"));
                    subtotal=precio*cant;
                    v = new Venta();
                    v.setItem(item);
                    v.setId(cod);
                    v.setDescripcionP(descripcion);
                    v.setPrecio(precio);
                    v.setCantidad(cant);
                    v.setSubtotal(subtotal);
                    lista.add(v);
                    for (int i=0; i< lista.size(); i++){
                        totalPagar=totalPagar +lista.get(i).getSubtotal();	
                    }
                    request.setAttribute("totalpagar", totalPagar);
                    request.setAttribute("lista", lista);
                    break;

                default:
                    numeroserie = vdao.GenerarSerie();
                    if(numeroserie==null){
                        numeroserie="00000001";
                        request.setAttribute("nserie", numeroserie);
                    }
                    else{
                        int incrementar = Integer.parseInt(numeroserie);
                        GenerarSerie gs=new GenerarSerie();
                        numeroserie=gs.NumeroSerie(incrementar);
                        request.setAttribute("nserie", numeroserie);
                        
                    }
                    request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
                    
            }
            request.getRequestDispatcher("RegistrarVenta.jsp").forward(request, response);
        }
          
        }

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/


    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/

    /*@Override
    public String getServletInfo() {
        return "Short description";
    }*/// </editor-fold>

}
