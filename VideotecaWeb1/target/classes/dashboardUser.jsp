<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.advancia.model.*" %>
<%@ page import="java.util.*" %>
	
	<head>
    <title>Videoteca User</title>
	</head>
	<body>
		<%  
			User user = (User) request.getSession().getAttribute("user");
			List<Director> listDirector = (List<Director>)request.getAttribute("directors");
		    List<Genre> listGenre = (List<Genre>)request.getAttribute("genres");
		    List<DVD> listAvailableDVDs = (List<DVD>)request.getAttribute("availableDVDs");
		%>
    	
    	<h1>Catalogo Videoteca di <%=user.getUsername() %></h1>
    	
    	<form action="DashboardServletUser" method="post">   
	    <table border="1">
	        <tr>
	            <th>Titolo</th>
	            <th>Regista</th>
	            <th>Genere</th>
	            <th>Data di Noleggio</th>
	        </tr>	
        	<tbody>
			<% 	List<DVD> listDVD = user.getListDVD();
		    	Set<Long> set = new HashSet<>();  // Set per evitare duplicati
		    	for (DVD d : listDVD) { 
		        	if (set.add(d.getId())) {  %>
					<tr>
						<td><%=d.getName()%></td>
						<td> <% Director dd = d.getDirector();
								if (dd != null) { %>
								<%=dd.getName() + " " + dd.getLastname()%>
 							<% } %>
						</td>
						<td> <% Genre gd = d.getGenre();
								if (gd != null) { %>
								<%=gd.getName()%>
 							<% } %>
						</td>
						<td> <%= d.getRentalDate() %></td>
						</tr>
					<% }} %>
				</tbody>
    	</table>
						
		<h2>DVD disponibili</h2>
	    <table border="1">
	        <tr>
	            <th>Titolo</th>
	            <th>Regista</th>
	            <th>Genere</th>
	            <th>Stato</th>
	        </tr>	
        	<tbody>	
		    <%  Set<Long> set1 = new HashSet<>();  // Set per evitare duplicati
		    	for (DVD d : listAvailableDVDs) { 
		        	if (set1.add(d.getId())) {  %>
					<tr>
						<td><%=d.getName()%></td>
						<td> <% Director dd = d.getDirector();
								if (dd != null) { %>
								<%=dd.getName() + " " + dd.getLastname()%>
 							<% } %>
						</td>
						<td> <% Genre gd = d.getGenre();
								if (gd != null) { %>
								<%=gd.getName()%>
 							<% } %>
						</td>
						<td>DISPONIBILE</td>
						<td>
							<form action="DashboardServletUser" method="post">
								<input type="hidden" name="actions" value="rentDVD">
								<input type="hidden" name="id_dvd" value="<%=d.getId()%>">
								<input type="submit" value="Noleggia">
							</form>
						</td>
					</tr>
					<% }} %>
				</tbody>
    	</table>
	</body>
</html>