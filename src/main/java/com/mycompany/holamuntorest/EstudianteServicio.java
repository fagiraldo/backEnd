package com.mycompany.holamuntorest;

import com.mycompany.jpa.Student;
import edu.sergioArboleda.exception.ConexionException;
import edu.sergioArboleda.facade.StudentFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.o7planning.restfulcrud.model.StudentDTO;

/**
 *
 * @author fabian.giraldo
 */
@Path("/estudiante")
public class EstudianteServicio {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<StudentDTO> getStudent_JSON() throws ConexionException {
        List<StudentDTO> studentsDTO = new ArrayList<StudentDTO>();
        StudentFacade facade = null;

        facade = new StudentFacade();
        List<Student> studentes = facade.findAll();
        for (Student studente : studentes) {
            StudentDTO dto = new StudentDTO();
            dto.setId(studente.getId());
            dto.setName(studente.getName());
            dto.setAge(studente.getAge());
            studentsDTO.add(dto);
        }

        return studentsDTO;
    }

    // URI:
    // /contextPath/servletPath/employees/{empNo}
    @GET
    @Path("/{empNo}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getEmployee(@PathParam("id") String id) throws ConexionException {
        StudentFacade facade = null;
        StudentDTO dto = null;

        facade = new StudentFacade();
        Student studente = facade.get(Integer.parseInt(id));
        
        if(studente != null){
            dto = new StudentDTO();
            dto.setId(studente.getId());
            dto.setName(studente.getName());
            dto.setAge(studente.getAge());
            
            return Response.status(Response.Status.CREATED)
                .entity(dto)
                .build();
        } 
        else{
          return Response.status(Response.Status.NOT_FOUND)
                 .build();
        }
        
    }

    // URI:
    // /contextPath/servletPath/employees
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addStudent(StudentDTO student) throws ConexionException {
        StudentFacade facade = null;
        facade = new StudentFacade();
        Student studentJPA = new Student();
        studentJPA.setId(student.getId());
        studentJPA.setName(student.getName());
        studentJPA.setAge(student.getAge());
        facade.save(studentJPA);

        return Response.status(Response.Status.CREATED)
                .build();
    }

    // URI:
    // /contextPath/servletPath/employees
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateEmployee(StudentDTO student) throws ConexionException {
        StudentFacade facade = null;
     
        facade = new StudentFacade();
        Student studentJPA = new Student();
        studentJPA.setId(student.getId());
        studentJPA.setName(student.getName());
        studentJPA.setAge(student.getAge());
        facade.update(studentJPA);

        
        return Response.status(Response.Status.CREATED)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteEmployee(@PathParam("id") String id) throws ConexionException {
        StudentFacade facade = null;

        facade = new StudentFacade();
        Student student = facade.get(Integer.parseInt(id));
        facade.delete(student);
        return Response.status(Response.Status.CREATED)
                .build();
    }
}
