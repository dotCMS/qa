
<%@page import="com.dotcms.repackage.org.apache.commons.httpclient.NameValuePair"%>
<%@page import="com.dotcms.repackage.org.apache.commons.httpclient.methods.PostMethod"%>
<%@page import="com.dotcms.repackage.org.apache.commons.httpclient.HttpClient"%>
<%@page import="org.mockito.stubbing.Answer"%>
<%@page import="org.mockito.Mockito"%>
<%@page import="com.dotcms.enterprise.LicenseUtil"%>
<%

HttpServletRequest req=Mockito.mock(HttpServletRequest.class);
Mockito.when(req.getParameter("iwantTo")).thenReturn("request_code");
Mockito.when(req.getParameter("license_type")).thenReturn("trial");
Mockito.when(req.getParameter("license_level")).thenReturn("400");

final StringBuilder reqcode=new StringBuilder();

Mockito.doAnswer(new Answer() {
    public Object answer(org.mockito.invocation.InvocationOnMock invocation) throws Throwable {
        reqcode.append(invocation.getArguments()[1].toString());
        return null;
    }
}).when(req).setAttribute(Mockito.eq("requestCode"),Mockito.any(String.class));

LicenseUtil.processForm(req);

HttpClient client=new HttpClient();
PostMethod post=new PostMethod("https://my.dotcms.com/app/licenseRequest3");
post.setRequestBody(new NameValuePair[] { new NameValuePair("code", reqcode.toString()) });
client.executeMethod(post);

%>

Request code: <%=reqcode.toString()%>

<% if(post.getStatusCode()==200) { %>
       <% String license = post.getResponseBodyAsString(); %>
       Success. License data: <%= license %>
       <%  
       HttpServletRequest req2=Mockito.mock(HttpServletRequest.class);
       Mockito.when(req2.getParameter("iwantTo")).thenReturn("paste_license");
       Mockito.when(req2.getParameter("license_text")).thenReturn(license);
       LicenseUtil.processForm(req2);
       
       %>
<% } else { %>
       Request failed!
<% } %>