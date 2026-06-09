package servlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Impl.StudentServiceImpl;
import service.Impl.UserServiceImpl;

import model.Student;
import model.User;

public class ServletRegistersStudent extends HttpServlet {

    private UserServiceImpl userServiceImpl = new UserServiceImpl();
    private StudentServiceImpl studentServiceImpl = new StudentServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    // Utility method to check for null or empty strings
    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // ================= 1. Get common required info for all users =================
        String stuNum = request.getParameter("stuNum"); // Account (Student ID / Teacher ID)
        String stuName = request.getParameter("stuName"); // Name
        String pwd = request.getParameter("password"); // Password
        String phone = request.getParameter("phone"); // Phone number
        String roleParam = request.getParameter("role"); // Role
        
        // Validate basic common information
        if (isBlank(stuNum) || isBlank(stuName) || isBlank(pwd) || isBlank(phone) || isBlank(roleParam)) {
            request.setAttribute("msg", "Error: Basic info (Account, Name, Password, Phone, Role) cannot be empty!");
            request.getRequestDispatcher("register/register.jsp").forward(request, response);
            return;
        }
        
        int roleID = 0;
        try {
            roleID = Integer.parseInt(roleParam.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Error: Role format incorrect!");
            request.getRequestDispatcher("register/register.jsp").forward(request, response);
            return;
        }

        // ================= 2. Get specific detailed information =================
        String stuSex = request.getParameter("stuSex");
        String stuClass = request.getParameter("stuClass");
        String major = request.getParameter("major");
        String department = request.getParameter("department");
        
        int stuAge = 0;
        String ageStr = request.getParameter("stuAge");
        if (!isBlank(ageStr)) {
            try {
                stuAge = Integer.parseInt(ageStr.trim());
            } catch (Exception e) {}
        }

        // ================= 3. Unified account creation: Write to User table =================
        // All roles must have a record in the User table to log in
        User user = new User(stuNum, stuName, pwd, phone, roleID);
        int rsUser = userServiceImpl.addUser(user);
        
        boolean isDetailSuccess = false; 

        // ================= 4. Role routing: Write to specific profile tables =================
        if (roleID == 0) {
            // [Student Channel]: Strictly validate student-specific fields
            if (isBlank(stuClass) || isBlank(major) || isBlank(department)) {
                request.setAttribute("msg", "Error: Students must fill Class, Major, and Department!");
                request.getRequestDispatcher("register/register.jsp").forward(request, response);
                return;
            }
            Student student = new Student(stuNum, stuName, stuSex, stuAge, stuClass, major, department);
            int rsStu = studentServiceImpl.addStu(student);
            isDetailSuccess = (rsStu > 0);
            
        } else if (roleID == 1) {
            // [Teacher Channel]: 
            // To prevent compilation crashes if TeacherServiceImpl is not yet implemented,
            // we bypass detail insertion here. Teachers can log in as long as they have a User record.
            isDetailSuccess = true; 
            
        } else if (roleID == 2) {
            // [Admin Channel]: 
            // There is no separate admin table. Admins only need a User record. Bypass detail insertion.
            isDetailSuccess = true;
        }

        // ================= 5. Determine final result and redirect =================
        if (rsUser > 0 && isDetailSuccess) {
            request.setAttribute("msg", "Register Success! Please login.");
            request.getRequestDispatcher("login/login.jsp").forward(request, response);
            return; 
        } else {
            request.setAttribute("msg", "Register Failed! Account may already exist.");
            request.getRequestDispatcher("register/register.jsp").forward(request, response);
            return;
        }
    }
}