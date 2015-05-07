<%@page import="com.dotmarketing.business.*"%>
<%@page import="java.util.*"%>

<%
	DotCacheAdministrator cacheAdmin = CacheLocator.getCacheAdministrator();
	String cacheImpName = cacheAdmin.getImplementationClass().getName();
	List<Map<String, Object>> cacheStats = cacheAdmin.getCacheStatsList();
%>



<html>
	<body>
		<b>Cache Implementation Class:</b> <%=cacheImpName%> <br/>
		<h2>Caches</h2>

		<div class="cacheList">
			<table class="listingTable">
				<thead>
					<tr>
						<th>Region</th>
						<th>In Memory</th>
					</tr>
				</thead>
				<tbody>
				<%
				for(int i=0; i<cacheStats.size(); i++) {
					out.println("<tr>");

					out.println("<td>");
					out.println("<a href=\"ListCacheEntries.jsp?cache=" + cacheStats.get(i).get("region") + "\">" + cacheStats.get(i).get("region") + "</a>");
					out.println("</td>");

					out.println("<td>");
					out.println(cacheStats.get(i).get("memory"));
					out.println("</td>");

					out.println("</tr>");
				}
				%>
				</tbody>
			</table>
		</div>

	</body>
</html>
