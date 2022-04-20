<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<!-- c:out ; c:forEach ; c:if -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (like dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Book Club Project</title>
    <!-- Bootstrap -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous"
    />
  </head>
  <body>
    <div class="container">
      <!-- Beginning of Container -->
		
		<a href="logout" class="btn btn-danger mt-2 float-end">Logout</a>
	
		<h1>Welcome, <span class="text-info"><c:out value="${loggedUser.firstName}"></c:out></span>!</h1>
		<p>Books from everyone's shelves:</p>
		<%-- <p class="text-danger"><c:out value="${user"></c:out></p>  --%>
		
		<a href="/newBook" class="btn btn-warning mt-2 float-end">Create New Book</a>
		
		<table class ="table table-blue table-striped table-hover">
    	<thead>
    		<tr>
    			<th class"align-middle">ID</th>
    			<th class"align-middle">Title</th>
    			<th class"align-middle">Author Name</th>
    			
    			<th class"align-middle">Posted By</th>
    			<th class"align-middle">Action</th>
    		</tr>
    	</thead>
    			<tbody>
    			<c:forEach var="i" items="${books}">
    			<tr>
    				<td> <c:out value="${i.id}"></c:out> </td>
    				<td>
    					<a href="/oneBook/${i.id }">
    				<c:out value="${i.title}"></c:out> 
    				</a> 
    				</td>
    				
    				<td> <c:out value="${i.author}"></c:out> </td>
    			
    				<td> 
    				<a href="oneUser/${i.id }">
    				<c:out value="${i.user.firstName }"></c:out>
    				</a>
    				</td>
    				
    				
    				<td>
    				<a class="btn btn-primary" href="/editBook/${i.id}"> Update Book</a>
    				<a class="btn btn-danger" href="/delete/${i.id}">Delete</a>
    			</tr>
    			</c:forEach>
    			</tbody>
    	
    	
    	</table>
		

   </div> <!-- End of Container -->
  </body>
</html>