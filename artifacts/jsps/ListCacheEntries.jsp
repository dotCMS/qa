<%@page import="com.dotmarketing.business.*"%>
<%@page import="java.util.*"%>

<%
	DotCacheAdministrator cacheAdmin = CacheLocator.getCacheAdministrator();
	String cacheImpName = cacheAdmin.getImplementationClass().getName();
	String cacheName = request.getParameter("cache");
	Set<String> keys = cacheAdmin.getKeys(cacheName);
%>



<html>
	<body>
		Cache Implementation class: <%=cacheImpName%> <br/>
		<h2>Cache Entries For <%=cacheName%>:</h2>

		<div class="cacheEntryList">
			<table class="listingTable">
				<thead>
					<tr>
						<th>Key</th>
						<th>Value</th>
					</tr>
				</thead>
				<tbody>
				<%
				Iterator<String> iter=keys.iterator();
				while(iter.hasNext()) {
					String key = iter.next();
					out.println("<tr>");

					out.println("<td>");
					out.println(key);
					out.println("</td>");

					out.println("<td>");
					out.println(cacheAdmin.get(key, cacheName));
					out.println("</td>");

					out.println("</tr>");
				}
				%>
				</tbody>
			</table>
		</div>

	</body>
</html>
