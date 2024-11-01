<?xml version = "1.0"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="com.advancia.model.*" %>
<%@ page import="java.util.*" %>
	
	<head>
    <title>Videoteca ADMIN</title>
	</head>
	<body>
		<%  
			User user = (User) request.getSession().getAttribute("user");
			List<Director> listDirector = (List<Director>)request.getAttribute("directors");
		    List<Genre> listGenre = (List<Genre>)request.getAttribute("genres");
		    List<DVD> listDVD = (List<DVD>)request.getAttribute("dvds");
		%>
    	
    	<h1>Accesso alla Videoteca come <%=user.getRole() %></h1>
    	
    	<form action="DashboardServletAdmin" method="post">
	        <table border="1" cellspacing="10" cellpadding="10" style="border-color: black;">
		        <tr>
		            <td>
		                <h3>Seleziona un genere</h3>
		                <% for (Genre i : listGenre) { %>
		                    <p>
		                        <label>
		                            <input type="radio" name="id_genre" value="<%= i.getId() %>"> <%= i.getName() %>
		                        </label>
		                    </p>
		                <% } %>
		            </td>
		            <td>
		                <h3>Seleziona un regista</h3>
		                <% for (Director d : listDirector) { %>
		                    <p>
		                        <label>
		                            <input type="radio" name="id_director" value="<%= d.getId() %>"> <%= d.getName() + " " + d.getLastname() %>
		                        </label>
		                    </p>
		                <% } %>
		            </td>             
		        </tr>
		    </table>
	        <p>
	         	<label for="DVDName">Nome del Film</label> 
	         	<input type="text" id="DVDName" name="DVDName" required>
				<input type="hidden" name="actions" value="addDVD"> 
				<input type="submit" value="Aggiungi DVD">
	        </p>
    	</form>

    	<h2>Catalogo Videoteca</h2>
	    <table border="1">
	        <tr>
	            <th>Titolo</th>
	            <th>Regista</th>
	            <th>Genere</th>
	            <th>Data di noleggio</th>
	            <th>Utente</th>
	        </tr>	
        	<tbody>	
		    <%  Set<Long> set = new HashSet<>();  // Set per evitare duplicati
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
						<td> <% List<User> dvdUsers = d.getUsers();
       								if (dvdUsers != null && !dvdUsers.isEmpty()) {%>
       								<%= dvdUsers.get(0).getUsername() %>
           							<%}else{%>
           								DISPONIBILE
        							<%}%>
        					</td>
						<td> <% if(d.getRentalDate() == null) {%>
							<form action="DashboardServletAdmin" method="post">
								<input type="hidden" name="actions" value="rentDVD">
								<input type="hidden" name="id_dvd" value="<%=d.getId()%>">
								<input type="submit" value="Noleggia">
							</form>
							<% } %>
						</td>
						<td>
							<form action="DashboardServletAdmin" method="post">
								<input type="hidden" name="actions" value="deleteDVD">
								<input type="hidden" name="id_dvd" value="<%=d.getId()%>">
								<input type="submit" value="Cancella">
							</form>
						</td>
					</tr>
					<% }} %>
				</tbody>
    	</table>
	</body>
</html>